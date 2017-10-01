package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;

public class FarmableTile extends Tile
{
	public FarmableTile(int id)
	{
		super(id);
		isFarmable = true;
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x * 24, y * 24, Sprite.farmable);
	}
}
