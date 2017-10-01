package com.paragongames.tcgproject.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.paragongames.tcgproject.events.Event;
import com.paragongames.tcgproject.events.EventListener;
import com.paragongames.tcgproject.game.states.GameState;
import com.paragongames.tcgproject.game.states.MainMenuState;
import com.paragongames.tcgproject.game.states.PlayingState;
import com.paragongames.tcgproject.graphics.Screen;
import com.paragongames.tcgproject.input.Keyboard;
import com.paragongames.tcgproject.input.Mouse;
import com.paragongames.tcgproject.util.Warning;

public class Game extends Canvas implements Runnable, EventListener
{
	private static final long serialVersionUID = 1L;
	private static int width = 1280 / 3;
	private static int height = width / 16 * 9;
	private static int scale = 3;
	public static String title = "Card Wars! (indev)";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Mouse mouse;
	
	private boolean running = false;

	private Screen screen;
	
	private static GameState activeState;
	private static MainMenuState mainMenu;
	private static PlayingState playing;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game()
	{
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard(this);
		mouse = new Mouse(this);
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		playing = new PlayingState();
		mainMenu = new MainMenuState();
		playing.init(screen.width, screen.height);
		activeState = mainMenu;
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				frame.setTitle(title + "   |   " + updates + " updates/sec, " + frames + " frames/sec");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update()
	{
		key.update();
		activeState.update();
		Warning.getInstance().update();
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		activeState.render(screen);
		Warning.getInstance().render(screen);
		
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void setActiveState(int state)
	{
		if(state == 0)
			activeState = mainMenu;
		else if(state == 1)
			activeState = playing;
	}
	
	public void onEvent(Event event)
	{
		activeState.onEvent(event);
	}
	
	public static int getWindowWidth()
	{
		return width * scale;
	}
	
	public static int getWindowHeight()
	{
		return height * scale;
	}
	
	public static int getGameWidth()
	{
		return width;
	}
	
	public static int getGameHeight()
	{
		return height;
	}
	
	public static int getScale()
	{
		return scale;
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}