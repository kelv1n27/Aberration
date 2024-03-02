package game;

import java.awt.event.MouseEvent;

import audioHandlerV2_Core.AudioHandler;
import input.InputHandler;
import tbh.gfxInterface.GraphicsCardInterface;

public class Globals {
	
	public static Game game;
	public static GraphicsCardInterface gfx;
	public static WindowHandler wnd;
	public static InputHandler inp;
	public static AudioHandler aud;
	
	public static int mainCanvas;
	public static int tickCount = 0;
	public static int font;
	
	public Globals(Game game) {
		this.game = game;
		gfx = new GraphicsCardInterface((short)2, getClass().getProtectionDomain().getCodeSource().getLocation().toString(), "/gfxPlugs/");
		mainCanvas = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {256, 240}));
		font = font = gfx.loadMemory(gfx.buildMemoryObject("BasicFont", new Object[] {"/sprites/font.png", 8, 8, "abcdefghijklmnopqrstuvwxyz.!? >                   1234567890"}));
		wnd = new WindowHandler(gfx, mainCanvas);
		inp = new InputHandler(wnd.getCanvas());
		inp.createInput("up", new int[] {38, 87});
		inp.createInput("down", new int[] {40, 83});
		inp.createInput("left", new int[] {37, 65});
		inp.createInput("right", new int[] {39, 68});
		inp.createInput("escape", new int[] {27});
		inp.createInput("attack", new int[] {MouseEvent.BUTTON1, 90, 32});
		aud = new AudioHandler(44100, 16, 1, true, false, 736);
	}

}
