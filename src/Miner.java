import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Miner
 */
public class Miner {
    public static final String RES_FOLDER = "./res/";
    public static final String OUT_FOLDER = "./out/";
    public static final String STORE_TRAIN = "./storeTrain/";
    public static final String TRAIN_FILE = RES_FOLDER + "train.csv";
    public static final String TEST_FILE = RES_FOLDER + "test.csv";

    public static final String SAMPLE_FILE = OUT_FOLDER + "sample.txt";
    public static final String SAMPLE_TRAIN = OUT_FOLDER + "sampleTrain.txt";
    public static final String SAMPLE_TEST = OUT_FOLDER + "sampleTest.txt";
    public static final int TRAIN_SIZE = 125497041;

    public static void main(String[] args) {
        // csvSplitter(TRAIN_FILE, STORE_TRAIN, "store_train_", "store_nbr");

        // csvExtractor(TRAIN_FILE, "out.csv", "store_nbr", "1", TRAIN_SIZE);
        // crossValidationSplit(TRAIN_FILE, SAMPLE_TRAIN, SAMPLE_TEST, .8);

        Timer timer = new Timer();
        ArrayList<Transaction> tList = new ArrayList<>();

        Scanner sc = Tools.fileReader(STORE_TRAIN + "store_1.csv");
        sc.nextLine();
        while (sc.hasNextLine()) {
            tList.add(new Transaction(sc.nextLine()));
        }

        timer.time();
        Tools.slow(10000000);

        // System.out.println(Tools.listToString(tList));

    }

    public static void sampleData(String src, String dest, int beginIndex, int endIndex) {
        Scanner sc = Tools.fileReader(src);
        PrintWriter pw = Tools.fileWriter(dest);
        // ProgressBar pb = new ProgressBar(endIndex);
        int count = 0;
        while (count < beginIndex) {
            sc.nextLine();
            count++;
            // pb.update(count);
        }
        while (sc.hasNext() && count < endIndex) {
            pw.println(sc.nextLine());
            count++;
            // pb.update(count);
        }
        pw.close();
        sc.close();
    }

    public static void crossValidationSplit(String src, String trainDest, String trueDest, double trainPerc) {
        int size = Tools.lineCount(src);
        int trainEnd = (int) (trainPerc * (double) size);
        System.out.println("size: " + size);
        System.out.println("trainEnd: " + trainEnd);
        sampleData(src, trainDest, 0, trainEnd);
        sampleData(src, trueDest, trainEnd, size);
    }

    public static void lineGen(String dest, int limit) {
        PrintWriter pw = Tools.fileWriter(dest);
        for (int i = 0; i < limit; i++)
            pw.println(i);
        pw.close();
    }

    public static void csvSplitter(String src, String folder, String outname, String attName) {
        Timer timer = new Timer();
        Scanner sc = Tools.fileReader(src);
        int index = -1;
        String str = sc.nextLine();
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; i++) {
            if (attName.equals(arr[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Column Not Found!!!");
            System.exit(1);
        }
        str = sc.nextLine();
        arr = str.split(",");
        String current = arr[index];
        StringBuilder sb = new StringBuilder();
        sb.append(str + "\n");
        ProgressBar pb = new ProgressBar(125497041);
        int lineNum = 0;
        while (sc.hasNextLine()) {
            str = sc.nextLine();
            arr = str.split(",");
            if (!current.equals(arr[index])) {
                Tools.appendFile(folder + outname + current + ".csv", sb.toString());
                sb = new StringBuilder();
                current = arr[index];
            }
            if (sc.hasNext())
                sb.append(str + "\n");
            else
                sb.append(str);
            lineNum++;
            pb.update(lineNum);
        }
        Tools.appendFile(folder + outname + current + ".csv", sb.toString());
        timer.time();
    }

    public static void csvExtractor(String src, String dest, String attName, String val, int pbSize) {
        Tools.tittleMaker("CSV Extractor");
        Timer timer = new Timer();
        Scanner sc = Tools.fileReader(src);
        PrintWriter pw = Tools.fileWriter(dest);
        ProgressBar pb = new ProgressBar(pbSize);
        String str = sc.nextLine();
        String[] arr = str.split(",");
        int col = -1;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (attName.equals(arr[i])) {
                col = i;
                break;
            }
        }
        if (col == -1) {
            System.out.println("Column Not Found!!!");
            System.exit(1);
        }
        pw.println(str);
        while (sc.hasNextLine()) {
            str = sc.nextLine();
            arr = str.split(",");
            if (val.equals(arr[col]))
                pw.println(str);
            if (pbSize > 0)
                pb.update(count++);
        }
        sc.close();
        pw.close();
    }
}