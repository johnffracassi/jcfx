package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadedTest {
	
	public static int THREADS = 1;
	
	public static void main(String[] args) {
		MultiThreadedTest mtt = new MultiThreadedTest();
		mtt.run();
	}

	public void run() {
		Manager m = new Manager();
		m.start();
	}
	
	class Manager extends Thread {

		private Worker[] workers = new Worker[THREADS];

		private int counter = 0;
		
		public Manager() {
			for(int w=0; w<workers.length; w++) {
				workers[w] = new Worker();
			}
		}
		
		public void run() {
			for(int w=0; w<workers.length; w++) {
				workers[w].start();
			}
		}
		
		class Worker extends Thread {

			private List<Integer> list = new ArrayList<Integer>();
			private int localCounter = 0;
			private long start = 0;
			
			public Worker() {
				System.out.println("Creating list for " + getName());
				for(int i=0; i<5000000; i++) {
					list.add((int)(Math.random() * 1000000));
				}
				System.out.println("List created for " + getName());
			}
			
			public void run() {
				
				start = System.nanoTime();
				
				while(localCounter < 200) {
					long sum = 0;
					for(Integer i : list) {
						sum += i;
					}
					localCounter ++;
					counter ++;
//					Thread.yield();
				}
				
				System.out.println(getName() + " processed " + localCounter + " in " + ((System.nanoTime() - start) / 1000000.0) + "ms");
			}
		}
	}
}
