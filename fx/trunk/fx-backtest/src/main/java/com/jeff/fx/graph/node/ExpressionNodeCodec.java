package com.jeff.fx.graph.node;

import com.jeff.fx.rules.Operand;
import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxObjectCodec;
import org.w3c.dom.Node;

public class ExpressionNodeCodec extends mxObjectCodec
{
    public ExpressionNodeCodec()
    {
        super(new ExpressionNode());
    }

    @Override
    protected void decodeAttribute(mxCodec dec, Node attr, Object obj)
    {
        if(attr.getNodeName().equals("operand"))
        {
            Operand operand = Operand.valueOf(attr.getTextContent());
            ExpressionNode node = (ExpressionNode)obj;
            node.setOperand(operand);
        }
        else if(attr.getNodeName().equals("leftExpr") || attr.getNodeName().equals("rightExpr"))
        {
            super.decodeAttribute(dec, attr, obj);
        }
    }

    @Override
    protected void encodeValue(mxCodec enc, Object obj, String fieldname, Object value, Node node)
    {
        if(fieldname.equals("leftExpr") || fieldname.equals("rightExpr") || fieldname.equals("operand"))
        {
            super.encodeValue(enc, obj, fieldname, String.valueOf(value), node);
        }
    }
}