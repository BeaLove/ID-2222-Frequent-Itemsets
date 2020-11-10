import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class APriori{
    
    public APriori(){

    }

    public HashMap<HashSet<Integer>, Integer> createSingletons(ArrayList<int[]> baskets) {
        HashMap<HashSet<Integer>, Integer> itemSets = new HashMap<>();
        int num_baskets = baskets.size();
        System.out.println("num baskets: " + num_baskets);
        for (int b = 0; b< num_baskets; b++){
            int num_items = baskets.get(b).length;
            //System.out.println("current basket size: " + num_items);
            for (int i = 0; i < num_items; i++){
                int item = baskets.get(b)[i];
                //System.out.println(item);
                //HashSet<Integer> singleton = new HashSet<>(baskets.get(b)[i]);
                HashSet<Integer> singleton = new HashSet<>();
                singleton.add(item);
                if (itemSets.containsKey(singleton)){
                    itemSets.put(singleton, itemSets.get(singleton) +1 );
                }    
                else {
                    itemSets.put(singleton, 1);
                }
                /*itemSets.entrySet().forEach( entry -> {
                    System.out.println( entry.getKey() + " => " + entry.getValue() );
                });*/
            }
        }
        return itemSets;
    }
}