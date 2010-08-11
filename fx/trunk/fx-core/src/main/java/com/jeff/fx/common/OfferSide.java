package com.jeff.fx.common;

public enum OfferSide 
{
	/** Bid = sell */
    Bid("Sell"), 
    
    /** Ask = buy */
    Ask("Buy");  
    
    String description;
    
    OfferSide(String description) {
    	this.description = description;
    }
    
    public String toString() {
    	return description;
    }
}
