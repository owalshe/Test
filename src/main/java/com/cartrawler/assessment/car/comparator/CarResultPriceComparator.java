package com.cartrawler.assessment.car.comparator;

import java.util.Comparator;

import com.cartrawler.assessment.car.CarResult;

public class CarResultPriceComparator implements Comparator<CarResult> {

	@Override
	public int compare(CarResult arg0, CarResult arg1) {
		if(arg0.getRentalCost() > arg1.getRentalCost()) {
			return 1;
		} else if(arg0.getRentalCost() < arg1.getRentalCost()) {
			return -1;
		}
		return 0;
	}

}
