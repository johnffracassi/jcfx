package com.siebentag.cj.graphics.renderer;

import org.springframework.stereotype.Component;

import com.siebentag.cj.mvc.BatsmanState;

@Component
public class BatsmanRenderer extends SpriteRenderer<BatsmanState>
{
	public String getSpriteKey()
	{
		return "batsman";
	}
}
