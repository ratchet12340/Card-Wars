package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;

public class GrassTile extends Tile
{
	public GrassTile(int id)
	{
		super(id);
	}
	
	public void render(int x, int y, Level level, Screen screen)
	{
		screen.renderTile(x * 24, y * 24, Sprite.grass);
		screen.renderTile(x * 24 + 12, y * 24, Sprite.grass);
		screen.renderTile(x * 24, y * 24 + 12, Sprite.grass);
		screen.renderTile(x * 24 + 12, y * 24 + 12, Sprite.grass);
	}
}
