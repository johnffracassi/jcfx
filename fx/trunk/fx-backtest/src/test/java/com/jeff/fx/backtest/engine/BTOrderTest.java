package com.jeff.fx.backtest.engine;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.OfferSide;
import static org.junit.Assert.*;

public class BTOrderTest {

	@Test
	public void stopLossesBuy() {
		
		BTOrder order = generateOpenOrder();
		
		// assert that there are no stops set
		assertTrue(order.getStopLoss() == 0);
		assertFalse("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());

		// check the setStopLoss
		order.setStopLoss(50);
		assertTrue("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
		assertTrue("StopLossPrice=" + order.getStopLossPrice(), order.getStopLossPrice() == 0.8950);
		order.setStopLoss(75);
		assertTrue("StopLossPrice: " + order.getStopLossPrice() + " != " + 0.8925f,(float)order.getStopLossPrice() == 0.8925f);
		
		// check the setStopLossPrice
		order.setStopLossPrice(0.8960);
		assertTrue("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
		assertTrue("StopLoss=" + order.getStopLoss(), order.getStopLoss() == 40);
		order.setStopLossPrice(0.8920);
		assertTrue("StopLoss=" + order.getStopLoss(), order.getStopLoss() == 80);

		// check stopLoss removal
		order.setStopLoss(0);
		assertFalse("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
	}

	@Test
	public void stopLossesSell() {
		
		BTOrder order = generateOpenOrder();
		order.setOfferSide(OfferSide.Sell);
		
		// assert that there are no stops set
		assertTrue(order.getStopLoss() == 0);
		assertFalse("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());

		// check the setStopLoss
		order.setStopLoss(50);
		assertTrue("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
		assertTrue("StopLossPrice=" + order.getStopLossPrice(), order.getStopLossPrice() == 0.9050);
		order.setStopLoss(75);
		assertTrue("StopLossPrice: " + order.getStopLossPrice() + " != " + 0.9075f,(float)order.getStopLossPrice() == 0.9075f);
		
		// check the setStopLossPrice
		order.setStopLossPrice(0.9060);
		assertTrue("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
		assertTrue("StopLoss=" + order.getStopLoss(), order.getStopLoss() == 60);
		order.setStopLossPrice(0.9080);
		assertTrue("StopLoss=" + order.getStopLoss(), order.getStopLoss() == 80);

		// check stopLoss removal
		order.setStopLoss(0);
		assertFalse("hasStopLoss=" + order.hasStopLoss(), order.hasStopLoss());
	}
	
	@Test
	public void takeProfitsBuy() {
		
		BTOrder order = generateOpenOrder();
		
		// assert that there are no stops set
		assertTrue(order.getTakeProfit() == 0);
		assertFalse("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());

		// check the setTakeProfit
		order.setTakeProfit(50);
		assertTrue("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
		assertTrue("takeProfitPrice=" + order.getTakeProfitPrice(), (float)order.getTakeProfitPrice() == 0.9050f);
		order.setTakeProfit(75);
		assertTrue("takeProfitPrice: " + order.getTakeProfitPrice(), (float)order.getTakeProfitPrice() == 0.9075f);
		
		// check the setTakeProfitPrice
		order.setTakeProfitPrice(0.9060f);
		assertTrue("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
		assertTrue("takeProfit=" + order.getTakeProfit(), order.getTakeProfit() == 60);
		order.setTakeProfitPrice(0.9080f);
		assertTrue("takeProfit=" + order.getTakeProfit() + " (" + order.getTakeProfitPrice() + "-" + order.getOpenPrice() + ")", order.getTakeProfit() == 80);

		// check stopLoss removal
		order.setTakeProfit(0);
		assertFalse("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
	}	
	
	@Test
	public void takeProfitsSell() {
		
		BTOrder order = generateOpenOrder();
		order.setOfferSide(OfferSide.Sell);
		
		// assert that there are no stops set
		assertTrue(order.getTakeProfit() == 0);
		assertFalse("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());

		// check the setTakeProfit
		order.setTakeProfit(50);
		assertTrue("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
		assertTrue("takeProfitPrice=" + order.getTakeProfitPrice(), (float)order.getTakeProfitPrice() == 0.8950f);
		order.setTakeProfit(75);
		assertTrue("takeProfitPrice: " + order.getTakeProfitPrice() ,(float)order.getTakeProfitPrice() == 0.8925f);
		
		// check the setTakeProfitPrice
		order.setTakeProfitPrice(0.8940f);
		assertTrue("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
		assertTrue("takeProfit=" + order.getTakeProfit(), order.getTakeProfit() == 60);
		order.setTakeProfitPrice(0.8920f);
		assertTrue("takeProfit=" + order.getTakeProfit() + " (" + order.getTakeProfitPrice() + "-" + order.getOpenPrice() + ")", order.getTakeProfit() == 80);

		// check stopLoss removal
		order.setTakeProfit(0);
		assertFalse("hasTakeProfit=" + order.hasTakeProfit(), order.hasTakeProfit());
	}

	public BTOrder generateOpenOrder() {
		BTOrder order = new BTOrder();
		order.setId(1);
		order.setInstrument(Instrument.AUDUSD);
		order.setOfferSide(OfferSide.Buy);
		order.setOpenPrice(0.9);
		order.setOpenTime(new LocalDateTime(2010, 8, 23, 13, 0, 0));
		order.setUnits(1);
		return order;
	}
	
}
