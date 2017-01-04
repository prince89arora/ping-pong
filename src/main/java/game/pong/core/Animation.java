package game.pong.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Animation  {
	
	private static final Logger log = LoggerFactory.getLogger(Animation.class);
	
	private Thread thread = null;
	private long delay = 0;
	private boolean interrupted;
	
	public Animation(int delay) {
		this.delay = delay;
	}
	
	public abstract void action();
	
	@SuppressWarnings("static-access")
	private void initThread() throws InterruptedException {
		this.thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!interrupted) {
					action();
					try {
						thread.sleep(delay);
					} catch (InterruptedException e) {}
				}
			}
		});
		interrupted = false;
	}
	
	public synchronized void start() {
		try {
			initThread();
		} catch (InterruptedException ex) {
			log.error("Error in animation initialization", ex);
		}
		if (this.thread != null) {
			notifyAll();
			this.thread.start();
		}
	}
	
	public synchronized void stop() {
		if (this.thread != null && !interrupted) {
				this.thread.interrupt();
				this.interrupted = true;
				this.thread = null;
		}
	}
	
}
