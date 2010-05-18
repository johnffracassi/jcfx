package com.jeff.fx.backtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.jfree.util.Log;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppCtx {
	
	private static Preferences prefs;
	private static Map<Class<? extends FXActionEvent>,List<FXActionEventListener>> listeners;
	private static Map<String,Object> tempRegister;
	private static ApplicationContext spring;
	
	private static BackTestDataManager dataManager;
	
	static {
		prefs = Preferences.userRoot();
		listeners = new HashMap<Class<? extends FXActionEvent>, List<FXActionEventListener>>();
		tempRegister = new HashMap<String, Object>();
	}

	public static void init(ApplicationContext ctx) {
		spring = ctx;
		dataManager = (BackTestDataManager)spring.getBean("backTestDataManager");
	}
	
	public static BackTestDataManager getDataManager() {
		return dataManager;
	}
	
	public static double getDouble(String key) {
		return (Double)tempRegister.get(key);
	}
	
	public static int getInt(String key) {
		if(tempRegister.containsKey(key)) {
			return (Integer)tempRegister.get(key);
		} else {
			Log.info("uh oh, can't find int " + key);
			return 0;
		}
	}
	
	public static LocalTime getTime(String key) {
		return (LocalTime)tempRegister.get(key);
	}
	
	public static LocalDate getDate(String key) {
		return (LocalDate)tempRegister.get(key);
	}
	
	public static void set(String key, Object value) {
		Log.debug("set value of " + key + " to " + value);
		tempRegister.put(key, value);
	}
	
	public static void save(String key, String value) {
		prefs.put(key, value);
	}
	
	public static String retrieve(String key) {
		return prefs.get(key, null);
	}
	
	public static void save(String key, LocalDate date) {
		save(key, ISODateTimeFormat.date().print(date));
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
