/**
 * 
 */
package com.siebentag.cj.game.shot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeff
 *
 */
public class Probability
{
	private String type;
	private List<ParamObject> params;
	
	public Probability()
	{
		params = new ArrayList<ParamObject>();
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void addParam(ParamObject param)
	{
		params.add(param);
	}
}
