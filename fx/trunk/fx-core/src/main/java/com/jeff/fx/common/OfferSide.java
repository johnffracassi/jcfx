package com.jeff.fx.common;

public enum OfferSide 
{
	/** Bid = sell */
    Sell("Sell"), 
    
    /** Ask = buy */
    Buy("Buy");  
    
    String description;
    
    OfferSide(String description) {
    	this.description = description;
    }
    
    public String toString() {
    	return description;
    }
}
