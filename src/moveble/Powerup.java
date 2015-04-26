package moveble;

import java.awt.Image;
import java.util.Random;

import GameEvents.GameEvents;

public class Powerup extends moveble
{
	public Powerup(Image img, int x, int y,int speed,Random gen)
	{
		this.img=img;
		this.speed=speed;
		height = img.getHeight(null);
		width = img.getWidth(null);
		this.x=x;
		this.y=y;
		show=true;
		this.gen=gen;
		
	}
	public void update(GameEvents gameEvents, MyPlane[] m)
	{
		y+=speed;
		for (int i = 0; i < 2; i++)
		{
			if(m[i]!=null)
			{
			if (m[i].collision(x, y, width, height))
			{
				show = false;
				// You need to remove this one and increase score etc
				if(i==0)
					gameEvents.setValue("Powerup");
				else
					gameEvents.setValue("Powerup2");
				gameEvents.setValue("");
				show=true;
				this.reset();
				
			} else
				gameEvents.setValue("");
			}
		}
		if (y > 500)
			this.reset();

	}
	public void reset()
	{	
		this.x = Math.abs(gen.nextInt() % (600 - 30));
		this.y = -30;
	}
}
