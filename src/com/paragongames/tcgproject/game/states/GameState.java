package com.paragongames.tcgproject.game.states;

import com.paragongames.tcgproject.events.EventListener;
import com.paragongames.tcgproject.graphics.Screen;

public interface GameState extends EventListener
{
	void update();
	
	void render(Screen screen);
}
