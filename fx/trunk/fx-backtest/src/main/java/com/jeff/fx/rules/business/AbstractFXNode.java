package com.jeff.fx.rules.business;

import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Node;

public abstract class AbstractFXNode extends AbstractLeafNode<CandleFilterModel>
{
    private String label;
    private String description;

    public AbstractFXNode(Node<CandleFilterModel> parent)
    {
        super(parent);
        label = getClass().getSimpleName();
        description = "This is a description for " + label;
    }
    
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
