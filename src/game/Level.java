package game;

import java.util.ArrayList;

import directives.BezierPath;
import directives.Directive;
import directives.Idle;
import directives.Shoot;
import directives.Spawn;
import enemies.BlobEnemy;
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
	
	private int score = 0, loop = 0;
	
	public Level() {
		entities.add(new BlobEnemy(100, 100, new Directive[] {
				new Spawn(new BlobEnemy(100, 100, new Directive[] {new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})),
				new BezierPath(100, new float[][] {{1, 1}, {0, 240}, {100, 0}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.PLAYER_DIR)}));
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
		//render the level
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xffff0000});
		
		//render all entities
		for (Entity e : entities) {
			e.render();
		}
		player.render();
		
		//render enemy blobs
		Globals.enemySoS();

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

}
