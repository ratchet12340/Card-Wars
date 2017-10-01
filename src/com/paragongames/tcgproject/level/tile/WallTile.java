package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;

public class WallTile extends Tile
{
	public WallTile(int id)
	{
		super(id);
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x * 24, y * 24, Sprite.wall);
	}
	
	public boolean solid()
	{
		return true;
	}
}
