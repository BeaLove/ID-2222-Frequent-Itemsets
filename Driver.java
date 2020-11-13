import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.HashMap;

public class Driver{
    //Constants
    private static final int THRESHOLD = 3;

    public static void main(String[] args) throws FileNotFoundException {

        String filename = "baskets.dat";
        ArrayList<HashSet<Integer>> baskets = readFromFile(filename);
        int num_baskets = baskets.size();
        int THRESHOLD = num_baskets/100; //1% frequency threshold
        //int size_last_basket = baskets.get(num_baskets-1).;
        System.out.println("number baskets: " + num_baskets);
        //System.out.println("size of last basket: " + size_last_basket);
        ArrayList<HashSet<Integer>> sub_baskets = new ArrayList<>();
        for (int i= 0; i < 1000; i++){
            sub_baskets.add(baskets.get(i));
        }
        APriori apriori = new APriori();
        HashMap<HashSet<Integer>, Integer> singletons = apriori.createSingletons(sub_baskets);
        //debugging code prints all key value pairs!! 
        //singletons.entrySet().forEach( entry -> {System.out.println( entry.getKey() + " => " + entry.getValue() );});
        int size = singletons.size();
        System.out.println("singletons size: "+ size);
        //pruning removes all key-value pairs where value is below threshold
        HashMap<HashSet<Integer>, Integer> pruned_singletons = apriori.pruning(singletons, 10);
        System.out.println("after pruning: " + pruned_singletons.size());
        System.out.println("original map after pruning: " + singletons.size());
        //ArrayList<HashSet<Integer>> singletons_list = new ArrayList<>(singletons.keySet());
        //PLACEHOLDER k for iteration count
        int k = 1;
        HashMap<HashSet<Integer>, Integer> merged_sets = apriori.mergeItemsets(singletons);
        HashMap<HashSet<Integer>, Integer> doubletons = apriori.counter(sub_baskets, merged_sets);
        System.out.println("hello 1");
        int doubles_size = doubletons.size();
        System.out.println(doubles_size);
        //doubletons.entrySet().forEach( entry -> {System.out.println( "doubletons " + entry.getKey() + "=>" + entry.getValue());});
        System.out.println("hello 2");
        HashMap<HashSet<Integer>, Integer> itemSets = singletons;
        while (!itemSets.isEmpty()){
            itemSets = apriori.pruning(itemSets, THRESHOLD);
            itemSets = apriori.mergeItemsets(itemSets);
            itemSets = apriori.counter(baskets, itemSets);
            


        }

    }

    public static ArrayList<HashSet<Integer>> readFromFile(String filename) throws FileNotFoundException {
        //IN filename
        //OUT market basket dataset ArrayList of sets of transactions with items represented as integers
        ArrayList<HashSet<Integer>> baskets = new ArrayList<>();
            // pass the path to the file as a parameter
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] splitted = s.split(" ");
                HashSet<Integer> basket = new HashSet<>();
                //int [] arr = new int[splitted.length];
                for (int i = 0; i < splitted.length; i++){
                    basket.add(Integer.parseInt(splitted[i]));
                }
                
                baskets.add(basket);
            }
        sc.close();
        return baskets;
    }
}