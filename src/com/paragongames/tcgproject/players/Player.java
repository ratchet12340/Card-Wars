package com.paragongames.tcgproject.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paragongames.tcgproject.card.Card;
import com.paragongames.tcgproject.card.FarmCard;
import com.paragongames.tcgproject.card.MineCard;
import com.paragongames.tcgproject.card.KnightCard;
import com.paragongames.tcgproject.entity.Entity;
import com.paragongames.tcgproject.entity.FarmEntity;
import com.paragongames.tcgproject.entity.MineEntity;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.game.states.PlayingState;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.font.Font;
import com.paragongames.tcgproject.graphics.ui.UIActionListener;
import com.paragongames.tcgproject.graphics.ui.UIButton;
import com.paragongames.tcgproject.graphics.ui.UICardButton;
import com.paragongames.tcgproject.graphics.ui.UICardButtonListener;
import com.paragongames.tcgproject.graphics.ui.UIPanel;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.level.states.NormalState;
import com.paragongames.tcgproject.level.states.PlacingState;
import com.paragongames.tcgproject.level.states.SelectingState;
import com.paragongames.tcgproject.util.Vector2i;
import com.paragongames.tcgproject.util.Warning;
import com.paragongames.tcgproject.util.WarningType;

public class Player
{
	public final int HAND_LIMIT = 8;
	
	private List<Card> deck = new ArrayList<Card>();
	private List<Card> hand = new ArrayList<Card>();
	private List<UICardButton> handButtons = new ArrayList<UICardButton>();

	private UIPanel cardPanel;
	private UIPanel deckPanel;

	private Level level;
	private Entity currentEntity;

	public boolean canDraw;
	private int gold = 100;
	private int food = 0;
	private int foodCap = 100;
	private int goldPerTurn = 1;

	public Player()
	{
		for(int i = 0; i < 10; i++)
		{
			deck.add(new FarmCard());
			deck.add(new MineCard());
			deck.add(new KnightCard());
		}
		
		canDraw = true;
		initUI();
	}
	
	private void initUI()
	{
		cardPanel = new UIPanel(new Vector2i(10, Game.getGameHeight() - Card.CARD_HEIGHT - 5), new Vector2i(288, Card.CARD_HEIGHT + 5), null, PlayingState.getUIManager());
		PlayingState.getUIManager().addPanel(cardPanel);

		deckPanel = new UIPanel(new Vector2i(Game.getGameWidth() - 100 - 8, Game.getGameHeight() - Card.CARD_HEIGHT - 5 - 8), new Vector2i(100, Card.CARD_HEIGHT + 8 + 5), null, PlayingState.getUIManager());
		PlayingState.getUIManager().addPanel(deckPanel);
		UIButton button1 = new UIButton(new Vector2i(0, 0), new Vector2i(Card.CARD_HEIGHT + 8, Card.CARD_HEIGHT + 8), null, null, new UIActionListener()
		{
			public void perform()
			{
				if(canDraw)
				{
					draw();
					canDraw = false;
				}
				else
				{
					Warning.getInstance().warn(WarningType.DRAW_LIMIT);
				}
			}
		});
		deckPanel.addComponent(button1);
	}

	public void setLevel(Level level)
	{
		this.level = level;
	}

	public void fillHand()
	{
		if(deck.size() == 0)
		{
			Warning.getInstance().warn(WarningType.DECK_EMPTY);
			return;
		}
		if(hand.size() == HAND_LIMIT)
		{
			Warning.getInstance().warn(WarningType.HAND_FULL);
			return;
		}
		for(int i = hand.size(); i < HAND_LIMIT; i++)
			draw();
	}

	public void draw()
	{
		if(deck.size() == 0)
		{
			Warning.getInstance().warn(WarningType.DECK_EMPTY);
			return;
		}
		if(hand.size() == HAND_LIMIT)
		{
			Warning.getInstance().warn(WarningType.HAND_FULL);
			return;
		}
		addToHand();
		deck.remove(deck.size() - 1);
	}

