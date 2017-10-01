package com.paragongames.tcgproject.level.states;

import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.players.Player;
import com.paragongames.tcgproject.util.Vector2i;

public class SelectingState implements LevelState
{
	private Level level;
	
	private List<Vector2i> viableTiles = new ArrayList<Vector2i>();
	private Entity entity;
	
	private int x, y;
	private boolean unitSelected = false;
	
	public void init(Level level, Player player)
	{
		this.level = level;
	}
	
	public void update()
	{
	}
	
	public void render(Screen screen)
	{
		if(unitSelected)
		{
			for(int i = 0; i < viableTiles.size(); i++)
			{
				screen.renderSprite(viableTiles.get(i).x * 24, viableTiles.get(i).y * 24, Sprite.tileGood, true);
			}
		}
	}
	
	public void onEvent(Event e)
	{
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event event) -> (onMouseReleased((MouseReleasedEvent) event)));
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		x = (e.getX() / Game.getScale() + level.getXScroll()) / 24;
		y = (e.getY() / Game.getScale() + level.getYScroll()) / 24;
		
		if(!unitSelected)
		{
			if(level.getEntity(x, y) == null || !level.getEntity(x, y).owner.equals("player") || level.getEntity(x, y).used)
			{
				level.setState("normal");
				return true;
			}
			entity = level.getEntity(x, y);
			unitSelected = true;
			findMovement(entity.moveCost, x, y);
		}
		else if(unitSelected)
		{
			unitSelected = false;
			for(int i = 0; i < viableTiles.size(); i++)
			{
				if(x == viableTiles.get(i).x && y == viableTiles.get(i).y)
				{
					entity.x = x;
					entity.y = y;
					Level.currentEntityX = x;
					Level.currentEntityY = y;
					level.setState("attack");
					entity.used = true;
					return true;
				}
			}
			level.setState("normal");
		}
		
		return true;
	}

	private void findMovement(int counter, int x, int y)
	{
		if(counter == 0) return;

		Vector2i vec = new Vector2i(x, y);
		if(!viableTiles.contains(vec)) viableTiles.add(vec);

		if(level.getEntity(x - 1, y) == null)
		{
			if(level.getTile(x - 1, y).isWater && entity.canSwim)
				findMovement(counter - 1, x - 1, y);
			else if(!level.getTile(x - 1, y).isWater)
				findMovement(counter - 1, x - 1, y);
		}
		if(level.getEntity(x + 1, y) == null)
		{
			if(entity.canSwim && level.getTile(x + 1, y).isWater)
				findMovement(counter - 1, x + 1, y);
			else if(!level.getTile(x + 1, y).isWater)
				findMovement(counter - 1, x + 1, y);
		}
		if(level.getEntity(x, y - 1) == null)
		{
			if(entity.canSwim && level.getTile(x, y - 1).isWater)
				findMovement(counter - 1, x, y - 1);
			else if(!level.getTile(x, y - 1).isWater)
				findMovement(counter - 1, x, y - 1);
		}
		if(level.getEntity(x, y + 1) == null)
		{
			if(entity.canSwim && level.getTile(x, y + 1).isWater)
				findMovement(counter - 1, x, y + 1);
			else if(!level.getTile(x, y + 1).isWater)
				findMovement(counter - 1, x, y + 1);
		}
	}
	
	public void onSet()
	{
		
	}
	
	public void flush()
	{
		viableTiles.clear();
		unitSelected = false;
	}
}
