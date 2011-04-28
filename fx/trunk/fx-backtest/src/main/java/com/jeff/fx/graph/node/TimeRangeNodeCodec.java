package com.jeff.fx.graph.node;

import com.jeff.fx.common.TimeOfWeek;
import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxObjectCodec;
import org.w3c.dom.Node;

public class TimeRangeNodeCodec extends mxObjectCodec
{
    public TimeRangeNodeCodec()
    {
        super(new TimeRangeNode());
    }

    @Override
    protected void decodeAttribute(mxCodec dec, Node attr, Object obj)
    {
        TimeRangeNode node = (TimeRangeNode)obj;

        if(attr.getNodeName().equals("from"))
        {
            TimeOfWeek tow = new TimeOfWeek(attr.getTextContent());
            node.setFrom(tow);
        }
        else if(attr.getNodeName().equals("to"))
        {
            TimeOfWeek tow = new TimeOfWeek(attr.getTextContent());
            node.setTo(tow);
        }
        else if(attr.getNodeName().equals("fromInclusive"))
        {
            node.setFromInclusive(new Boolean(attr.getTextContent()));
        }
        else if(attr.getNodeName().equals("toInclusive"))
        {
            node.setToInclusive(new Boolean(attr.getTextContent()));
        }
    }

    @Override
    protected void encodeValue(mxCodec enc, Object obj, String fieldname, Object value, Node node)
    {
        TimeRangeNode trn = (TimeRangeNode)obj;

        if(fieldname.equals("fromInclusive"))
        {
            super.encodeValue(enc, obj, fieldname, String.valueOf(trn.getFromInclusive()), node);
        }
        else if(fieldname.equals("toInclusive"))
        {
            super.encodeValue(enc, obj, fieldname, String.valueOf(trn.getToInclusive()), node);
        }
        else
        {
            super.encodeValue(enc, obj, fieldname, String.valueOf(value), node);
        }
    }
}