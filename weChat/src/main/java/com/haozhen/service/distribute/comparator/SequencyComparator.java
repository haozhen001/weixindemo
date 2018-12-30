package com.haozhen.service.distribute.comparator;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class SequencyComparator implements Comparator<AtomicInteger> {

	@Override
	public int compare(AtomicInteger o1, AtomicInteger o2) {
		return o1.intValue()>o2.intValue()?1:-1;
	}

	
}
