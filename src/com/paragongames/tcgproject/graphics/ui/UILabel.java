package com.paragongames.tcgproject.graphics.ui;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.font.Font;
import com.paragongames.tcgproject.util.Vector2i;

public class UILabel extends UIComponent
{
	public String text;
	private int fontColor;
	
	public UILabel(Vector2i position, String text, int fontColor)
	{
		super(position);
		this.text = text;
		this.fontColor = fontColor;
	}
	
	public void render(Screen screen)
	{
		Font.render(text, position.x + offset.x, position.y + offset.y, 0, fontColor, true, screen);
	}
}
