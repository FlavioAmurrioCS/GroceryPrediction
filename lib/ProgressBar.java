public class ProgressBar {
    private long startTime;
    private long size;
    private long unit;
    private long lastPrint = 0;
    private char[] bar = "..................................................".toCharArray();

    public ProgressBar(long size) {
        this.size = size;
        this.unit = this.size / 100;
        this.startTime = System.currentTimeMillis();
    }

    public void update(long i) {
        if (i == 0 || i == 1)
            System.out.print(barMaker(0));
        long perc = i / this.unit;
        if (perc != this.lastPrint) {
            long remainingTime = remainingTime(perc);
            System.out.print("\r" + barMaker(perc) + " " + timeToString(remainingTime) + "      ");
            lastPrint = perc;
        }
        if (i == this.size - 1)
            System.out.println();
    }

    private long remainingTime(long perc) {
        long elapseTime = System.currentTimeMillis() - this.startTime;
        long estimateTime = (100 * elapseTime) / perc;
        return (estimateTime - elapseTime);
    }

    private String barMaker(long perc) {
        for (long i = lastPrint / 2; i < perc / 2; i++) {
            if (i < bar.length) {
                bar[(int) i] = '#';
            }
        }
        perc = perc > 100 ? 100 : perc;
        return "Progress: [" + perc + "%][" + new String(bar) + "]";
    }

    private String timeToString(long time) {
        time /= 1000;
        if (time > 60)
            return "Time: " + (time / 60) + "m " + (time % 60) + "s";
        else
            return "Time: " + time + "s";
    }
}