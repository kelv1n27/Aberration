package events;

import entities.Entity;
import game.DeferredConditional;
import game.Globals;

public class ConditionalSpawnEvent extends Event {
	
	public ConditionalSpawnEvent(int time, DeferredConditional conditional, Entity entity) {
		super(time, new Runnable() {
			@Override
			public void run() {
				if (conditional.conditional())
					Globals.level.queueAddEntity(entity);
			}
		});
	}

}
