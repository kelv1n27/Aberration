package enemies;

import directives.Directive;
import game.Globals;

public class BigEyeEnemy extends BigBlobEnemy{
	
	private int eye = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/big eye.png"}));

	public BigEyeEnemy(float x, float y, int health, int score, Directive[] directives) {
		super(x, y, health, score, directives);
	}

	@Override
	public void render() {
		super.render();
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, eye, (int)x, (int)y, 0, 0, 40, 40, 1f, 1f, 40, 40, direction, false, false, 1f});
			}
		});
	}

	@Override
	public void release() {
		super.release();
		Globals.gfx.releaseMemory(eye);
	}

}
