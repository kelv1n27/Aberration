package interrupts;

import game.Globals;
import game.Level;

public class GameOver implements Interrupt {
	
	private int color = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[] {1, 1}));
	
	private Level level;
	
	private int index = 0;
	private int menuSize = 2;
	private boolean complete = false;
	private boolean quit = false;
	
	private Interrupt interrupt;
	
	public GameOver(Level level) {
		this.level = level;
		Globals.gfx.runPlugin("FillColor", new Object[] {color, 0xff000000});
	}

	@Override
	public void tick() {
		if (interrupt != null) {
			interrupt.tick();
			if (interrupt.complete()) {
				interrupt.release();
				interrupt = null;
				if (quit) {
					complete = true;
					level.quit();
				} else {
					complete = true;
					level.reset();
				}
			}
			return;
		}
		
		if (Globals.inp.getFresh("up") && Globals.inp.getPressed("up")) {
			Globals.inp.setFresh("up", false);
			index = (index + menuSize - 1) % menuSize;
		}
		if (Globals.inp.getFresh("down") && Globals.inp.getPressed("down")) {
			Globals.inp.setFresh("down", false);
			index = (index + menuSize + 1) % menuSize;
		}
		if (Globals.inp.getFresh("attack") && Globals.inp.getPressed("attack")) {
			Globals.inp.setFresh("attack", false);
			switch(index) {
			case 0:
				interrupt = new FadeOut(60);
				quit = false;
				break;
			case 1:
				interrupt = new FadeOut(60);
				quit = true;
				break;
			}
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, color, 0, 0, 0, 0, 1, 1, 256f, 240f, false, false, .5f});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 56, 64, 2f, "Game over"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 108, 90, 1f, "retry"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 112, 108, 1f, "quit"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, (index == 0 ? 100 : 104), 90 + (18 * index), 1f, ">"});
		
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
		Globals.gfx.releaseMemory(color);
	}

}
