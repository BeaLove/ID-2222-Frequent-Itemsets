import java.util.*;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;


public class APriori {
    public HashMap<Integer, Set<Integer>> basket_counts = new HashMap<>();
    private final int threshold;
    public APriori(int t){
        threshold = t;

    }

    public HashMap<Set<Integer>, Integer> createSingletons(ArrayList<Set<Integer>> baskets) {
        System.out.println("hello from singletons");
        long start = System.currentTimeMillis();
        //IN market basket model dataset, list of sets of integers (eg items purchased in transactions)
        //OUT HashMap of key-value pairs of each integer (item) and the number of times it appears in all baskets
        HashMap<Set<Integer>, Integer> singletons = new HashMap<>();

        int num_baskets = baskets.size();
        System.out.println("num baskets: " + num_baskets);
        for (Set<Integer> basket : baskets) { //for each basket
            //System.out.println("new basket");
            for (int item : basket) { //for each item in each basket
                //System.out.println("new item");
                Set<Integer> basket_set = Sets.newHashSet();
                Set<Integer> singleton = Sets.newHashSet();
                singleton.add(item);
                if (singletons.containsKey(singleton)) { //if we've already seen this item, increment its count by 1
                    singletons.put(singleton, singletons.get(singleton) + 1);
                    basket_set = basket_counts.get(item);
                    basket_set.add(baskets.indexOf(basket));
                    basket_counts.put(item, basket_set);
                } else {
                    singletons.put(singleton, 1); //if item is new, count it once
                    basket_set.add(baskets.indexOf(basket));
                    basket_counts.put(item, basket_set);
                }
                System.out.println("after if else");
            }
        }
        //removes infrequent items
        basket_counts.entrySet().removeIf(entry -> entry.getValue().size() < threshold);
        int num_items = basket_counts.size();
        System.out.println("number of frequent items" + " " + num_items);
        long end = System.currentTimeMillis();
        System.out.println("goodbye from singletons" + " " + (end - start));
        return singletons;
    }

    //NEW FUNCTION COUNT!!!!!
    public HashMap<Set<Integer>, Integer> count(HashMap<Set<Integer>, Integer> sets_counts, int k){
        //Set<Integer> frequent_items = basket_counts.keySet();
        long start = System.currentTimeMillis();
        System.out.println("hello from count");
        Set<Integer> frequent_items = Sets.newHashSet();
        for (Set<Integer> set: sets_counts.keySet()){
            frequent_items.addAll(set);
        }
        HashMap<Set<Integer>, Integer> counts = new HashMap<>();
        Set<Set<Integer>> candidate_sets = Sets.combinations(frequent_items, k);
        for (Set<Integer> candidate :candidate_sets) {
            ArrayList<Integer> item_list = new ArrayList<>(candidate);
            Set<Integer> support = Sets.newHashSet();
            for(int item: item_list){
                support.retainAll(basket_counts.get(item));
            }
            counts.put(candidate, support.size());
        }
        long end = System.currentTimeMillis();
        System.out.println("Goodbye from count" + " " + (end-start));
        return counts;
    }

    public HashMap<Integer, Integer> prune_singles(HashMap<Integer, Integer> map, int threshold){
        //IN hashmap of items and counts
        //OUT pruned hashmap of only items that appear at least "threshold" times
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        return map;
    }
    public HashMap<Set<Integer>, Integer> prune(HashMap<Set<Integer>, Integer> map){
        System.out.println("Hello prune");
        map.entrySet().removeIf(entry -> entry.getValue() < threshold); //removes all key-value pairs if value is below support threshold
        System.out.println("bye prune");
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
