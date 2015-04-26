package KeyControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import GameEvents.*;

public class KeyControl extends KeyAdapter
{
	GameEvents gameEvents;

	public KeyControl(GameEvents gameEvents)
	{
		this.gameEvents = gameEvents;
	}

	public void keyReleased(KeyEvent e)
	{
		gameEvents.setValue(e);
	}
}
