package com.oracle.cloud.speedtest.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MathUtilTest {

	@Test
	public void average() throws Exception {
		// setup
		List<Long> items = Arrays.asList(3L, 4L, 8L);

		// execute
		Long average = MathUtil.average(items);

		// assert
		assertEquals(5L, average.longValue());
	}
}
