package com.jeff.fx.rules.business;

import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.Node;

public class CodeNodeTemplate extends AbstractFXNode
{
    // fields //
    
    public CodeNodeTemplate(Node<CandleFilterModel> parent)
    {
        super(parent);
    }

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
