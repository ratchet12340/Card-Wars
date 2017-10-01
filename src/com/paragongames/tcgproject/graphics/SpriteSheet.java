package com.paragongames.tcgproject.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH;
	public final int SPRITE_HEIGHT;
	private int sheetWidth;
	private int sheetHeight;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/tiles/tiles.png", 216);
	public static SpriteSheet gui = new SpriteSheet("/textures/sheets/gui/gui.png", 256);
	public static SpriteSheet cards = new SpriteSheet("/textures/sheets/cards/cards1.png", 74, 100);
	public static SpriteSheet entities = new SpriteSheet("/textures/sheets/entities/entity1.png", 216);
	
	public SpriteSheet(String path, int size)
	{
		this.path = path;
		this.SIZE = size;
		this.SPRITE_HEIGHT = size;
		this.SPRITE_WIDTH = size;
		pixels = new int[1 * 1];
		load();
	}
	
	public SpriteSheet(String path, int width, int height)
	{
		this.path = path;
		this.SIZE = -1;
		this.SPRITE_HEIGHT = height;
		this.SPRITE_WIDTH = width;
		pixels = new int[1 * 1];
		load();
	}
	
	private void load()
	{
		try
		{
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			sheetWidth = image.getWidth();
			sheetHeight = image.getHeight();
			pixels = new int[sheetWidth * sheetHeight];
			image.getRGB(0, 0, sheetWidth, sheetHeight, pixels, 0, sheetWidth);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getSheetWidth()
	{
		return sheetWidth;
	}
	
	public int getSheetHeight()
	{
		return sheetHeight;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}