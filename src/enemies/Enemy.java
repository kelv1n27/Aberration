package enemies;

import directives.Directive;
import entities.Entity;
import game.Globals;

public abstract class Enemy extends Entity{
	
	private float direction = 0;
	private Directive[] directives;
	private int currentDirective = 0;

	public Enemy(float x, float y, int width, int height, int health, Directive[] directives) {
		super(x, y, width, height, health);
		this.directives = directives;
	}
	
	@Override
	public void tick() {
		directives[currentDirective].tick(this);
		if (directives[currentDirective].complete()) {
			currentDirective++;
			if (currentDirective >= directives.length) Globals.level.queueRemoveEntity(this);
		}
	}
	
	public float getDirection() {
		return direction;
	};
	
	public void setDirection(float direction) {
		this.direction = direction;
	}

}
