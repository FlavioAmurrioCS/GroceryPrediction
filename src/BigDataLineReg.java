import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * BigDataLineReg
 */
public class BigDataLineReg {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("##.00");
    public static HashMap<String, BigDataLineReg> regMap = new HashMap<>();
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

    @SuppressWarnings("deprecation")
    public static void analyzeLine(String str) {
        String[] arr = str.split(",");
        double unit_sales = Double.parseDouble(arr[4]);
        Date date = new Date();
        try {
            date = dateFormat.parse(arr[1]);
        } catch (Exception e) {
            return;
        }
        String key = "W:" + date.getDay() + "\t S: " + arr[2] + "\tI: " + arr[3];
        getBigDataLineReg(key).inputXY(date, unit_sales);
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
        this.xSqSum += (x * x);
        this.xySum += (x * y);
        this.size++;
    }

    public double getY(double xVal) {
        if (size == 0)
            return 0;
        if (size < 4)
            return ySum / size;
        double ans = (xVal * this.slope) + this.inter;
        if (ans < 0)
            return 0;
        return this.whole ? (double) Math.round(ans) : ((double) Math.round(ans * 100) / 100);
    }

    public synchronized void calculateLine() {
        this.whole = xySum % 1 == 0;
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
    }

    public static void main(String[] args) {
        Timer total = new Timer();
        readFile();
        calculateAllLines();
        predict();
        total.time();
    }

    public static void readFile() {
        ProgressBar pb = new ProgressBar(125497041, "Training");
        Scanner sc = Tools.fileReader(Miner.TRAIN_FILE);
        sc.nextLine();
        while (sc.hasNextLine()) {
            analyzeLine(sc.nextLine());
            pb.step();
        }
        sc.close();
    }

    public static void calculateAllLines() {
        ProgressBar pb = new ProgressBar(regMap.size(), "Calculate All Lines");
        for (BigDataLineReg big : regMap.values()) {
            big.calculateLine();
            pb.step();
        }
    }

    @SuppressWarnings("deprecation")
    public static void predict() {
        ProgressBar pb = new ProgressBar(3370464, "Prediction");
        Scanner sc = Tools.fileReader(Miner.TEST_FILE);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            new Runnable() {
                @Override
                public void run() {
                    String[] arr = str.split(",");
                    long x = 0;
                    Date date = new Date();
                    try {
                        date = dateFormat.parse(arr[1]);
                        x = date.getTime();
                    } catch (Exception e) {
                    }
                    String key = "W:" + date.getDay() + "\t S: " + arr[2] + "\tI: " + arr[3];
                    BigDataLineReg big = getBigDataLineReg(key);
                    pairTracker.add(Integer.parseInt(arr[0]), big.getY(x));
                }
            }.run();
            pb.step();
        }
        pairTracker.toFile("output.csv");
    }
}