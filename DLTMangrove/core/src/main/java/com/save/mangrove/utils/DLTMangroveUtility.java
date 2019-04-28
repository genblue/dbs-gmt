package com.save.mangrove.utils;

import org.springframework.stereotype.Component;

@Component
public class DLTMangroveUtility {

	public static Object isNullOrBlank(Object obj) {
		if (obj==null) {
			obj="null";
		}
		return obj;
	}
	
	
}
