package Util;

public class Timer {


    private long start = -1;

    private long elapsed = -1;


    public Timer() { }

    public Timer(boolean start) {
        // Start the timer
        if(start)
            start();
    }

    public void start() {
        // Make sure the timer isn't currently running
        if(isRunning())
            return;

        // Set the start time
        this.start = System.currentTimeMillis();
    }


    public void stop() {
        // Make sure the timer isn't running
        if(!isRunning())
            return;

        // Calculate the elapsed time, store it in the elapsed field and reset the start field
        this.elapsed += System.currentTimeMillis() - this.start;
        this.start = -1;
    }

    public void restart() {
        // Reset and start
        reset();
        start();
    }

    public void reset() {
        // Reset the start and elapsed time
        this.start = -1;
        this.elapsed = -1;
    }

    public boolean isRunning() {
        return start != -1;
    }

    public boolean isStarted() {
        return start != -1 || elapsed != -1;
    }

    public long getElapsedMillis() {
        return (this.start != -1 ? System.currentTimeMillis() - this.start : 0) + Math.max(this.elapsed, 0);
    }

    public int getElapsedSeconds(){
        return Math.round(getElapsedMillis()/1000);
    }

}
