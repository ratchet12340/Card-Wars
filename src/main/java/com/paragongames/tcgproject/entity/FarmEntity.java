package com.paragongames.tcgproject.entity;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;

public class FarmEntity extends Entity
{
	public FarmEntity()
	{
		sprite = Sprite.farm;
		
		attack = 0;
		health = 10;
		defense = 1;
		
		isFarm = true;
	}

	public void update()
	{

	}

	public void render(Screen screen)
	{
		super.render(screen);
		Font.renderSmall("" + health, x * 24 + 24 - (4 * (health / 10 + 1)) - screen.xOffset, y * 24 + 24 - 6 - screen.yOffset, 0, 0xffffffff, screen);
	}
}
