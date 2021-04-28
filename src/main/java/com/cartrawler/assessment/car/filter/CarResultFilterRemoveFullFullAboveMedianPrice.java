package com.cartrawler.assessment.car.filter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.CarResult.FuelPolicy;
import com.cartrawler.assessment.car.comparator.CarResultPriceComparator;

public class CarResultFilterRemoveFullFullAboveMedianPrice implements CarResultFilter {

	@Override
	public void filter(List<CarResult> carResults) {
		List<CarResult> subList = getListOfResultsAboveMedianPrice(carResults);
		List<CarResult> filteredSubList = subList.stream().filter(c->c.getFuelPolicy() == FuelPolicy.FULLFULL).collect(Collectors.toList());
		for(CarResult carResult : filteredSubList) {
			carResults.remove(carResult);
		}
	}

	private List<CarResult> getListOfResultsAboveMedianPrice(List<CarResult> carResults) {
		Collections.sort(carResults, new CarResultPriceComparator());
		if(!(carResults.size()%2 == 0)) {
			return carResults.subList((carResults.size()/2)+1, carResults.size());
		} else {
			return carResults.subList((carResults.size()/2), carResults.size());
		}
	}

}
