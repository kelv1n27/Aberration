package enemies;

import directives.Directive;
import game.Globals;

public class EyeEnemy extends BlobEnemy{
	
	private int eye = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/eye.png"}));
	
	public EyeEnemy(int x, int y, Directive[] directives) {
		super(x, y, directives);
	}
	
	@Override
	public void render() {
		super.render();
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, eye, (int)x, (int)y, 0, 0, 20, 20, 1.3f, 1.3f, 20, 20, direction, false, false, 1f});
			}
		});
	}
	
	@Override
	public void release() {
		super.release();
		Globals.gfx.releaseMemory(eye);
	}

}
