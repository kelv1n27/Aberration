package directives;

import enemies.Enemy;
import game.Globals;

public class BezierPath implements Directive {
	
	public enum angleBehavior {
		FACE_PATH,
		FACE_PLAYER,
		FACE_CONSTANT
	}
	
	private int totalTime;
	private int timer = 0;
	private float [][] points;
	private angleBehavior behavior;
	
	public BezierPath(int totalTime, float[][] points, angleBehavior behavior) {
		this.totalTime = totalTime;
		this.points = points;
		this.behavior = behavior;
	}

	@Override
	public void tick(Enemy host) {
		float[] coords = getCoords();
		host.teleport(coords[0], coords[1]);
		switch(behavior) {
		case FACE_PATH:
			host.setDirection(getSlope());
			break;
		case FACE_PLAYER:
			host.setDirection((float)Math.atan2((Globals.level.getPlayer().getY() + (Globals.level.getPlayer().getHeight() >> 1)) - (host.getY() + (host.getHeight() >> 1)), (Globals.level.getPlayer().getX() + (Globals.level.getPlayer().getWidth() >> 1)) - (host.getX() + (host.getWidth() >> 1))));
			break;
		case FACE_CONSTANT:
			break;
		}
		timer++;
	}

	@Override
	public boolean complete() {
		return timer >= totalTime;
	}
	
	private float[] getCoords() {
		float progress = ((float)timer)/totalTime;
		float[][] ptsCopy = new float[points.length][];
		for (int i = 0; i < points.length; i++) ptsCopy[i] = points[i].clone();
		int numPoints = ptsCopy.length;
		while(numPoints > 1){
			for(int i = 0; i < numPoints - 1; i++){
//				System.out.println(i);
				ptsCopy[i][0] += (int)(progress * (ptsCopy[i + 1][0] - ptsCopy[i][0]));
				ptsCopy[i][1] += (int)(progress * (ptsCopy[i + 1][1] - ptsCopy[i][1]));
			}
//			System.out.println();
			numPoints--;
		}
		return new float[] {ptsCopy[0][0], ptsCopy[0][1]};
	}
	
	public float getSlope() {
		float progress = (float)timer/totalTime;
		float[][] ptsCopy = new float[points.length][];
		for (int i = 0; i < points.length; i++) ptsCopy[i] = points[i].clone();
		int numPoints = ptsCopy.length;
		while(numPoints > 2){
			for(int i = 0; i < numPoints - 1; i++){
//				System.out.println(i);
				ptsCopy[i][0] += (int)(progress * (ptsCopy[i + 1][0] - ptsCopy[i][0]));
				ptsCopy[i][1] += (int)(progress * (ptsCopy[i + 1][1] - ptsCopy[i][1]));
			}
//			System.out.println();
			numPoints--;
		}
		return (float)Math.atan2(ptsCopy[1][1]-ptsCopy[0][1], ptsCopy[1][0] - ptsCopy[0][0]);
	}

}
