package game;

import interrupts.FadeIn;
import interrupts.Interrupt;
import interrupts.PauseMenu;

public class Level implements Interrupt{
	
	private Interrupt interrupt = new FadeIn(60);
	private boolean complete = false;

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
		
		if (Globals.inp.getFresh("escape") && Globals.inp.getPressed("escape")) {
			Globals.inp.setFresh("escape", false);
			interrupt = new PauseMenu(this);
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xffff0000});
		
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
		// TODO Auto-generated method stub
		
	}
	
	public void quit() {
		complete = true;
	}

}
