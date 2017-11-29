package com.paragongames.tcgproject.card;

import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.entity.FarmEntity;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;

public class FarmCard extends Card
{
	private String name1, desc1, desc2, desc3, cost;
	
	public FarmCard()
	{
		sprite = Sprite.farmCard;
		entitySprite = Sprite.farm;
		name1 = "FARM";
		desc1 = "a0  d1";
		desc2 = "h10";
		desc3 = "";
		cost = "f+5";
	}
	
	public Entity getEntity()
	{
		return new FarmEntity();
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderSprite(x, y, sprite, false);
		Font.renderSmall(name1, x + 2, y + 2, 0, 0xffffff, screen);
		Font.renderSmall(desc1, x + 2, y + 34 + 0 * 6, 0, 0xffffff, screen);
		Font.renderSmall(desc2, x + 2, y + 34 + 1 * 6, 0, 0xffffff, screen);
		Font.renderSmall(desc3, x + 2, y + 34 + 2 * 6, 0, 0xffffff, screen);
		Font.renderSmall(cost, x + 2, y + 34 + 3 * 6, 0, 0xffffff, screen);
	}
}
