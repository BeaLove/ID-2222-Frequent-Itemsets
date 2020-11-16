import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver{
    //Constants
    private static HashMap<Integer, Integer> count_elements;
    private static final String filename = "input.dat";

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<HashSet<Integer>> baskets = firstPass();
        int THRESHOLD = baskets.size()/100;
        System.out.println("Threshold" +  THRESHOLD);
        APriori apriori = new APriori();
        HashMap<Integer, Integer> freq_table = new HashMap<>();
        //Filter out most frequent items
        for(Map.Entry<Integer, Integer> entry : count_elements.entrySet()){
            int val = entry.getValue();
            if(THRESHOLD <= val ){
                int key = entry.getKey();
                freq_table.put(key, val);
            }
        }

        HashMap<int[], Integer> multiples_freq = null;
        apriori.secondPass(baskets, freq_table);


        //print results
        for(Map.Entry<int[], Integer> entry : multiples_freq.entrySet()){
            int val = entry.getValue();
            if(THRESHOLD <= val ){
                int[] key = entry.getKey();
                System.out.println("Array: " + Arrays.toString(key) + ", Frequency: " + val );
            }
        }



        
    }


    public static ArrayList<HashSet<Integer>> firstPass() throws FileNotFoundException {
        ArrayList<HashSet<Integer>> baskets = new ArrayList<>();
        count_elements = new HashMap<>();
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] splitted = s.split(" ");
            HashSet<Integer> basket = new HashSet<>();
            for (int i = 0; i < splitted.length; i++){
                int val = Integer.parseInt(splitted[i]);
                if(count_elements.containsKey(val)){
                    count_elements.put(val, count_elements.get(val) + 1);
                }else{
                    count_elements.put(val, 1);
                }
                basket.add(val);
            }

            baskets.add(basket);
        }
        sc.close();
        return baskets;
    }
}