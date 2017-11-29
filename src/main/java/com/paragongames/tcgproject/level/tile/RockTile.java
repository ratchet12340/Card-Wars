package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;

public class RockTile extends Tile
{	
	public RockTile(int id)
	{
		super(id);
		isMinable = true;
	}
	
	public void render(int x, int y, Level level, Screen screen)
	{
		boolean u = level.getTile(x, y - 1).isMinable;
		boolean d = level.getTile(x, y + 1).isMinable;
		boolean l = level.getTile(x - 1, y).isMinable;
		boolean r = level.getTile(x + 1, y).isMinable;

		boolean ul = level.getTile(x - 1, y - 1).isMinable;
		boolean ur = level.getTile(x + 1, y - 1).isMinable;
		boolean bl = level.getTile(x - 1, y + 1).isMinable;
		boolean br = level.getTile(x + 1, y + 1).isMinable;

		if(!u && !l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.ulrock);
		}
		else if(u && !l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.mlrock);
		}
		else if(!u && l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.umrock);
		}

		if(!u && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.urrock);
		}
		else if(u && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.mrrock);
		}
		if(!u && r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.umrock);
		}

		if(!d && !l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.blrock);
		}
		else if(d && !l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.mlrock);
		}
		else if(!d && l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.bmrock);
		}

		if(!d && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.brrock);
		}
		else if(d && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.mrrock);
		}
		else if(!d && r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.bmrock);
		}

		if(u && l && !ul)
		{
			screen.renderTile(x * 24, y * 24, Sprite.brcrock);
		}
		else if(u && l && ul)
		{
			screen.renderTile(x * 24, y * 24, Sprite.mrock);
		}
		if(u && r && !ur)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.blcrock);
		}
		else if(u && r && ur)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.mrock);
		}
		if(d && l && !bl)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.urcrock);
		}
		else if(d && l && bl)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.mrock);
		}
		if(d && r && !br)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.ulcrock);
		}
		else if(d && r && br)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.mrock);
		}
	}
	
	public boolean solid()
	{
		return true;
	}
}
