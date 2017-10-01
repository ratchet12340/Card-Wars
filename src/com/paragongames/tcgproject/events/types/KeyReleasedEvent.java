package com.paragongames.tcgproject.events.types;

import com.paragongames.tcgproject.events.Event;

public class KeyReleasedEvent extends Event
{
	protected int keyCode;
	
	public KeyReleasedEvent(int keyCode)
	{
		super(Event.Type.KEY_RELEASED);
		this.keyCode = keyCode;
	}

	public int getKeyCode()
	{
		return keyCode;
	}
}
