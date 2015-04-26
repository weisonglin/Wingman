package HealthBar;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HealthBar
{

	int blood = 4;
	Image blood1, blood2, blood3, blood4;
	boolean player2;

	public HealthBar(boolean player2)
	{
		this.player2 = player2;
		try
		{
			blood1 = ImageIO.read(new File("Resources/health3.png"));
			blood2 = ImageIO.read(new File("Resources/health2.png"));
			blood3 = ImageIO.read(new File("Resources/health1.png"));
			blood4 = ImageIO.read(new File("Resources/health.png"));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void reduceBlood()
	{
		blood--;

	}

	public void draw(ImageObserver obs, Graphics2D g2,int x, int y)
	{
		//if (player2)
		//{
			if (blood == 4)
				// System.out.println("haha4");
				g2.drawImage(blood4, x, y+70, 64,5, obs);
			else if (blood == 3)
				// System.out.println("haha3");
				g2.drawImage(blood3, x, y+70, 64,5, obs);
			else if (blood == 2)
				// System.out.println("haha2");
				g2.drawImage(blood2, x, y+70, 64,5, obs);
			else if (blood == 1)
				// System.out.println("haha1");
				g2.drawImage(blood1, x, y+70, 64,5, obs);

			/*	

		} else
		{
			if (blood == 4)
				// System.out.println("haha4");
				g2.drawImage(blood4, x, y+70, 64,5, obs);
			else if (blood == 3)
				// System.out.println("haha3");
				g2.drawImage(blood3, x, y+70, 64,5, obs);
			else if (blood == 2)
				// System.out.println("haha2");
				g2.drawImage(blood2, x, y+70, 64,5, obs);
			else if (blood == 1)
				// System.out.println("haha1");
				g2.drawImage(blood1, x, y+70, 64,5, obs);
		}
	*/
	}

}
