package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;

public class WaterTile extends Tile
{
	public WaterTile(int id)
	{
		super(id);
		isWater = true;
	}

	public void render(int x, int y, Level level, Screen screen)
	{	
		boolean u = level.getTile(x, y - 1).isWater;
		boolean d = level.getTile(x, y + 1).isWater;
		boolean l = level.getTile(x - 1, y).isWater;
		boolean r = level.getTile(x + 1, y).isWater;

		boolean ul = level.getTile(x - 1, y - 1).isWater;
		boolean ur = level.getTile(x + 1, y - 1).isWater;
		boolean bl = level.getTile(x - 1, y + 1).isWater;
		boolean br = level.getTile(x + 1, y + 1).isWater;

		if(!u && !l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.ulwater);
		}
		else if(u && !l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.mlwater);
		}
		else if(!u && l)
		{
			screen.renderTile(x * 24, y * 24, Sprite.umwater);
		}

		if(!u && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.urwater);
		}
		else if(u && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.mrwater);
		}
		if(!u && r)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.umwater);
		}

		if(!d && !l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.blwater);
		}
		else if(d && !l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.mlwater);
		}
		else if(!d && l)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.bmwater);
		}

		if(!d && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.brwater);
		}
		else if(d && !r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.mrwater);
		}
		else if(!d && r)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.bmwater);
		}

		if(u && l && !ul)
		{
			screen.renderTile(x * 24, y * 24, Sprite.brcwater);
		}
		else if(u && l && ul)
		{
			screen.renderTile(x * 24, y * 24, Sprite.mwater);
		}
		if(u && r && !ur)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.blcwater);
		}
		else if(u && r && ur)
		{
			screen.renderTile(x * 24 + 12, y * 24, Sprite.mwater);
		}
		if(d && l && !bl)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.urcwater);
		}
		else if(d && l && bl)
		{
			screen.renderTile(x * 24, y * 24 + 12, Sprite.mwater);
		}
		if(d && r && !br)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.ulcwater);
		}
		else if(d && r && br)
		{
			screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.mwater);
		}
	}

	public boolean solid()
	{
		return true;
	}
}
