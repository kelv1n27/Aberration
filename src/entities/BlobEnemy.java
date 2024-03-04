package entities;

import game.Globals;
import particles.BounceBlob;

public class BlobEnemy extends Entity{
	
	BounceBlob[] blobs;

	public BlobEnemy(int x, int y) {
		super(x, y, 20, 20, 1);
		blobs = new BounceBlob[] {	new BounceBlob(x + 6,	y + 6,	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	0),
									new BounceBlob(x + 13, 	y + 6, 	5, 	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5,	10),
									new BounceBlob(x + 6, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(6) - 3,	28 + Globals.rand.nextInt(10) - 5, 20),
									new BounceBlob(x + 13, 	y + 13,	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 30),
									new BounceBlob(x + 9, 	y + 9, 	5,	15,	10 + Globals.rand.nextInt(3) - 3,	28 + Globals.rand.nextInt(10) - 5, 0)};
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		for (BounceBlob b : blobs) {
			b.render();
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hurt() {
		if (--health <= 0) Globals.level.queueRemoveEntity(this);
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		for (BounceBlob b : blobs) b.translate(x, y);
	}

}
