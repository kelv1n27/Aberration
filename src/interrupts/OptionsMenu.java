package interrupts;

import game.Globals;

public class OptionsMenu implements Interrupt{
	
	private int index = 0;
	private int menuSize = 5;
	
	private boolean complete = false;

	@Override
	public void tick() {
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
			
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				complete = true;
			}
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xff000000});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 0, 2f, "options Menu"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 32, 1f, "sfxVol"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 48, 1f, "bgm vol"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 64, 1f, "output res"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 80, 1f, "fullscreen"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 96, 1f, "back"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 8, 32 + (16 * index), 1f, ">"});
	}

	@Override
	public boolean complete() {
		return complete;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
