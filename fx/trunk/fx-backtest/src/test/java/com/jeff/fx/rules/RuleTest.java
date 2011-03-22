package com.jeff.fx.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jeff.fx.rules.logic.AndNode;
import com.jeff.fx.rules.logic.FalseNode;
import com.jeff.fx.rules.logic.NandNode;
import com.jeff.fx.rules.logic.OrNode;
import com.jeff.fx.rules.logic.TrueNode;
import com.jeff.fx.rules.logic.XorNode;

public class RuleTest
{
    private MockModel model;

    private Node trueNode;
    private Node falseNode;

    @Before
    public void setupModel()
    {
        model = new MockModel();
        trueNode = new TrueNode();
        falseNode = new FalseNode();
    }

    @Test
    public void testTrueNode()
    {
        boolean result = trueNode.evaluate(model);
        assertTrue(result);
    }
    
    @Test
    public void testFalseNode()
    {
        boolean result = falseNode.evaluate(model);
        assertFalse(result);
    }
    
    @Test 
    public void testAndNode()
    {
        Node node = new AndNode(null, trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new AndNode(null, falseNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode(null, trueNode, falseNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
    
    @Test 
    public void testOrNode()
    {
        Node node = new OrNode(null, trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }    
    
    @Test 
    public void testNandNode()
    {
        Node node = new NandNode(null, trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new NandNode(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode(null, falseNode, falseNode);
        assertTrue(node.evaluate(model));
    }    
    
    @Test 
    public void testXorNode()
    {
        Node node = new XorNode(null, trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new XorNode(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
}
