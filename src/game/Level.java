package game;

import java.util.ArrayList;

import audioHandlerV2_Processors.SampleProcessor;
import directives.BezierPath;
import directives.Directive;
import directives.HoundPlayer;
import directives.Idle;
import directives.Loop;
import directives.Shoot;
import enemies.Annihilator;
import enemies.BigBlobEnemy;
import enemies.BigEyeEnemy;
import enemies.BigMouthEnemy;
import enemies.BlobEnemy;
import enemies.Enemy;
import entities.Entity;
import entities.Player;
import events.EventList;
import interrupts.BossIntro;
import interrupts.BossSplat;
import interrupts.FadeIn;
import interrupts.GameOver;
import interrupts.Interrupt;
import interrupts.PauseMenu;

public class Level implements Interrupt{
	
	private Interrupt pausingInterrupt;
	private Interrupt concurrentInterrupt = new FadeIn(60);
	private boolean complete = false;
	
	public ArrayList<Runnable> postRenders = new ArrayList<Runnable>();
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> entityAddQueue = new ArrayList<Entity>();
	private ArrayList<Entity> entityRemoveQueue = new ArrayList<Entity>();
	private Player player = new Player(120, 150);
	
	private int score = 0, loop = 0;
	
	private EventList events = new EventList();
	private int eventTimer = 0;
	
	private int landscape = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/landscape.png"}));
	private int landscapewater = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/landscape water.png"}));
	
	private Enemy boss;
	private int bossTimer = 0;
	private SampleProcessor bossbgm = new SampleProcessor("/sfx/Aberration - Track 03 (Boss).wav");
	
	private SampleProcessor bgm = new SampleProcessor("/sfx/Aberration - Track 02 (Flight).wav");
	
	private int lastLife = 0;
	
	private int lastInvade = 0;
	
	private int waves;
	
