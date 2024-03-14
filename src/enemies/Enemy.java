package enemies;

import directives.Directive;
import entities.Entity;
import game.Globals;

public abstract class Enemy extends Entity{
	
	protected float direction = 0;
	private Directive[] directives;
	private int currentDirective = 0;
	private boolean resetSkip = false;
	private float initialx, initialy;
	private int initialHealth;

	public Enemy(float x, float y, int width, int height, int health, Directive[] directives) {
		super(x, y, width, height, health);
		this.directives = directives;
		initialx=x;
		initialy=y;
		initialHealth = health;
	}
	
	@Override
	public void tick() {
		if (currentDirective >= directives.length) {
			System.out.println("why is this happening?");
			return;
		}
		directives[currentDirective].tick(this);
		if (directives[currentDirective].complete() && !resetSkip) {
			currentDirective++;
			if (currentDirective >= directives.length) Globals.level.queueRemoveEntity(this);
		}
		resetSkip = false;
	}
	
	public float getDirection() {
		return direction;
	};
	
	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	public void resetDirectives() {
		currentDirective = 0;
//		x = initialx;
//		y = initialy;
		for (Directive d : directives) d.reset();
//		health = initialHealth;
	}
	
	public void resetDirectives(Directive looper) {
		for(int i = 0; i < directives.length; i++) {
			if (directives[i] != looper) directives[i].reset();
			else {
				return;
				
			}
		}
	}
	
	public void setDirective(int index) {
		currentDirective = index;
		resetSkip = true;
	}

}
