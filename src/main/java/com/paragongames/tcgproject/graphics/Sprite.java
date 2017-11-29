package com.paragongames.tcgproject.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite
{
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	private SpriteSheet sheet;

	// TILES
	public static Sprite ulwater = new Sprite(12, 0, 0, SpriteSheet.tiles);
	public static Sprite umwater = new Sprite(12, 1, 0, SpriteSheet.tiles);
	public static Sprite urwater = new Sprite(12, 2, 0, SpriteSheet.tiles);
	public static Sprite mlwater = new Sprite(12, 0, 1, SpriteSheet.tiles);
	public static Sprite mwater = new Sprite(12, 1, 1, SpriteSheet.tiles);
	public static Sprite mrwater = new Sprite(12, 2, 1, SpriteSheet.tiles);
	public static Sprite blwater = new Sprite(12, 0, 2, SpriteSheet.tiles);
	public static Sprite bmwater = new Sprite(12, 1, 2, SpriteSheet.tiles);
	public static Sprite brwater = new Sprite(12, 2, 2, SpriteSheet.tiles);

	public static Sprite ulcwater = new Sprite(12, 3, 0, SpriteSheet.tiles);
	public static Sprite urcwater = new Sprite(12, 4, 0, SpriteSheet.tiles);
	public static Sprite blcwater = new Sprite(12, 3, 1, SpriteSheet.tiles);
	public static Sprite brcwater = new Sprite(12, 4, 1, SpriteSheet.tiles);

	public static Sprite ulrock = new Sprite(12, 0, 0 + 3, SpriteSheet.tiles);
	public static Sprite umrock = new Sprite(12, 1, 0 + 3, SpriteSheet.tiles);
	public static Sprite urrock = new Sprite(12, 2, 0 + 3, SpriteSheet.tiles);
	public static Sprite mlrock = new Sprite(12, 0, 1 + 3, SpriteSheet.tiles);
	public static Sprite mrock = new Sprite(12, 1, 1 + 3, SpriteSheet.tiles);
	public static Sprite mrrock = new Sprite(12, 2, 1 + 3, SpriteSheet.tiles);
	public static Sprite blrock = new Sprite(12, 0, 2 + 3, SpriteSheet.tiles);
	public static Sprite bmrock = new Sprite(12, 1, 2 + 3, SpriteSheet.tiles);
	public static Sprite brrock = new Sprite(12, 2, 2 + 3, SpriteSheet.tiles);

	public static Sprite ulcrock = new Sprite(12, 3, 0 + 3, SpriteSheet.tiles);
	public static Sprite urcrock = new Sprite(12, 4, 0 + 3, SpriteSheet.tiles);
	public static Sprite blcrock = new Sprite(12, 3, 1 + 3, SpriteSheet.tiles);
	public static Sprite brcrock = new Sprite(12, 4, 1 + 3, SpriteSheet.tiles);

	public static Sprite grass = new Sprite(12, 5, 0, SpriteSheet.tiles);

	public static Sprite rock = new Sprite(24, 2, 0, SpriteSheet.tiles);
	public static Sprite tileSelect = new Sprite(24, 3, 0, SpriteSheet.tiles);
	public static Sprite farmable = new Sprite(24, 4, 0, SpriteSheet.tiles);
	public static Sprite tileGood = new Sprite(24, 5, 0, SpriteSheet.tiles);
	public static Sprite wall = new Sprite(24, 6, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(24, 0xabcccf);

	// CARDS
	public static Sprite warriorCard = new Sprite(36, 60, 0, 0, SpriteSheet.cards);
	public static Sprite farmCard = new Sprite(36, 60, 1, 0, SpriteSheet.cards);
	public static Sprite mineCard = new Sprite(36, 60, 2, 0, SpriteSheet.cards);
	public static Sprite cardBack = new Sprite(36, 60, 3, 0, SpriteSheet.cards);

	// ENTITIES
	public static Sprite warrior = new Sprite(24, 0, 0, SpriteSheet.entities);
	public static Sprite farm = new Sprite(24, 1, 0, SpriteSheet.entities);
	public static Sprite mine = new Sprite(24, 2, 0, SpriteSheet.entities);

	// UI
	public static Sprite title = new Sprite("/title_screen.png");
	public static Sprite cursor = new Sprite("/textures/sheets/gui/cursor.png");
	public static Sprite endTurn = new Sprite("/textures/sheets/gui/end_turn.png");

	public static Sprite uiTest = new Sprite(96, 0, 1, SpriteSheet.gui);
	public static Sprite uiButtonTest = new Sprite(64, 32, 3, 0, SpriteSheet.gui);

	public static Sprite uiTopRightCorner = new Sprite(32, 0, 0, SpriteSheet.gui);
	public static Sprite uiTopLeftCorner = new Sprite(32, 1, 0, SpriteSheet.gui);
	public static Sprite uiBottomLeftCorner = new Sprite(32, 2, 0, SpriteSheet.gui);
	public static Sprite uiBottomRightCorner = new Sprite(32, 3, 0, SpriteSheet.gui);
	public static Sprite uiTopFlat = new Sprite(32, 0, 1, SpriteSheet.gui);
	public static Sprite uiBottomFlat = new Sprite(32, 1, 1, SpriteSheet.gui);
	public static Sprite uiLeftFlat = new Sprite(32, 2, 1, SpriteSheet.gui);
	public static Sprite uiRightFlat = new Sprite(32, 3, 1, SpriteSheet.gui);

	// square sprites
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		loadSquare();
	}

	// rectangular sprites
	public Sprite(int width, int height, int x, int y, SpriteSheet sheet)
	{
		this.SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		loadRectangular();
	}

	// generic, one-color, rectangular sprites
	public Sprite(int width, int height, int color)
	{
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}

	// generic, one-color, square sprites
	public Sprite(int size, int color)
	{
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	// for use with split
	public Sprite(int[] pixels, int width, int height)
	{
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++)
		{
			this.pixels[i] = pixels[i];
		}
	}

	// for use with sprites contained in one image file
	public Sprite(String path)
	{
		SIZE = -1;
		load(path);
	}

	private void load(String path)
	{
		try
		{
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Sprite[] split(SpriteSheet sheet)
	{
		int amountOfSprites = (sheet.getSheetWidth() * sheet.getSheetHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amountOfSprites];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];

		for(int yp = 0; yp < sheet.getSheetHeight() / sheet.SPRITE_HEIGHT; yp++)
		{
			for(int xp = 0; xp < sheet.getSheetWidth() / sheet.SPRITE_WIDTH; xp++)
			{
				for(int y = 0; y < sheet.SPRITE_HEIGHT; y++)
				{
					for(int x = 0; x < sheet.SPRITE_WIDTH; x++)
					{
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getSheetWidth()];
					}
				}

				sprites[current] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
				current++;
			}
		}

		return sprites;
	}

	private void loadSquare()
	{
		for(int yy = 0; yy < SIZE; yy++)
		{
			for(int xx = 0; xx < SIZE; xx++)
			{
				pixels[xx + yy * SIZE] = sheet.pixels[(xx + this.x) + (yy + this.y) * sheet.SIZE];
			}
		}
	}

	private void loadRectangular()
	{
		for(int yy = 0; yy < height; yy++)
		{
			for(int xx = 0; xx < width; xx++)
			{
				pixels[xx + yy * width] = sheet.pixels[(xx + this.x) + (yy + this.y) * sheet.getSheetWidth()];
			}
		}
	}

	private void setColor(int color)
	{
		for(int i = 0; i < width * height; i++)
			pixels[i] = color;
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