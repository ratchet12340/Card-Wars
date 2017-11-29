package com.paragongames.tcgproject.graphics;

import java.util.Random;

import com.paragongames.tcgproject.level.tile.Tile;

public class Screen
{
	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private final int ALPHA_COLOR = 0xffff00ff;

	public int xOffset, yOffset;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	private Random random = new Random();

	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++)
			{
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if(color == ALPHA_COLOR) continue;

				if(hasAlpha(color))
				{
					int[] rgb1 = hexToRGB(color);
					int[] rgb2 = hexToRGB(pixels[xa + ya * width]);
					pixels[xa + ya * width] = blendColors(rgb1, rgb2);
				}
				else
					pixels[xa + ya * width] = color;
			}
		}
	}

	private int[] hexToRGB(int hex)
	{
		int[] rgb = new int[4];
		rgb[0] = (hex >> 24) & 0xFF;
		rgb[1] = (hex >> 16) & 0xFF;
		rgb[2] = (hex >> 8) & 0xFF;
		rgb[3] = (hex >> 0) & 0xFF;

		return rgb;
	}

	private boolean hasAlpha(int hex)
	{
		if(((hex >> 24) & 0xFF) < 255) return true;
		return false;
	}

	private int blendColors(int[] rgb1, int[] rgb2)
	{
		int newR = (rgb1[1] * rgb1[0] + rgb2[1] * (255 - rgb1[0])) / 255;
		int newG = (rgb1[2] * rgb1[0] + rgb2[2] * (255 - rgb1[0])) / 255;
		int newB = (rgb1[3] * rgb1[0] + rgb2[3] * (255 - rgb1[0])) / 255;

		return ((newR & 0xFF) << 16) | ((newG & 0xFF) << 8) | ((newB & 0xFF) << 0);
	}

	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++)
			{
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if(col == ALPHA_COLOR) continue;
				if(col == 0xffffffff)
					pixels[xa + ya * width] = color;
				else
				{
					if(hasAlpha(col))
					{
						int[] rgb1 = hexToRGB(col);
						int[] rgb2 = hexToRGB(pixels[xa + ya * width]);
						pixels[xa + ya * width] = blendColors(rgb1, rgb2);
					}
					else
						pixels[xa + ya * width] = col;
				}
			}
		}
	}

	/*
	 * xp, yp = xposition, yposition xa, ya = absolute position of x and y
	 * (position + which pixel the loops is on)
	 */

	public void renderTile(int xp, int yp, Sprite sprite)
	{
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < sprite.SIZE; y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sprite.SIZE; x++)
			{
				int xa = x + xp;
				if(xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];

			}
		}
	}

	public void renderPlayer(int xp, int yp, Sprite sprite, int flip)
	{
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < 32; y++)
		{
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = 31 - y;
			for(int x = 0; x < 32; x++)
			{
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 31 - x;
				if(xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * 32];
				if(color != ALPHA_COLOR) pixels[xa + ya * width] = color;

			}
		}
	}

	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}