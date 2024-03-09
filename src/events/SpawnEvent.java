package events;

import entities.Entity;
import game.Globals;

public class SpawnEvent extends Event {

	public SpawnEvent(int time, Entity entity) {
		super(time, new Runnable() {
			@Override
			public void run() {
				Globals.level.queueAddEntity(entity);
			}
		});
	}

}
