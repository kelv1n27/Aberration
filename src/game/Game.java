package game;

import interrupts.Interrupt;
import interrupts.MainMenu;

public class Game {
	
	private boolean running = true;
	private Globals globals;
	private Interrupt interrupt;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
	
	public Game() {
		globals = new Globals(this);
		interrupt = new MainMenu();
	}
	
	public void run() {
		double lastSec = System.currentTimeMillis();
		int numTicks = 0;
		double lastTick = System.currentTimeMillis();
		while (running) {
			if (System.nanoTime() - lastTick >= 1000000000/60) {
				numTicks++;
				lastTick = System.nanoTime();
				tick();
				render();
				Globals.tickCount++;
				Globals.wnd.updateCanvas();
			}
			if (System.nanoTime() - lastSec >= 1000000000) {
				lastSec = System.nanoTime();
				Globals.wnd.setWindowTitle("" + numTicks);
				numTicks = 0;
			}
		}
		Globals.gfx.Release();
		Globals.aud.release();
		Globals.wnd.exit();
		System.exit(0);
	}

	private void render() {
		if (interrupt != null) interrupt.render();
		else {
			
		}
	}

	private void tick() {
		if (interrupt != null) {
			interrupt.tick();
			if (interrupt.complete()) {
				interrupt.release();
				interrupt = null;
			}
		}
		else {
			
		}
	}
	
	public void stop() {
		running = false;
	}

}
