package com.paragongames.tcgproject.level.states;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventListener;
import com.paragongames.tcgproject.events.types.MouseMovedEvent;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.players.Player;

public interface LevelState extends EventListener
{
	void init(Level level, Player player);
	
	void update();

	void render(Screen screen);
	
	void onEvent(Event event);
	
	void onSet();
	
	void flush();
}
