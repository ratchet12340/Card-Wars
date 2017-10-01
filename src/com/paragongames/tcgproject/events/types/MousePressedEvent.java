package com.paragongames.tcgproject.events.types;

import com.paragongames.tcgproject.events.Event;

public class MousePressedEvent extends MouseButtonEvent
{
	public MousePressedEvent(int button, int x, int y)
	{
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}
}
