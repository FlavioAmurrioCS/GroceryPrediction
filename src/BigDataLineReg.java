import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * BigDataLineReg
 */
public class BigDataLineReg {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("##.00");
    public static HashMap<String, Regression> regMap = new HashMap<>();
    public static PairTracker<Double> pairTracker = new PairTracker<>();
    public static HashMap<Integer, Integer> sizeTracker = new HashMap<>();

    double size = 0;
    double ySum = 0;
    double xSum = 0;
    double xSqSum = 0;
    double xySum = 0;
    boolean finalize = false;
    boolean whole = true;
    double slope;
    double inter;

    public void inputXY(Date dt, double y) {
        double x = dt.getTime();
        this.xSum += x;
        this.ySum += y;
        this.xSqSum += (x * x);
        this.xySum += (x * y);
        this.size++;
    }

    public synchronized void fit() {
        this.whole = (xySum % 1 == 0);
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
    }

    public double predictY(double xVal) {
        Integer count = sizeTracker.get((int) size);
        if (count == null)
            count = 0;
        sizeTracker.put((int) size, count + 1);
        if (size == 0)
            return 0;
        if (size < 4)
            return ySum / size;
        double ans = (xVal * this.slope) + this.inter;
        // if (ans < 0)
        //     return 0;
        return this.whole ? (double) Math.round(ans) : ((double) Math.round(ans * 100) / 100);
    }

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
    
    public static void main(String[] args) {
        Timer total = new Timer();
        readFile();
        calculateAllLines();
        predict();
        Tools.listToFile(Tools.mapToPairList(sizeTracker), "SizeCount.txt");
        total.time();
        // fileSplitterPython();
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
            big.fit();
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
                    double ans = big.predictY(x);
                    ans = ans < 0 || Double.isNaN(ans) ? 0 : ans;
                    pairTracker.add(Integer.parseInt(arr[0]), ans);
                }
            }.run();
            pb.step();
        }
        pairTracker.toFile("output.csv");
    }

    @SuppressWarnings("deprecation")
    public static void fileSplitterPython() {
        ProgressBar pb = new ProgressBar(125497041, "Python File Splitting");
        Scanner sc = Tools.fileReader(Miner.TRAIN_FILE);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] arr = str.split(",");
            Date date = new Date();
            try {
                date = dateFormat.parse(arr[1]);
            } catch (Exception e) {
                return;
            }
            String filename = "./pfiles/" + "W" + date.getDay() + "S" + arr[2] + "I" + arr[3] + ".csv";
            Tools.appendFile(filename, date.getTime() + "," + arr[4]);
            pb.step();
        }
        sc.close();
    }
}