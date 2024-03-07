package particles;

import entities.Entity;
import game.Globals;

public class BounceBlob extends Entity{
	
	private int circle = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/circle.png"}));
	private int timer = 0;
	private boolean mode = false;
	
	private float minSize, maxSize;
	private int growTime, shrinkTime;
	
	public BounceBlob(float x, float y, float minSize, float maxSize, int growTime, int shrinkTime, int offset) {
		super(x, y, 0, 0, 0);
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.growTime = growTime;
		this.shrinkTime = shrinkTime;
		timer = offset;
	}
	
	@Override
	public void render() {
		//logic
		float size;
		if (mode) {//growth
			size = (maxSize - minSize) * (float)Math.pow((float)timer/growTime, 3) + minSize;
		} else {//shrink
			size = (maxSize - minSize) * (1 - (float)Math.pow((float)timer/shrinkTime, 3)) + minSize;
		}
		float scale = size/100;
		//render
		Globals.gfx.runPlugin("Render", new Object[] {Globals.enemyMask, circle, (int)x + (((int)-size)>>1), (int)y + (((int)-size)>>1), 0, 0, 100, 100, Math.max(scale, 0), Math.max(scale, 0), false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(circle);
	}

	@Override
	public void tick() {
		if (mode) {//growth
			if (timer >= growTime) {
				timer -= growTime;
				mode = !mode;
			}
		} else {//shrink
			if (timer >= shrinkTime) {
				timer -= shrinkTime;
				mode = !mode;
			}
		}
		timer++;
	}

	@Override
	public void hurt() {}
	
}
