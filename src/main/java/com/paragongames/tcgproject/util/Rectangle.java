package com.paragongames.tcgproject.util;

public class Rectangle
{
	public Vector2i position;
	public Vector2i size;
	
	public Rectangle(Vector2i pos, Vector2i size)
	{
		this.position = pos;
		this.size = size;
	}
	
	public boolean contains(Vector2i vec)
	{
		int x0 = position.x;
		int x1 = position.x + size.x;
		int y0 = position.y;
		int y1 = position.y + size.y;
		
		if(vec.x >= x0 && vec.x <= x1 && vec.y >= y0 && vec.y <= y1)
			return true;
		else
			return false;
	}
}
