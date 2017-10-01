package com.paragongames.tcgproject.entity;

import java.util.Random;

import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.level.Level;

public abstract class Entity
{
	protected Level level;
	protected Sprite sprite;
	protected Random random = new Random();

	public String owner; // "player" or "opponent"
	public int health, attack, defense;
	public int x, y;
	public int foodCost, goldCost, moveCost;
	public boolean isMovable, isMine, isFarm, canSwim, used;

	public void update()
	{
		
	}
	
	public void render(Screen screen)
	{
		screen.renderSprite(x * 24, y * 24, sprite, true);
	}
	
	public boolean isRemoved()
	{
		return health <= 0;
	}
	
	public void attack(Entity entity)
	{
		this.health -= entity.attack - this.defense;
		entity.health -= this.attack - entity.defense;
	}
	
	public void init(Level level, int x, int y, String owner)
	{
		this.level = level;
		this.x = x;
		this.y = y;
		this.owner = owner;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}