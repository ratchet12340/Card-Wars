package com.paragongames.tcgproject.events.types;

import com.paragongames.tcgproject.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent
{
	public MouseReleasedEvent(int button, int x, int y)
	{
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}
}
