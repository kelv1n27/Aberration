package game;

import interrupts.Interrupt;
import interrupts.MainMenu;

public class Game {
	
	private boolean running = true;
	private Globals globals;
	private Interrupt interrupt;
	private boolean startGame = false;
	
	private boolean skipMainMenu = true;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
	
	public Game() {
		globals = new Globals(this);
		Globals.wnd.updateWindow();
		if (!skipMainMenu)
			interrupt = new MainMenu();
		else {
			interrupt = new Level();
			Globals.level = (Level) interrupt;
		}
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
				if (startGame) {
					startGame = false;
					interrupt = new Level();
					Globals.level = (Level)interrupt;
				}
			}
		}
		else {
			interrupt = new MainMenu();
		}
	}
	
	public void stop() {
		running = false;
	}

	public void startGame() {
		startGame = true;
	}
}
