package com.cartrawler.assessment.car.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.EnumUtils;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.SippGroup;

public class CarResultSippGroupComparator implements Comparator<CarResult> {

	@Override
	public int compare(CarResult arg0, CarResult arg1) {
		String firstSippGroup = arg0.getSippCode().substring(0,1);
		String secondSippGroup = arg1.getSippCode().substring(0,1);
		boolean isFirstSippGroup = EnumUtils.isValidEnum(SippGroup.class, firstSippGroup);
		boolean isSecondSippGroup = EnumUtils.isValidEnum(SippGroup.class, secondSippGroup);
		if(isFirstSippGroup && isSecondSippGroup) {
			SippGroup firstSippGroupEnum = SippGroup.valueOf(firstSippGroup);
			SippGroup secondSippGroupEnum = SippGroup.valueOf(secondSippGroup);
			return firstSippGroupEnum.compareTo(secondSippGroupEnum);
		} else if(isFirstSippGroup) {
			return -1;
		} else if(isSecondSippGroup) {
			return 1;
		}
		return 0;
	}
}
