import java.util.Date;
import java.util.HashMap;

/**
 * Item
 */
public class Item {

    // id,date,store_nbr,item_nbr,unit_sales,onpromotion
    // 0,2013-01-01,25,103665,7.0,

    int item_nbr;
    HashMap<Date, TransDate> hMap;

    public Item(int item_nbr) {
        this.item_nbr = item_nbr;
        hMap = new HashMap<>();
    }

    public void addTrans(TransDate td) {
        hMap.put(td.date, td);
    }
}