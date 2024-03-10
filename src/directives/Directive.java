package directives;

import enemies.Enemy;

public interface Directive {
	
	public abstract void tick(Enemy host);
	
	public abstract boolean complete();
	
	public abstract void reset();

}
