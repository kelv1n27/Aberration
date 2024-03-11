package entities;

import enemies.Enemy;
import game.Globals;

public class Bullet extends Entity{
	
	private int bullet = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/bullet.png"}));

	public Bullet(float x, float y) {
		super(x, y, 3, 3, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		translate(0, -3);
		for (Entity e : Globals.level.entities) {
			//instanceof is bad practice but i dont care anymnoer
			if (intersects(e) && e != this && e instanceof Enemy) {
				e.hurt();
				Globals.level.queueRemoveEntity(this);
			}
		}
		if(y < 0) Globals.level.queueRemoveEntity(this);
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, bullet, (int)x, (int)y, 0, 0, 3, 5, 1f, 1f, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(bullet);
	}

	@Override
	public void hurt() {}

}
