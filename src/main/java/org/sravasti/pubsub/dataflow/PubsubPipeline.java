package org.sravasti.pubsub.dataflow;

import java.util.concurrent.TimeUnit;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Sum;
import org.apache.beam.sdk.transforms.windowing.AfterFirst;
import org.apache.beam.sdk.transforms.windowing.AfterPane;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.AfterWatermark;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Repeatedly;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;

public class PubsubPipeline {

	public static void main(String[] args) throws Exception {
		CitiBikePubSubOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
				.as(CitiBikePubSubOptions.class);
		options.setPubsubRootUrl("http://localhost:8085");
		options.as(DataflowPipelineOptions.class).setStreaming(true);

		Pipeline p = Pipeline.create(options);

		/*Window window1 = Window.into(FixedWindows.of(Duration.standardMinutes(new Integer(2))))
				.triggering(Repeatedly.forever(AfterWatermark.pastEndOfWindow()))
                .withAllowedLateness(Duration.ZERO)
                .discardingFiredPanes();*/
		Window window1 = Window.into(FixedWindows.of(Duration.standardMinutes(new Integer(2))))
				.triggering(AfterWatermark.pastEndOfWindow()
				.withEarlyFirings(AfterProcessingTime
				.pastFirstElementInPane()
				.plusDelayOf(Duration.standardMinutes(1)))
				.withLateFirings(AfterPane.elementCountAtLeast(1)))
				.withAllowedLateness(Duration.standardSeconds(10)).discardingFiredPanes();
		

		
		Window window2 = Window.<String>into(FixedWindows.of(Duration.standardMinutes(new Integer(2))))
				.triggering(AfterProcessingTime.pastFirstElementInPane()
                .plusDelayOf(Duration.standardMinutes(1)))
				.withAllowedLateness(Duration.standardSeconds(10))
				.discardingFiredPanes();
		
		PCollection<String> citiBikeCsvLines = p.apply(PubsubIO.readStrings().fromTopic(options.getPubsubTopic()))
				.apply(window1);

		PCollection<CitiBike> citiBikeStructData = citiBikeCsvLines.apply(ParDo.of(new CitiBikeFn()));
		PCollection<KV<String, Long>> tripDurationByUserType = citiBikeStructData
				.apply(ParDo.of(new TripDurationByUserType()));
		
		PCollection<KV<String, Long>> totalTripDurationByUserType = tripDurationByUserType
				.apply(Sum.<String>longsPerKey());
		
		System.out.println("After totalTripDurationByUserType");
		PCollection<String> output = totalTripDurationByUserType.apply(ParDo.of(new DoFn<KV<String, Long>, String>() {
			private static final long serialVersionUID = 1L;

			@ProcessElement
			public void processElement(ProcessContext c) {
				System.out.println("Entered in process element2");
				c.output("\"" + c.element().getKey() + "\"," + c.element().getValue() + "\"");
				System.out.println("\"" + c.element().getKey() + "\"," + c.element().getValue());
				System.out.println("Exited process element2");
			}
		}));
	
		output.apply("total_trip_durations_by_user_type", TextIO.write().to("total_trip_durations_by_user_type")
	            .withWindowedWrites().withNumShards(1));
		p.run().waitUntilFinish();
	}
}
