package com.cartrawler.assessment.car.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.EnumUtils;

import com.cartrawler.assessment.car.CorporateSupplier;

public class CarResultCorporateComparator implements Comparator<String> {
	
	@Override
	public int compare(String arg0, String arg1) {
		boolean isFirstCorporate = isCorporate(arg0);
		boolean isSecondCorporate = isCorporate(arg1);
		if(isFirstCorporate && isSecondCorporate) {
			return 0;
		} else if(isFirstCorporate) {
			return -1;
		} else if(isSecondCorporate) {
			return 1;
		}
		return 0;
	}

	public boolean isCorporate(String supplier) {
		return EnumUtils.isValidEnum(CorporateSupplier.class, supplier);
	}

}
