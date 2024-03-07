package directives;

import enemies.Enemy;
import entities.Entity;
import game.Globals;

public class Spawn implements Directive {
	
	private Entity entity;
	
	public Spawn(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void tick(Enemy host) {
		Globals.level.queueAddEntity(entity);
	}

	@Override
	public boolean complete() {
		return true;
	}

}
