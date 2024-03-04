package entities;

import game.Globals;

public class Player extends Entity{
	
	private int attackTimer = 0;

	public Player(int x, int y) {
		super(x, y, 20, 20, 1);
	}

	@Override
	public void tick() {
		if (Globals.inp.getPressed("up")) translate(0, -1);
		if (Globals.inp.getPressed("down")) translate(0, 1f);
		if (Globals.inp.getPressed("left")) translate(-1, 0);
		if (Globals.inp.getPressed("right")) translate(1, 0);
		if (attackTimer-- < 0 && Globals.inp.getPressed("attack")) {
			Globals.level.queueAddEntity(new Bullet(x + 9, y));
			attackTimer = 15;
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hurt() {}

}