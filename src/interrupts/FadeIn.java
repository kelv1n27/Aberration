package interrupts;

import audioHandlerV2_Processors.VolumeProcessor;
import game.Globals;

public class FadeIn implements Interrupt{
	
	private int length;
	private int timer;
	private int fadeColor = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[] {1, 1}));
	private VolumeProcessor vol = new VolumeProcessor();
	
	public FadeIn(int length) {
		this.length = length;
		this.timer = length;
		Globals.gfx.runPlugin("FillColor", new Object[] {fadeColor, 0xff000000});
		Globals.bgm.addProcessor(vol);
	}

	@Override
	public void tick() {
		timer--;
		vol.changeVol(1 - (timer/(float)length));
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, fadeColor, 0, 0, 0, 0, 1, 1, 256f, 240f, false, false, (float)timer/length});
	}

	@Override
	public boolean complete() {
		return timer < 0;
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(fadeColor);
		vol.dispose();
		Globals.bgm.removeProcessor(vol);
	}

}
