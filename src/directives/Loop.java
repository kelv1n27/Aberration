package directives;

import enemies.Enemy;

public class Loop implements Directive {
	
	private int loopPoint, iterations, currentIteration = 0;
	
	public Loop(int loopPoint, int iterations) {
		this.loopPoint = loopPoint;
		this.iterations = iterations;
	}

	@Override
	public void tick(Enemy host) {
		host.resetDirectives();
		host.setDirective(loopPoint);
		currentIteration++;
	}

	@Override
	public boolean complete() {
		return currentIteration >= iterations;
	}

	@Override
	public void reset() {
		currentIteration = 0;
	}

}
