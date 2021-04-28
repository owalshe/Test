package com.cartrawler.assessment.car;

import org.apache.commons.lang3.EnumUtils;

public enum CorporateSupplier {

	AVIS,
	BUDGET,
	ENTERPRISE,
	FIREFLY,
	HERTZ,
	SIXT,
	THRIFTY;
	
	public static boolean isCorporate(String supplier) {
		return EnumUtils.isValidEnum(CorporateSupplier.class, supplier);
	}
}
