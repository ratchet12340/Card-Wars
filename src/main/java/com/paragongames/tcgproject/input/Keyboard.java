package com.paragongames.tcgproject.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.paragongames.tcgproject.events.EventListener;
import com.paragongames.tcgproject.events.types.KeyPressedEvent;
import com.paragongames.tcgproject.events.types.KeyReleasedEvent;

public class Keyboard implements KeyListener
{
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, use;
	private EventListener eventListener;
	
	public Keyboard(EventListener eventListener)
	{
		this.eventListener = eventListener;
	}
	
	public void update()
	{
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		use = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_ENTER];
	}
	
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
		
		KeyPressedEvent event = new KeyPressedEvent(e.getKeyCode());
		eventListener.onEvent(event);
	}

	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
		
		KeyReleasedEvent event = new KeyReleasedEvent(e.getKeyCode());
		eventListener.onEvent(event);
	}

	public void keyTyped(KeyEvent e)
	{

	}
}