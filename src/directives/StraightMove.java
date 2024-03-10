package directives;

import enemies.Enemy;

public class StraightMove implements Directive {
	
	public enum angleBehavior {
		MOVE_HOST_DIR,
		MOVE_CUSTOM_DIR
	}
	
	private float speed, customAngle = 0;
	private int timer, initialTimer;
	private angleBehavior behavior;
	
	public StraightMove(int timer, float speed, angleBehavior behavior) {
		this.timer = timer;
		this.speed = speed;
		this.behavior = behavior;
		initialTimer = timer;
	}
	
	public StraightMove(int timer, float speed, angleBehavior behavior, float customAngle) {
		this.timer = timer;
		this.speed = speed;
		this.behavior = behavior;
		this.customAngle = customAngle;
		initialTimer = timer;
	}

	@Override
	public void tick(Enemy host) {
		switch(behavior) {
		case MOVE_HOST_DIR:
			host.translate((float)Math.cos(host.getDirection()) * speed, (float)Math.sin(host.getDirection()) * speed);
			break;
		case MOVE_CUSTOM_DIR:
			host.translate((float)Math.cos(customAngle) * speed, (float)Math.sin(customAngle) * speed);
			break;
		}
		timer--;
	}

	@Override
	public boolean complete() {
		return timer < 0;
	}

	@Override
	public void reset() {
		timer = initialTimer;
	}

}