	public Level() {
		waves = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[] {256, 240}));
		Globals.bgm.addProcessor(bgm, 0);
		bgm.togglePause(false);
		Globals.bgm.addProcessor(bossbgm, 0);
		bossbgm.togglePause(true);
		bossbgm.setLooping(true);
	}

	@Override
	public void tick() {
		
		if (pausingInterrupt != null) {
			pausingInterrupt.tick();
			if (pausingInterrupt.complete()) {
				pausingInterrupt.release();
				pausingInterrupt = null;
			}
			return;
		}
		
		if (concurrentInterrupt != null) {
			concurrentInterrupt.tick();
			if (concurrentInterrupt.complete()) {
				concurrentInterrupt.release();
				concurrentInterrupt = null;
			}
		}
		
		//about 13200 ticks before boss maybe?
		if (eventTimer == 13200) eventTimer = 0;
		events.checkEvents(eventTimer++);
		
		//random invaders
		if (loop > 0 && boss == null && lastInvade-- < 0) {
			//random chance for invade to occur
			if (Globals.rand.nextFloat() * 15 < loop) {
				float direction = (Globals.rand.nextFloat() * -3.14f);
				if (loop < 5) entities.add(new BlobEnemy(((float)Math.cos(direction) * 200) + 128, ((float)Math.sin(direction) * 200) + 120, new Directive[] {new HoundPlayer(.75f)}));
				else if (loop < 8) entities.add(new BigBlobEnemy(((float)Math.cos(direction) * 200) + 128, ((float)Math.sin(direction) * 200) + 120, new Directive[] {new HoundPlayer(.5f)}));
				else {
					if (Globals.rand.nextFloat() * 10 < 5) entities.add(new BigBlobEnemy(((float)Math.cos(direction) * 200) + 128, ((float)Math.sin(direction) * 200) + 120, new Directive[] {new HoundPlayer(.5f)}));
					else entities.add(new BigMouthEnemy(((float)Math.cos(direction) * 200) + 128, ((float)Math.sin(direction) * 200) + 120, new Directive[] {new HoundPlayer(.5f)}));
				}
			}
			lastInvade = Math.max(1200 - (60 * loop), 300);
		}
		
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
			pausingInterrupt = new PauseMenu(this);
		}
	}

	@Override
	public void render() {
		//render the level
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xffff0000});
		
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, landscapewater, 0, 0, 0, 13244 - (eventTimer >> 2), 256, 240, 1f, 1f, false, false, 1f});
		Globals.gfx.runPlugin("SumOfSines", new Object[] {waves, 5, .5f, 50f, .8f, .2f, eventTimer >> 3, eventTimer >> 2});
    	Globals.gfx.runPlugin("HeightMapColorize", new Object[] {waves, waves, 200, new int[] {0xff5256fa, 0xffffffff, 0xff5256fa, 0xff4fafff}});
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, waves, 0, 0, 0, 0, 256, 240, 1f, 1f, false, true, .1f});
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
//		for (Entity e : entities) {
//			e.renderOutline(0xff00ff00);
//		}
//		player.renderOutline(0xff0000ff);
		
		//render interrupt
		if (concurrentInterrupt != null) {
			concurrentInterrupt.render();
		}
		if (pausingInterrupt != null) {
			pausingInterrupt.render();
		}
	}

	@Override
	public boolean complete() {
		return complete;
	}

	@Override
	public void release() {
		Globals.gfx.releaseMemory(waves);
		Globals.gfx.releaseMemory(landscape);
		Globals.gfx.releaseMemory(landscapewater);
		player.release();
		for (Entity e : entities) {
			e.release();
		}
		Globals.bgm.removeProcessor(bgm);
		bgm.dispose();
		Globals.bgm.removeProcessor(bossbgm);
		bossbgm.dispose();
	}
	
	public void quit() {
		complete = true;
	}
	
	public void queueAddEntity(Entity e) {
		entityAddQueue.add(e);
	}
	
	public void queueRemoveEntity(Entity e) {
		entityRemoveQueue.add(e);
		if (boss != null & e.equals(boss)) {
			concurrentInterrupt = new BossSplat(e.getX() + (e.getWidth() >> 1), e.getY());
			boss = null;
		}
	}
	
	public void addScore(int score) {
		this.score += score;
		if (this.score >= lastLife + 10000) {
			player.getLife();
			lastLife += 10000;
			Globals.createManagedSfx("/sfx/Aberration - Track 13 (powerup).wav", 30);
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLoop(){
		return loop;
	}
	
	public void gameOver() {
		pausingInterrupt = new GameOver(this);
		bgm.togglePause(true);
	}
	
	public void reset() {
		bgm.resetSample();
		bgm.togglePause(false);
		bossbgm.resetSample();
		bossbgm.togglePause(true);
		score = 0;
		loop = 0;
		eventTimer = 0;
//		events.reset();
		//nevermind, i give up
		events = new EventList();
		player.release();
		player = new Player(120, 150);
		for (Entity e : entities) {
			//e.release();
			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
		}
		entities.clear();
		for (Entity e : entityAddQueue) {
			//e.release();
			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
		}
		entityAddQueue.clear();
		entityRemoveQueue.clear();
		if (concurrentInterrupt != null)
			concurrentInterrupt.release();
		concurrentInterrupt = null;
		lastLife = 0;
	}
	
	public void loopReset() {
		bgm.resetSample();
		bgm.togglePause(false);
		bossbgm.resetSample();
		bossbgm.togglePause(true);
		eventTimer = 0;
//		events.reset();
		//nevermind, i give up
		events = new EventList();
		for (Entity e : entities) {
			//e.release();
			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
		}
		entities.clear();
		for (Entity e : entityAddQueue) {
			//e.release();
			//aaaaaaaaaaaaaargh
//			if (e instanceof Enemy) ((Enemy) e).resetDirectives();
		}
		entityAddQueue.clear();
		entityRemoveQueue.clear();
		loop++;
		lastInvade = 1200;
	}

	public void startBossIntro() {
//		entities.add(new BigEyeEnemy(108, 30, 1, new Directive[] {new Idle(6000, Idle.angleBehavior.FACE_PLAYER)}));
//		entities.add(new BigMouthEnemy(108, 30, new Directive[] {new Shoot(Shoot.bulletType.FAN, Shoot.directionType.PLAYER_DIR), new Idle(6000, Idle.angleBehavior.FACE_PLAYER)}));
		concurrentInterrupt = new BossIntro();
	}
	
	public void startBossFight() {
		bgm.togglePause(true);
		bossbgm.resetSample();
		bossbgm.togglePause(false);
		Enemy newBoss;
		switch(loop) {
		case 0:
			newBoss = new BigEyeEnemy(108, -50, 50, 3000, new Directive[] {
					new BezierPath(30, new float[][] {{108, -50}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new BezierPath(80, new float[][] {{108, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR), 
					new BezierPath(80, new float[][] {{44, 10}, {0, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{0, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{108, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {216, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{216, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
//					new Idle(60, Idle.angleBehavior.FACE_CONSTANT),
					new Loop(1, 3)});
			break;
		case 1:
			newBoss = new BigEyeEnemy(108, -50, 60, 3000, new Directive[] {
					new BezierPath(30, new float[][] {{108, -50}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new BezierPath(80, new float[][] {{108, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR), 
					new BezierPath(80, new float[][] {{44, 10}, {0, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{0, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{108, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {216, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{216, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
//					new Idle(60, Idle.angleBehavior.FACE_CONSTANT),
					new Loop(1, 3)});
			break;
		case 2:
			newBoss = new BigEyeEnemy(108, -50, 60, 5000, new Directive[] {
					new BezierPath(30, new float[][] {{108, -50}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new BezierPath(80, new float[][] {{108, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {0, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{0, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{108, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {216, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{216, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new Idle(20, Idle.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
//					new Idle(60, Idle.angleBehavior.FACE_CONSTANT),
					new Loop(1, 3)});
			break;
		case 3:
			newBoss = new BigEyeEnemy(108, -50, 75, 5000, new Directive[] {
					new BezierPath(30, new float[][] {{108, -50}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new BezierPath(80, new float[][] {{108, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {0, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{0, 10}, {44, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{44, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{108, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {216, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{216, 10}, {172, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new BezierPath(80, new float[][] {{172, 10}, {108, 10}}, BezierPath.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.FAN, Shoot.directionType.HOST_DIR),
					new Idle(20, Idle.angleBehavior.FACE_PLAYER),
					new Shoot(Shoot.bulletType.TRIPLET, Shoot.directionType.HOST_DIR),
//					new Idle(60, Idle.angleBehavior.FACE_CONSTANT),
					new Loop(1, 3)});
			break;
		default:
			newBoss = new Annihilator(0, -50, 100 + (50 * (loop - 3)));
			break;
		}
		
		entities.add(newBoss);
		boss = newBoss;
	}

	public void flushEntities() {
		for (Entity e : entities) {
			entityRemoveQueue.add(e);
		}
	}

}
