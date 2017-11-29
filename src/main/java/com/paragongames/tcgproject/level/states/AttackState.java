package com.paragongames.tcgproject.level.states;

import java.util.ArrayList;
import java.util.List;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventDispatcher;
import com.paragongames.tcgproject.events.types.MouseReleasedEvent;
import com.paragongames.tcgproject.game.Game;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.graphics.Sprite;
import com.paragongames.tcgproject.graphics.ui.UIManager;
import com.paragongames.tcgproject.graphics.ui.UIPanel;
import com.paragongames.tcgproject.graphics.ui.menu.UIMoveMenu;
import com.paragongames.tcgproject.level.Level;
import com.paragongames.tcgproject.players.Player;
import com.paragongames.tcgproject.util.Vector2i;

public class AttackState implements LevelState
{
	private Level level;

	private UIManager manager;
	private UIPanel panel;
	private UIMoveMenu menu;

	private List<Vector2i> attackTiles = new ArrayList<Vector2i>();

	public void init(Level level, Player player)
	{
		this.level = level;
		manager = new UIManager();
		menu = new UIMoveMenu(new Vector2i(0, 0), new Vector2i(50, 40), level);
		panel = new UIPanel(new Vector2i(0, 0), new Vector2i(Game.getGameWidth(), Game.getGameHeight()), null, manager);
		panel.addComponent(menu);
		manager.addPanel(panel);
	}

	public void update()
	{
		manager.update();

		if(menu.shouldAttack)
		{
			attack();
			menu.shouldAttack = false;
		}
		else if(menu.shouldWait) level.setState("normal");
	}

	public void attack()
	{
		int x = Level.currentEntityX;
		int y = Level.currentEntityY;
		if(level.getEntity(x - 1, y) != null) if(level.getEntity(x - 1, y).owner.equals("opponent")) attackTiles.add(new Vector2i(x - 1, y));
		if(level.getEntity(x + 1, y) != null) if(level.getEntity(x + 1, y).owner.equals("opponent")) attackTiles.add(new Vector2i(x + 1, y));
		if(level.getEntity(x, y - 1) != null) if(level.getEntity(x, y - 1).owner.equals("opponent")) attackTiles.add(new Vector2i(x, y - 1));
		if(level.getEntity(x, y + 1) != null) if(level.getEntity(x, y + 1).owner.equals("opponent")) attackTiles.add(new Vector2i(x, y + 1));
	}

	public void render(Screen screen)
	{
		if(!attackTiles.isEmpty())
		{
			for(int i = 0; i < attackTiles.size(); i++)
			{
				screen.renderSprite(attackTiles.get(i).x * 24, attackTiles.get(i).y * 24, Sprite.tileGood, true);
			}
		}
		else
		{
			manager.render(screen);
		}
	}

	public void onEvent(Event event)
	{
		if(attackTiles.isEmpty())
		{
			manager.onEvent(event);
		}
		else
		{
			EventDispatcher dispatcher = new EventDispatcher(event);
			dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> (onMouseReleased((MouseReleasedEvent) e)));
		}
	}

	public boolean onMouseReleased(MouseReleasedEvent e)
	{
		int x = (e.getX() / Game.getScale() + level.getXScroll()) / 24;
		int y = (e.getY() / Game.getScale() + level.getYScroll()) / 24;

		for(int i = 0; i < attackTiles.size(); i++)
		{
			if(x == attackTiles.get(i).x && y == attackTiles.get(i).y)
			{
				level.getEntity(Level.currentEntityX, Level.currentEntityY).attack(level.getEntity(x, y));
				level.setState("normal");
			}
		}

		return true;
	}

	public void onSet()
	{
		panel.position.x = Level.currentEntityX * 24 - level.getXScroll();
		panel.position.y = Level.currentEntityY * 24 - level.getYScroll();

		int x = Level.currentEntityX;
		int y = Level.currentEntityY;

		if(level.getEntity(x - 1, y) != null && level.getEntity(x - 1, y).owner.equals("opponent"))
		{
			addAttackOption();
			return;
		}
		if(level.getEntity(x + 1, y) != null && level.getEntity(x + 1, y).owner.equals("opponent"))
		{
			addAttackOption();
			return;
		}
		if(level.getEntity(x, y - 1) != null && level.getEntity(x, y - 1).owner.equals("opponent"))
		{
			addAttackOption();
			return;
		}
		if(level.getEntity(x, y + 1) != null && level.getEntity(x, y + 1).owner.equals("opponent"))
		{
			addAttackOption();
			return;
		}
		menu.options.add("WAIT");
	}

	private void addAttackOption()
	{
		menu.options.clear();
		menu.options.add("ATTACK");
		menu.options.add("WAIT");
	}

	public void flush()
	{
		attackTiles.clear();
		menu.options.clear();
		menu.shouldAttack = false;
		menu.shouldWait = false;
	}
}
