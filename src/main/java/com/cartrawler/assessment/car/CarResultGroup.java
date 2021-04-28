package com.cartrawler.assessment.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.cartrawler.assessment.car.comparator.CarResultPriceComparator;
import com.cartrawler.assessment.car.filter.CarResultFilter;

public class CarResultGroup {

	private boolean isCorporate;
	private SippGroup sippGroup;
	private List<CarResult> carResults;
	
	public CarResultGroup(boolean isCorporate, SippGroup sippGroup) {
		this.isCorporate = isCorporate;
		this.sippGroup = sippGroup;
		this.carResults = new ArrayList<>();
	}

	public boolean isCorporate() {
		return isCorporate;
	}

	public SippGroup getSippGroup() {
		return sippGroup;
	}

	public void add(CarResult carResult) {	
		this.carResults.add(carResult);
	}

	public void sort(Comparator<CarResult> comparator) {
		Collections.sort(carResults, new CarResultPriceComparator());
	}
	
	public void filter(CarResultFilter carResultFilter) {
		carResultFilter.filter(carResults);
	}

	public List<CarResult> getCarResults() {
		return carResults;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CarResultGroup) {
			CarResultGroup other = (CarResultGroup) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.isCorporate, other.isCorporate());
			builder.append(this.sippGroup, other.getSippGroup());
			return builder.isEquals();
		}
		return false;
	}
}
