package events;

import java.util.ArrayList;

import directives.BezierPath;
import directives.Directive;
import directives.Idle;
import directives.Shoot;
import directives.StraightMove;
import enemies.BlobEnemy;
import enemies.EyeEnemy;
import game.DeferredConditional;
import game.Globals;

public class EventList {
	
	private int index = 0;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	public EventList() {
//		events.add(new SpawnEvent(0, new MouthEnemy(100, 50, new Directive[] {new Idle(3200, Idle.angleBehavior.FACE_PLAYER)})));

		events.add(new SpawnEvent(180, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 60}, {280, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(270, 
				(DeferredConditional) new DeferredConditional() {public boolean conditional() {return Globals.level.getLoop() > 0;}}, 
				new EyeEnemy(-50, 0, new Directive[] {
						new BezierPath(100, new float[][] {{-10, 230}, {128, 230}}, BezierPath.angleBehavior.FACE_PLAYER),
						new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR),
						new BezierPath(100, new float[][] {{128, 230}, {260, 230}}, BezierPath.angleBehavior.FACE_PLAYER)})));
		events.add(new SpawnEvent(360, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 60}, {-10, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(450, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{250, 0}, {176, 60}}, BezierPath.angleBehavior.FACE_PLAYER), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{176, 60}, {250, 0}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new SpawnEvent(450, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{-10, 0}, {64, 60}}, BezierPath.angleBehavior.FACE_PLAYER), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{64, 60}, {-10, 0}}, BezierPath.angleBehavior.FACE_PATH)})));
		
		events.add(new SpawnEvent(700, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{128, -20}, {128, 20}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(760, new DeferredConditional() {public boolean conditional() {return Globals.level.getLoop() > 0;}}, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{128, 256}, {128, 226}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(900, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 60}, {280, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1000, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 60}, {-10, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(1100, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 200}, {260, 200}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1150, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 20}, {-10, 20}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1200, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 120}, {260, 120}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1250, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 160}, {-10, 160}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1300, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 80}, {260, 80}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1350, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 210}, {-10, 210}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1100, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 40}, {260, 40}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1150, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 230}, {-10, 230}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
	}
	
	//assumes events list is sorted
	public void checkEvents(int time) {
		if (index < events.size() && events.get(index).getTime() <= time) {
			if (events.get(index).getTime() < time - 60) return;
			events.get(index).run();
			index++;
			checkEvents(time);
		}
		
	}
	
	public void reset() {
		index = 0;
	}

}