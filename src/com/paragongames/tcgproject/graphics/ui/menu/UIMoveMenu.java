package com.paragongames.tcgproject.graphics.ui.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.KeyReleasedEvent;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.util.Vector2i;

public class UIMoveMenu extends UIMenu
{
	public List<String> options = new ArrayList<String>();

	public boolean shouldAttack, shouldWait;

	public UIMoveMenu(Vector2i pos, Vector2i size, Level level)
	{
		super(pos, size);
	}

	public void update()
	{
		if(options.size() == 1)
		{
			if(use)
			{
				shouldWait = true;
				use = false;
			}
		}
		if(options.size() == 2)
		{
			if(up)
			{
				if(index == 0)
					index = 1;
				else
					index--;
				up = false;
			}
			else if(down)
			{
				if(index == 1)
					index = 0;
				else
					index++;
				down = false;
			}
			else if(use)
			{
				if(options.get(index).equals("ATTACK")) shouldAttack = true;
				if(options.get(index).equals("WAIT")) shouldWait = true;
				use = false;
			}
		}
	}

	public void render(Screen screen)
	{
		int x = panel.position.x + position.x + 24;
		int y = panel.position.y + position.y;
		for(int i = 0; i < options.size(); i++)
			Font.render(options.get(i), x + Sprite.cursor.getWidth(), y + 10 * i, 0, 0xffffffff, true, screen);
		screen.renderSprite(x, y + 10 * index, Sprite.cursor, false);
	}

	public void onEvent(Event event)
	{
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> (onKeyReleased((KeyReleasedEvent) e)));
	}

	public boolean onKeyReleased(KeyReleasedEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) up = true;
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) use = true;

		return true;
	}
}
