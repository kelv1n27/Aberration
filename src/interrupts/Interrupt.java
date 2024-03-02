package interrupts;

public interface Interrupt {
	
	public void tick();
	
	public void render();
	
	public boolean complete();

	public void release();
}