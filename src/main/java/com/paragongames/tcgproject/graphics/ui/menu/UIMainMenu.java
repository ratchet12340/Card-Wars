package com.paragongames.tcgproject.graphics.ui.menu;

import java.awt.event.KeyEvent;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.KeyReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;
import com.paragongames.tcgproject.sound.Sound;
import com.paragongames.tcgproject.util.Vector2i;
import com.paragongames.tcgproject.util.Warning;
import com.paragongames.tcgproject.util.WarningType;

public class UIMainMenu extends UIMenu
{
	private final String[] options =
	{ "PLAY", "EXIT" };

	public UIMainMenu(Vector2i pos, Vector2i size)
	{
		super(pos, size);
	}

	public void update()
	{
		if(down)
		{
			if(index == options.length - 1)
				index = 0;
			else
				index++;
			down = false;
			Sound.select.play();
		}
		else if(up)
		{
			if(index == 0)
				index = options.length - 1;
			else
				index--;
			up = false;
			Sound.select.play();
		}

		if(use)
		{
			if(index == 0)
				Game.setActiveState(1);
			else if(index == 1)
				System.exit(0);
			else
				Warning.getInstance().warn(WarningType.UNSUPPORTED_OPERATION);
			use = false;
		}
	}

	public void render(Screen screen)
	{
		screen.renderSprite(0, 0, Sprite.title, false);
		for(int i = 0; i < options.length; i++)
		{
			Font.render(options[i], 43, 80 + 20 * i, 0, 0xffffffff, true, screen);
		}
		screen.renderSprite(43 - Sprite.cursor.getWidth(), 80 + 20 * index, Sprite.cursor, false);
		Font.render("A GAME BY CHARLIE SCHMITTER", 43, Game.getGameHeight() - 20, 0, 0xffffffff, true, screen);
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
