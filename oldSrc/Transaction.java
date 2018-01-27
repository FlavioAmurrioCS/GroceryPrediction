import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction
 */
public class Transaction {

    int id;
    int item_nbr;
    // int store_nbr;   Not needed since we are approaching one store at a time.
    float unit_sales;
    boolean onpromotion;
    Date date;

    public static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

    // id,date,store_nbr,item_nbr,unit_sales,onpromotion
    public Transaction(String str) {
        String[] arr = str.split(",");
        this.id = Integer.parseInt(arr[0]);
        // this.store_nbr = Integer.parseInt(arr[2]);
        this.item_nbr = Integer.parseInt(arr[3]);
        this.unit_sales = Float.parseFloat(arr[4]);
        this.onpromotion = arr.length < 6 ? false : Boolean.parseBoolean(arr[5]);
        try {
            this.date = parser.parse(arr[1]);
        } catch (Exception e) {
            //TODO: handle exception
            this.date = new Date();
        }
    }

    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + this.id + "\n");
        sb.append("date: " + parser.format(this.date) + "\n");
        // sb.append("store_nbr: " + this.store_nbr + "\n");
        sb.append("item_nbr: " + this.item_nbr + "\n");
        sb.append("unit_sales: " + this.unit_sales + "\n");
        sb.append("onpromotion: " + this.onpromotion + "\n");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id + ",");
        sb.append(parser.format(this.date) + ",");
        // sb.append(this.store_nbr + "\n");
        sb.append(this.item_nbr + ",");
        sb.append(this.unit_sales + ",");
        sb.append(this.onpromotion);
        return sb.toString();
    }

}