package com.quake.log.parser.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ParserUtil {

	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	    if( c != null && string != null ) {
	        try {
	            return Enum.valueOf(c, string.trim().toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        }
	    }
	    return null;
	}
	
	public static <T> List<List<T>> splitBySeparator(List<T> list, Predicate<? super T> predicate) {
	    final List<List<T>> finalList = new ArrayList<>();
	    int fromIndex = 0;
	    int toIndex = 0;
	    for(T elem : list) {
	        if(predicate.test(elem)) {
	            finalList.add(list.subList(fromIndex, toIndex));
	            fromIndex = toIndex + 1;
	        }
	        toIndex++;
	    }
	    if(fromIndex != toIndex) {
	        finalList.add(list.subList(fromIndex, toIndex));
	    }
	    return finalList;
	}
}
