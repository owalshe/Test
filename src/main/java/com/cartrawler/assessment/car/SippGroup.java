package com.cartrawler.assessment.car;

import org.apache.commons.lang3.EnumUtils;

public enum SippGroup {

	M("Mini"), E("Economy"), C("Compact"), OTHER("Other");
	
	private String label;
	
	SippGroup(String label) {
		this.label = label;
	}

	public String getCode() {
		return label;
	}
	
	public static SippGroup parse(String code) {
		if(EnumUtils.isValidEnum(SippGroup.class, code)) {
			return SippGroup.valueOf(code);
		}
		return OTHER;
	}
}
