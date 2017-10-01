package com.paragongames.tcgproject.graphics.font;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.SpriteSheet;

public class Font
{
	private static SpriteSheet font = new SpriteSheet("/fonts/font.png", 8);
	private static Sprite[] characters = Sprite.split(font);
	
	private static SpriteSheet smallFont = new SpriteSheet("/fonts/font_small.png", 4, 6);
	private static Sprite[] smallCharacters = Sprite.split(smallFont);
	
	private static SpriteSheet fontShadow = new SpriteSheet("/fonts/font_shadow.png", 8);
	private static Sprite[] charactersShadow = Sprite.split(fontShadow);

	private static String charIndex = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"0123456789.,!" + //
			"?\'\"-+=/\\%()<>" + //
			":;gf";

	private static String smallCharIndex = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"0123456789.,!?\'" + //
			"\":;()+-=/\\%gfadh";

	public static void renderSmall(String text, int x, int y, int spacing, int color, Screen screen)
	{
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++)
		{
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if(currentChar == '\n')
			{
				line++;
				xOffset = 0;
			}
			int index = smallCharIndex.indexOf(currentChar);
			if(index == -1)
			{
				xOffset += 4 + spacing;
				continue;
			}
			screen.renderTextCharacter(x + xOffset, y + line * 6 + yOffset, smallCharacters[index], color, false);
			xOffset += 4 + spacing;
		}
	}
	
	public static void render(String text, int x, int y, int spacing, int color, boolean shadow, Screen screen)
	{
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++)
		{
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if(currentChar == '\n')
			{
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if(index == -1)
			{
				xOffset += 4 + spacing;
				continue;
			}
			if(shadow)
				screen.renderTextCharacter(x + xOffset, y + line * 8 + yOffset, charactersShadow[index], color, false);
			else
				screen.renderTextCharacter(x + xOffset, y + line * 8 + yOffset, characters[index], color, false);
			xOffset += 8 + spacing;
		}
	}
}
