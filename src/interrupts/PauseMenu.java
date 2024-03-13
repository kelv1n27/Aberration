package interrupts;

import game.Globals;
import game.Level;

public class PauseMenu implements Interrupt{
	
	private int color = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[] {1, 1}));
	
	private int index = 0;
	private int menuSize = 3;
	private boolean complete = false;
	private boolean quit = false;
	
	private Interrupt interrupt;
	
	private Level level;
	
	public PauseMenu(Level level) {
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
				}
			}
			return;
		}
		
		if (Globals.inp.getFresh("up") && Globals.inp.getPressed("up")) {
			Globals.inp.setFresh("up", false);
			index = (index + menuSize - 1) % menuSize;
			Globals.createManagedSfx("/sfx/Aberration - Track 04 (item change).wav", 10);
		}
		if (Globals.inp.getFresh("down") && Globals.inp.getPressed("down")) {
			Globals.inp.setFresh("down", false);
			index = (index + menuSize + 1) % menuSize;
			Globals.createManagedSfx("/sfx/Aberration - Track 04 (item change).wav", 10);
		}
		if (Globals.inp.getFresh("attack") && Globals.inp.getPressed("attack")) {
			Globals.inp.setFresh("attack", false);
			Globals.createManagedSfx("/sfx/Aberration - Track 05 (item select).wav", 10);
			switch(index) {
			case 0:
				complete = true;
				break;
			case 1:
				interrupt = new OptionsMenu();
				break;
			case 2:
				interrupt = new FadeOut(60);
				quit = true;
			}
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, color, 0, 0, 0, 0, 1, 1, 256f, 240f, false, false, .5f});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 0, 1f, "paused"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 32, 1f, "resume"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 48, 1f, "options"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 64, 1f, "quit"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 8, 32 + (16 * index), 1f, ">"});
		
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
