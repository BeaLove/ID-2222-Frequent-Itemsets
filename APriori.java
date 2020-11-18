import java.util.*;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
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
            for (int item : basket) { //for each item in each basket
                if (singletons.containsKey(item)) { //if we've already seen this item, increment its count by 1
                    singletons.put(item, singletons.get(item) + 1);
                } else {
                    singletons.put(item, 1); //if item is new, count it once
                }
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

    public Set<Integer> merge(Set<Set<Integer>> frequent_sets){
        Set<Integer> merged = Sets.newHashSet();
        for (Set<Integer>set:frequent_sets
             ) {
            merged.addAll(set);
        }
        return merged;
    }

    public HashMap<Set<Integer>, Integer> counter(ArrayList<Set<Integer>> baskets, Set<Integer> frequent_items, int k){
        //IN: list of baskets of items, HashMap of itemsets and their counts
        //OUT: HashMap of itemsets and zeroed counts
        //itemsets.replaceAll((key, value) -> value = 0); //set all counts from previous round to 0;
        //Set<Integer> candidates = Sets.newHashSet();

        HashMap<Set<Integer>, Integer> sets_counts = new HashMap<>();
        System.out.println("hello in counter");
        for (Set<Integer> basket: baskets){
            Set<Integer> candidates = Sets.newHashSet();
            for (int item:basket) {
                if (frequent_items.contains(item)){
                    candidates.add(item);
                }
            }
            try {
                Set<Set<Integer>> combos = Sets.combinations(candidates, k);
                for (Set<Integer> combo: combos){
                        if (sets_counts.containsKey(combo)) {
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

    public Multimap<Integer, Integer> singles_rules(HashMap<Set<Integer>, Integer> doubletons, HashMap<Integer, Integer> frequent_singletons){
        SetMultimap<Integer, Integer> rules = HashMultimap.create();
        for (Set<Integer> doubleton:doubletons.keySet()) {
            //Iterator itr = doubleton.iterator();
            List<Integer> values = new ArrayList<>(doubleton);
            float support1 = frequent_singletons.get(values.get(0));
            float support2 = frequent_singletons.get(values.get(1));
            //float confidence;
            if (support1 <= support2){
                float double_support = doubletons.get(doubleton);
                float confidence = double_support/support1;
                if(confidence >= 0.5){
                    rules.put(values.get(0), values.get(1));
                }
            }
            else {
                float confidence = doubletons.get(doubleton)/support2;
                if (confidence >= 0.5){
                    rules.put(values.get(1), values.get(0));
                }
            }

        }
        return rules;
    }

    public Multimap<Set<Integer>, Integer> double_rules(HashMap<Set<Integer>, Integer> tripletons, HashMap<Set<Integer>, Integer> doubletons,
                                                        HashMap<Integer, Integer> frequent_singles) {
        SetMultimap<Set<Integer>, Integer> rules = HashMultimap.create();

        for (Map.Entry<Set<Integer>, Integer> tripleton : tripletons.entrySet()) {
            Set<Set<Integer>> contained_doubles = Sets.combinations(tripleton.getKey(), tripleton.getKey().size() - 1);
            List<Integer> items = new ArrayList<>(tripleton.getKey());
            List<Float> support_vals = new ArrayList<>();
            for (Integer item : items) {
                float item_support = frequent_singles.get(item);
                support_vals.add(item_support);
                for (Set<Integer> doubleton : contained_doubles) {
                    if (!doubleton.contains(item)) {
                        if (doubletons.containsKey(doubleton)) {
                            float double_support = doubletons.get(doubleton);
                            float confidence = double_support / item_support;

                            if (confidence >= 0.5) {
                                rules.put(doubleton, item);
                                //System.out.println("tested rule confidence" + confidence);
                            }
                        }
                        //System.out.println("tested rule" + doubleton +"->"+item);
                    }
                }
            }
        }
        return rules;
    }
}
