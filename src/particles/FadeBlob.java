package particles;

import entities.Entity;
import game.Globals;

public class FadeBlob extends Entity {
	
	private int circle = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/circle.png"}));
	
	private int decayTime, timer;
	private float startingSize, angle, speed;

	public FadeBlob(float x, float y, int decayTime, float startingSize, float angle, float speed) {
		super(x, y, 0, 0, 0);
		this.decayTime = decayTime;
		this.timer = decayTime;
		this.startingSize = startingSize;
		this.angle = angle;
		this.speed = speed;
	}

	@Override
	public void tick() {
		if(--timer < 0) {
			Globals.level.queueRemoveEntity(this);
		}
		translate((float)Math.cos(angle) * speed, (float)Math.sin(angle) * speed);
	}

	@Override
	public void render() {
		float size = startingSize * (1 - (float)Math.pow((1 - (float)timer/decayTime), 3));
		float scale = size/100;
		Globals.gfx.runPlugin("Render", new Object[] {Globals.enemyMask, circle, (int)x + (((int)-size)>>1), (int)y + (((int)-size)>>1), 0, 0, 100, 100, scale, scale, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(circle);
	}

	@Override
	public void hurt() {}

}
