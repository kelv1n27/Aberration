package interrupts;

import audioHandlerV2_Processors.VolumeProcessor;
import entities.UpgradeSprite;
import game.Globals;

public class BossSplat implements Interrupt{
	
	private int splotch = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/splotch.png"}));
	
	private int timer = 40;
	private VolumeProcessor vol = new VolumeProcessor();
	
	private boolean complete = false;
	
	private float x, y;
	
	private UpgradeSprite bullet = new UpgradeSprite(-50, 0, UpgradeSprite.UpgradeType.BULLET_UP);
	private UpgradeSprite speed = new UpgradeSprite(-50, 0, UpgradeSprite.UpgradeType.SPEED_UP);
	private UpgradeSprite star = new UpgradeSprite(-50, 0, UpgradeSprite.UpgradeType.STAR);
	
	boolean phase = false;
	
	public BossSplat(float x, float y) {
		Globals.bgm.addProcessor(vol);
		this.x = x;
		this.y = y;
	}

	@Override
	public void tick() {
		if (!phase)
			timer--;
		else
			timer++;
		vol.changeVol(Math.max(0, timer/(float)40));
		
		if (timer < 0 && !phase) {
			if (Globals.level.getPlayer().getBulletLevel() < 4) bullet.teleport(52, 40);
			star.teleport(120, 40);
			if (Globals.level.getPlayer().getSpeedLevel() < 4) speed.teleport(184, 40);
		}
		if (timer == 20 && !phase) Globals.createManagedSfx("/sfx/Aberration - Track 11 (riser).wav", 10);
		if (timer == -59 && phase) Globals.createManagedSfx("/sfx/Aberration - Track 12 (faller).wav", 10);
		
		if (Globals.level.getPlayer().intersects(bullet)) {
			Globals.level.getPlayer().upgradeBullets();
			Globals.level.addScore(1000);
			bullet.teleport(-50,  0);
			star.teleport(-50, 0);
			speed.teleport(-50, 0);
			phase = true;
			timer = -60;
			Globals.level.loopReset();
			Globals.createManagedSfx("/sfx/Aberration - Track 13 (powerup).wav", 30);
		}
		if (Globals.level.getPlayer().intersects(star)) {
			Globals.level.addScore(10000);
			bullet.teleport(-50,  0);
			star.teleport(-50, 0);
			speed.teleport(-50, 0);
			phase = true;
			timer = -60;
			Globals.level.loopReset();
		}
		if (Globals.level.getPlayer().intersects(speed)) {
			Globals.level.getPlayer().upgradeSpeed();
			Globals.level.addScore(1000);
			bullet.teleport(-50,  0);
			star.teleport(-50, 0);
			speed.teleport(-50, 0);
			phase = true;
			timer = -60;
			Globals.level.loopReset();
			Globals.createManagedSfx("/sfx/Aberration - Track 13 (powerup).wav", 30);
		}
	}

	@Override
	public void render() {
		float scale = Math.min(4f, (float)(20 - timer)/20);
		float size = scale * 256;
		Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.enemyMask, splotch, (int)x + (((int)-size)>>1), (int)y + (((int)-size)>>1), 0, 0, 256, 256, Math.max(scale, 0), Math.max(scale, 0), (int)(256 * scale), (int)(256 * scale), (float)timer / 8, false, false, 1f});
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.level.getPlayer().render();
				bullet.render();
				star.render();
				speed.render();
			}
		});
	}

	@Override
	public boolean complete() {
		return timer > 40 && phase;
	}

	@Override
	public void release() {
		Globals.bgm.removeProcessor(vol);
		vol.dispose();
		Globals.gfx.releaseMemory(splotch);
		bullet.release();
		star.release();
		speed.release();
	}

}
