package com.vsp.binarysize;



public class Tester {	
	
	public Tester() {
	}

	public static void main(String[] args) {
		//Payload p = new Payload();
		//System.out.println( "Object Size :" + instrumentation.getObjectSize(p) );
		System.out.println(ObjectSizeFetcher.getObjectSize(new Payload()));
	}

}
