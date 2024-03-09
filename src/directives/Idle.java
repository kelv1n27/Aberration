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
			host.setDirection((float)Math.atan2((Globals.level.getPlayer().getY() + (Globals.level.getPlayer().getHeight() >> 1)) - (host.getY() + (host.getHeight() >> 1)), (Globals.level.getPlayer().getX() + (Globals.level.getPlayer().getWidth() >> 1)) - (host.getX() + (host.getWidth() >> 1))));
		}
		timer--;
	}

	@Override
	public boolean complete() {
		return timer < 0;
	}

}
