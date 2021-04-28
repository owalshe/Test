package com.cartrawler.assessment.car.comparator;

import java.util.Comparator;

import com.cartrawler.assessment.car.CarResultGroup;

public class CarResultGroupComparator implements Comparator<CarResultGroup>{
	
	@Override
	public int compare(CarResultGroup arg0, CarResultGroup arg1) {
		int isCorporate = isCorporate(arg0, arg1);
		if(isCorporate == 0) {
			return compareSippGroup(arg0, arg1);
		}
		return isCorporate;
	}
	
	private int isCorporate(CarResultGroup arg0, CarResultGroup arg1) {
		if(arg0.isCorporate() && arg1.isCorporate()) {
			return 0;
		} else if(arg0.isCorporate()) {
			return -1;
		} else if(arg1.isCorporate()) {
			return 1;
		}
		return 0;
	}

	private int compareSippGroup(CarResultGroup arg0, CarResultGroup arg1) {
		return arg0.getSippGroup().compareTo(arg1.getSippGroup());
	}
}
