import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

public class Testfile{
    
    public Testfile(){

    } 
    public static void main(String[] args) {
    HashSet<Integer> mySet = new HashSet<>();
    mySet.add(1);
    mySet.add(2);
    mySet.add(3);
    //System.out.println(mySet);
    HashSet<Integer> mySet2 = new HashSet<>();
    mySet2.add(1);
    mySet2.add(2);
    mySet2.add(3);
    HashSet<Integer> mySet3 = new HashSet<>();
    mySet3.add(4);
    mySet3.add(5);
    mySet3.add(6);
    //mySet.addAll(mySet3);
    System.out.println(mySet);
    //mySet.addAll(mySet2);
    System.out.println(mySet2);
    System.out.println(mySet3);
    ArrayList<HashSet<Integer>> allSets = new ArrayList<>();
    allSets.add(mySet);
    allSets.add(mySet2);
    allSets.add(mySet3);
    HashMap<HashSet<Integer>, Integer> allSets2 = new HashMap<>();
    allSets2.put(mySet, 1);
    allSets2.put(mySet2, 2);
    allSets2.put(mySet3, 3);
    for (HashSet<Integer> set: allSets2.keySet()){
        for (HashSet<Integer> otherset: allSets2.keySet()) {
            set.addAll(otherset);
            
        }
    }
    /*for (int i = 0; i < allSets.size(); i ++){
        for (int j = 0; j < allSets.size(); j ++){
            allSets.get(i).addAll(allSets.get(j));
        }
    }*/
    for (Map.Entry<HashSet<Integer>, Integer> set: allSets2.entrySet()){
        System.out.println(set);
    }
    allSets2.replaceAll((k, v) -> v=0);
    System.out.println(allSets2.size());
    for (Map.Entry<HashSet<Integer>, Integer> set: allSets2.entrySet()){
        System.out.println(set.getKey() + " " + set.getValue());
    }
    }
}