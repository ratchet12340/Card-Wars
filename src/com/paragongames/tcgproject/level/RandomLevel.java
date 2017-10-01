package com.paragongames.tcgproject.level;

import java.util.Random;

import com.paragongames.tcgproject.level.tile.Tile;

public class RandomLevel extends Level
{
	private static final Random random = new Random();

	public RandomLevel(int width, int height)
	{
		super(width, height);
	}

	protected void generateLevel()
	{
		tiles = new int[width * height];
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int rand = random.nextInt(3) + 1;
				if(rand == 1) tiles[x + y * width] = Tile.colorGrass;
				if(rand == 2) tiles[x + y * width] = Tile.colorWater;
				if(rand == 3) tiles[x + y * width] = Tile.colorRock;
			}
		}
	}
}
