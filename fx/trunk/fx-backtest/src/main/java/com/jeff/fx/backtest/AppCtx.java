package com.jeff.fx.backtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;

public class AppCtx {

	private static Logger log = Logger.getLogger(AppCtx.class);

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
	
	///////////////////////////////////////////////////////////////////////////

	static {
		persistentRegister = Preferences.userRoot();
		sessionRegister = new HashMap<String, Object>();
		listeners = new HashMap<Class<? extends FXActionEvent>, List<FXActionEventListener>>();
	}

	public static void initialise(ApplicationContext ctx) {
		log.info("Initialising Application Context");
		springCtx = ctx;
		dataManager = (BackTestDataManager)springCtx.getBean("backTestDataManager");
	}
	
	public static Object getBean(String name) {
		return springCtx.getBean(name);
	}
	
	///////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static <T> T getSessionValue(String key) {
		log.debug("get session value for '" + key + "'");
		return (T)sessionRegister.get(key);
	}
	
	public static double getSessionDouble(String key) {
		
		Double result = getSessionValue(key);
		
		if(result == null) {
			return 0.0;
		} else {
			return (Double)sessionRegister.get(key);
		}
	}
	
	public static int getSessionInt(String key) {
		
		Integer result = getSessionValue(key);
		
		if(result == null) {
			return 0;
		} else {
			return (Integer)sessionRegister.get(key);
		}
	}
	
	public static LocalTime getSessionLocalTime(String key) {
		return getSessionValue(key);
	}	
	
	public static LocalDate getSessionDate(String key) {
		return getSessionValue(key);
	}
	
	public static void setSession(String key, Object value) {
		log.debug("set session value of '" + key + "' to " + value);
		sessionRegister.put(key, value);
	}

	///////////////////////////////////////////////////////////////////////////
	
	public static void setPersistent(String key, Object value) {
		log.debug("set persistent value of '" + key + "' to " + value);
		persistentRegister.put(key, String.valueOf(value));
	}
	
	public static void setPersistent(String key, LocalDate date) {
		setPersistent(key, ISODateTimeFormat.date().print(date));
	}
	
	///////////////////////////////////////////////////////////////////////////

	public static String getPersistent(String key) {
		log.debug("get persistent value of '" + key + "'");
		return persistentRegister.get(key, null);
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
		} catch(Exception ex) {	} 
	
		String valueStr = JOptionPane.showInputDialog(null, "Enter value for " + key);
		int value = new Integer(valueStr);
		persistentRegister.putInt(key, value);
		return value;
	}
	
	public static int getPersistentInt(String key, int defaultValue) {
		try {
			int val = persistentRegister.getInt(key, Integer.MIN_VALUE);
			if(val != Integer.MIN_VALUE) {
				return val;
			}
		} catch(Exception ex) {	}
		
		return defaultValue;
	}
	
	public static double getPersistentDouble(String key) {
		try {
			double val = persistentRegister.getDouble(key, Double.NEGATIVE_INFINITY);
			if(val != Double.NEGATIVE_INFINITY) {
				return val;
			}
		} catch(Exception ex) { } 
	
		String valueStr = JOptionPane.showInputDialog(null, "Enter value for " + key);
		double value = new Double(valueStr);
		persistentRegister.putDouble(key, value);
		return value;
	}
	
	///////////////////////////////////////////////////////////////////////////

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

	public static BackTestDataManager getDataManager() {
		return dataManager;
	}	
}
