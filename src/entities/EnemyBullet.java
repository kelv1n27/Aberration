package entities;

import game.Globals;

public class EnemyBullet extends Entity{
	
	private float direction, speed;
	private int bullet = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/enemy bullet.png"}));

	public EnemyBullet(float x, float y, float direction, float speed) {
		super(x, y, 5, 5, 0);
		this.direction = direction;
		this.speed = speed;
	}

	@Override
	public void tick() {
		translate((float)Math.cos(direction) * speed, (float)Math.sin(direction) * speed);
		if (intersects(Globals.level.getPlayer())) {
			Globals.level.getPlayer().hurt();
		}
		if(y < 0) Globals.level.queueRemoveEntity(this);
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, bullet, (int)x, (int)y, 0, 0, 5, 5, 1f, 1f, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(bullet);
	}

	@Override
	public void hurt() {}

}
