package interrupts;

import audioHandlerV2_Processors.VolumeProcessor;
import game.Globals;

public class BossIntro implements Interrupt{
	
	private int length;
	private int timer;
	private VolumeProcessor vol = new VolumeProcessor();
	
	public BossIntro() {
		this.length = 60;
		this.timer = 120;
		Globals.bgm.addProcessor(vol);
	}

	@Override
	public void tick() {
		timer--;
		vol.changeVol(Math.max(timer-60, 0)/(float)length);
		if (timer == 0) Globals.level.startBossFight();
	}

	@Override
	public void render() {}

	@Override
	public boolean complete() {
		return timer < 0;
	}

	@Override
	public void release() {
		Globals.bgm.removeProcessor(vol);
	}

}
