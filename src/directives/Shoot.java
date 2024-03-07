package directives;

import enemies.Enemy;
import entities.EnemyBullet;
import game.Globals;

public class Shoot implements Directive {
	
	public enum bulletType{
		NORMAL
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
		float usedDirection = 0;
		switch(dirType) {
		case HOST_DIR:
			usedDirection = host.getDirection();
			break;
		case PLAYER_DIR:
			usedDirection = (float)Math.atan2(Globals.level.getPlayer().getY() - host.getY(), Globals.level.getPlayer().getX() - host.getX());
			break;
		case CUSTOM_DIR:
			usedDirection = direction;
			break;
		}
		switch(bullType) {
		case NORMAL:
			Globals.level.queueAddEntity(new EnemyBullet(host.getX(), host.getY(), usedDirection, 2));
		}
		
	}

	@Override
	public boolean complete() {
		return true;
	}

}
