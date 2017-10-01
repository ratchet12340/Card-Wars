package com.paragongames.tcgproject.level.states;

import java.awt.event.KeyEvent;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.KeyPressedEvent;
import com.paragongames.tcgproject.events.types.KeyReleasedEvent;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.players.Player;

public class NormalState implements LevelState
{
	private Level level;
	
	private boolean up, down, left, right;
	
	public void init(Level level, Player player)
	{
		this.level = level;
	}
	
	public void update()
	{
		if(up && level.getYOffset() - 3 >= 117) level.setYOffset(level.getYOffset() -3);
		if(down && level.getYOffset() + 3 <= 117 + (24 * 30 - Game.getGameHeight())) level.setYOffset(level.getYOffset() + 3);;
		if(left && level.getXOffset() - 3 >= 213) level.setXOffset(level.getXOffset() - 3);;
		if(right && level.getXOffset() + 3 <= 213 + (24 * 30 - Game.getGameWidth())) level.setXOffset(level.getXOffset() + 3);
	}
	
	public void render(Screen screen)
	{
		
	}

	public void onEvent(Event e)
	{
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event event) -> (onKeyReleased((KeyReleasedEvent) event)));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event event) -> (onKeyPressed((KeyPressedEvent) event)));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event event) -> (onMouseReleased((MouseReleasedEvent) event)));
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		flush();
		level.setState("selecting");
		
		return false;
	}
	
	public boolean onKeyReleased(KeyReleasedEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) right = false;
		return true;
	}

	public boolean onKeyPressed(KeyPressedEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) right = true;
		return true;
	}
	
	public void onSet()
	{
		
	}
	
	public void flush()
	{
		up = down = left = right = false;
	}
}
