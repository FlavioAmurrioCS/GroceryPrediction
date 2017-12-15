import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * BigDataLineReg
 */
public class BigDataLineReg {

    public static HashMap<String, BigDataLineReg> regMap = new HashMap<>();
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("##.00");
    public static PairTracker<Double> pairTracker = new PairTracker<>();

    double size = 0;
    double ySum = 0;
    double xSum = 0;
    double xSqSum = 0;
    double xySum = 0;
    boolean finalize = false;
    boolean whole = true;
    double slope;
    double inter;

    public static void analyzeLine(String str) {
        String[] arr = str.split(",");
        String store_item = "S: " + arr[2] + "\tI: " + arr[3];
        double unit_sales = Double.parseDouble(arr[4]);
        Date date;
        try {
            date = dateFormat.parse(arr[1]);
        } catch (Exception e) {
            return;
        }
        getBigDataLineReg(store_item).inputXY(date, unit_sales);
    }

    public static synchronized BigDataLineReg getBigDataLineReg(String key) {
        BigDataLineReg ret = regMap.get(key);
        if (ret == null) {
            ret = new BigDataLineReg();
            regMap.put(key, ret);
        }
        return ret;
    }

    public void inputXY(Date dt, double y) {
        double x = dt.getTime();
        this.xSum += x;
        this.ySum += y;
        this.xSqSum = (x * x);
        this.xySum = (x * y);
    }

    public double getY(double xVal) {
        // if (!this.finalize)
        //     calculateLine();
        double ans = (xVal * this.slope) + this.inter;
        return this.whole ? (double) Math.round(ans) : ((double) Math.round(ans * 100) / 100);
    }

    public synchronized void calculateLine() {
        this.whole = xySum % 1 == 0;
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
        // this.finalize = true;
    }

    public static void main(String[] args) {
        Timer total = new Timer();
        readFile();
        calculateAllLines();
        predict();
        total.time();
    }

    public static void readFile() {
        Timer timer = new Timer("Training");
        ProgressBar pb = new ProgressBar(125497041);
        int lineNum = 0;
        Scanner sc = Tools.fileReader(Miner.TRAIN_FILE);
        sc.nextLine();
        while (sc.hasNextLine()) {
            analyzeLine(sc.nextLine());
            pb.update(lineNum++);
        }
        sc.close();
        timer.time();
    }

    public static void calculateAllLines() {
        Timer timer = new Timer("Calculate All Lines");
        ProgressBar pb = new ProgressBar(regMap.size());
        int lineNum = 0;
        for (BigDataLineReg big : regMap.values()) {
            big.calculateLine();
            pb.update(lineNum);
        }
        timer.time();
    }

    public static void predict() {
        Timer timer = new Timer("Predict");
        Scanner sc = Tools.fileReader(Miner.TEST_FILE);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            new Runnable() {
                @Override
                public void run() {
                    String[] arr = str.split(",");
                    String key = "S: " + arr[2] + "\tI: " + arr[3];
                    long x = 0;
                    try {
                        x = dateFormat.parse(arr[1]).getTime();
                    } catch (Exception e) {
                    }
                    BigDataLineReg big = getBigDataLineReg(key);
                    pairTracker.add(Integer.parseInt(arr[0]), big.getY(x));
                }
            }.run();
        }
        pairTracker.toFile("output.csv");
        timer.time();
    }
}