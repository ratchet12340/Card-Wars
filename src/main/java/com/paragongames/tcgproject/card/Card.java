package com.paragongames.tcgproject.card;

import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;

public class Card
{
	public static int CARD_WIDTH = 36;
	public static int CARD_HEIGHT = 60;
	
	public Sprite sprite;
	public Sprite entitySprite;
	public Font font = new Font();
	
	public Card()
	{
		
	}
	
	public void render(int x, int y, Screen screen)
	{
		
	}
	
	public Entity getEntity()
	{
		return null;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
