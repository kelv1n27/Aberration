package interrupts;

import game.Globals;

public class MainMenu implements Interrupt{
	
	private int index = 0;
	private int menuSize = 3;
	
	private Interrupt interrupt;

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
				
				break;
			case 1:
				interrupt = new OptionsMenu();
				break;
			case 2:
				Globals.game.stop();
			}
		}
	}

	@Override
	public void render() {
		
		if (interrupt != null) {
			interrupt.render();
			return;
		}
		
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xff000000});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 0, 2f, "Main Menu"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 32, 1f, "play"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 48, 1f, "options"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 64, 1f, "quit"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 8, 32 + (16 * index), 1f, ">"});
	}

	@Override
	public boolean complete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
