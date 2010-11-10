package com.jeff.fx.rules.business;

import com.jeff.fx.lookforward.CandleFilterModel;

public class CodeNodeTemplate extends AbstractFXNode
{
    // fields //
    
    public void setup()
    {
        // init code //
    }
    
    @Override
    public boolean evaluate(CandleFilterModel model)
    {
        boolean result = false;
        int idx = model.getIndex();
        
        // code here //
        
        return result;
    }
    
    @Override
    public String getDescription()
    {
        return "// description //";
    }
    
    @Override
    public String getLabel()
    {
        return "// label //";
    }
}
