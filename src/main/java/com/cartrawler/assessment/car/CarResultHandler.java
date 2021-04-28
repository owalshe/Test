package com.cartrawler.assessment.car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.cartrawler.assessment.car.comparator.CarResultComparator;
import com.cartrawler.assessment.car.comparator.CarResultGroupComparator;
import com.cartrawler.assessment.car.comparator.CarResultPriceComparator;
import com.cartrawler.assessment.car.filter.CarResultFilter;
import com.cartrawler.assessment.car.filter.CarResultFilterRemoveFullFullAboveMedianPrice;

public class CarResultHandler {
	
	public static class GroupedResultsBuilder {
		
		private	CarResultGroupFactory carResultGroupfactory;
		private List<CarResultGroup> groupList;
		
		public GroupedResultsBuilder(Set<CarResult> carResults, CarResultGroupFactory carResultGroupfactory) {
			this.groupList = new ArrayList<>();
			this.carResultGroupfactory = carResultGroupfactory;
			populteGroupedResults(carResults, carResultGroupfactory);  
		}

		private void populteGroupedResults(Set<CarResult> carResults, CarResultGroupFactory carResultGroupMaker) {
			for(CarResult carResult : carResults) {
	        	CarResultGroup group = carResultGroupMaker.createCarResultGroup(carResult);
	        	if(groupList.contains(group)) {
	        		groupList.get(groupList.indexOf(group)).add(carResult);
	        	} else {
	        		group.add(carResult);
	        		groupList.add(group);
	        	}
	        }
		}

		public GroupedResultsBuilder sortWithinGroup(Comparator<CarResult> comparator) {
			this.groupList.stream().forEach(g->g.sort(comparator));
	        return this;
		}
		
		public GroupedResultsBuilder sort(Comparator<CarResultGroup> comparator) {
			Collections.sort(groupList, comparator);
	        return this;
		}
		
		public GroupedResultsBuilder filterWithinGroup(CarResultFilter filter) {
			this.groupList.stream().forEach(g->g.filter(new CarResultFilterRemoveFullFullAboveMedianPrice()));
	        return this;
		}
		
		public List<CarResult> build() {
			List<CarResult> resultsList = new ArrayList<>();
			Collections.sort(this.groupList, this.carResultGroupfactory.getComparator());
	    	groupList.stream().forEach(g->resultsList.addAll(g.getCarResults()));
			return resultsList;		
		}
	}
	
	private CarResultHandler() {}

	public static List<CarResult> getSortedResults(Collection<CarResult> carResults) {
		List<CarResult> carResultsList = new ArrayList<>(carResults);
        Collections.sort(carResultsList, new CarResultComparator());
        return carResultsList;
	}
	
	public static List<CarResult> getSortedAndFilteredResults(Collection<CarResult> carResults) {
		List<CarResultGroup> groupList = new ArrayList<>();
		for(CarResult carResult : carResults) {
        	CarResultGroup group = new CarResultGroupFactory().createCarResultGroup(carResult);
        	if(groupList.contains(group)) {
        		groupList.get(groupList.indexOf(group)).add(carResult);
        	} else {
        		group.add(carResult);
        		groupList.add(group);
        	}
        }  
		groupList.stream().forEach(g->g.sort(new CarResultPriceComparator()));
        groupList.stream().forEach(g->g.filter(new CarResultFilterRemoveFullFullAboveMedianPrice()));
    	Collections.sort(groupList, new CarResultGroupComparator());
    	List<CarResult> sortedList = new ArrayList<>();
    	groupList.stream().forEach(g->sortedList.addAll(g.getCarResults()));
        return sortedList;
	}
}
