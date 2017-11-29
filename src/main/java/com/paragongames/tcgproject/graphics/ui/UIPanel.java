package com.paragongames.tcgproject.graphics.ui;

import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.util.Vector2i;

public class UIPanel extends UIComponent
{
	public List<UIComponent> components = new ArrayList<UIComponent>();
	public Vector2i size;
	private Sprite sprite;
	private UIManager manager;
	
	public UIPanel(Vector2i position, Vector2i size, Sprite sprite, UIManager manager)
	{
		super(position);
		this.size = size;
		this.sprite = sprite;
		this.manager = manager;
	}

	public void addComponent(UIComponent comp)
	{
		comp.init(this);
		components.add(comp);
		manager.addLayer(comp);
	}
	
	public void removeComponent(UIComponent comp)
	{
		components.remove(comp);
		manager.layerStack.remove(comp);
	}
	
	public void setPosition(Vector2i pos)
	{
		position = pos;
	}
	
	public void setSize(Vector2i size)
	{
		this.size = size;
	}
	
	public void update()
	{
		
	}

	public void render(Screen screen)
	{
		if(sprite != null)
			screen.renderSprite(position.x, position.y, sprite, false);
	}
	
	public void onEvent(Event event)
	{
		
	}
	
	public UIManager getUIManager()
	{
		return manager;
	}
}
