package com.paragongames.tcgproject.util;

public class Vector2i
{
	public int x, y;

	public Vector2i()
	{
		x = 0;
		y = 0;
	}

	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2i add(int n)
	{
		Vector2i vec = new Vector2i(this.x + n, this.y + n);
		return vec;
	}

	public Vector2i add(Vector2i vec)
	{
		return new Vector2i(this.x + vec.x, this.y + vec.y);
	}

	public void subtract(Vector2i vec)
	{
		this.x -= vec.x;
		this.y -= vec.y;
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) obj;
		return this.x == vec.x && this.y == vec.y;
	}
}