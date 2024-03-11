package interrupts;

import audioHandlerV2_Processors.VolumeProcessor;
import game.Globals;

public class BossSplat implements Interrupt{
	
	private int splotch = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/splotch.png"}));
	
	private int timer = 80;
	private VolumeProcessor vol = new VolumeProcessor();
	
	private boolean complete = false;
	
	private float x, y;
	
	public BossSplat(float x, float y) {
		Globals.bgm.addProcessor(vol);
		this.x = x;
		this.y = y;
	}

	@Override
	public void tick() {
		timer--;
		vol.changeVol(Math.max(0, timer/(float)80));
	}

	@Override
	public void render() {
		float scale = Math.min(4f, (float)(80 - timer)/80);
		float size = scale * 256;
		Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.enemyMask, splotch, (int)x + (((int)-size)>>1), (int)y + (((int)-size)>>1), 0, 0, 256, 256, Math.max(scale, 0), Math.max(scale, 0), (int)(256 * scale), (int)(256 * scale), (float)timer / 8, false, false, 1f});
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.level.getPlayer().render();
			}
		});
	}

	@Override
	public boolean complete() {
		return complete;
	}

	@Override
	public void release() {
		Globals.bgm.removeProcessor(vol);
		vol.dispose();
		Globals.gfx.releaseMemory(splotch);
	}

}
