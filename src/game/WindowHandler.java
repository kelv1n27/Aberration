package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.swing.JFrame;

import tbh.gfxInterface.GraphicsCardInterface;

public class WindowHandler {

	private Game game;
	private GraphicsCardInterface gfx;
	private JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private int upscaleCanvas;
	private int sourceCanvas;
	private int dimIndex = 2;
	private boolean fullscreen = false;
	private int[][] dimensions = {{(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()}, {1920, 1080}, {1536, 864}, {1440, 900}, {1280, 720}, {480, 270}};
	private final int[] INTERNAL_RATIO = new int[] {1, 1};//must be configured per project
	private RATIO currentRatio = RATIO.FIT_SCREEN;
	public static enum RATIO{
		WIDESCREEN, //12:9
		INTERNAL,   //whatever internal resolution we use
		FIT_SCREEN  //fit whatever window/screen
	}
	private int[] drawDims;
	
	public WindowHandler(GraphicsCardInterface gfx, int sourceIndex) {
		this.gfx = gfx;
		drawDims = new int[] {dimensions[dimIndex][0], dimensions[dimIndex][1]};
		upscaleCanvas = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {dimensions[dimIndex][0], dimensions[dimIndex][1]}));
		image = new BufferedImage(dimensions[dimIndex][0], dimensions[dimIndex][1], BufferedImage.TYPE_INT_ARGB);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(dimensions[dimIndex][0], dimensions[dimIndex][1]));
		canvas.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		canvas.requestFocus();
		canvas.setBackground(Color.BLACK);
		sourceCanvas = sourceIndex;
	}
	
	public void updateWindow() {
		frame.dispose();
		gfx.releaseMemory(upscaleCanvas);
		switch (currentRatio) {
		case WIDESCREEN:
			if (dimensions[dimIndex][1] >= (dimensions[dimIndex][0]/16)*9) {
				drawDims = new int[] {dimensions[dimIndex][0], (dimensions[dimIndex][0]/16)*9};
			} else {
				drawDims = new int[] {(dimensions[dimIndex][1]/9)*16, dimensions[dimIndex][1]};
			}
			break;
		case INTERNAL:
			if (dimensions[dimIndex][1] >= (dimensions[dimIndex][0]/INTERNAL_RATIO[0])*INTERNAL_RATIO[1]) {
				drawDims = new int[] {dimensions[dimIndex][0], (dimensions[dimIndex][0]/INTERNAL_RATIO[0])*INTERNAL_RATIO[1]};
			} else {
				drawDims = new int[] {(dimensions[dimIndex][1]/INTERNAL_RATIO[1])*INTERNAL_RATIO[0], dimensions[dimIndex][1]};
			}
			break;
		case FIT_SCREEN:
			drawDims = new int[] {dimensions[dimIndex][0], dimensions[dimIndex][1]};
			break;
		}
		upscaleCanvas = gfx.loadMemory(gfx.buildMemoryObject("IntArrayImage", new Object[] {drawDims[0], drawDims[1]}));
		image = new BufferedImage(drawDims[0], drawDims[1], BufferedImage.TYPE_INT_ARGB);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (fullscreen) {
			this.fullscreen = true;
			frame.setVisible(false);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			frame.setUndecorated(true);	
		}
		frame.setVisible(true);
		canvas.setPreferredSize(new Dimension(dimensions[dimIndex][0], dimensions[dimIndex][1]));
		canvas.setVisible(true);
		canvas.setBackground(Color.BLACK);
		
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		canvas.requestFocus();
		
	}
	
	public void updateCanvas() {
		gfx.runPlugin("Upscale", new Object[] {Globals.mainCanvas, upscaleCanvas});
		image = (BufferedImage) gfx.retrieveMemory(Globals.mainCanvas);
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			bs = canvas.getBufferStrategy();
		}
		Graphics g = bs.getDrawGraphics();
		canvas.paint(g);
//		g.drawImage(image, 0, 0, dimensions[dimIndex][0], dimensions[dimIndex][1], null);
		g.drawImage(image, (dimensions[dimIndex][0] - drawDims[0])/2, (dimensions[dimIndex][1] - drawDims[1])/2, drawDims[0], drawDims[1], null);
		bs.show();
		g.dispose();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setWindowTitle(String name) {
		frame.setTitle(name);
	}
	
	public boolean getFullscreen() {
		return fullscreen;
	}
	
	public void setFullscreen(boolean bool) {
		fullscreen = bool;
	}
	
	public int getDimIndex() {
		return dimIndex;
	}
	
	public void setDimIndex(int index) {
		dimIndex = index%dimensions.length;
	}
	
	public int[][] getDims() {
		return dimensions;
	}
	
	public RATIO getCurrentRatio() {
		return currentRatio;
	}
	
	public void cycleRatio() {
		switch(currentRatio) {
		case WIDESCREEN:
			currentRatio = RATIO.INTERNAL;
			break;
		case INTERNAL:
			currentRatio = RATIO.FIT_SCREEN;
			break;
		case FIT_SCREEN:
			currentRatio = RATIO.WIDESCREEN;
			break;
		}
	}
	
	public void exit() {
		frame.setVisible(false);
		frame.dispose();
	}
}
