package com.paragongames.tcgproject.events.types;

import com.paragongames.tcgproject.events.Event;

public class KeyPressedEvent extends Event
{
	protected int keyCode;
	
	public KeyPressedEvent(int keyCode)
	{
		super(Event.Type.KEY_PRESSED);
		this.keyCode = keyCode;
	}

	public int getKeyCode()
	{
		return keyCode;
	}
}
