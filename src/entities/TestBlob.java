package entities;

import game.Globals;

public class TestBlob {
	
	private int circle = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/circle.png"}));
	int timer = 0;
	boolean mode = false;
	
	public TestBlob(int offset) {
		timer = offset;
	}
	
	public void render(int x, int y, int minSize, int maxSize, int growTime, int shrinkTime) {
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
		Globals.gfx.runPlugin("Render", new Object[] {Globals.enemyMask, circle, x + ((int)-size)>>1, y + ((int)-size)>>1, 0, 0, 100, 100, scale, scale, false, false, 1f});
	}
	
}
