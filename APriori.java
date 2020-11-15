import java.util.*;

import com.google.common.collect.Sets;


public class APriori {
    public APriori(){

    }

    public HashMap<Integer, Integer> createSingletons(ArrayList<Set<Integer>> baskets) {
        //IN market basket model dataset, list of sets of integers (eg items purchased in transactions)
        //OUT HashMap of key-value pairs of each integer (item) and the number of times it appears in all baskets
        HashMap<Integer, Integer> singletons = new HashMap<>();
        int num_baskets = baskets.size();
        System.out.println("num baskets: " + num_baskets);
        for (Set<Integer> basket : baskets) { //for each basket
            //System.out.println("current basket size: " + num_items);
            for (int item : basket) { //for each item in each baske
                //Set<Integer> singleton = Sets.newHashSet();
                //singleton.add(item); //create new hashset and add current item to it.
                if (singletons.containsKey(item)) { //if we've already seen this item, increment its count by 1
                    singletons.put(item, singletons.get(item) + 1);
                } else {
                    singletons.put(item, 1); //if item is new, count it once
                }
                /*itemSets.entrySet().forEach( entry -> {
                    System.out.println( entry.getKey() + " => " + entry.getValue() );
                });*/
            }
        }
        return singletons;
    }

    public HashMap<Integer, Integer> prune_singles(HashMap<Integer, Integer> map, int threshold){
        //IN hashmap of items and counts
        //OUT pruned hashmap of only items that appear at least "threshold" times
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        return map;
    }
    public HashMap<Set<Integer>, Integer> prune(HashMap<Set<Integer>, Integer> map, int threshold){
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        return map;
    }
    /*public HashMap<Set<Integer>, Integer> mergeItemsets(HashMap<Integer, Integer> sets_counts) {
        //IN: HashMap of itemsets and counts, k = iteration round of A Priori
        //OUT: HashMap where keys have been merged ( k <-- k-1)
        //ArrayList<Set<Integer>> sets = new ArrayList<>(sets_counts.keySet());
        HashMap<Set<Integer>, Integer> mergedSets = new HashMap<>();
        for (Set<Integer> set: sets){
            for (Set<Integer> otherset: sets){
                if (set.equals(otherset)){
                }
                else {
                    Set<Integer> merge = Sets.union(set, otherset);
                    mergedSets.put(merge, 0);
                }
            }
        }
        return mergedSets;
    }*/

    public HashMap<Set<Integer>, Integer> counter(ArrayList<Set<Integer>> baskets, Set<Integer> frequent_singles, int k){
        //IN: list of baskets of items, HashMap of itemsets and their counts
        //OUT: HashMap of itemsets and zeroed counts
        //itemsets.replaceAll((key, value) -> value = 0); //set all counts from previous round to 0;
        //Set<Integer> candidates = Sets.newHashSet();
        HashMap<Set<Integer>, Integer> sets_counts = new HashMap<>();
        System.out.println("hello in counter");
        for (Set<Integer> basket: baskets){
            Set<Integer> candidates = Sets.newHashSet();
            for (int item:basket) {
                if (frequent_singles.contains(item)){
                    candidates.add(item);
                }
            }
            try {
                Set<Set<Integer>> combos = Sets.combinations(candidates, k);
                for (Set<Integer> combo: combos){
                        if (sets_counts.keySet().contains(combo)) {
                            sets_counts.put(combo, sets_counts.get(combo) + 1);
                        }
                        else{
                            sets_counts.put(combo, 1);
                        }
                    }

            }
            catch (Exception IllegalArgumentException){
                continue;
            }

        }
        System.out.println("done in counter");
        return sets_counts;
    }
}
