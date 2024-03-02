package game;

import entities.TestBlob;
import interrupts.FadeIn;
import interrupts.Interrupt;
import interrupts.PauseMenu;

public class Level implements Interrupt{
	
	private Interrupt interrupt = new FadeIn(60);
	private boolean complete = false;
	
	private TestBlob blob = new TestBlob(0);
	private TestBlob blob1 = new TestBlob(10);
	private TestBlob blob2 = new TestBlob(20);
	private TestBlob blob3 = new TestBlob(30);
	private TestBlob blob4 = new TestBlob(0);

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
		
		blob.render(30, 10, 5, 20, 11, 20);
		blob1.render(40, 10, 5, 20, 10, 35);
		blob2.render(30, 20, 5, 20, 9, 31);
		blob3.render(40, 20, 5, 20, 13, 28);
		blob4.render(35, 15, 5, 20, 10, 30);
		
		Globals.enemySoS();
		
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
