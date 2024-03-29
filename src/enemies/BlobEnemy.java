package enemies;

import directives.Directive;
import game.Globals;
import particles.BounceBlob;
import particles.FadeBlob;

public class BlobEnemy extends Enemy{
	
	private BounceBlob[] blobs;
	protected int ticks = 0;
	protected int score = 100;

	public BlobEnemy(float x, float y, Directive[] directives) {
		super(x, y, 20, 20, 1, directives);
		blobs = new BounceBlob[] {	new BounceBlob(x + 6,	y + 6,	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	0),
									new BounceBlob(x + 13, 	y + 6, 	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	10),
									new BounceBlob(x + 6, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5, 20),
									new BounceBlob(x + 13, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 30),
									new BounceBlob(x + 9, 	y + 9, 	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 0)};
	}
	
	public BlobEnemy(float x, float y, int health, int score, Directive[] directives) {
		super(x, y, 20, 20, health, directives);
		this.score = score;
		blobs = new BounceBlob[] {	new BounceBlob(x + 6,	y + 6,	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	0),
									new BounceBlob(x + 13, 	y + 6, 	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	10),
									new BounceBlob(x + 6, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5, 20),
									new BounceBlob(x + 13, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 30),
									new BounceBlob(x + 9, 	y + 9, 	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 0)};
	}

	@Override
	public void tick() {
		super.tick();
		for (BounceBlob b : blobs) {
			b.tick();
		}
		if (ticks++ %30 == 0) Globals.level.queueAddEntity(new FadeBlob(x + (width >> 1), y + (height >> 1), 30, 5, Globals.rand.nextFloat() * 6.28f, 1));
		if (intersects(Globals.level.getPlayer())) Globals.level.getPlayer().hurt();
//		System.out.println(x);
	}

	@Override
	public void render() {
		for (BounceBlob b : blobs) {
			b.render();
		}
	}

	@Override
	public void release() {
		for (BounceBlob b : blobs) {
			b.release();
		}
	}

	@Override
	public void hurt() {
		if (--health <= 0) {
			Globals.level.addScore(score);
			Globals.level.queueRemoveEntity(this);
			Globals.createManagedSfx("/sfx/Aberration - Track 10 (enemy death).wav", 10);
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
		blobs[0].teleport(x + 6, y + 6);
		blobs[1].teleport(x + 13, y + 6);
		blobs[2].teleport(x + 6, y + 13);
		blobs[3].teleport(x + 13, y + 13);
		blobs[4].teleport(x + 9, y + 9);
	}

}
