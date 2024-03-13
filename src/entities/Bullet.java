package entities;

import enemies.Enemy;
import game.Globals;

public class Bullet extends Entity{
	
	private int bullet = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/bullet.png"}));
	private float direction = 1.57f;
	private int timer = 0;

	public Bullet(float x, float y) {
		super(x, y, 3, 3, 1);
		// TODO Auto-generated constructor stub
	}
	
	public Bullet(float x, float y, float direction) {
		super(x, y, 3, 3, 1);
		this.direction = direction;
	}

	@Override
	public void tick() {
		translate(-3 * (float)Math.cos(direction), -3 * (float)Math.sin(direction));
		for (Entity e : Globals.level.entities) {
			//instanceof is bad practice but i dont care anymnoer
			if (intersects(e) && e != this && e instanceof Enemy) {
				e.hurt();
				Globals.level.queueRemoveEntity(this);
			}
		}
		if(timer++ > 100) Globals.level.queueRemoveEntity(this);
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, bullet, (int)x, (int)y, 0, 0, 3, 5, 1f, 1f, 3, 5, direction - 1.57f, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(bullet);
	}

	@Override
	public void hurt() {}

}
