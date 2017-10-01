package com.paragongames.tcgproject.graphics.ui;

import java.awt.event.MouseEvent;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.MouseMovedEvent;
import com.paragongames.tcgproject.events.types.MousePressedEvent;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.util.Rectangle;
import com.paragongames.tcgproject.util.Vector2i;

public class UIButton extends UIComponent
{
	public UIButtonListener buttonListener;
	public UIActionListener actionListener;
	public UILabel label;

	public boolean inside = false;
	public boolean pressed = false;
	
	public UIButton(Vector2i position, Vector2i size, UILabel label, Sprite sprite, UIActionListener actionListener)
	{
		super(position, size);
		this.actionListener = actionListener;
		this.sprite = sprite;
		if(label == null)
			this.label = new UILabel(position, "", 0);
		else
			this.label = label;

		buttonListener = new UIButtonListener();
	}

	void init(UIPanel panel)
	{
		super.init(panel);
		panel.addComponent(label);
	}

	public void setButtonListener(UIButtonListener buttonListener)
	{
		this.buttonListener = buttonListener;
	}

	public void setActionListener(UIActionListener actionListener)
	{
		this.actionListener = actionListener;
	}
	
	public void setText(String text)
	{
		label.text = text;
	}

	public void update()
	{
		
	}

	public void render(Screen screen)
	{
		if(sprite != null) screen.renderSprite(position.x + offset.x, position.y + offset.y, sprite, false);

		if(label != null) label.render(screen);
	}

	public void onEvent(Event event)
	{
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> (onMouseReleased((MouseReleasedEvent) e)));
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> (onMousePressed((MousePressedEvent) e)));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> (onMouseMoved((MouseMovedEvent) e)));
	}

	public boolean onMouseMoved(MouseMovedEvent e)
	{
		Vector2i absolutePosition = new Vector2i(position.x + offset.x, position.y + offset.y);
		Rectangle bounds = new Rectangle(absolutePosition, size);
		Vector2i mouse = new Vector2i(e.getX() / Game.getScale(), e.getY() / Game.getScale());
		
		if(!inside && bounds.contains(mouse))
		{
			buttonListener.entered(this);
			inside = true;
		}
		else if(inside && !bounds.contains(mouse))
		{
			buttonListener.exited(this);
			inside = false;
		}
		
		return inside;
	}
	
	public boolean onMousePressed(MousePressedEvent e)
	{
		boolean leftMouseButtonDown = e.getButton() == MouseEvent.BUTTON1;

		if(inside && leftMouseButtonDown)
		{
			buttonListener.pressed(this);
			pressed = true;
		}
		
		return pressed;
	}

	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		if(inside && pressed)
		{
			pressed = false;
			buttonListener.released(this);
			actionListener.perform();
			return true;
		}
		return false;
	}
}