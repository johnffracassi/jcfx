package com.jeff.fx.backtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppCtx {
	
	private static Preferences prefs;
	private static Map<Class<? extends FXActionEvent>,List<FXActionEventListener>> listeners;
	private static Map<String,Double> tempRegisterNums;
	private static ApplicationContext spring;
	
	private static BackTestDataManager dataManager;
	
	static {
		prefs = Preferences.userRoot();
		listeners = new HashMap<Class<? extends FXActionEvent>, List<FXActionEventListener>>();
		tempRegisterNums = new HashMap<String, Double>();
	}

	public static void init(ApplicationContext ctx) {
		spring = ctx;
		dataManager = (BackTestDataManager)spring.getBean("backTestDataManager");
	}
	
	public static BackTestDataManager getDataManager() {
		return dataManager;
	}
	
	public static void set(String key, double value) {
		tempRegisterNums.put(key, value);
	}
	
	public static double get(String key) {
		return tempRegisterNums.containsKey(key) ? tempRegisterNums.get(key) : 50.0;
	}
	
	public static void update(String key, String value) {
		prefs.put(key, value);
	}
	
	public static String retrieve(String key) {
		return prefs.get(key, null);
	}
	
	public static void update(String key, LocalDate date) {
		update(key, ISODateTimeFormat.date().print(date));
	}
	
	public static LocalDate retrieveDate(String key) {
		String dateStr = prefs.get(key, null);
		if(dateStr == null) {
			return new LocalDate();
		} else {
			return ISODateTimeFormat.dateParser().parseDateTime(dateStr).toLocalDate();
		}
	}
	
	public static void register(Class<? extends FXActionEvent> c, FXActionEventListener listener) {
		if(listeners.containsKey(c)) {
			listeners.get(c).add(listener);
		} else {
			List<FXActionEventListener> newListeners = new ArrayList<FXActionEventListener>();
			newListeners.add(listener);
			listeners.put(c, newListeners);
		}
	}
	
	public static void fireEvent(FXActionEvent ev) {
		
		List<? extends FXActionEventListener> listeners = AppCtx.listeners.get(ev.getClass());
		
		for(FXActionEventListener listener : listeners) {
			listener.event(ev);
		}
	}
}
