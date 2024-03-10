package events;

import java.util.ArrayList;

import directives.BezierPath;
import directives.Directive;
import directives.Idle;
import directives.Shoot;
import directives.Spawn;
import directives.StraightMove;
import enemies.BlobEnemy;
import enemies.EyeEnemy;
import enemies.MouthEnemy;
import game.DeferredConditional;
import game.Globals;

public class EventList {
	
	private int index = 0;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	//stupid way to spawn enemies, too late to redo it now
	public EventList() {
//		events.add(new SpawnEvent(0, new MouthEnemy(100, 50, new Directive[] {new Idle(3200, Idle.angleBehavior.FACE_PLAYER)})));

		events.add(new SpawnEvent(180, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 60}, {280, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(270, 
				getLoopConditional(0), 
				new EyeEnemy(-50, 0, new Directive[] {
						new BezierPath(100, new float[][] {{-10, 230}, {128, 230}}, BezierPath.angleBehavior.FACE_PLAYER),
						new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR),
						new BezierPath(100, new float[][] {{128, 230}, {260, 230}}, BezierPath.angleBehavior.FACE_PLAYER)})));
		events.add(new SpawnEvent(360, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 60}, {-10, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(360, 
				getLoopConditional(2), 
				new MouthEnemy(-50, 0, new Directive[] {
						new BezierPath(100, new float[][] {{260, 50}, {200, 30}}, BezierPath.angleBehavior.FACE_CONSTANT),
						new Spawn(new BlobEnemy(200, 30, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
						new BezierPath(100, new float[][] {{200, 30}, {120, 80}}, BezierPath.angleBehavior.FACE_CONSTANT),
						new Spawn(new BlobEnemy(120, 80, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
						new BezierPath(100, new float[][] {{120, 80}, {30, 45}}, BezierPath.angleBehavior.FACE_CONSTANT),
						new Spawn(new BlobEnemy(30, 45, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
						new BezierPath(100, new float[][] {{30, 45}, {-20, 60}}, BezierPath.angleBehavior.FACE_CONSTANT)
				}))
		);
		
		events.add(new SpawnEvent(450, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{250, 0}, {176, 60}}, BezierPath.angleBehavior.FACE_PLAYER), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{176, 60}, {250, 0}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new SpawnEvent(450, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{-10, 0}, {64, 60}}, BezierPath.angleBehavior.FACE_PLAYER), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{64, 60}, {-10, 0}}, BezierPath.angleBehavior.FACE_PATH)})));
		
		events.add(new SpawnEvent(700, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{128, -20}, {128, 20}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(760, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(120, new float[][] {{128, 256}, {128, 226}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(900, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 60}, {280, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1000, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 60}, {-10, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(1100, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 200}, {260, 200}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1150, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 20}, {-10, 20}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1200, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 120}, {260, 120}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(1225, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{64, 256},{64, -10}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1250, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 160}, {-10, 160}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(1275, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{128, -10},{128, 256}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1300, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 80}, {260, 80}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(1325, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{192, 256},{192, -10}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1350, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 210}, {-10, 210}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1400, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{-10, 40}, {260, 40}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(1450, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(240, new float[][] {{260, 190}, {-10, 190}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(1500, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{260, 260}, {220, 260}, {220, 120}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{220, 120}, {220, -20}, {260, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new SpawnEvent(1500, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-20, -20}, {30, -20}, {30, 120}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{30, 120}, {30, 250}, {-20, 250}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1575, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{260, -20}, {260, 30}, {120, 30}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{120, 30}, {-20, 30}, {-20, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1575, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-20, 260}, {-20, 210}, {126, 210}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{126, 210}, {260, 210}, {260, 260}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1650, getLoopConditional(3), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{128, -20}, {64, 64}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{64, 64}, {0, 128}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1650, getLoopConditional(3), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{128, 260}, {190, 190}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{190, 190}, {260, 128}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1725, getLoopConditional(5), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{260, 128}, {210, 64}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{210, 64}, {128, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(1725, getLoopConditional(5), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-20, 128}, {64, 190}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(160, new float[][] {{64, 190}, {128, 260}}, BezierPath.angleBehavior.FACE_PATH)})));
		
		events.add(new SpawnEvent(1800, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(90, new float[][] {{-20, 128}, {20, 128}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(1800, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(90, new float[][] {{260, 128}, {220, 128}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(1800, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(90, new float[][] {{128, -20}, {128, 20}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(1800, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new BezierPath(90, new float[][] {{128, 260}, {128, 230}}, BezierPath.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(2000, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 60}, {280, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2000, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 0}, {128, 120}, {280, 100}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2200, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 120}, {-10, 100}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2200, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 0}, {128, 60}, {-10, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new ConditionalSpawnEvent(2210, getLoopConditional(2), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(60, new float[][] {{260, 260}, {210, 210}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{210, 210}, {210, 20}}, BezierPath.angleBehavior.FACE_PLAYER) , new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(60, new float[][] {{210, 20}, {260, -20}}, BezierPath.angleBehavior.FACE_PLAYER) })));
		events.add(new ConditionalSpawnEvent(2210, getLoopConditional(2), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(60, new float[][] {{-20, -20}, {20, 20}}, BezierPath.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(120, new float[][] {{20, 20}, {20, 210}}, BezierPath.angleBehavior.FACE_PLAYER) , new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(60, new float[][] {{20, 210}, {-20, 260}}, BezierPath.angleBehavior.FACE_PLAYER) })));
		events.add(new SpawnEvent(2400, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 260}, {128, 150}, {280, 200}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2400, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{-10, 260}, {128, 100}, {280, 190}}, BezierPath.angleBehavior.FACE_CONSTANT), new Idle(200, Idle.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2600, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 260}, {128, 150}, {-10, 200}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(2600, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(160, new float[][] {{290, 260}, {128, 100}, {-10, 190}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(2800, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{-10, 210}, {190, 170}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(50, new float[][] {{190, 170}, {260, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new SpawnEvent(2800, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{260, 30}, {60, 80}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(50, new float[][] {{60, 80}, {-10, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		
		events.add(new ConditionalSpawnEvent(2800, getLoopConditional(0), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{-60, 210}, {140, 180}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{140, 180}, {260, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new ConditionalSpawnEvent(2800, getLoopConditional(0), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{310, 30}, {110, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{110, 70}, {-10, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		
		events.add(new ConditionalSpawnEvent(2800, getLoopConditional(2), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{-110, 210}, {90, 190}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(90, new float[][] {{90, 190}, {260, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new ConditionalSpawnEvent(2800, getLoopConditional(2), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{360, 30}, {160, 60}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(90, new float[][] {{160, 60}, {-10, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		
		events.add(new SpawnEvent(3200, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{260, 210}, {60, 170}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(50, new float[][] {{60, 170}, {-10, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new SpawnEvent(3200, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{-10, 30}, {190, 80}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(50, new float[][] {{190, 80}, {260, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		
		events.add(new ConditionalSpawnEvent(3200, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{310, 210}, {110, 180}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{110, 180}, {-10, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new ConditionalSpawnEvent(3200, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{-60, 30}, {140, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{140, 70}, {260, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
//		
		events.add(new ConditionalSpawnEvent(3200, getLoopConditional(3), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 4.51f), new BezierPath(130, new float[][] {{360, 210}, {160, 190}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(90, new float[][] {{160, 190}, {-10, 160}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		events.add(new ConditionalSpawnEvent(3200, getLoopConditional(3), new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.5f), new BezierPath(130, new float[][] {{-110, 30}, {90, 60}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(90, new float[][] {{90, 60}, {260, 90}}, BezierPath.angleBehavior.FACE_CONSTANT) })));
		
		events.add(new SpawnEvent(3500, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{42, -90}, {42, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{42, 10}, {22, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{22, 10}, {22, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(140, new float[][] {{22, 30}, {52, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{52, 30}, {42, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{84, -90}, {84, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{84, 10}, {64, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{64, 10}, {64, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(140, new float[][] {{64, 30}, {94, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{94, 30}, {84, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{126, -90}, {126, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{126, 10}, {106, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{106, 10}, {106, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(140, new float[][] {{106, 30}, {136, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{136, 30}, {126, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{168, -90}, {168, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{168, 10}, {148, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{148, 10}, {148, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(140, new float[][] {{148, 30}, {178, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{178, 30}, {168, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new EyeEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{210, -90}, {210, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{210, 10}, {190, 10}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{190, 10}, {190, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(140, new float[][] {{190, 30}, {220, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(70, new float[][] {{220, 30}, {210, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(3500, new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{42, -70}, {42, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{42, 30}, {22, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{22, 30}, {22, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{22, 50}, {52, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{52, 50}, {42, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{84, -70}, {84, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{84, 30}, {64, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{64, 30}, {64, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{64, 50}, {94, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{94, 50}, {84, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{126, -70}, {126, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{126, 30}, {106, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{106, 30}, {106, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{106, 50}, {136, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{136, 50}, {126, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{168, -70}, {168, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{168, 30}, {148, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{148, 30}, {148, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{148, 50}, {178, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{178, 50}, {168, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(3500, new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{210, -70}, {210, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{210, 30}, {190, 30}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{190, 30}, {190, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{190, 50}, {220, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{220, 50}, {210, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{42, -50}, {42, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{42, 50}, {22, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{22, 50}, {22, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{22, 70}, {52, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{52, 70}, {42, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{84, -50}, {84, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{84, 50}, {64, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{64, 50}, {64, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{64, 70}, {94, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{94, 70}, {84, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{126, -50}, {126, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{126, 50}, {106, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{106, 50}, {106, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{106, 70}, {136, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{136, 70}, {126, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{168, -50}, {168, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{168, 50}, {148, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{148, 50}, {148, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{148, 70}, {178, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{178, 70}, {168, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(0), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{210, -50}, {210, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{210, 50}, {190, 50}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{190, 50}, {190, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{190, 70}, {220, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{220, 70}, {210, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{42, -30}, {42, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{42, 70}, {22, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{22, 70}, {22, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{22, 90}, {52, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{52, 90}, {42, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{84, -30}, {84, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{84, 70}, {64, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{64, 70}, {64, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{64, 90}, {94, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{94,90}, {84, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{126, -30}, {126, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{126, 70}, {106, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{106, 70}, {106, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{106, 90}, {136, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{136, 90}, {126, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{168, -30}, {168, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{168, 70}, {148, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{148, 70}, {148, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{148, 90}, {178, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{178, 90}, {168, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new ConditionalSpawnEvent(3500, getLoopConditional(1), new BlobEnemy(-50, 0, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_CUSTOM, 1.57f), new BezierPath(70, new float[][] {{210, -30}, {210, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{210, 70}, {190, 70}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{190, 70}, {190, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(140, new float[][] {{190, 90}, {220, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new BezierPath(70, new float[][] {{220, 90}, {210, 90}}, BezierPath.angleBehavior.FACE_CONSTANT), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(4100, new MouthEnemy(-50, 0, new Directive[] {
				new BezierPath(100, new float[][] {{260, 40}, {192, 70}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(192, 70, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{192, 70}, {128, 20}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(128, 20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{128, 20}, {64, 40}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(64, 40, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{64, 40}, {-1, 50}}, BezierPath.angleBehavior.FACE_CONSTANT)
				})));
		
		events.add(new ConditionalSpawnEvent(4100, getLoopConditional(0), new EyeEnemy(-50, 0, new Directive[] {
				new BezierPath(100, new float[][] {{260, 70}, {192, 100}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{192, 100}, {128, 50}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{128, 50}, {64, 70}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{64, 70}, {-1, 80}}, BezierPath.angleBehavior.FACE_PLAYER)
				})));
		
		events.add(new ConditionalSpawnEvent(4100, getLoopConditional(2), new MouthEnemy(-50, 0, new Directive[] {
				new BezierPath(100, new float[][] {{-10, 200}, {64, 210}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(64, 210, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{64, 210}, {128, 190}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(128, 190, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{128, 190}, {192, 180}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(192, 180, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(100, new float[][] {{192, 180}, {260, 250}}, BezierPath.angleBehavior.FACE_CONSTANT)
				})));
		
		events.add(new ConditionalSpawnEvent(4100, getLoopConditional(0), new EyeEnemy(-50, 0, new Directive[] {
				new BezierPath(100, new float[][] {{-10, 170}, {64, 180}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{64, 180}, {128, 160}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{128, 160}, {192, 150}}, BezierPath.angleBehavior.FACE_PLAYER),
				new BezierPath(100, new float[][] {{192, 150}, {260, 250}}, BezierPath.angleBehavior.FACE_PLAYER)
				})));
		
		events.add(new SpawnEvent(4500, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(300, new float[][] {{0, 256}, {0, 0}, {256, 0}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(4500, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(300, new float[][] {{256, 256}, {256, 0}, {0, 0}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(4800, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(300, new float[][] {{0, 0}, {0, 256}, {256, 256}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		events.add(new SpawnEvent(4800, new BlobEnemy(-50, 0, new Directive[] {new BezierPath(300, new float[][] {{256, 0}, {256, 256}, {0, 256}}, BezierPath.angleBehavior.FACE_CONSTANT)})));
		
		events.add(new SpawnEvent(5100, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{-20, -20}, {20, 20}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{20, 20}, {-20, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new SpawnEvent(5100, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{118, -20}, {118, 20}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{118, 20}, {118, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new SpawnEvent(5100, new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{260, -20}, {216, 20}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{216, 20}, {260, -20}}, BezierPath.angleBehavior.FACE_PATH)})));
		
		events.add(new ConditionalSpawnEvent(5200, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{-20, 260}, {20, 210}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{20, 210}, {-20, 260}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(5200, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{118, 260}, {118, 210}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{118, 210}, {118, 260}}, BezierPath.angleBehavior.FACE_PATH)})));
		events.add(new ConditionalSpawnEvent(5200, getLoopConditional(1), new EyeEnemy(-50, 0, new Directive[] {new BezierPath(100, new float[][] {{260, 260}, {216, 210}}, BezierPath.angleBehavior.FACE_PATH), new Idle(60, Idle.angleBehavior.FACE_PLAYER), new Shoot(Shoot.bulletType.NORMAL, Shoot.directionType.HOST_DIR), new BezierPath(100, new float[][] {{216, 210}, {260, 260}}, BezierPath.angleBehavior.FACE_PATH)})));
		
		events.add(new SpawnEvent(5400, new BlobEnemy(-20, -20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5400, new BlobEnemy(276, -20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5400, new BlobEnemy(-20, 260, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5400, new BlobEnemy(276, 260, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(5550, new BlobEnemy(128, -20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5550, new BlobEnemy(276, 120, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5550, new BlobEnemy(128, 260, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		events.add(new SpawnEvent(5550, new BlobEnemy(-20, 120, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1.5f, StraightMove.angleBehavior.MOVE_HOST_DIR)})));
		
		events.add(new SpawnEvent(5700, new MouthEnemy(-50, 0, new Directive[] {
				new BezierPath(120, new float[][] {{-20, -20}, {20, 20}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(20, 20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(120, new float[][] {{20, 20}, {128, 20}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(128, 20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(120, new float[][] {{128, 20}, {236, 20}}, BezierPath.angleBehavior.FACE_CONSTANT),
				new Spawn(new BlobEnemy(236, 20, new Directive[] {new Idle(1, Idle.angleBehavior.FACE_PLAYER), new StraightMove(250, 1, StraightMove.angleBehavior.MOVE_HOST_DIR)})),
				new BezierPath(120, new float[][] {{236, 20}, {260, -20}}, BezierPath.angleBehavior.FACE_CONSTANT)
				})));
	}
	
	//assumes events list is sorted
	public void checkEvents(int time) {
		if (index < events.size() && events.get(index).getTime() <= time) {
			if (!(events.get(index).getTime() < time - 60))
				events.get(index).run();
			index++;
			checkEvents(time);
		}
		
	}
	
	public void reset() {
		index = 0;
	}
	
	private DeferredConditional getLoopConditional(int loop) {
		return new DeferredConditional() {public boolean conditional() {return Globals.level.getLoop() > loop;}};
	}

}