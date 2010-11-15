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
        Node<MockModel> node = new AndNode<MockModel>(null, trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new AndNode<MockModel>(null, falseNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode<MockModel>(null, trueNode, falseNode);
        assertFalse(node.evaluate(model));
        
        node = new AndNode<MockModel>(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
    
    @Test 
    public void testOrNode()
    {
        Node<MockModel> node = new OrNode<MockModel>(null, trueNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new OrNode<MockModel>(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }    
    
    @Test 
    public void testNandNode()
    {
        Node<MockModel> node = new NandNode<MockModel>(null, trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new NandNode<MockModel>(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode<MockModel>(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new NandNode<MockModel>(null, falseNode, falseNode);
        assertTrue(node.evaluate(model));
    }    
    
    @Test 
    public void testXorNode()
    {
        Node<MockModel> node = new XorNode<MockModel>(null, trueNode, trueNode);
        assertFalse(node.evaluate(model));
        
        node = new XorNode<MockModel>(null, falseNode, trueNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode<MockModel>(null, trueNode, falseNode);
        assertTrue(node.evaluate(model));
        
        node = new XorNode<MockModel>(null, falseNode, falseNode);
        assertFalse(node.evaluate(model));
    }
}
