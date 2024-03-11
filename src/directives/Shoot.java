package directives;

import enemies.Enemy;
import entities.EnemyBullet;
import game.Globals;

public class Shoot implements Directive {
	
	public enum bulletType{
		NORMAL,
		TRIPLET,
		FAN
	}
	
	public enum directionType{
		HOST_DIR,
		PLAYER_DIR,
		CUSTOM_DIR
	}
	
	private bulletType bullType;
	private directionType dirType;
	private float direction = 0;
	
	public Shoot(bulletType bullType, directionType dirType) {
		this.bullType = bullType;
		this.dirType = dirType;
	}
	
	public Shoot(bulletType bullType, directionType dirType, float direction) {
		this.bullType = bullType;
		this.dirType = dirType;
		this.direction = direction;
	}

	@Override
	public void tick(Enemy host) {
		System.out.println("in shoot");
		float usedDirection = 0;
		switch(dirType) {
		case HOST_DIR:
			usedDirection = host.getDirection();
			break;
		case PLAYER_DIR:
			usedDirection = ((float)Math.atan2((Globals.level.getPlayer().getY() + (Globals.level.getPlayer().getHeight() >> 1)) - (host.getY() + (host.getHeight() >> 1)), (Globals.level.getPlayer().getX() + (Globals.level.getPlayer().getWidth() >> 1)) - (host.getX() + (host.getWidth() >> 1))));
			break;
		case CUSTOM_DIR:
			usedDirection = direction;
			break;
		}
		switch(bullType) {
		case NORMAL:
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection, 2));
			break;
		case TRIPLET:
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection - .26f, 2));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection, 2));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection + .26f, 2));
			break;
		case FAN:
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection - .628f, 1));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection - .314f, 1));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection, 1));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection + .314f, 1));
			Globals.level.queueAddEntity(new EnemyBullet(host.getX() + (host.getWidth() >> 1), host.getY() + (host.getHeight() >> 1), usedDirection + .628f, 1));
			break;
		}
		
	}

	@Override
	public boolean complete() {
		return true;
	}

	@Override
	public void reset() {}

}
