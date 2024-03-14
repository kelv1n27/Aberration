package enemies;

import directives.Directive;
import directives.Idle;
import directives.StraightMove;
import game.Globals;
import particles.FadeBlob;

public class BigMouthEnemy extends BigBlobEnemy{
	
	private int mouth = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/big mouth.png"}));

	public BigMouthEnemy(float x, float y, Directive[] directives) {
		super(x, y, 40, 1500, directives);
	}
	
	@Override
	public void render() {
		super.render();
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, mouth, (int)x, (int)y, 0, 0, 40, 40, 1f + (.1f * (float)Math.cos(ticks >> 3)), 1f + (.1f * (float)Math.sin(ticks >> 2)), 20, 20, .5f * (float)Math.sin(ticks >> 3), false, false, 1f});
			}
		});
	}
	
	@Override
	public void release() {
		super.release();
		Globals.gfx.releaseMemory(mouth);
	}
	
	@Override
	public void hurt() {
		if (--health <= 0) {
			Globals.level.addScore(score);
			Globals.level.queueRemoveEntity(this);
			for (int i = 0; i < 5; i++) {
				Globals.level.queueAddEntity(new FadeBlob(
						x + Globals.rand.nextInt(width), 
						y + Globals.rand.nextInt(height), 
						30 + Globals.rand.nextInt(10), 
						10, 
						4.41f + 
						(Globals.rand.nextFloat() * 0.7f), 
						1f + (Globals.rand.nextFloat() * 1f)));
			}
			for (int i = 0; i < 6; i++)
				Globals.level.queueAddEntity(new BlobEnemy(x + 20, y + 20, 5, 0, new Directive[] {
					new Idle(1, Idle.angleBehavior.FACE_CUSTOM, (6.14f/6) * i),
					new StraightMove(15, 1f, StraightMove.angleBehavior.MOVE_HOST_DIR),
					new StraightMove(10, .5f, StraightMove.angleBehavior.MOVE_HOST_DIR),
					new Idle(5, Idle.angleBehavior.FACE_PLAYER, 0f),
					new StraightMove(200, 2.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)
					}));
		}
	}

}
