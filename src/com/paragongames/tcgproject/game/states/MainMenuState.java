package com.paragongames.tcgproject.game.states;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.ui.UIManager;
import com.paragongames.tcgproject.graphics.ui.UIPanel;
import com.paragongames.tcgproject.graphics.ui.menu.UIMainMenu;
import com.paragongames.tcgproject.util.Vector2i;

public class MainMenuState implements GameState
{
	private UIManager manager;
	private UIPanel panel;
	private UIMainMenu menu;
	
	public MainMenuState()
	{
		manager = new UIManager();
		panel = new UIPanel(new Vector2i(0, 0), new Vector2i(Game.getGameWidth(), Game.getGameHeight()), null, manager);
		menu = new UIMainMenu(new Vector2i(0, 0), new Vector2i(Game.getGameWidth(), Game.getGameHeight()));
		panel.addComponent(menu);
		manager.addPanel(panel);
	}

	public void update()
	{
		manager.update();
	}

	public void render(Screen screen)
	{
		manager.render(screen);
	}

	public void onEvent(Event event)
	{
		manager.onEvent(event);
	}
}
