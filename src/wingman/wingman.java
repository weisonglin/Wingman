package wingman;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.io.File;

import moveble.Enemy;
import moveble.Island;
import moveble.MyPlane;
import moveble.Powerup;
import Explosion.Explosion;
import GameEvents.*;
import KeyControl.KeyControl;
import Sound.Sound;

//import java.awt.event.KeyListener;
/**
 *
 */
public class wingman extends JApplet implements Runnable
{

	private Thread thread;
	Image sea;
	Image myPlane;
	private BufferedImage bimg;
	private Graphics2D g2;
	int speed = 1, move = 0;
	private Random generator = new Random(1234567);
	private Random releaseE = new Random();
	private int NumOfE = 1;
	Island I1, I2, I3;
	private MyPlane[] m;
	int w = 640, h = 480; // fixed size window game
	private Enemy[] e1;
	private GameEvents gameEvents;
	public static boolean player2 = false;
	private Powerup P;
	private Explosion [] Exp;
	public static boolean boom1=false;
	public static boolean boom2=false;
	private int finalscore1=-1;
	private int finalscore2=-1;
	private Sound sound;

	// public static int i=-1;

	public void init()
	{
		setFocusable(true);
		setBackground(Color.white);
		Image island1, island2, island3, enemyImg, powerup;
		e1 = new Enemy[10];
		m = new MyPlane[3];
		Exp=new Explosion[20];

		try
		{
			// sea = getSprite("Resources/water.png");
			sea = ImageIO.read(new File("Resources/water.png"));
			island1 = ImageIO.read(new File("Resources/island1.png"));
			island2 = ImageIO.read(new File("Resources/island2.png"));
			island3 = ImageIO.read(new File("Resources/island3.png"));
			myPlane = ImageIO.read(new File("Resources/myplane_1.png"));
			enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));
			powerup = ImageIO.read(new File("Resources/powerup.png"));

			I1 = new Island(island1, 100, 100, speed, generator);
			I2 = new Island(island2, 200, 400, speed, generator);
			I3 = new Island(island3, 300, 200, speed, generator);
			P = new Powerup(powerup, 400, 400, speed, generator);
			e1[0] = new Enemy(enemyImg, 1, generator, 0);

			m[0] = new MyPlane(myPlane, 300, 360, 5, player2,Exp);
			sound=new Sound();

