package org.sravasti.pubsub.dataflow;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;

public interface CitiBikePubSubOptions extends DataflowPipelineOptions {
	@Description("Path of the file to read from")
    @Default.String("projects/springmlproject/topics/test")
	String getPubsubTopic();	
	void setPubsubTopic(String pubsubTopic);

}
