package au.com.barstard.spin;

import au.com.barstard.symbol.SymbolModel;

public interface SpinListener
{
    public void spinComplete(SymbolModel[][] block);
}