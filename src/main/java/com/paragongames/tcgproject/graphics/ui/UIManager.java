package com.paragongames.tcgproject.graphics.ui;

import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.layers.Layer;

public class UIManager extends Layer
{
	private List<UIPanel> panels = new ArrayList<UIPanel>();
	public List<Layer> layerStack = new ArrayList<Layer>();

	public UIManager()
	{

	}

	public void addPanel(UIPanel panel)
	{
		panels.add(panel);
		addLayer(panel);
	}

	public void addLayer(Layer layer)
	{
		layerStack.add(layer);
	}

	public void update()
	{
		for(int i = 0; i < layerStack.size(); i++)
		{
			layerStack.get(i).update();
		}
	}

	public void render(Screen screen)
	{
		for(int i = 0; i < layerStack.size(); i++)
		{
			layerStack.get(i).render(screen);
		}
	}

	public void onEvent(Event event)
	{
		for(int i = layerStack.size() - 1; i >= 0; i--)
		{
			layerStack.get(i).onEvent(event);
		}
	}
}
