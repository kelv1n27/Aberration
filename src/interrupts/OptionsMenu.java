package interrupts;

import audioHandlerV2_Processors.SampleProcessor;
import game.Globals;

public class OptionsMenu implements Interrupt{
	
	private int index = 0;
	private int menuSize = 6;
	
	private boolean complete = false;
	
	
	
	public OptionsMenu() {
		
	}

	@Override
	public void tick() {
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
				Globals.sfxVolume += .1;
				if ((int)(Globals.sfxVolume * 10) > 10) Globals.sfxVolume = 0;
				Globals.sfxVol.changeVol(Globals.sfxVolume);
				break;
			case 1:
				Globals.bgmVolume += .1;
				if ((int)(Globals.bgmVolume * 10) > 10) Globals.bgmVolume = 0;
				Globals.bgmVol.changeVol(Globals.bgmVolume);
				break;
			case 2:
				Globals.wnd.setFullscreen(!Globals.wnd.getFullscreen());
				Globals.wnd.updateWindow();
				break;
			case 3:
				Globals.wnd.cycleRatio();
				Globals.wnd.updateWindow();
				break;
			case 4:
				Globals.wnd.setDimIndex(Globals.wnd.getDimIndex()+1);
				Globals.wnd.updateWindow();
				break;
			case 5:
				complete = true;
			}
		}
	}

	@Override
	public void render() {
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xff000000});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 0, 2f, "options Menu"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 32, 1f, "sfxVol: " + (int)(Globals.sfxVolume * 10)});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 48, 1f, "bgm vol: " + (int)(Globals.bgmVolume * 10)});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 64, 1f, "fullscreen: " + Globals.wnd.getFullscreen()});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 80, 1f, "ratio: " + Globals.wnd.getCurrentRatio()});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 96, 1f, "resolution: " + Globals.wnd.getDims()[Globals.wnd.getDimIndex()][0] + ":" + Globals.wnd.getDims()[Globals.wnd.getDimIndex()][1]+ " " + (Globals.wnd.getDimIndex()==0?"(auto)":"")} );
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 112, 1f, "back"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 8, 32 + (16 * index), 1f, ">"});
	}

	@Override
	public boolean complete() {
		return complete;
	}

	@Override
	public void release() {
		
	}

}
