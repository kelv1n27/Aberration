package entities;

import game.Globals;

public class Player extends Entity{
	
	private int timer = 0;
	private int attackTimer = 0;
	private int lives = 1;
	private int invulnerable = 0;
	private int deathTimer = -1;
	
	private int ship = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/ship.png"}));
	private int explosion = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/explosion.png"}));
	
	private int bulletLevel = 0;
	private float speed = 1;
	private int speedLevel = 0;

	public Player(int x, int y) {
		super(x, y, 13, 12, 1);
	}

	@Override
	public void tick() {
		timer++;
		invulnerable--;
		if (deathTimer == 0) Globals.level.gameOver();
		if(deathTimer > 0) deathTimer--;
		if (Globals.inp.getPressed("up") && deathTimer == -1) translate(0, -1 * speed);
		if (Globals.inp.getPressed("down") && deathTimer == -1) translate(0, speed);
		if (Globals.inp.getPressed("left") && deathTimer == -1) translate(-1 * speed, 0);
		if (Globals.inp.getPressed("right") && deathTimer == -1) translate(speed, 0);
		if (attackTimer-- < 0 && Globals.inp.getPressed("attack") && deathTimer == -1) {
			attackTimer = 20 - speedLevel;
			Globals.createManagedSfx("/sfx/Aberration - Track 06 (player shoot).wav", 10);
			switch (bulletLevel) {
			case 0:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				break;
			case 1:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 12, 4.724f));
				break;
			case 2:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 12, 4.724f));
				Globals.level.queueAddEntity(new Bullet(x + 13, y + 6, 3.14f));
				Globals.level.queueAddEntity(new Bullet(x, y + 6, 0f));
				break;
			case 3:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 12, 4.724f));
				Globals.level.queueAddEntity(new Bullet(x + 13, y + 6, 3.14f));
				Globals.level.queueAddEntity(new Bullet(x, y + 6, 0f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.39f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.74f));
				break;
			case 4:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 12, 4.724f));
				Globals.level.queueAddEntity(new Bullet(x + 13, y + 6, 3.14f));
				Globals.level.queueAddEntity(new Bullet(x, y + 6, 0f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.39f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.74f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 1.65f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 1.48f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, .785f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 2.36f));
				break;
			default:
				Globals.level.queueAddEntity(new Bullet(x + 5, y));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 12, 4.724f));
				Globals.level.queueAddEntity(new Bullet(x + 13, y + 6, 3.14f));
				Globals.level.queueAddEntity(new Bullet(x, y + 6, 0f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.39f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 4, 1.74f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 1.65f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 1.48f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, .785f));
				Globals.level.queueAddEntity(new Bullet(x + 5, y + 2, 2.36f));
				break;
			}
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
				Globals.createManagedSfx("/sfx/Aberration - Track 09 (player death).wav", 60);
				deathTimer = 120;
			} else {
				Globals.createManagedSfx("/sfx/Aberration - Track 08 (player hurt).wav", 15);
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

	public int getBulletLevel() {
		return bulletLevel;
	}
	
	public void upgradeBullets() {
		bulletLevel += 1;
	}
	
	public int getSpeedLevel() {
		return speedLevel;
	}
	
	public void upgradeSpeed() {
		speedLevel += 1;
		speed += .2f;
	}
}
