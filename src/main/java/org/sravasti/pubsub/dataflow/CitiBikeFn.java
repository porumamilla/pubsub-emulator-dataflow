package org.sravasti.pubsub.dataflow;

import org.apache.beam.sdk.transforms.DoFn;

public class CitiBikeFn extends DoFn<String, CitiBike> {
	@ProcessElement
	public void processElement(ProcessContext c) {
		
			//System.out.println("Entered in process element");
			//System.out.println("Timestamp == " + c.timestamp());
			String line = c.element();
			CitiBike citiBike = getCitiBike(line);
			c.output(citiBike);
			//System.out.println(citiBike.getUserType() + ": " + citiBike.getTripduration());
			//System.out.println("Exited process element");
	}
	
	private CitiBike getCitiBike(String line) {
		CitiBike citiBike = new CitiBike();
		String[] tokens = line.split(",");
		citiBike.setTripduration(tokens[0]);
		citiBike.setUserType(tokens[12].replaceAll("\"", ""));
		return citiBike;
	}
	
	/*public static void main(String[] args) {
		String line = "695,\"2013-06-01 00:00:01\",\"2013-06-01 00:11:36\",444,\"Broadway & W 24 St\",40.7423543,-73.98915076,434,\"9 Ave & W 18 St\",40.74317449,-74.00366443,19678,\"Subscriber\",1983,1";
		CitiBike citiBike = new CitiBikeFn().getCitiBike(line);
		System.out.println(citiBike.getUserType() + ": " + citiBike.getTripduration());
	}*/
}
