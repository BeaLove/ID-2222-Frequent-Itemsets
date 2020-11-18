import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.checkerframework.checker.units.qual.A;


public class Driver {
    //private static final int THRESHOLD = 3;

    public static void main(String[] args) throws FileNotFoundException {

        String filename = "baskets.dat";
        ArrayList<Set<Integer>> baskets = readFromFile(filename);
        int num_baskets = baskets.size();
        ArrayList<Set<Integer>> sub_baskets = new ArrayList<>();
        for (int i = 0; i < 10; i ++){
            sub_baskets.add(baskets.get(i));
        }

        int THRESHOLD = num_baskets/100; //1% frequency threshold
        //int size_last_basket = baskets.get(num_baskets-1).;
        APriori apriori = new APriori(1);
        HashMap<Set<Integer>, Integer> frequent_items = apriori.createSingletons(sub_baskets);
        //debugging code prints all key value pairs!!
        //singletons.entrySet().forEach( entry -> {System.out.println( entry.getKey() + " => " + entry.getValue() );});
        //pruning removes all key-value pairs where value is below threshold
        int k = 1;

        //HashMap<Integer, Integer> pruned_singles = apriori.prune_singles(singletons, THRESHOLD);
        //pruned_singles.entrySet().forEach(entry -> System.out.println(entry.getKey() + " > " + entry.getValue()));
        //Set<Integer> frequent_items = Sets.newHashSet(singletons.keySet());


        HashMap<Set<Integer>, Integer> sets_counts;
        while(k < 4){
            k++;
            sets_counts = apriori.count(frequent_items, k);
            sets_counts = apriori.prune(sets_counts);
            sets_counts.entrySet().forEach(entry -> System.out.println(entry.getKey() + " --> " + entry.getValue()));
        }
        /*do {
            Long start_time = System.currentTimeMillis();
            k++;
            sets_counts = apriori.counter(baskets, frequent_items, k);
            sets_counts = apriori.prune(sets_counts, THRESHOLD);
            for (Map.Entry itemSet:sets_counts.entrySet()){
                System.out.println(itemSet);
            }
            if (k == 2){
                Multimap<Integer, Integer> single_rules= apriori.singles_rules(sets_counts, pruned_singles);
                System.out.println("single item rules");
                single_rules.entries().forEach(entry -> System.out.println(entry.getKey() + "-->" + entry.getValue()));
            }
            HashMap<Set<Integer>, Integer> frequent_itemsets = new HashMap<>();
            frequent_itemsets.putAll(sets_counts);
            Multimap<Set<Integer>, Integer> rules = apriori.double_rules(sets_counts, frequent_itemsets, pruned_singles);
            rules.entries().forEach(entry -> System.out.println(entry.getKey() + "-->" + entry.getValue()));
            frequent_items = apriori.merge(sets_counts.keySet());
            Long end_time = System.currentTimeMillis();
            System.out.println("Counting frequent sets took" + (end_time-start_time) + "ms");
            //k++;
        }
        while (!sets_counts.isEmpty());*/



    }

    public static ArrayList<Set<Integer>> readFromFile(String filename) throws FileNotFoundException {
        //IN filename
        //OUT market basket dataset ArrayList of sets of transactions with items represented as integers
        ArrayList<Set<Integer>> baskets = new ArrayList<>();
        // pass the path to the file as a parameter
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] splitted = s.split(" ");
            Set<Integer> basket = Sets.newHashSet();
            //int [] arr = new int[splitted.length];
            for (String value : splitted) {
                basket.add(Integer.parseInt(value));
            }

            baskets.add(basket);
        }
        sc.close();
        return baskets;
    }
}

