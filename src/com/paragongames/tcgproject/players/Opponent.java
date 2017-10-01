package com.paragongames.tcgproject.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.paragongames.tcgproject.card.Card;
import com.paragongames.tcgproject.card.KnightCard;
import com.paragongames.tcgproject.entity.KnightEntity;
import com.paragongames.tcgproject.game.states.PlayingState;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.level.tile.GrassTile;

public class Opponent
{
	private Level level;
	private Random random = new Random();
	private PlayingState playingState;

	private List<Card> deck = new ArrayList<Card>();

	private long startTime = 0;

	public Opponent(PlayingState playingState)
	{
		this.playingState = playingState;

		for(int i = 0; i < 30; i++)
		{
			deck.add(new KnightCard());
		}
	}

	public void update()
	{
		if(startTime == 0)
		{
			startTime = System.currentTimeMillis();
		}

		if(System.currentTimeMillis() - startTime >= 1000)
		{
			startTime = 0;
			playCard();
			playingState.endTurn();
		}
	}

	private void playCard()
	{
		boolean cardPlayed = false;
		while(!cardPlayed)
		{
			int randx = random.nextInt(level.getWidth());
			int randy = random.nextInt(level.getHeight());

			if(level.getEntity(randx, randy) == null && level.getTile(randx, randy) instanceof GrassTile)
			{
				level.addOpponentEntity(new KnightEntity(), randx, randy);
				cardPlayed = true;
			}
		}
	}

	public void render(Screen screen)
	{

	}

	public void setLevel(Level level)
	{
		this.level = level;
	}
}
