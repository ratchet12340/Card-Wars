package com.paragongames.tcgproject.graphics.ui;

import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.util.Vector2i;

public class UICardButton extends UIButton
{
	public static final int yOriginal = 0;
	public static final int yAdjusted = -25;
	
	public boolean selected = false;
	public boolean deselected = false;

	public UICardButton(Vector2i position, Vector2i size, UILabel label, Sprite sprite, UIActionListener actionListener)
	{
		super(position, size, label, sprite, actionListener);
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof UICardButton)) return false;

		UICardButton button = (UICardButton) obj;

		return this.position.x == button.position.x && this.position.y == button.position.y;
	}

	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		if(inside && pressed)
		{
			pressed = false;
			if(selected)
			{
				actionListener.perform();
				buttonListener.released(this);
			}
			else if(!selected)
			{
				actionListener.perform();
				buttonListener.released(this);
				selected = true;
			}
			return true;
		}
		return false;
	}
}