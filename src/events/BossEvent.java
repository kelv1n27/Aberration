package events;

import game.Globals;

public class BossEvent extends Event{

	public BossEvent(int time) {
		super(time, new Runnable() {public void run() {Globals.level.startBossIntro();}});
	}

}
