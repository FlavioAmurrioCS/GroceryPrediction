import java.util.Date;
import java.util.HashMap;

/**
 * Item
 */
public class Item {

    int item_nbr;
    HashMap<Date, Double> salesMap; //Long will represent the date milli
    LinReg reg;

    // id,date,store_nbr,item_nbr,unit_sales,onpromotion
    // 0,2013-01-01,25,103665,7.0,

    // List<Pair<Date, unit_sales>>;

    // int item_nbr;
    // HashMap<Date, TransDate> hMap;

    // item_nbr,family,class,perishable
    // 96995,GROCERY I,1093,0

    public Item(int item_nbr) {
        this.item_nbr = item_nbr;
        this.salesMap = new HashMap<>();
    }

    public void addTrans(Date date, Double unitSale) {
        this.salesMap.put(date, unitSale);
    }

    public void linReg() {
        reg = new LinReg(this.salesMap);
    }

    public double getPrediction(Date date) {
        return this.reg.getY(xVal);
    }
}