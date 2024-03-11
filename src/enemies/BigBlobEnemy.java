package enemies;

import directives.Directive;
import game.Globals;
import particles.BounceBlob;
import particles.FadeBlob;

public class BigBlobEnemy extends Enemy{

	private BounceBlob[] blobs;
	protected int ticks = 0;
	protected int score = 2000;
	
	public BigBlobEnemy(float x, float y, Directive[] directives) {
		super(x, y, 40, 40, 15, directives);
		initBlobs();
	}
	
	public BigBlobEnemy(float x, float y, int health, int score, Directive[] directives) {
		super(x, y, 40, 40, health, directives);
		this.score = score;
		initBlobs();
	}
	
	@Override
	public void tick() {
		super.tick();
		for (BounceBlob b : blobs) {
			b.tick();
		}
		if (ticks++ %30 == 0) Globals.level.queueAddEntity(new FadeBlob(x + (width >> 1), y + (height >> 1), 50, 10, Globals.rand.nextFloat() * 6.28f, 1));
		if (intersects(Globals.level.getPlayer())) Globals.level.getPlayer().hurt();
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
	
	private void initBlobs() {
		blobs = new BounceBlob[] {
				new BounceBlob(x + 20,	y + 20,	20,	30,	10,	40,	0),
				new BounceBlob(x + 15,	y + 5, 	10,	20,	20,	20, 0),
				new BounceBlob(x + 5,	y + 15,	10,	20,	5,	30,	0),
				new BounceBlob(x + 10,	y + 25,	20,	25,	15,	30,	0),
				new BounceBlob(x + 30,	y + 15,	15,	25,	30,	10,	0),
				new BounceBlob(x + 28,	y + 25,	10,	20,	10,	25,	0),
		};
	}

}
