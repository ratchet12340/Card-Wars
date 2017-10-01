package com.paragongames.tcgproject.level.states;

import java.awt.event.MouseEvent;

import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.MouseMovedEvent;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.level.tile.Tile;
import com.paragongames.tcgproject.players.Player;
import com.paragongames.tcgproject.util.Warning;
import com.paragongames.tcgproject.util.WarningType;

public class PlacingState implements LevelState
{
	private Level level;
	private Player player;

	private Entity currentEntity;
	private Sprite tileSelect = Sprite.tileSelect;

	private int x, y;
	private boolean moved = false;

	public void init(Level level, Player player)
	{
		this.level = level;
		this.player = player;
	}

	public void update()
	{
		currentEntity = player.getCurrentEntity();
	}

	public void render(Screen screen)
	{
		if(moved)
		{
			screen.renderSprite(x * 24, y * 24, currentEntity.getSprite(), true);
			screen.renderSprite(x * 24, y * 24, tileSelect, true);
		}
	}

	public void onEvent(Event e)
	{
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event event) -> (onMouseReleased((MouseReleasedEvent) event)));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event event) -> (onMouseMoved((MouseMovedEvent) event)));
	}

	public boolean onMouseMoved(MouseMovedEvent e)
	{
		moved = true;
		x = (e.getX() / Game.getScale() + level.getXScroll()) / 24;
		y = (e.getY() / Game.getScale() + level.getYScroll()) / 24;
		return true;
	}

	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		x = (e.getX() / Game.getScale() + level.getXScroll()) / 24;
		y = (e.getY() / Game.getScale() + level.getYScroll()) / 24;

		if(e.getButton() == MouseEvent.BUTTON1)
		{
			if(!player.canMake(currentEntity))
			{
				player.resetHand();
				Warning.getInstance().warn(WarningType.INSUFFICIENT_RESOURCES);
			}
			else
			{
				if(validTilePlacement(currentEntity, level))
				{
					currentEntity.x = x;
					currentEntity.y = y;
					player.calculateResources(currentEntity);
					level.addPlayerEntity(currentEntity, x, y);
					player.removeCard();
				}
				else
				{
					Warning.getInstance().warn(WarningType.INVALID_TILE_PLACEMENT);
					player.resetHand();
				}
			}
			level.setState("normal");
		}

		return true;
	}

	private boolean validTilePlacement(Entity ent, Level level)
	{
		if(level.getEntity(x, y) != null) return false;
		Tile tile = level.getTile(x, y);
		if(ent.isFarm && tile.isFarmable) return true;
		if(ent.isMine && tile.isMinable) return true;
		if(ent.isMovable && !ent.canSwim && !tile.isWater) return true;
		return false;
	}
	
	public void onSet()
	{
		
	}
	
	public void flush()
	{
		moved = false;
	}
}
