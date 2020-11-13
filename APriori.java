import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Arrays;
//import java.util.Iterator;

public class APriori{
    
    public APriori(){

    }

    public HashMap<HashSet<Integer>, Integer> createSingletons(ArrayList<HashSet<Integer>> baskets) {
        //IN market basket model dataset, list of sets of integers (eg items purchased in transactions)
        //OUT HashMap of key-value pairs of each integer (item) and the number of times it appears in all baskets
        HashMap<HashSet<Integer>, Integer> singletons = new HashMap<>();
        int num_baskets = baskets.size();
        System.out.println("num baskets: " + num_baskets);
        for (int b = 0; b< num_baskets; b++){ //for each basket
            HashSet<Integer> basket = baskets.get(b);
            //System.out.println("current basket size: " + num_items);
            for (int item: basket){ //for each item in each basket
                //int item = baskets.get(b)[i];
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
        //IN hashmap of items and counts 
        //OUT pruned hashmap of only items that appear at least "threshold" times
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        return map;
    }

    public HashMap<HashSet<Integer>, Integer> mergeItemsets(HashMap<HashSet<Integer>, Integer> sets_counts) {
        //IN: HashMap of itemsets and counts, k = iteration round of A Priori
        //OUT: HashMap where keys have been merged ( k <-- k-1)
        ArrayList<HashSet<Integer>> sets = new ArrayList<>(sets_counts.keySet());
        HashMap<HashSet<Integer>, Integer> mergedSets = new HashMap<>();
        for (HashSet<Integer> set: sets){
            for (HashSet<Integer> otherset: sets){
                if (set.equals(otherset)){
                    continue;
                }
                else {
                    HashSet<Integer> merge = new HashSet<>(set);
                    merge.addAll(otherset);
                    mergedSets.put(merge, 0);
                }
            }
        }
        /*HashMap<HashSet<Integer>, Integer> mergedSets = new HashMap<>(); 
        for (HashSet<Integer> set: sets.keySet()){
            for (HashSet<Integer> otherset: sets.keySet()){
                HashSet<Integer> merged = new HashSet<>();
                merged.addAll(set);
                merged.addAll(otherset);
                //set.addAll(otherset);
                mergedSets.put(set, 0);
            }
        }
        mergedSets.entrySet().removeIf(entry -> entry.getKey().size() < k); //remove any k-1 sets remaining (addAll wont add a set to itself)
        */
        //mergedSets.entrySet().forEach( entry -> {System.out.println( "doubletons " + entry.getKey() + "=>" + entry.getValue());});
        return mergedSets;
    }

    public HashMap<HashSet<Integer>, Integer> counter(ArrayList<HashSet<Integer>> baskets, HashMap<HashSet<Integer>, Integer> itemsets){
        //IN: list of baskets of items, HashMap of itemsets and their counts
        //OUT: HashMap of itemsets and zeroed counts
        //itemsets.replaceAll((key, value) -> value = 0); //set all counts from previous round to 0;
        System.out.println("hello in counter");
        for (HashSet<Integer> basket: baskets){
            for (HashSet<Integer> itemset: itemsets.keySet()){
                if (basket.containsAll(itemset)){
                    itemsets.put(itemset, itemsets.get(itemset) +1);
                }
            }
        }
        System.out.println("done in counter");
        return itemsets;
    }
        
    
}