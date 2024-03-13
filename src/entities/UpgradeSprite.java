package entities;

import game.Globals;

public class UpgradeSprite extends Entity{
	
	private int sprite;
	private int timer = 0;
	
	public enum UpgradeType{
		BULLET_UP,
		SPEED_UP,
		STAR
	}

	public UpgradeSprite(float x, float y, UpgradeType type) {
		super(x, y, 16, 16, 1);
		switch (type) {
		case BULLET_UP:
			sprite = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/bulletup.png"}));
			break;
		case SPEED_UP:
			sprite = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/speedup.png"}));
			break;
		case STAR:
			sprite = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/star.png"}));
			break;
		}
	}

	@Override
	public void tick() {}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, sprite, (int)x, (int)y, 0, ((timer++ >> 2) % 2) * 16, 16, 16, 1f, 1f, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(sprite);
	}

	@Override
	public void hurt() {}

}
