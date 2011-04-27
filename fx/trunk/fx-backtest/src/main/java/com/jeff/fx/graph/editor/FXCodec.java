package com.jeff.fx.graph.editor;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FXCodec extends mxCodec
{
    public FXCodec(Document document)
    {
        super(document);
    }

    public FXCodec()
    {
        super();
    }

    @Override
    public Object decode(Node node, Object object)
    {
        System.out.println("decode => " + node + " / " + object);
        return super.decode(node, object);
    }

    @Override
    public Node encode(Object o)
    {
        System.out.println("encode => " + o.getClass().getName());

        if(o instanceof mxCell)
        {
            mxCell cell = (mxCell)o;

            if(cell.isEdge())
            {
                System.out.println("cell is an edge");
                System.out.println(cell.getValue());
            }
        }

        return super.encode(o);
    }
}
