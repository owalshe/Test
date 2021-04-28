package com.cartrawler.assessment.view;

import java.util.Collection;

import com.cartrawler.assessment.car.CarResult;

public class Display {
    public void render(Collection<CarResult> cars) {
        for (CarResult car : cars) {
            System.out.println (car);
        }
    }
}
