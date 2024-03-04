package particles;

import game.Globals;

public class BounceBlob extends Particle{
	
	private int circle = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/circle.png"}));
	int timer = 0;
	boolean mode = false;
	
	int minSize, maxSize, growTime, shrinkTime;
	
	public BounceBlob(int x, int y, int minSize, int maxSize, int growTime, int shrinkTime, int offset) {
		super(x, y);
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
			if (timer >= growTime) {
				timer -= growTime;
				mode = !mode;
			}
		} else {//shrink
			size = (maxSize - minSize) * (1 - (float)Math.pow((float)timer/shrinkTime, 3)) + minSize;
			if (timer >= shrinkTime) {
				timer -= shrinkTime;
				mode = !mode;
			}
		}
		float scale = size/100;
		timer++;
		//render
		Globals.gfx.runPlugin("Render", new Object[] {Globals.enemyMask, circle, (int)x + (((int)-size)>>1), (int)y + (((int)-size)>>1), 0, 0, 100, 100, scale, scale, false, false, 1f});
	}

	@Override
	public boolean complete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(circle);
	}
	
}
