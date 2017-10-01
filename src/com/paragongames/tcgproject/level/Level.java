package com.paragongames.tcgproject.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.layers.Layer;
import com.paragongames.tcgproject.level.states.AttackState;
import com.paragongames.tcgproject.level.states.LevelState;
import com.paragongames.tcgproject.level.states.NormalState;
import com.paragongames.tcgproject.level.states.PlacingState;
import com.paragongames.tcgproject.level.states.SelectingState;
import com.paragongames.tcgproject.level.tile.Tile;
import com.paragongames.tcgproject.players.Opponent;
import com.paragongames.tcgproject.players.Player;

public class Level extends Layer
{
	protected int width, height;
	protected int xOffset, yOffset;
	protected int[] tiles;
	protected List<Entity> pEntities = new ArrayList<Entity>();
	protected List<Entity> oEntities = new ArrayList<Entity>();
	protected Player player;
	protected Opponent opponent;

	protected LevelState currentState;
	protected LevelState normal;
	protected LevelState selecting;
	protected LevelState placing;
	protected LevelState attack;

	private int xScroll, yScroll;

	public static int currentEntityX, currentEntityY;

	public static Level spawn = new FileLevel("/levels/first.png");
	public static Level test = new RandomLevel(30, 30);
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		generateLevel();

		normal = new NormalState();
		selecting = new SelectingState();
		placing = new PlacingState();
		attack = new AttackState();

		setState("normal");
	}

	public Level(String path)
	{
		loadLevel(path);
		generateLevel();

		normal = new NormalState();
		selecting = new SelectingState();
		placing = new PlacingState();
		attack = new AttackState();

		setState("normal");
	}

	protected void generateLevel()
	{

	}

	protected void loadLevel(String path)
	{

	}

	public void update()
	{
		for(int i = 0; i < oEntities.size(); i++)
			oEntities.get(i).update();
		for(int i = 0; i < pEntities.size(); i++)
			pEntities.get(i).update();
		remove();

		currentState.update();
	}

	public void remove()
	{
		Iterator<Entity> itr;
		for(itr = pEntities.iterator(); itr.hasNext();)
		{
			Entity ent = itr.next();
			if(ent.isRemoved()) itr.remove();
		}
		for(itr = oEntities.iterator(); itr.hasNext();)
		{
			Entity ent = itr.next();
			if(ent.isRemoved()) itr.remove();
		}
	}

	public void render(Screen screen)
	{
		screen.setOffset(xScroll, yScroll);
		int x0 = (xScroll - 24) / 24;
		int x1 = (xScroll + screen.width + 24) / 24;
		int y0 = (yScroll - 24) / 24;
		int y1 = (yScroll + screen.height + 24) / 24;

		for(int y = y0; y < y1; y++)
		{
			for(int x = x0; x < x1; x++)
			{
				getTile(x, y).render(x, y, this, screen);
			}
		}

		for(int i = 0; i < oEntities.size(); i++)
			oEntities.get(i).render(screen);
		for(int i = 0; i < pEntities.size(); i++)
			pEntities.get(i).render(screen);

		currentState.render(screen);
	}

	public void onEvent(Event event)
	{
		if(getState().getClass().equals(NormalState.class))
		{
			EventDispatcher dispatcher = new EventDispatcher(event);
			dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> (onMouseReleased((MouseReleasedEvent) e)));
			currentState.onEvent(event);
		}
		else
			currentState.onEvent(event);
	}

	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		setState("selecting");
		return false;
	}

	public void addPlayerEntity(Entity e, int x, int y)
	{
		e.init(this, x, y, "player");
		pEntities.add(e);
	}

	public void addOpponentEntity(Entity e, int x, int y)
	{
		e.init(this, x, y, "opponent");
		oEntities.add(e);
	}

	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if(tiles[x + y * width] == Tile.colorGrass) return Tile.grass;
		if(tiles[x + y * width] == Tile.colorRock) return Tile.rock;
		if(tiles[x + y * width] == Tile.colorWater) return Tile.water;
		if(tiles[x + y * width] == Tile.colorFarmable) return Tile.farmable;
		if(tiles[x + y * width] == Tile.colorWall) return Tile.wall;
		return Tile.voidTile;
	}
	
	public Entity getEntity(int x, int y)
	{
		for(int i = 0; i < oEntities.size(); i++)
		{
			if(oEntities.get(i).x == x && oEntities.get(i).y == y) return oEntities.get(i);
		}
		for(int i = 0; i < pEntities.size(); i++)
		{
			if(pEntities.get(i).x == x && pEntities.get(i).y == y) return pEntities.get(i);
		}
		return null;
	}

	public List<Entity> getPlayerEntities()
	{
		return pEntities;
	}

	public List<Entity> getOpponentEntities()
	{
		return oEntities;
	}

	public void setScroll(int xScroll, int yScroll)
	{
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	public int getXOffset()
	{
		return xOffset;
	}

	public int getYOffset()
	{
		return yOffset;
	}

	public int getXScroll()
	{
		return xScroll;
	}

	public int getYScroll()
	{
		return yScroll;
	}

	public void setXOffset(int xOffset)
	{
		this.xOffset = xOffset;
	}

	public void setYOffset(int yOffset)
	{
		this.yOffset = yOffset;
	}

	public void setState(String state)
	{
		if(currentState != null) currentState.flush();

		switch(state)
		{
			case "normal":
				currentState = normal;
				break;
			case "placing":
				currentState = placing;
				break;
			case "selecting":
				currentState = selecting;
				break;
			case "attack":
				currentState = attack;
				break;
		}

		currentState.onSet();
	}

	public LevelState getState()
	{
		return currentState;
	}

	public void init(Player player)
	{
		this.player = player;
		normal.init(this, player);
		placing.init(this, player);
		selecting.init(this, player);
		attack.init(this, player);
	}

	public void setOpponent(Opponent opp)
	{
		this.opponent = opp;
	}

	public void resetUnits()
	{
		for(int i = 0; i < pEntities.size(); i++)
			pEntities.get(i).used = false;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}