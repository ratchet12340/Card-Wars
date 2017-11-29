package com.paragongames.tcgproject.level.tile;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;

public class Tile
{
	public final int id;
	
	public boolean isMinable, isFarmable, isWater;
	public int x, y;

	public static Tile[] tiles = new Tile[256];
	public static Tile grass = new GrassTile(0);
	public static Tile water = new WaterTile(1);
	public static Tile rock = new RockTile(2);
	public static Tile farmable = new FarmableTile(3);
	public static Tile wall = new WallTile(4);
	public static Tile voidTile = new VoidTile(5);

	public static final int colorGrass = 0xff44b239;
	public static final int colorRock = 0xffafafaf;
	public static final int colorWater = 0xff3d7ace;
	public static final int colorFarmable = 0xff3b9a31;
	public static final int colorWall = 0xff505050;

	public Tile(int id)
	{
		this.id = id;
		if(tiles[id] != null) throw new RuntimeException("Tile with this id already exists!");
		tiles[id] = this;
	}

	public void render(int x, int y, Level level, Screen screen)
	{

	}

	public boolean solid()
	{
		return false;
	}
}