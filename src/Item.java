import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Item
 */
public class Item {

    public static HashMap<Integer, Item> itemMap = new HashMap<>();

    int item_nbr;
    ArrayList<HashMap<Date, Double>> salesMap = new ArrayList<>(Arrays.asList(new HashMap<Date, Double>(),
            new HashMap<Date, Double>(), new HashMap<Date, Double>(), new HashMap<Date, Double>(),
            new HashMap<Date, Double>(), new HashMap<Date, Double>(), new HashMap<Date, Double>()));
    LinReg[] reg = new LinReg[7];
    int count = 0;

    // id,date,store_nbr,item_nbr,unit_sales,onpromotion
    // 0,2013-01-01,25,103665,7.0,

    // List<Pair<Date, unit_sales>>;

    // int item_nbr;
    // HashMap<Date, TransDate> hMap;

    // item_nbr,family,class,perishable
    // 96995,GROCERY I,1093,0

    public Item(int item_nbr) {
        this.item_nbr = item_nbr;
    }

    @SuppressWarnings("deprecation")
    public void addTrans(Date date, Double unitSale) {
        int index = date.getDay();
        this.salesMap.get(index).put(date, unitSale);
    }

    @SuppressWarnings("deprecation")
    public double getPrediction(Date date) {
        int index = date.getDay();
        if (this.reg[index] == null)
            this.reg[index] = new LinReg(this.salesMap.get(index));
        return this.reg[index].getY(date);
    }

    public void toGraphFile(String filename) {
        PrintWriter pw = Tools.fileWriter(filename);
        for (HashMap<Date, Double> hp : this.salesMap) {
            for (Date dt : hp.keySet()) {
                pw.println(dt.getTime() + " " + hp.get(dt));
            }
        }
        pw.close();
    }

    public void toGraphFile(int weekday, String filename) {
        PrintWriter pw = Tools.fileWriter(filename);
        HashMap<Date, Double> holder = this.salesMap.get(weekday);
        for (Date dt : holder.keySet()) {
            pw.println(dt.getTime() + " " + holder.get(dt));
        }
        pw.close();
    }
}