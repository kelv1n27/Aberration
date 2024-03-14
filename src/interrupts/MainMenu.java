package interrupts;

import audioHandlerV2_Processors.SampleProcessor;
import game.Globals;

public class MainMenu implements Interrupt{
	
	private int index = 0;
	private int menuSize = 3;
	
	private Interrupt interrupt = new FadeIn(60);
	
	private boolean startGame = false;
	private boolean complete = false;
	
	private SampleProcessor bgm = new SampleProcessor("/sfx/Aberration - Track 01 (Title Screen).wav");
	
	private int titlescreen = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/title screen.png"}));
	private int cardColor = Globals.gfx.loadMemory(Globals.gfx.buildMemoryObject("IntArrayImage", new Object[]{"/sprites/title screen card.png"}));
	
	public MainMenu() {
		Globals.bgm.addProcessor(bgm, 0);
		bgm.togglePause(false);
//		Globals.gfx.runPlugin("FillColor", new Object[] {cardColor, 0xff000000});
	}

	@Override
	public void tick() {
		
		if (interrupt != null) {
			interrupt.tick();
			if (interrupt.complete()) {
				interrupt.release();
				interrupt = null;
				if (startGame) {//complete interrupt only after fade out from choosing level
					startGame = false;
					complete = true;
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
				interrupt = new FadeOut(60);
				startGame = true;
				Globals.game.startGame();
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
		
		Globals.gfx.runPlugin("FillColor", new Object[] {Globals.mainCanvas, 0xff000000});
		
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, titlescreen, 0, 0, 0, 0, 256, 240, 1f, 1f, false, false, 1f});
		Globals.gfx.runPlugin("Render", new Object[] {Globals.mainCanvas, cardColor, 0, 0, 0, 0, 256, 240, 1f, 1f, false, false, 1f});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 0, 2f, "aberrant"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 20, 2f, "defense"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 52, 1f, "play"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 68, 1f, "options"});
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 16, 84, 1f, "quit"});
		
		Globals.gfx.runPlugin("RenderFont", new Object[] {Globals.mainCanvas, Globals.font, 8, 52 + (16 * index), 1f, ">"});
		
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
		bgm.dispose();
		Globals.bgm.removeProcessor(bgm);
		Globals.gfx.releaseMemory(titlescreen);
		Globals.gfx.releaseMemory(cardColor);
	}

}
