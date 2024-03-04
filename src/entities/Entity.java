package entities;

import game.Globals;

public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected int health;
	
	public Entity(float x, float y, int width, int height, int health) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.health = health;
	}
	
	public abstract void tick();
	
	public abstract void render();
	
	public abstract void release();
	
	public abstract void hurt();
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void teleport(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean intersects(Entity e) {
		return ((Math.abs((x + (width / 2)) - (e.x + (e.width / 2))) * 2) < (width + e.width)) && ((Math.abs((y + (height / 2)) - (e.y + (e.height / 2))) * 2) < (height + e.height));
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	} 
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void renderOutline(int color) {
		Globals.gfx.runPlugin("DrawLine", new Object[] {(int)x, (int)y, (int)x + width, (int)y, color, Globals.mainCanvas});
		Globals.gfx.runPlugin("DrawLine", new Object[] {(int)x + width, (int)y, (int)x + width, (int)y + height, color, Globals.mainCanvas});
		Globals.gfx.runPlugin("DrawLine", new Object[] {(int)x + width, (int)y + height, (int)x, (int)y + height, color, Globals.mainCanvas});
		Globals.gfx.runPlugin("DrawLine", new Object[] {(int)x, (int)y + height, (int)x, (int)y, color, Globals.mainCanvas});
	}

}
