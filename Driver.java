import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.google.common.collect.Sets;


public class Driver {
    private static final int THRESHOLD = 3;

    public static void main(String[] args) throws FileNotFoundException {

        String filename = "baskets.dat";
        ArrayList<Set<Integer>> baskets = readFromFile(filename);
        int num_baskets = baskets.size();
        int THRESHOLD = num_baskets/100; //1% frequency threshold
        //int size_last_basket = baskets.get(num_baskets-1).;
        System.out.println("number baskets: " + num_baskets);
        //System.out.println("size of last basket: " + size_last_basket);
        APriori apriori = new APriori();
        HashMap<Integer, Integer> singletons = apriori.createSingletons(baskets);
        //debugging code prints all key value pairs!!
        //singletons.entrySet().forEach( entry -> {System.out.println( entry.getKey() + " => " + entry.getValue() );});
        int size = singletons.size();
        System.out.println("singletons size: "+ size);
        //pruning removes all key-value pairs where value is below threshold
        //HashMap<Set<Integer>, Integer> pruned_singletons = apriori.pruning(singletons, 10);
        //System.out.println("after pruning: " + pruned_singletons.size());
        //System.out.println("original map after pruning: " + singletons.size());
        //ArrayList<Set<Integer>> singletons_list = new ArrayList<>(singletons.keySet());
        //PLACEHOLDER k for iteration count
        int k = 1;
        //HashMap<Integer, Integer> merged_sets = apriori.mergeItemsets(singletons);
        HashMap<Integer, Integer> pruned_singles = apriori.prune_singles(singletons, THRESHOLD);
        //HashMap<Integer, Integer> itemSets = singletons;
        Set<Integer> frequent_singles = Sets.newHashSet(singletons.keySet());
        //XMLStreamWriterImpl.Element sets_counts;
        HashMap<Set<Integer>, Integer> sets_counts;
        do {
            //itemSets = apriori.mergeItemsets(itemSets);
            sets_counts = apriori.counter(baskets, frequent_singles, k);
            sets_counts = apriori.prune(sets_counts, THRESHOLD);
            for (Map.Entry itemSet:sets_counts.entrySet()){
                System.out.println(itemSet);
            }
            k++;
        }
        while (!sets_counts.isEmpty());

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

