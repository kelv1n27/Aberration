package enemies;

import directives.Directive;
import game.Globals;

public class MouthEnemy extends BlobEnemy{
	
	private int mouth = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/mouth.png"}));

	public MouthEnemy(int x, int y, Directive[] directives) {
		super(x, y, 5, 500, directives);
	}
	
	@Override
	public void render() {
		super.render();
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, mouth, (int)x, (int)y, 0, 0, 20, 20, 1f + (.2f * (float)Math.cos(ticks >> 3)), 1f + (.2f * (float)Math.sin(ticks >> 2)), 20, 20, (float)Math.sin(ticks >> 3), false, false, 1f});
			}
		});
	}
	
	@Override
	public void release() {
		super.release();
		Globals.gfx.releaseMemory(mouth);
	}

}
