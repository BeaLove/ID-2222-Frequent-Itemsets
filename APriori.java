import java.util.*;

public class APriori{
    private HashMap<Set<Integer>, Integer> candidates;
    /**
     * Empty constructor
     */
    public APriori(){
        candidates = new HashMap<>();
    }


    public HashMap<Set<Integer>, Integer> secondPass(ArrayList<HashSet<Integer>> dataset,
                                                     HashMap<Integer, Integer> freq_table) {
        for (HashSet<Integer> basket : dataset) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int item : basket) {
                if (freq_table.containsKey(item)) {
                    tmp.add(item);
                }
            }
            if (tmp.size() != 0) {
                if(tmp.size() >= 2){
                    int[] test = new int[tmp.size()];
                    for(int i = 0; i < tmp.size(); i++){
                        test[i] = tmp.get(i);
                    }
                    System.out.println("INPUT: " + Arrays.toString(test));
                    for(int i = 2; i < tmp.size(); i++){
                        List<int[]> combinations = generate(test, i);
                    }
                    /*
                    for(int [] itemset : combinations){
                        System.out.println(Arrays.toString(itemset));
                    }
                    */

                }
            }
        }
        return candidates;
    }


    private List<int[]> generate(int[] input, int k){
        List<int[]> subsets = new ArrayList<>();
        int[] s = new int[k];
        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++);
            subsets.add(getSubset(input, s));
            for(;;) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--);
                if (i < 0) {
                    break;
                }
                s[i]++;                    // increment this item
                for (++i; i < k; i++) {    // fill up remaining items
                    s[i] = s[i - 1] + 1;
                }
                subsets.add(getSubset(input, s));
            }
        }
        return subsets;
    }

    // generate actual subset by index sequence
    private int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }


}