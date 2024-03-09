package events;

public class Event {
	
	int time;
	private Runnable runner;
	
	public Event(int time, Runnable runner) {
		this.time = time;
		this.runner = runner;
	}
	
	public int getTime() {
		return time;
	}
	
	public void run() {
		runner.run();
	}

}
