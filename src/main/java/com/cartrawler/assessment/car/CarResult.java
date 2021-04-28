package com.cartrawler.assessment.car;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CarResult {
	
    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final FuelPolicy fuelPolicy;
    
	public enum FuelPolicy {
        FULLFULL,
        FULLEMPTY
    }
    
    public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sipp;
        this.rentalCost = cost;
        this.fuelPolicy = fuelPolicy;
    }
    
    public String getDescription() {
        return this.description;        
    }
    
    public String getSupplierName() {
        return this.supplierName;        
    }
    
    public String getSippCode() {
        return this.sippCode;        
    }
    
    public double getRentalCost() {
        return this.rentalCost;        
    }
    
    public FuelPolicy getFuelPolicy() {
        return this.fuelPolicy;
    }
    
    public String toString() {
        return this.supplierName + " : " +
            this.description + " : " +
            this.sippCode + " : " +
            this.rentalCost + " : " +
            this.fuelPolicy;
    }
    
    @Override
    public int hashCode() {
    	HashCodeBuilder builder = new HashCodeBuilder();
    	builder.append(this.supplierName);
    	builder.append(this.description);
    	builder.append(this.sippCode);
    	builder.append(this.fuelPolicy);
    	return builder.toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof CarResult) {
    		CarResult other = (CarResult) obj;
	    	EqualsBuilder builder = new EqualsBuilder();
	    	builder.append(this.supplierName, other.getSupplierName());
	    	builder.append(this.description, other.getDescription());
	    	builder.append(this.sippCode, other.getSippCode());
	    	builder.append(this.fuelPolicy, other.getFuelPolicy());
	    	return builder.isEquals();
    	}
    	return false;
    }
}
