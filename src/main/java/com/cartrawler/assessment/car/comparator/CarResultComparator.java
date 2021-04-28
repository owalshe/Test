package com.cartrawler.assessment.car.comparator;

import java.util.Comparator;

import com.cartrawler.assessment.car.CarResult;

public class CarResultComparator implements Comparator<CarResult> {
	
	private CarResultCorporateComparator corporateComparator = new CarResultCorporateComparator();
	private CarResultSippGroupComparator sippComparator = new CarResultSippGroupComparator();
	private CarResultPriceComparator priceComparator = new CarResultPriceComparator();
	
	@Override
	public int compare(CarResult arg0, CarResult arg1) {
		int isCorporate = this.corporateComparator.compare(arg0.getSupplierName(), arg1.getSupplierName());
		if(isCorporate == 0) {
			int sippGroup = this.sippComparator.compare(arg0, arg1);
			if(sippGroup == 0) {
				return this.priceComparator.compare(arg0, arg1);
			}
			return sippGroup;
		}
		return isCorporate;
	}
}
