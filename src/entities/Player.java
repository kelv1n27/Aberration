package entities;

import game.Globals;

public class Player extends Entity{
	
	private int timer = 0;
	private int attackTimer = 0;
	private int lives = 0;
	private int invulnerable = 0;
	private int deathTimer = -1;
	
	private int ship = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/ship.png"}));
	private int explosion = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/explosion.png"}));

	public Player(int x, int y) {
		super(x, y, 13, 12, 1);
	}

	@Override
	public void tick() {
		timer++;
		invulnerable--;
		if (deathTimer == 0) Globals.level.gameOver();
		if(deathTimer > 0) deathTimer--;
		if (Globals.inp.getPressed("up") && deathTimer == -1) translate(0, -1);
		if (Globals.inp.getPressed("down") && deathTimer == -1) translate(0, 1f);
		if (Globals.inp.getPressed("left") && deathTimer == -1) translate(-1, 0);
		if (Globals.inp.getPressed("right") && deathTimer == -1) translate(1, 0);
		if (attackTimer-- < 0 && Globals.inp.getPressed("attack") && deathTimer == -1) {
			Globals.level.queueAddEntity(new Bullet(x + 5, y));
			attackTimer = 15;
		}
	}

	@Override
	public void render() {
		if (deathTimer == -1)
			Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, ship, (int)x - 1, (int)y, ((timer/5)%4) * 16, (Globals.inp.getPressed("left") ^ Globals.inp.getPressed("right")?32:0), 16, 16, 1f, 1f, (Globals.inp.getPressed("left") && !Globals.inp.getPressed("right")), false, (invulnerable > 0 && (invulnerable >> 2) % 2 == 0?.5f:1f)});
		else if (deathTimer > 80) 
			Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, explosion, (int)x - 5, (int)y - 4, (2 - ((deathTimer/10)-8)) * 20, 0, 20, 20, 1f, 1f, false, false, 1f});
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(ship);
	}

	@Override
	public void hurt() {
		if (invulnerable < 0 && deathTimer == -1) {
			if (lives == 0) {
				deathTimer = 120;
			} else {
				lives--;
				invulnerable = 180;
			}
		}
	}
	
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
