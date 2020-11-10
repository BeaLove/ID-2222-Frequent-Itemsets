import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class APriori{
    
    public APriori(){

    }

    public HashMap<HashSet<Integer>, Integer> createSingletons(ArrayList<int[]> baskets) {
        HashMap<HashSet<Integer>, Integer> singletons = new HashMap<>();
        int num_baskets = baskets.size();
        System.out.println("num baskets: " + num_baskets);
        for (int b = 0; b< num_baskets; b++){ //for each basket
            int num_items = baskets.get(b).length;
            //System.out.println("current basket size: " + num_items);
            for (int i = 0; i < num_items; i++){ //for each item in each basket
                int item = baskets.get(b)[i];
                //System.out.println(item);
                //HashSet<Integer> singleton = new HashSet<>(baskets.get(b)[i]);
                HashSet<Integer> singleton = new HashSet<>(); 
                singleton.add(item); //create new hashset and add current item to it.
                if (singletons.containsKey(singleton)){ //if we've already seen this item, increment its count by 1
                    singletons.put(singleton, singletons.get(singleton) +1 );
                }    
                else {
                    singletons.put(singleton, 1); //if item is new, count it once
                }
                /*itemSets.entrySet().forEach( entry -> {
                    System.out.println( entry.getKey() + " => " + entry.getValue() );
                });*/
            }
        }
        return singletons;
    }

    public HashMap<HashSet<Integer>, Integer> pruning(HashMap<HashSet<Integer>, Integer> map, int threshold){
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        return map;
    }
}