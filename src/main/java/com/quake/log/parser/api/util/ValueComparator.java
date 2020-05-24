package com.quake.log.parser.api.util;

import java.util.Map;

public class ValueComparator implements java.util.Comparator {
	private Map<String, Object> m = null; // the original map 

	public ValueComparator(Map m) {
		this.m = m;
	}

	public int compare(Object o1, Object o2) {
		// handle some exceptions here 
		String v1 = (String) m.get(o1);
		String v2 = (String) m.get(o2);
		// make sure the values implement Comparable
		
		return v1.compareTo(v2);
	}
	// do something similar in equals. 

} 