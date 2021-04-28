package com.cartrawler.assesment.car;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.CarResult.FuelPolicy;
import com.cartrawler.assessment.car.filter.CarResultFilterRemoveFullFullAboveMedianPrice;

public class CarResultFilterTest {

	@Test
	public void testEvenNumberOfResults() {
		List<CarResult> dataList = new ArrayList<CarResult>();
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.01, FuelPolicy.FULLFULL));
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.02, FuelPolicy.FULLFULL));
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.03, FuelPolicy.FULLFULL));
		CarResultFilterRemoveFullFullAboveMedianPrice filter = new CarResultFilterRemoveFullFullAboveMedianPrice();
		filter.filter(dataList);
		assertEquals(2, dataList.size());
	}
	
	@Test
	public void testOddNumberOfResults() {
		List<CarResult> dataList = new ArrayList<CarResult>();
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.01, FuelPolicy.FULLFULL));
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.02, FuelPolicy.FULLFULL));
		CarResultFilterRemoveFullFullAboveMedianPrice filter = new CarResultFilterRemoveFullFullAboveMedianPrice();
		filter.filter(dataList);
		assertEquals(1, dataList.size());
	}
	
	@Test
	public void testFilterOnFullEmpty() {
		List<CarResult> dataList = new ArrayList<CarResult>();
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.01, FuelPolicy.FULLFULL));
		dataList.add(new CarResult("Peugeot 107", "AVIS", "CDMR", 0.02, FuelPolicy.FULLEMPTY));
		CarResultFilterRemoveFullFullAboveMedianPrice filter = new CarResultFilterRemoveFullFullAboveMedianPrice();
		filter.filter(dataList);
		assertEquals(2, dataList.size());
	}
}
