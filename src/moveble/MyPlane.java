package moveble;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import Explosion.Explosion;
import GameEvents.*;
import HealthBar.HealthBar;

//import HealthBar.HealthBar;

public class MyPlane extends moveble implements Observer
{
	// Image img;
	// int x, y, speed, width, height;
	// Rectangle bbox;
	// boolean boom;
	private Bullet[] b1;
	private int i = -1;
	private HealthBar h1;
	private MyPlane[] hahaha;
	private boolean player2;
	private int NumOfkill = 0;
	private boolean unbeat = false;
	private int blood = 4;
	private Explosion[] Exp;

	public MyPlane(Image img, int x, int y, int speed, boolean player2,
			Explosion[] Exp)
	{
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.boom = false;
		this.show = true;
		b1 = new Bullet[20];
		this.goodSide = true;
		h1 = new HealthBar(player2);
		this.player2 = player2;
		this.Exp = Exp;

	}

	/*
	 * public void set_graphic(Graphics2D g) { super.set_graphic(g); for (int
	 * i=0; i<10; i++) b1[i].set_graphic(g); }
	 */
	public int getNumOfkill()
	{
		return NumOfkill;
	}

	public void draw(ImageObserver obs, Graphics2D g2)
	{
		// h1.draw(obs, g2);
		if (unbeat == true)
			draw_shield(obs, g2);
		super.draw(obs, g2);
		for (int i = 0; b1[i] != null && i < 19; i++)
		{
			b1[i].draw(obs, g2);
		}

	}

