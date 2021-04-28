package com.cartrawler.assessment.car;

import java.util.Comparator;

import com.cartrawler.assessment.car.comparator.CarResultGroupComparator;

public class CarResultGroupFactory {

	public CarResultGroup createCarResultGroup(CarResult carResult) {
		return new CarResultGroup(CorporateSupplier.isCorporate(carResult.getSupplierName()), 
				SippGroup.parse(carResult.getSippCode().substring(0,1)));
	}

	public Comparator<CarResultGroup> getComparator() {
		return new CarResultGroupComparator();
	}
}
