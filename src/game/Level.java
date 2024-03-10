package game;

import java.util.ArrayList;

import enemies.Enemy;
import entities.Entity;
import entities.Player;
import events.EventList;
import interrupts.FadeIn;
import interrupts.GameOver;
import interrupts.Interrupt;
import interrupts.PauseMenu;

public class Level implements Interrupt{
	
	private Interrupt interrupt = new FadeIn(60);
	private boolean complete = false;
	
	public ArrayList<Runnable> postRenders = new ArrayList<Runnable>();
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> entityAddQueue = new ArrayList<Entity>();
	private ArrayList<Entity> entityRemoveQueue = new ArrayList<Entity>();
	private Player player = new Player(120, 150);
	
	private int score = 0, loop = 2;
	
	private EventList events = new EventList();
	private int eventTimer = 0;
	
	private int landscape = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/landscape.png"}));
	
	public Level() {

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
		//about 13200 ticks before boss maybe?
		if (eventTimer == 13200) eventTimer = 0;
		events.checkEvents(eventTimer++);
		
		for (Entity e : entities) {
			e.tick();
		}
		player.tick();
		for (Entity e : entityRemoveQueue) {
			//e.release();
			//aaaaaaaaaaaaaargh
			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
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
		//render the level
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xffff0000});
		
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, landscape, 0, 0, 0, 13244 - (eventTimer >> 2), 256, 240, 1f, 1f, false, false, 1f});
		
		//render all entities
		for (Entity e : entities) {
			e.render();
		}
		player.render();
		
		//render enemy blobs
		Globals.enemySoS();
		
		//render enemy add-ons
		for (Runnable r : postRenders) r.run();
		postRenders.clear();

		//HUD
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 0, 0, 1f, "lives"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 0, 12, 1f, "" + player.getLives()});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 108, 0, 1f, "score"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 128 - (((int)Math.log10(score+1)+1) * 4), 12, 1f, "" + score});
		
		if (loop > 0) {
			Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 224, 0, 1f, "loop"});
			Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 256 - (((int)Math.log10(loop+1)+1) * 8), 12, 1f, "" + loop});
		}
		
		//debug
		for (Entity e : entities) {
			e.renderOutline(0xff00ff00);
		}
		player.renderOutline(0xff0000ff);
		
		//render interrupt
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
		Globals.gfx.releaseMemory(landscape);
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
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLoop(){
		return loop;
	}
	
	public void gameOver() {
		interrupt = new GameOver(this);
	}
	
	public void reset() {
		score = 0;
		loop = 0;
		eventTimer = 0;
//		events.reset();
		//nevermind, i give up
		events = new EventList();
		player.release();
		player = new Player(120, 150);
//		for (Entity e : entities) {
//			//e.release();
//			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
//		}
		entities.clear();
//		for (Entity e : entityAddQueue) {
//			//e.release();
//			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
//		}
		entityAddQueue.clear();
		entityRemoveQueue.clear();
		
	}

}
