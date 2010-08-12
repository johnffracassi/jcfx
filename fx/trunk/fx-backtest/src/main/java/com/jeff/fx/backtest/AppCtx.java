package com.jeff.fx.backtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.jfree.util.Log;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppCtx {

	/**
	 * Persistent application values (stored in registry)
	 */
	private static Preferences persistentRegister;

	/**
	 * Application scope preferences (non-persistent)
	 */
	private static Map<String,Object> sessionRegister;
	
	/**
	 * Map event listeners to event types
	 */
	private static Map<Class<? extends FXActionEvent>,List<FXActionEventListener>> listeners;
	
	/**
	 * Spring application context
	 */
	private static ApplicationContext springCtx;
	
	/**
	 * Hold a reference to the data manager (shared/thread safe)
	 */
	private static BackTestDataManager dataManager;
	
	static {
		persistentRegister = Preferences.userRoot();
		sessionRegister = new HashMap<String, Object>();
		listeners = new HashMap<Class<? extends FXActionEvent>, List<FXActionEventListener>>();
	}

	public static void initialise(ApplicationContext ctx) {
		springCtx = ctx;
		dataManager = (BackTestDataManager)springCtx.getBean("backTestDataManager");
	}
	
	public static BackTestDataManager getDataManager() {
		return dataManager;
	}
	
	public static double getSessionDouble(String key) {
		
		Object result = sessionRegister.get(key);
		
		if(result == null) {
			return 0.0;
		} else if(result instanceof Integer) {
			return ((Integer)sessionRegister.get(key)).doubleValue();
		} else {
			return (Double)sessionRegister.get(key);
		}
	}
	
	public static int getSessionInt(String key) {
		if(sessionRegister.containsKey(key)) {
			return (Integer)sessionRegister.get(key);
		} else {
			String valueStr = JOptionPane.showInputDialog(null, "Enter value for " + key);
			int value = new Integer(valueStr);
			setSession(key, value);
			return getSessionInt(key);
		}
	}
	
	public static LocalTime getSessionTime(String key) {
		return (LocalTime)sessionRegister.get(key);
	}
	
	public static LocalDate getSessionDate(String key) {
		return (LocalDate)sessionRegister.get(key);
	}
	
	public static void setSession(String key, Object value) {
		Log.debug("set value of " + key + " to " + value);
		sessionRegister.put(key, value);
	}
	
	public static void setPersistent(String key, Object value) {
		persistentRegister.put(key, String.valueOf(value));
	}
	
	public static String getPersistent(String key) {
		return persistentRegister.get(key, null);
	}
	
	public static void setPersistent(String key, LocalDate date) {
		setPersistent(key, ISODateTimeFormat.date().print(date));
	}
	
	public static LocalDate getPersistentDate(String key) {
		String dateStr = persistentRegister.get(key, null);
		if(dateStr == null) {
			return new LocalDate();
		} else {
			return ISODateTimeFormat.dateParser().parseDateTime(dateStr).toLocalDate();
		}
	}
	
	public static int getPersistentInt(String key) {
		try {
			int val = persistentRegister.getInt(key, Integer.MIN_VALUE);
			
			if(val != Integer.MIN_VALUE) {
				return val;
			}
		} catch(Exception ex) {
		} 
	
		String valueStr = JOptionPane.showInputDialog(null, "Enter value for " + key);
		int value = new Integer(valueStr);
		persistentRegister.putInt(key, value);
		return value;
	}
	
	public static void registerEventListener(Class<? extends FXActionEvent> c, FXActionEventListener listener) {
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
