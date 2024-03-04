package particles;

public abstract class Particle {
	
	protected float x, y;
	
	public Particle(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render();
	
	public abstract boolean complete();
	
	public abstract void release();
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void teleport(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
