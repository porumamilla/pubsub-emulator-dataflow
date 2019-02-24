package org.sravasti.pubsub.dataflow;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Sum;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

public class BatchPipeline {
	public static void main(String[] args) {
		Pipeline pipeline = Pipeline.create();

		PCollection<String> csvlines = pipeline
				.apply(TextIO.read().from("file:////Users/raghu/Downloads/201306-citibike-tripdata1.csv"));

		PCollection<CitiBike> citiBikeStructData = csvlines.apply(ParDo.of(new CitiBikeFn()));
		PCollection<KV<String, Long>> tripDurationByUserType = citiBikeStructData
				.apply(ParDo.of(new TripDurationByUserType()));

		PCollection<KV<String, Long>> totalTripDurationByUserType = tripDurationByUserType
				.apply(Sum.<String>longsPerKey());

		//System.out.println("After totalTripDurationByUserType");
		PCollection<String> output = totalTripDurationByUserType.apply(ParDo.of(new DoFn<KV<String, Long>, String>() {
			private static final long serialVersionUID = 1L;

			@ProcessElement
			public void processElement(ProcessContext c) {
				//System.out.println("Entered in process element2");
				c.output("\"" + c.element().getKey() + "\"," + c.element().getValue());
				//System.out.println("\"" + c.element().getKey() + "\"," + c.element().getValue());
				//System.out.println("Exited process element2");
			}
		}));

		output.apply("total_trip_durations_by_user_type", TextIO.write()
				.to("total_trip_durations_by_user_type"));
		pipeline.run().waitUntilFinish();
	}
}
