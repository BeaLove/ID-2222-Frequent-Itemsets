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
        ArrayList<int[]> baskets = readFromFile(filename);
        int num_baskets = baskets.size();
        int THRESHOLD = num_baskets/100;
        int size_last_basket = baskets.get(num_baskets-1).length;
        System.out.println("number baskets: " + num_baskets);
        System.out.println("size of last basket: " + size_last_basket);
        APriori apriori = new APriori();
        HashMap<HashSet<Integer>, Integer> singletons = apriori.createSingletons(baskets);
        //debugging code prints all key value pairs!! 
        singletons.entrySet().forEach( entry -> {
            System.out.println( entry.getKey() + " => " + entry.getValue() );
        });
        int size = singletons.size();
        System.out.println("singletons size: "+ size);
        //pruning removes all key-value pairs where value is below threshold
        HashMap<HashSet<Integer>, Integer> pruned_singletons = apriori.pruning(singletons, 1000);
        System.out.println("after pruning: " + pruned_singletons.size());
        System.out.println("original map after pruning: " + singletons.size());
        ArrayList<HashSet<Integer>> singletons_list = new ArrayList<>(singletons.keySet());
        ArrayList<HashSet<Integer>> merged_sets = apriori.mergeItemsets(singletons_list);

    }

    public static ArrayList<HashSet<Integer>> readFromFile(String filename) throws FileNotFoundException {
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