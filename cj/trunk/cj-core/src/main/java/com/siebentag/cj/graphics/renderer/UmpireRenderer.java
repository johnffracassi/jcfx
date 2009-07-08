package com.siebentag.cj.graphics.renderer;

import org.springframework.stereotype.Component;

import com.siebentag.cj.mvc.UmpireState;

@Component
public class UmpireRenderer extends SpriteRenderer<UmpireState>
{
	public String getSpriteKey()
	{
		return "umpire";
	}
}
