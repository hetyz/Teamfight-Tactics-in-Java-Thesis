package hu.elte.fi.szakdolgozat.model;

public class Stopwatch {
    private long start;
    private long maxTime;

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public int elapsedTime() {
        long now = System.currentTimeMillis();
        return (int) (maxTime - (now - start) / 1000);
    }

    public void stop() {
        start = -1;
    }

    public void resetTimer() {
        start = System.currentTimeMillis();
    }
}

