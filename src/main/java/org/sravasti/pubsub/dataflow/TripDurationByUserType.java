package org.sravasti.pubsub.dataflow;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class TripDurationByUserType extends DoFn<CitiBike, KV<String, Long>> {
	
	@ProcessElement
	public void processElement(ProcessContext c) {
			//System.out.println("Begin TripDurationByUserType.processElement");
			c.output(KV.of(c.element().getUserType(), new Long(c.element().getTripduration())));
			//System.out.println("End TripDurationByUserType.processElement");
	}

}
