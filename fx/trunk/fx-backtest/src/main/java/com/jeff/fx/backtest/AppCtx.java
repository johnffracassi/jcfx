package com.jeff.fx.backtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * @author Jeff
 */
public class AppCtx {
	
	private static Preferences prefs;
	private static Map<Class<? extends FXActionEvent>,List<FXActionEventListener>> listeners;
	
	static {
		prefs = Preferences.userRoot();
		listeners = new HashMap<Class<? extends FXActionEvent>, List<FXActionEventListener>>();
	}
	
	public static void set(String key, String value) {
		prefs.put(key, value);
	}
	
	public static String getString(String key) {
		return prefs.get(key, null);
	}
	
	public static void set(String key, LocalDate date) {
		set(key, ISODateTimeFormat.date().print(date));
	}
	
	public static LocalDate getDate(String key) {
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
