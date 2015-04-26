package moveble;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Explosion.Explosion;
import GameEvents.GameEvents;

public class Enemy extends moveble
{

	// Image img;
	// int x, y, sizeX, sizeY, speed;
	// Random gen;
	// boolean show;
	private Bullet[] eb1;
	private int i = -1;
	private Enemy[] haha;

	// private Random releaseE= new Random();
	// private int NumOfE=1;

	public Enemy(Image img, int speed, Random gen, int powerup)
	{
		this.img = img;
		this.x = Math.abs(gen.nextInt() % (600 - 30));
		this.y = -20;
		this.speed = speed;
		this.gen = gen;
		this.show = true;
		width = img.getWidth(null);
		height = img.getHeight(null);
		// System.out.println("w:" + width + " y:" + height);
		eb1 = new Bullet[20];
		this.goodSide = false;
		this.powerup = powerup;
	}

	public void draw(ImageObserver obs, Graphics2D g2)
	{
		super.draw(obs, g2);
		for (int i = 0; eb1[i] != null && i < 19; i++)
		{
			eb1[i].draw(obs, g2);
		}
	}

	public void update_eBullet(GameEvents gameEvents, MyPlane[] m,Explosion[] Exp)
	{
		for (int i = 0; eb1[i] != null && i < 19; i++)
		{
			eb1[i].update(gameEvents, haha, m,Exp);
		}
	}

	public void update(GameEvents gameEvents, MyPlane[] m, Explosion[] Exp)
	{
		y += speed;
		if (y % 30 == 0)
		{
			if (gen.nextBoolean())
				x += 30 * speed;
			else
				x -= 30 * speed;
		}
		if (y % 100 == 0)
		{
			Image ebullet = null;
			try
			{
				ebullet = ImageIO.read(new File("Resources/enemybullet1.png"));
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i += 1;
			if (i == 19)
				i = 0;
			eb1[i] = new Bullet(ebullet, 2, x, y, goodSide, powerup, false);
		}

		update_eBullet(gameEvents, m,Exp);

		for (int i = 0; i < 2; i++)
		{
			if(m[i]!=null)
			{
			if (m[i].collision(x, y, width, height))
			{
				show = false;
				for (int j=0; j<20; j++)
				{
					if(Exp[j]==null)
					{
						Exp[j]=new Explosion(x,y,false);
						if(j==19)
							Exp[0]=null;
						else
							Exp[j+1]=null;
						break;
					}
					
				}
				// You need to remove this one and increase score etc
				if (i == 0)
					gameEvents.setValue("Explosion");
				else
					gameEvents.setValue("Explosion2");
				gameEvents.setValue("");
				this.reset();
				show = true;
			} else
				gameEvents.setValue("");
			}
		}
		if (y > 500)
			this.reset();

	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	public void reset()
	{
		this.x = Math.abs(gen.nextInt() % (600 - 30));
		this.y = -30;
	}
	/*
	 * public void draw(ImageObserver obs) { if (show) { g2.drawImage(img, x, y,
	 * obs); } }
	 */
}