	private void addToHand()
	{
		Card draw = deck.get(deck.size() - 1);
		hand.add(draw);

		Vector2i position = new Vector2i(0, 0);
		Vector2i size = new Vector2i(Card.CARD_WIDTH, Card.CARD_HEIGHT);
		UICardButton button = new UICardButton(position, size, null, null, null);
		button.setActionListener(new UIActionListener()
		{
			public void perform()
			{
				if(button.selected && (level.getState().getClass().equals(PlacingState.class) || level.getState().getClass().equals(SelectingState.class)))
					level.setState("normal");
				else if(!button.selected && (level.getState().getClass().equals(NormalState.class) || level.getState().getClass().equals(SelectingState.class)))
				{
					level.setState("placing");
				}
				resetHand();
			}
		});
		UICardButtonListener buttonListener = new UICardButtonListener();
		button.setButtonListener(buttonListener);
		cardPanel.addComponent(button);
		handButtons.add(button);
		updatePositions();
	}

	public void update()
	{
		for(int i = 0; i < handButtons.size(); i++)
		{
			if(handButtons.get(i).selected)
			{
				handButtons.get(i).position.y = UICardButton.yAdjusted;
				currentEntity = hand.get(i).getEntity();
			}
		}

		updatePositions();
	}

	public void render(Screen screen)
	{
		renderHand(screen);
		renderDeck(screen);
	}

	public boolean canMake(Entity entity)
	{
		return (gold >= entity.goldCost) && (foodCap >= (entity.foodCost + food));
	}

	public void calculateResources(Entity entity)
	{
		if(entity instanceof FarmEntity)
		{
			foodCap += 5;
			gold -= entity.goldCost;
		}
		else if(entity instanceof MineEntity)
		{
			goldPerTurn += 5;
			gold -= entity.goldCost;
		}
		else
		{
			gold -= entity.goldCost;
			food += entity.foodCost;
		}
	}

	public void collectResources()
	{
		gold += goldPerTurn;
	}
	
	private void updatePositions()
	{
		int midPanelX = (cardPanel.position.x + cardPanel.size.x) / 2;
		int pos = midPanelX - hand.size() * 18;

		for(int i = 0; i < handButtons.size(); i++)
		{
			handButtons.get(i).position.x = pos;
			pos += 36;
		}
	}

	private void renderHand(Screen screen)
	{
		for(int i = 0; i < handButtons.size(); i++)
		{
			hand.get(i).render(handButtons.get(i).getAbsolutePosition().x, handButtons.get(i).getAbsolutePosition().y, screen);
		}

		String str1 = "gGOLD: " + gold + "(+" + goldPerTurn + ")";
		String str2 = "fFOOD: " + food + "/" + foodCap;
		Font.render(str1, 5, 5, 0, 0xffffff, true, screen);
		Font.render(str2, 5, 15, 0, 0xffffff, true, screen);
	}

	public void renderDeck(Screen screen)
	{
		int numCards = (int)Math.sqrt((double)deck.size());
		int renderx = deckPanel.position.x + Card.CARD_WIDTH + 2 * 4;
		int rendery = deckPanel.position.y + 2 * 4;
		for(int i = 0; i < numCards; i++)
		{
			screen.renderSprite(renderx - i * 2, rendery - i * 2, Sprite.cardBack, false);
		}
	}

	public void resetHand()
	{
		for(int i = 0; i < handButtons.size(); i++)
		{
			handButtons.get(i).selected = false;
			handButtons.get(i).position.y = UICardButton.yOriginal;
		}
	}

	public void removeCard()
	{
		Iterator<UICardButton> itr;
		Iterator<Card> itr_card = hand.iterator();
		for(itr = handButtons.iterator(); itr.hasNext();)
		{
			UICardButton b = itr.next();
			itr_card.next();
			if(b.selected)
			{
				b.panel.removeComponent(b);
				itr.remove();
				itr_card.remove();
				break;
			}
		}
	}

	public Entity getCurrentEntity()
	{
		return currentEntity;
	}
	
	public List<Card> getDeck()
	{
		return deck;
	}
}