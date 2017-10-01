package com.paragongames.tcgproject.game.states;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.layers.Layer;
import com.paragongames.tcgproject.graphics.ui.UIActionListener;
import com.paragongames.tcgproject.graphics.ui.UIButton;
import com.paragongames.tcgproject.graphics.ui.UILabel;
import com.paragongames.tcgproject.graphics.ui.UIManager;
import com.paragongames.tcgproject.graphics.ui.UIPanel;
import com.paragongames.tcgproject.input.Mouse;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.level.states.NormalState;
import com.paragongames.tcgproject.players.Opponent;
import com.paragongames.tcgproject.players.Player;
import com.paragongames.tcgproject.util.Vector2i;
import com.paragongames.tcgproject.util.Warning;
import com.paragongames.tcgproject.util.WarningType;

public class PlayingState extends Canvas implements GameState
{
	private static final long serialVersionUID = 1L;
	private Level level;
	private Player player;
	private Opponent opponent;
	private int xScroll;
	private int yScroll;
	private static boolean playerTurn = true;

	private static UIManager uiManager;
	private UIPanel turnPanel;

	private static List<Layer> layerStack = new ArrayList<Layer>();

	public PlayingState()
	{
		level = Level.test;
		addLayer(level);
		uiManager = new UIManager();
		addLayer(uiManager);
		player = new Player();
		player.setLevel(level);
		level.init(player);
		opponent = new Opponent(this);
		opponent.setLevel(level);
		level.setOpponent(opponent);
	}

	public void init(int screenWidth, int screenHeight)
	{
		Mouse mouse = new Mouse(this);
		xScroll = level.getXOffset() - screenWidth / 2;
		yScroll = level.getYOffset() - screenHeight / 2;
		level.setXOffset(screenWidth / 2);
		level.setYOffset(screenHeight / 2);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		turnPanel = new UIPanel(new Vector2i(Game.getGameWidth() - Sprite.endTurn.getWidth() - 5, 5), new Vector2i(Sprite.endTurn.getWidth(), Sprite.endTurn.getHeight()), null, uiManager);
		UIButton button = new UIButton(new Vector2i(0, 0), new Vector2i(Sprite.endTurn.getWidth(), Sprite.endTurn.getHeight()), null, Sprite.endTurn, new UIActionListener()
		{
			public void perform()
			{
				if(level.getState() instanceof NormalState)
				{
					endTurn();
					level.resetUnits();

				}
			}
		});
		UILabel label1 = new UILabel(new Vector2i(Sprite.endTurn.getWidth() / 2 - (3 * 8 / 2), 5), "END", 0xffffffff);
		UILabel label2 = new UILabel(new Vector2i(Sprite.endTurn.getWidth() / 2 - (4 * 8 / 2), 7 + 8), "TURN", 0xffffffff);

		uiManager.addPanel(turnPanel);
		turnPanel.addComponent(button);
		turnPanel.addComponent(label1);
		turnPanel.addComponent(label2);
	}

	public void addLayer(Layer layer)
	{
		layerStack.add(layer);
	}

	public void onEvent(Event event)
	{
		if(playerTurn)
		{
			for(int i = layerStack.size() - 1; i >= 0; i--)
			{
				layerStack.get(i).onEvent(event);
			}
		}
	}

	public void update()
	{
		level.setScroll(xScroll, yScroll);

		if(playerTurn)
		{
			for(int i = 0; i < layerStack.size(); i++)
			{
				layerStack.get(i).update();
			}

			player.update();
		}
		if(!playerTurn)
		{
			opponent.update();
		}
	}

	public void render(Screen screen)
	{
		xScroll = level.getXOffset() - screen.width / 2;
		yScroll = level.getYOffset() - screen.height / 2;
		level.setScroll(xScroll, yScroll);

		for(int i = 0; i < layerStack.size(); i++)
		{
			layerStack.get(i).render(screen);
		}

		player.render(screen);
	}

	public static UIManager getUIManager()
	{
		return uiManager;
	}

	public static List<Layer> getLayerStack()
	{
		return layerStack;
	}

	public void endTurn()
	{
		if(playerTurn)
		{
			Warning.getInstance().warn(WarningType.PLAYER_TURN_END);
		}
		else
		{
			Warning.getInstance().warn(WarningType.PLAYER_TURN_BEGIN);
			player.collectResources();
			player.canDraw = true;
		}
		playerTurn = !playerTurn;
	}
}