	public void draw_shield(ImageObserver obs, Graphics2D g2)
	{
		Image shield = null;
		try
		{
			shield = ImageIO.read(new File("Resources/haha.png"));

		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		g2.drawImage(shield, x - 10, y - 5, obs);
	}

	public void draw_heathBar(ImageObserver obs, Graphics2D g2)
	{
		h1.draw(obs, g2,x,y);
	}

	public void update_bullet(GameEvents gameEvents, Enemy[] e1, Explosion[] Exp)
	{
		for (int i = 0; b1[i] != null && i < 19; i++)
		{
			b1[i].update(gameEvents, e1, hahaha, Exp);
		}
	}
	
	public void check_dead(boolean player2)
	{
		if (blood == 0)
		{
			if(player2)
				wingman.wingman.boom2 = true;
			else
				wingman.wingman.boom1=true;
			for (int j = 0; j < 20; j++)
			{
				if (Exp[j] == null)
				{
					Exp[j] = new Explosion(x, y, true);
					if (j == 19)
						Exp[0] = null;
					else
						Exp[j + 1] = null;
					break;
				}
			}
		}
	}

	public void update(Observable obj, Object arg)
	{
		if (player2 == false)
		{
			GameEvents ge = (GameEvents) arg;

			if (ge.type == 1)
			{
				KeyEvent e = (KeyEvent) ge.event;
				switch (e.getKeyChar())
				{
				case 'a':
					if (x > 0)
						x -= 5 * speed;
					break;
				case 'd':
					if (x < 570)
						x += 5 * speed;
					break;
				case 'w':
					if (y > 0)
						y -= 5 * speed;
					break;
				case 's':
					if (y < 385)
						y += 5 * speed;
					break;
				case 'o':
					if (unbeat == false)
						unbeat = true;
					else
						unbeat = false;

				default:
					if (e.getKeyChar() == ' ')
					{
						System.out.println("Fire");
						if (powerup == 1)
						{
							Image bullet = null;
							try
							{
								bullet = ImageIO.read(new File(
										"Resources/weisonghahaha.png"));
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							i += 1;
							if (i == 19)
								i = 0;
							b1[i] = new Bullet(bullet, 5, x - 40, y - 100,
									goodSide, powerup, player2);
							powerup--;
							System.out.println("GO !!");
						} else
						{
							Image bullet = null;
							try
							{
								bullet = ImageIO.read(new File(
										"Resources/bullet.png"));
							} catch (IOException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							i += 1;
							if (i == 19)
								i = 0;
							b1[i] = new Bullet(bullet, 5, x + 16, y, goodSide,
									powerup, player2);
						}
					} else if (e.getKeyChar() == '+')
					{
						if (wingman.wingman.player2 == false)
						{
							System.out.println("two player mode");
							wingman.wingman.player2 = true;
						} else if (wingman.wingman.player2 == true)
						{
							System.out.println("single player mode");
							wingman.wingman.player2 = false;
						}

					}
				}
			}

			else if (ge.type == 2)
			{
				String msg = (String) ge.event;
				if (msg.equals("Explosion"))
				{
					if (unbeat == false)
					{
						h1.reduceBlood();
						blood--;
						check_dead(player2);
						System.out.println("Explosion! Reduce Health");
					}

				} else if (msg.equals("beat"))
				{
					NumOfkill++;
					System.out.println("You hit the Enemy, player1 score is "
							+ NumOfkill);

				} else if (msg.equals("beated"))
				{
					if (unbeat == false)
					{
						h1.reduceBlood();
						blood--;
						check_dead(player2);
						System.out
								.println("You get hited by the Enemy! Reduce Health");
					}
				} else if (msg.equals("Powerup"))
				{
					powerup = 1;
					System.out.println("WEISONG CANNON IS READY!!!!");
				}

			}

		} else if (player2 == true)
		{
			GameEvents ge = (GameEvents) arg;

			if (ge.type == 1)
			{
				KeyEvent e = (KeyEvent) ge.event;
				switch (e.getKeyCode())
				{
				case KeyEvent.VK_LEFT:
					if (x > 0)
						x -= 5 * speed;
					break;
				case KeyEvent.VK_RIGHT:
					if (x < 570)
						x += 5 * speed;
					break;
				case KeyEvent.VK_UP:
					if (y > 0)
						y -= 5 * speed;
					break;
				case KeyEvent.VK_DOWN:
					if (y < 385)
						y += 5 * speed;
					break;
				default:
					if (e.getKeyChar() == '0')
					{
						System.out.println("Fire!");

						if (powerup == 1)
						{
							Image bullet = null;
							try
							{
								bullet = ImageIO.read(new File(
										"Resources/weisonghahaha.png"));
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							i += 1;
							if (i == 19)
								i = 0;
							b1[i] = new Bullet(bullet, 5, x - 40, y - 100,
									goodSide, powerup, player2);
							powerup--;
							System.out.println("GO !!");
						} else
						{
							Image bullet = null;
							try
							{
								bullet = ImageIO.read(new File(
										"Resources/bullet.png"));
							} catch (IOException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							i += 1;
							if (i == 19)
								i = 0;
							b1[i] = new Bullet(bullet, 5, x + 16, y, goodSide,
									powerup, player2);
						}
					} else if (e.getKeyChar() == '/')
					{
						if (unbeat == false)
							unbeat = true;
						else
							unbeat = false;

					}
				}
			} else if (ge.type == 2)
			{
				String msg = (String) ge.event;
				if (msg.equals("Explosion2"))
				{
					if (unbeat == false)
					{
						h1.reduceBlood();
						blood--;
						check_dead(player2);
						System.out.println("Explosion! Player2 Reduce Health");
					}

				} else if (msg.equals("beat2"))
				{
					NumOfkill++;
					System.out.println("You hit the Enemy, player2 score is "
							+ NumOfkill);

				} else if (msg.equals("beated2"))
				{
					if (unbeat == false)
					{
						h1.reduceBlood();
						blood--;
						check_dead(player2);
						System.out
								.println("You get hited by the Enemy! Player2 Reduce Health");
					}

				} else if (msg.equals("Powerup2"))
				{
					powerup = 1;
					System.out.println("WEISONG CANNON IS READY!!!!");
				}

			}
			
		}

	}
}
