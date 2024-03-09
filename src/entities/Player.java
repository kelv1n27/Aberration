package entities;

import game.Globals;

public class Player extends Entity{
	
	private int timer = 0;
	private int attackTimer = 0;
	private int lives = 0;
	
	private int ship = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/ship.png"}));

	public Player(int x, int y) {
		super(x, y, 13, 12, 1);
	}

	@Override
	public void tick() {
		timer++;
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
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, ship, (int)x - 1, (int)y, ((timer/5)%4) * 16, (Globals.inp.getPressed("left") ^ Globals.inp.getPressed("right")?32:0), 16, 16, 1f, 1f, (Globals.inp.getPressed("left") && !Globals.inp.getPressed("right")), false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(ship);
	}

	@Override
	public void hurt() {}
	
	@Override
	public void translate(float x, float y) {
		this.x = Math.max(Math.min(this.x + x, 256 - width), 0);
		this.y = Math.max(Math.min(this.y + y, 240 - height), 0);
	}
	
	public int getLives() {
		return lives;
	}
	
	public void getLife() {
		lives++;
	}

}
