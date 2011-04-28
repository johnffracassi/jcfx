package com.jeff.fx.graph.editor;

import com.jeff.fx.graph.node.ExpressionNodeCodec;
import com.jeff.fx.graph.node.TimeRangeNodeCodec;
import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;
import com.mxgraph.model.mxCell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
    public Object decode(Node node, Object into)
    {
        mxCodecRegistry.register(new ExpressionNodeCodec());
        mxCodecRegistry.register(new TimeRangeNodeCodec());

		Object obj = null;

		if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
		{
			mxObjectCodec codec = mxCodecRegistry.getCodec(node.getNodeName());

			try
			{
				if (codec != null)
				{
					obj = codec.decode(this, node, into);
				}
				else
				{
					obj = node.cloneNode(true);
					((Element) obj).removeAttribute("as");
				}
			}
			catch (Exception e)
			{
				System.err.println("Cannot decode " + node.getNodeName() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}

		return obj;
    }

    @Override
    public Node encode(Object obj)
    {
        mxCodecRegistry.register(new ExpressionNodeCodec());
        mxCodecRegistry.register(new TimeRangeNodeCodec());

        if(obj instanceof mxCell)
        {
            mxCell cell = (mxCell)obj;

            if(cell.isEdge())
            {
                System.out.println("cell is an edge");
                System.out.println(cell.getValue());
            }
        }

        Node node = null;
		if (obj != null)
		{
			String name = mxCodecRegistry.getName(obj);
			mxObjectCodec enc = mxCodecRegistry.getCodec(name);

			if (enc != null)
			{
				node = enc.encode(this, obj);
			}
			else
			{
				if (obj instanceof Node)
				{
					node = ((Node) obj).cloneNode(true);
				}
				else
				{
					System.err.println("No codec for " + name);
				}
			}
		}

		return node;
    }
}
