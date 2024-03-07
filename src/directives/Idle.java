package directives;

import enemies.Enemy;
import game.Globals;

public class Idle implements Directive {
	
	public enum angleBehavior {
		FACE_PLAYER,
		FACE_CONSTANT
	}
	
	private int timer;
	private angleBehavior behavior;
	
	public Idle(int timer, angleBehavior behavior) {
		this.behavior = behavior;
		this.timer = timer;
	}

	@Override
	public void tick(Enemy host) {
		if (behavior == angleBehavior.FACE_PLAYER) {
			host.setDirection((float)Math.atan2(host.getY() - Globals.level.getPlayer().getY(), host.getX() - Globals.level.getPlayer().getX()));
		}
		timer--;
	}

	@Override
	public boolean complete() {
		return timer < 0;
	}

}
