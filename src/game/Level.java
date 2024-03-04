package game;

import java.util.ArrayList;

import entities.BlobEnemy;
import entities.Entity;
import entities.Player;
import interrupts.FadeIn;
import interrupts.Interrupt;
import interrupts.PauseMenu;

public class Level implements Interrupt{
	
	private Interrupt interrupt = new FadeIn(60);
	private boolean complete = false;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> entityAddQueue = new ArrayList<Entity>();
	private ArrayList<Entity> entityRemoveQueue = new ArrayList<Entity>();
	private Player player = new Player(120, 150);
	
	public Level() {
		entities.add(new BlobEnemy(100, 100));
	}

	@Override
	public void tick() {
		
		if (interrupt != null) {
			interrupt.tick();
			if (interrupt.complete()) {
				interrupt.release();
				interrupt = null;
			}
			return;
		}
		
		for (Entity e : entities) {
			e.tick();
		}
		player.tick();
		for (Entity e : entityRemoveQueue) {
			e.release();
			entities.remove(e);
		}
		entityRemoveQueue.clear();
		for (Entity e : entityAddQueue) entities.add(e);
		entityAddQueue.clear();
		
		if (Globals.inp.getFresh("escape") && Globals.inp.getPressed("escape")) {
			Globals.inp.setFresh("escape", false);
			interrupt = new PauseMenu(this);
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xffff0000});
		
		for (Entity e : entities) {
			e.render();
		}
		player.render();
		
		Globals.enemySoS();
		
		for (Entity e : entities) {
			e.renderOutline(0xff00ff00);
		}
		player.renderOutline(0xff0000ff);
		
		if (interrupt != null) {
			interrupt.render();
		}
	}

	@Override
	public boolean complete() {
		return complete;
	}

	@Override
	public void release() {
		player.release();
		for (Entity e : entities) e.release();
	}
	
	public void quit() {
		complete = true;
	}
	
	public void queueAddEntity(Entity e) {
		entityAddQueue.add(e);
	}
	
	public void queueRemoveEntity(Entity e) {
		entityRemoveQueue.add(e);
	}

}
