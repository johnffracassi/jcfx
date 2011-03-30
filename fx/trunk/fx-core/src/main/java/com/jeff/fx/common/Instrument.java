package com.jeff.fx.common;

public enum Instrument 
{
    AUDJPY(0.01), AUDNZD, AUDUSD, EURAUD, EURCHF, 
    EURGBP, EURJPY(0.01), EURUSD, GBPCHF, GBPJPY(0.01), 
    GBPUSD, NZDUSD, USDCAD, USDCHF, USDJPY(0.01),
    
    EURCAD, EURDKK, CADJPY(0.01), CHFJPY(0.01), EURHKD, 
    EURNOK, EURSEK, USDDKK, USDHKD, USDMXN, 
    USDNOK, USDSEK, USDSGD, USDTRY, NZDJPY(0.01),
    
    XAUUSD(0.1), XAGUSD(0.01);
    
    
    double pipValue = 0.0001;
    
    private Instrument() {
    }
    
    private Instrument(double pipValue) {
    	this.pipValue = pipValue;
    }
    
    public double getPipValue() {
    	return pipValue;
    }
}