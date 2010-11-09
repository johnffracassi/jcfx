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

    private Node<MockModel> trueNode;
    private Node<MockModel> falseNode;

    @Before
    public void setupModel()
    {
        model = new MockModel();
        trueNode = new TrueNode<MockModel>();
        falseNode = new FalseNode<MockModel>();
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
        Node<MockModel> node = new AndNode<MockModel>(trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new AndNode<MockModel>(falseNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode<MockModel>(trueNode, falseNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode<MockModel>(falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
    
    @Test 
    public void testOrNode()
    {
        Node<MockModel> node = new OrNode<MockModel>(trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }    
    
    @Test 
    public void testNandNode()
    {
        Node<MockModel> node = new NandNode<MockModel>(trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new NandNode<MockModel>(falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode<MockModel>(trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode<MockModel>(falseNode, falseNode);
        assertTrue(node.evaluate(model));
    }    
    
    @Test 
    public void testXorNode()
    {
        Node<MockModel> node = new XorNode<MockModel>(trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new XorNode<MockModel>(falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode<MockModel>(trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode<MockModel>(falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
}
