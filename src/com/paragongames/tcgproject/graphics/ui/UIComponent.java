package com.paragongames.tcgproject.graphics.ui;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.layers.Layer;
import com.paragongames.tcgproject.util.Vector2i;

public class UIComponent extends Layer
{
	public Vector2i position, size; 
	protected Vector2i offset;
	public Sprite sprite;
	public UIPanel panel;
	
	public UIComponent(Vector2i position)
	{
		this.position = position;
		offset = new Vector2i();
	}

	public UIComponent(Vector2i position, Vector2i size)
	{
		this(position);
		this.size = size;
	}
	
	void init(UIPanel panel)
	{
		this.panel = panel;
		setOffset(panel.position);
	}
	
	void setOffset(Vector2i offset)
	{
		this.offset = offset;
	}
	
	public Vector2i getAbsolutePosition()
	{
		return new Vector2i(position.x + offset.x, position.y + offset.y);
	}
	
	public void update()
	{
		setOffset(panel.position);
	}
	
	public void render(Screen screen)
	{
		
	}
	
	public void onEvent(Event event)
	{
		
	}
}
