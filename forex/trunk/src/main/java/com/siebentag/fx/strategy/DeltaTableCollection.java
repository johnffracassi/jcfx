package com.siebentag.fx.strategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class DeltaTableCollection
{
	private Map<String,DeltaTable> tables;
	private Set<String> keys;
	
	public DeltaTableCollection()
	{
		tables = new HashMap<String, DeltaTable>();
		keys = new HashSet<String>();
	}
	
	public Map<String,DeltaTable> getTables()
	{
		return tables;
	}
	
	public Set<String> getKeys()
	{
		return keys;
	}
	
	public void add(String key,DeltaTable tbl)
	{
		tables.put(key, tbl);
		keys.addAll(tbl.getLabels());
	}
}
