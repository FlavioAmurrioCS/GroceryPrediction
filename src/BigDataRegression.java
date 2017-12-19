import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * BigDataRegression
 */
public class BigDataRegression {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("##.00");
    public static HashMap<String, Regression> regMap = new HashMap<>();
    public static PairTracker<Double> pairTracker = new PairTracker<>();
    // public static HashMap<Integer, Integer> sizeTracker = new HashMap<>();
    // id,unit_sales

    public static void main(String[] args) {
        Timer total = new Timer();
        readFile();
        calculateAllLines();
        predict();
        // Tools.listToFile(Tools.mapToPairList(sizeTracker), "SizeCount.txt");
        total.time();
        // fileSplitterPython();
    }

    public static Regression getMethod() {
        return new LinearReg();
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
        for (Regression reg : regMap.values()) {
            reg.fit();
            pb.step();
        }
    }

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
                    double x = 0;
                    Date date = new Date();
                    try {
                        date = dateFormat.parse(arr[1]);
                        x = (double) date.getTime();
                    } catch (Exception e) {
                    }
                    String key = generateKey(date, arr[2], arr[3]);
                    Regression reg = getMethod();
                    if (regMap.containsKey(key)) {
                        reg = regMap.get(key);
                    }
                    double ans = reg.predictY(x);
                    ans = ans < 0 || Double.isNaN(ans) ? 0 : ans;
                    pairTracker.add(Integer.parseInt(arr[0]), ans);
                }
            }.run();
            pb.step();
        }
        pairTracker.toFile("output.csv");
    }

    public static void analyzeLine(String str) {
        String[] arr = str.split(",");
        double unit_sales = Double.parseDouble(arr[4]);
        Date date = new Date();
        try {
            date = dateFormat.parse(arr[1]);
        } catch (Exception e) {
            return;
        }
        String key = generateKey(date, arr[2], arr[3]);
        getRegression(key).inputXY((double) date.getTime(), unit_sales);
    }

    public static synchronized Regression getRegression(String key) {
        Regression ret = regMap.get(key);
        if (ret == null) {
            ret = getMethod();
            regMap.put(key, ret);
        }
        return ret;
    }

    @SuppressWarnings("deprecation")
    public static String generateKey(Date date, String store_nbr, String item_nbr) {
        // return "W" + date.getDay() + "S" + store_nbr + "I" + item_nbr;
        return "W" + (date.getDay() == 0 || date.getDay() == 6) + "S" + store_nbr + "I" + item_nbr;
    }
}