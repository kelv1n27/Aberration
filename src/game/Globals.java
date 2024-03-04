package game;

import java.awt.event.MouseEvent;
import java.util.Random;

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
	public static Random rand;
	public static Level level;
	
	public static int enemySoS;
	public static int enemyMask;
	
	public Globals(Game game) {
		this.game = game;
		gfx = new GraphicsCardInterface((short)2, getClass().getProtectionDomain().getCodeSource().getLocation().toString(), "/gfxPlugs/");
//		gfx.initDebugWindow();
		gfx.showDebug();
		mainCanvas = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {256, 240}));
		font = font = gfx.loadMemory(gfx.buildMemoryObject("BasicFont", new Object[] {"/sprites/font.png", 8, 8, "abcdefghijklmnopqrstuvwxyz.!? >                   1234567890"}));
		enemySoS = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {256, 240}));
		enemyMask = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {256, 240}));
		wnd = new WindowHandler(gfx, mainCanvas);
		inp = new InputHandler(wnd.getCanvas());
		inp.createInput("up", new int[] {38, 87});
		inp.createInput("down", new int[] {40, 83});
		inp.createInput("left", new int[] {37, 65});
		inp.createInput("right", new int[] {39, 68});
		inp.createInput("escape", new int[] {27});
		inp.createInput("attack", new int[] {MouseEvent.BUTTON1, 90, 32});
		aud = new AudioHandler(44100, 16, 1, true, false, 736);
		rand = new Random();
	}
	
	public static void enemySoS() {
		gfx.runPlugin("SumOfSines", new Object[] {enemySoS, 4, .99f, 7f, .8f, .1f, tickCount});
		gfx.runPlugin("HeightMapColorize", new Object[] {enemySoS, enemySoS, 130, new int[] {0xffff8b18, 0xff09ff3f, 0xffff18f6, 0xff000000}});
		gfx.runPlugin("WaterMaskOverlay", new Object[] {mainCanvas, enemySoS, enemyMask});
		gfx.runPlugin("FillColor", new Object[] {enemyMask, 0});
	}

}
