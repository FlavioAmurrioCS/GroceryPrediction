import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * CountTracker
 */
public class CountTracker {

    HashMap<String, Integer> cMap;

    public CountTracker() {
        this.cMap = new HashMap<>();
    }

    public void add(String key) {
        if (this.cMap.containsKey(key)) {
            Integer value = this.cMap.get(key);
            this.cMap.put(key, value + 1);
        } else {
            this.cMap.put(key, 1);
        }
    }

    private ArrayList<Pair<String>> toSortedList() {
        ArrayList<Pair<String>> retList = new ArrayList<>();
        for (String key : this.cMap.keySet()) {
            int value = this.cMap.get(key);
            retList.add(new Pair<String>(value, key));
        }
        Collections.sort(retList);
        return retList;
    }

    public void toFile(String filename) {
        System.out.println(this.cMap.size());
        Tools.listToFile(this.toSortedList(), filename);
    }

}