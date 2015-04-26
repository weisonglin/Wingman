package Sound;


import java.io.File;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Sound
{
	
	//AudioClip BExplosion, SExplosion,background; 
	Clip BExplosion, SExplosion,background; 
	
	public Sound()
	{
		try{
		//URL urlBE = getClass().getResource("Resources/snd_explosion1.wav");
		//BExplosion=Applet.newAudioClip(new File("Resources/snd_explosion1.wav"));
		//System.out.println(urlBE);
		BExplosion=AudioSystem.getClip();
		BExplosion.open(AudioSystem.getAudioInputStream(new File("Resources/snd_explosion2.wav")));
		SExplosion=AudioSystem.getClip();
		SExplosion.open(AudioSystem.getAudioInputStream(new File("Resources/snd_explosion1.wav")));
		background=AudioSystem.getClip();
		background.open(AudioSystem.getAudioInputStream(new File("Resources/background.wav")));
		//URL urlSE = getClass().getResource("Resources/snd_explosion2.wav");
		//SExplosion=Applet.newAudioClip(urlSE);
		//BExplosion=Applet.newAudioClip(Sound.class.getResource("/snd_explosion1.wav"));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	//	System.out.println(urlBE);
	//	URL urlSE = getClass().getResource("Resources/snd_explosion2.wav");
	//	SExplosion=Applet.newAudioClip(urlSE);
	}
	public void playBE()
	{
	
		BExplosion.start();
		
	}
	public void playSE()
	{
		SExplosion.start();
	}
	
	public void playBack()
	{
		
		background.start();
		background.loop(Clip.LOOP_CONTINUOUSLY);
		//thread.sleep(100);
	}
}
