package com.paragongames.tcgproject.util;

import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.font.Font;

public class Warning
{
	private final static Warning INSTANCE = new Warning();
	
	private WarningType type;
	private long startTime = 0L;
	private long elapsedTime = 0L;
	private boolean shouldWarn = false;
	
	private Warning()
	{
		
	}
	
	public void warn(WarningType type)
	{
		this.type = type;
		shouldWarn = true;
		startTime = System.currentTimeMillis();
	}
	
	public void update()
	{
		elapsedTime = System.currentTimeMillis();
		if((elapsedTime - startTime) >= 750)
		{
			shouldWarn = false;
			elapsedTime = 0L;
		}
	}
	
	public void render(Screen screen)
	{
		if(shouldWarn)
			Font.render(type.getMessage(), Game.getGameWidth() / 2 - (8 * type.getMessage().length() / 2), 25, 0, 0xffffff, true, screen);
	}
	
	public static Warning getInstance()
	{
		return INSTANCE;
	}
}
