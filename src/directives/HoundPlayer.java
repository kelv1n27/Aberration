package directives;

import enemies.Enemy;
import game.Globals;

public class HoundPlayer implements Directive{
	
	private int initialTimer = Integer.MAX_VALUE;
	private int timer = Integer.MAX_VALUE;
	private float speed;
	
	public HoundPlayer(float speed) {
		this.speed = speed;
	}
	
	public HoundPlayer(int timer, float speed) {
		this.timer = timer;
		initialTimer = timer;
		this.speed = speed;
	}

	@Override
	public void tick(Enemy host) {
		float direction = ((float)Math.atan2((Globals.level.getPlayer().getY() + (Globals.level.getPlayer().getHeight() >> 1)) - (host.getY() + (host.getHeight() >> 1)), (Globals.level.getPlayer().getX() + (Globals.level.getPlayer().getWidth() >> 1)) - (host.getX() + (host.getWidth() >> 1))));
		host.translate(speed * (float)Math.cos(direction), speed * (float)Math.sin(direction));
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
