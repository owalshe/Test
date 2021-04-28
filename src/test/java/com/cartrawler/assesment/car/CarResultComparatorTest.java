package com.cartrawler.assesment.car;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.CarResult.FuelPolicy;
import com.cartrawler.assessment.car.comparator.CarResultComparator;

public class CarResultComparatorTest {

	@Test
	public void testCompareCorporate() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", StringUtils.EMPTY, 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "DELPASO", StringUtils.EMPTY, 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupMiniVsEconomy() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "MCMR", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "EDMN", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupEconomyVsCompact() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "EDMN", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "CDMR", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupMiniVsCompact() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "MCMR", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "CDMR", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupMiniVsOther() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "MCMR", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "ICAV", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupEconomyVsOther() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "EDMN", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "ICAV", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testCompareSippGroupCompactVsOther() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "CDMR", 0.0, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "ICAV", 0.0, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	@Test
	public void testComparePrice() {
		CarResult ref = new CarResult(StringUtils.EMPTY, "AVIS", "MCMR", 0.02, FuelPolicy.FULLFULL);
		CarResult other = new CarResult(StringUtils.EMPTY, "AVIS", "MCMR", 0.01, FuelPolicy.FULLFULL);
		testCompare(ref, other);
	}
	
	private void testCompare(CarResult ref, CarResult other) {
		List<CarResult> dataList = new ArrayList<CarResult>();
		dataList.add(other);
		dataList.add(ref);
		Collections.sort(dataList, new CarResultComparator());
		assertEquals(ref, dataList.get(0));
	}
}
