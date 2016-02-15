package com.oracle.cloud.speedtest.util;

import java.util.List;

public class MathUtil {

	public static Long average(List<Long> list) {
		if(list == null || list.size() == 0){
			return 0L;
		}
		Long sum = 0L;
		int count = 0;
		for(Long value : list){
			sum += value;
			count++;
		}
		return sum/count;
	}

}
