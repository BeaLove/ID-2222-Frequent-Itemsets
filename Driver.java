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
        int size_last_basket = baskets.get(num_baskets-1).length;
        System.out.println("number baskets: " + num_baskets);
        System.out.println("size of last basket: " + size_last_basket);
        APriori apriori = new APriori();
        HashMap<HashSet<Integer>, Integer> itemSets = apriori.createSingletons(baskets);
        //debugging code prints all key value pairs!! 
        itemSets.entrySet().forEach( entry -> {
            System.out.println( entry.getKey() + " => " + entry.getValue() );
        });
        int size = itemSets.size();
        System.out.println(size);
    }

    public static ArrayList<int[]> readFromFile(String filename) throws FileNotFoundException {
        ArrayList<int[]> baskets = new ArrayList<>();
            // pass the path to the file as a parameter
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] splitted = s.split(" ");
                int [] arr = new int[splitted.length];
                for (int i = 0; i < splitted.length; i++){
                    arr[i] = Integer.parseInt(splitted[i]);
                }
                baskets.add(arr);
            }
        sc.close();
        return baskets;
    }
}