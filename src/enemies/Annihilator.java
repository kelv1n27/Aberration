package enemies;

import directives.BezierPath;
import directives.Directive;
import directives.Idle;
import directives.Loop;
import directives.Shoot;
import directives.Spawn;
import game.Globals;
import particles.BounceBlob;
import particles.FadeBlob;

public class Annihilator extends Enemy{
	
	private BounceBlob[] blobs;
	private int eye = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/big eye.png"}));

	public Annihilator(float x, float y, int health) {
		super(x, y, 256, 15, health, new Directive[] {
				new BezierPath(120, new float[][] {{0, -50}, {0, 0}}, BezierPath.angleBehavior.FACE_PLAYER),
				new Idle(90, Idle.angleBehavior.FACE_PLAYER),
				new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
				new Spawn(new BlobEnemy(64, 10, new Directive[] {new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.CUSTOM_DIR, 1.57f)})),
				new Spawn(new BlobEnemy(192, 10, new Directive[] {new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.CUSTOM_DIR, 1.57f)})),
				new Loop(1, 1),
				new Idle(90, Idle.angleBehavior.FACE_PLAYER),
				new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
				new Spawn(new BlobEnemy(64, 10, new Directive[] {new Shoot(Shoot.bulletType.FAN, Shoot.directionType.CUSTOM_DIR, 1.57f)})),
				new Spawn(new BlobEnemy(192, 10, new Directive[] {new Shoot(Shoot.bulletType.FAN, Shoot.directionType.CUSTOM_DIR, 1.57f)})),
				new Loop(6, 1),
				new Loop(1, Integer.MAX_VALUE)
		});
		initBlobs();
	}

	@Override
	public void tick() {
		super.tick();
		for (BounceBlob b : blobs) {
			b.tick();
		}
		if (intersects(Globals.level.getPlayer())) Globals.level.getPlayer().hurt();
	}
	
	@Override
	public void render() {
		for (BounceBlob b : blobs) {
			b.render();
		}
		Globals.level.postRenders.add(new Runnable() {
			@Override
			public void run() {
				Globals.gfx.runPlugin("RenderRotate", new Object[] {Globals.mainCanvas, eye, (int)x + 98, (int)y, 0, 0, 40, 40, 1.5f, 1.5f, 60, 60, direction, false, false, 1f});
			}
		});
	}

	@Override
	public void release() {
		for (BounceBlob b : blobs) {
			b.release();
		}
		Globals.gfx.releaseMemory(eye);
	}

	@Override
	public void hurt() {
		if (--health <= 0) {
			Globals.level.addScore(7500);
			Globals.level.queueRemoveEntity(this);
			Globals.createManagedSfx("/sfx/Aberration - Track 10 (enemy death).wav", 10);
			// below needs work
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
		}
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		for (BounceBlob b : blobs) b.translate(x, y);
	}
	
	@Override
	public void teleport(float x, float y) {
		super.teleport(x, y);
		blobs[0].teleport(x + 128,	y + 15);
		blobs[1].teleport(x + 64,	y + 15);
		blobs[2].teleport(x + 192,	y + 15);
		blobs[3].teleport(x + 160,	y + 25);
		blobs[4].teleport(x + 96,	y + 25);
		blobs[5].teleport(x + 160,	y);
		blobs[6].teleport(x + 96,	y);
		blobs[7].teleport(x + 224,	y);
		blobs[8].teleport(x + 32,	y);
	}
	
	private void initBlobs() {
		blobs = new BounceBlob[] {
				new BounceBlob(x + 128,	y + 15,	80,	70,	25,	40,	0),
				new BounceBlob(x + 64,	y + 15,	70,	55,	40,	20,	0),
				new BounceBlob(x + 192,	y + 15,	70,	45,	35,	35,	0),
				new BounceBlob(x + 160,	y + 25,	30,	25,	35,	35,	0),
				new BounceBlob(x + 96,	y + 25,	40,	25,	15,	35,	0),
				new BounceBlob(x + 160,	y,		50,	30,	10,	20,	0),
				new BounceBlob(x + 96,	y,		50,	30,	20,	5,	0),
				new BounceBlob(x + 224,	y,		45,	25,	50,	25,	0),
				new BounceBlob(x + 32,	y,		60,	25,	25,	20,	0),
		};
	}

}
