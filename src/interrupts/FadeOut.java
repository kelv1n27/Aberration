package interrupts;

import game.Globals;

public class FadeOut implements Interrupt{
	
	private int length;
	private int timer;
	private int fadeColor = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[] {1, 1}));
	
	public FadeOut(int length) {
		this.length = length;
		this.timer = length;
		Globals.gfx.runPlugin("FillColor", new Object[] {fadeColor, 0xff000000});
	}

	@Override
	public void tick() {
		timer--;
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, fadeColor, 0, 0, 0, 0, 1, 1, 256f, 240f, false, false, 1 - (float)timer/length});
	}

	@Override
	public boolean complete() {
		return timer < 0;
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(fadeColor);
	}

}
