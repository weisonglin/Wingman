package moveble;

import java.awt.Image;

import Explosion.Explosion;
import GameEvents.GameEvents;
import Sound.Sound;

public class Bullet extends moveble
{
//	Sound sound1;
	boolean player2;
	Sound sound;
	Bullet(Image img, int speed, int x, int y, boolean goodSide, int powerup, boolean player2)
	{
		this.img = img;
		this.speed = speed;
		height = img.getHeight(null);
		width = img.getWidth(null);
		this.x = x;
		this.y = y;
		this.show = true;
		this.goodSide = goodSide;
		this.powerup = powerup;
		this.player2=player2;
		sound=new Sound();
		
	}

	public void update(GameEvents gameEvents, Enemy[] e1, MyPlane[] m, Explosion [] Exp)
	{
		if (goodSide)
		{
			y -= speed;
			if (powerup == 1)
			{
				sound.playBE();
				
				for (int i = 0; e1[i] != null && i < 10; i++)
				{
					if (e1[i].collision(x, y, width, height))
					{
						for (int j=0; j<20; j++)
						{
							if(Exp[j]==null)
							{
								Exp[j]=new Explosion(e1[i].getX(),e1[i].getY(),false);	

								if(j==19)
									Exp[0]=null;
								else
									Exp[j+1]=null;
								break;
							}
							
						}
						if(player2)
							gameEvents.setValue("beat2");
						else
							gameEvents.setValue("beat");
						gameEvents.setValue("");
						e1[i].reset();
						// show = true;
					} else
						gameEvents.setValue("");
				}

			} else
			{				
				for (int i = 0; e1[i] != null && i < 10; i++)
				{
					if (e1[i].collision(x, y, width, height))
					{								
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
						reset();
						if(player2)
							gameEvents.setValue("beat2");
						else
							gameEvents.setValue("beat");
						gameEvents.setValue("");
						e1[i].reset();
						// show = true;
					} else
						gameEvents.setValue("");
				}
			}
		} else
		{
			if (powerup == 0)
			{
				y += speed;
			} else if (powerup == 1)
			{
				y += speed;
				x += speed;
			} else if (powerup == 2)
			{
				y += speed;
				x -= speed;
			}
			for (int i = 0;i < 2; i++)
			{
				if(m[i]!=null)
				{
				if (m[i].collision(x, y, width, height))
				{
					reset();
					// You need to remove this one and increase score etc
					if (i == 0)
						gameEvents.setValue("beated");
					else
						gameEvents.setValue("beated2");
					gameEvents.setValue("");
					// show = true;
				} else
					gameEvents.setValue("");
				}
			}
		}

	}

	public void reset()
	{
		show = false;
		x = 700;
		y = 600;
	}
}