			gameEvents = new GameEvents();
			KeyControl key = new KeyControl(gameEvents);
			addKeyListener(key);
			gameEvents.addObserver(m[0]);

		} catch (Exception e)
		{
			System.out.print("No resources are found");
		}
	}

	public void drawBackGroundWithTileImage()
	{
		int TileWidth = sea.getWidth(this);
		int TileHeight = sea.getHeight(this);

		int NumberX = (int) (w / TileWidth);
		int NumberY = (int) (h / TileHeight);

		for (int i = -1; i <= NumberY; i++)
		{
			for (int j = 0; j <= NumberX; j++)
			{
				g2.drawImage(sea, j * TileWidth, i * TileHeight
						+ (move % TileHeight), TileWidth, TileHeight, this);
			}
		}
		move += speed;
	}

	public void drawDemo()
	{
		
		sound.playBack();
		
		//sound.loop(sound.LOOP_CONTINUOUSLY);  
		drawBackGroundWithTileImage();
		I1.update(h, w);
		I2.update(h, w);
		I3.update(h, w);
		P.update(gameEvents, m);
		for (int i = 0; e1[i] != null && i < 10; i++)
		{
			e1[i].update(gameEvents, m,Exp);
			// m.update_bullet(gameEvents,e1[i]);
		}
		for (int i = 0;i < 2; i++)
		{
			if(m[i]!=null)
			m[i].update_bullet(gameEvents, e1,Exp);
		}

		if (player2 )
		{
			if(m[1]==null&&boom2==false)
			{
			m[1] = new MyPlane(myPlane, 500, 360, 5, player2,Exp);
			gameEvents.addObserver(m[1]);
			}
		} else
			m[1] = null;

		

		I1.draw(this, g2);
		I2.draw(this, g2);
		I3.draw(this, g2);
		P.draw(this, g2);
		for (int i = 0;i < 2; i++)
		{
			if(m[i]!=null)
			m[i].draw_heathBar(this, g2);
		}
		for (int i = 0; i < 2; i++)
		{
			if(m[i]!=null)
			m[i].draw(this, g2);
		}

		for (int i = 0; e1[i] != null && i < 10; i++)
		{
			e1[i].draw(this, g2);
		}
		
		for(int i=0;Exp[i]!=null&&i<20;i++)
			Exp[i].draw(this, g2);

		if (NumOfE < 9 && releaseE.nextFloat() > 0.998)
		{
			float dir = releaseE.nextFloat();
			if (dir > 0.666)
			{
				Image enemyImage = null;
				try
				{
					enemyImage = ImageIO
							.read(new File("Resources/enemy2_1.png"));
				} catch (Exception e)
				{
					System.out.print("No resources are found");
				}
				e1[NumOfE] = new Enemy(enemyImage, 1, generator, 1);
				NumOfE++;

			} else if (dir < 0.666 && dir > 0.333)
			{
				Image enemyImage = null;
				try
				{
					enemyImage = ImageIO
							.read(new File("Resources/enemy3_1.png"));
				} catch (Exception e)
				{
					System.out.print("No resources are found");
				}
				e1[NumOfE] = new Enemy(enemyImage, 1, generator, 2);
				NumOfE++;

			} else
			{
				Image enemyImg = null;
				try
				{
					enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));
				} catch (Exception e)
				{
					System.out.print("No resources are found");
				}
				e1[NumOfE] = new Enemy(enemyImg, 1, generator, 0);
				NumOfE++;
			}
		}
		
		if(boom1)
		{
			if(finalscore1==-1)
			{
			finalscore1=m[0].getNumOfkill();
			m[0]=null;
			}

		}
	
		if(boom2)
		{
			if(finalscore2==-1)
			{
			finalscore2=m[1].getNumOfkill();
			m[1]=null;
			}

		//	boom2=false;
		}
		if(boom1&&(player2==false)||boom1&&boom2)
		{
			Image gameover, score;
			try{
				gameover = ImageIO.read(new File("Resources/gameOver.png"));
				g2.drawImage(gameover, 200, 100, this);
				score = ImageIO.read(new File("Resources/bottom.png"));
				g2.drawImage(score, 0, 275, this);
				g2.setColor(Color.yellow);
				g2.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
				g2.drawString("Player1  ",30,300);
				g2.drawString(String.valueOf(finalscore1), 250, 300);
				if(finalscore1>50)
					g2.drawString("Well Done!!",200,325);
				else
					g2.drawString("Ask Weisong if he will teach you!!",200,325);
				if(boom2)
				{
					g2.drawImage(score, 0, 350, this);
					g2.drawString("Player2  ",30,375);
					g2.drawString(String.valueOf(finalscore2), 250, 375);
					if(finalscore1>50)
						g2.drawString("Well Done!!",200,400);
					else
						g2.drawString("Ask Weisong if he will teach you!!",200,400);
					
				}
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void paint(Graphics g)
	{
		if (bimg == null)
		{
			Dimension windowSize = getSize();
			bimg = (BufferedImage) createImage(640,
					480);
			g2 = bimg.createGraphics();
			// m.set_graphic(g2);
			// e1.set_graphic(g2);
		}
		drawDemo();
		g.drawImage(bimg, 0, 0, this);
	}

	public void start()
	{
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public void run()
	{

		Thread me = Thread.currentThread();
		while (thread == me)
		{
			repaint();
			try
			{
				thread.sleep(15);
			} catch (InterruptedException e)
			{
				break;
			}

		}
	}

	public static void main(String argv[])
	{
		final wingman demo = new wingman();
		demo.init();
		JFrame f = new JFrame("Scrolling Shooter");
		f.addWindowListener(new WindowAdapter()
		{
		});
		f.getContentPane().add("Center", demo);
		f.pack();
		f.setSize(new Dimension(640, 480));
		f.setVisible(true);
		f.setResizable(false);

		demo.start();
	}

}
