import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver{
    //Constants
    private static final int THRESHOLD = 3;

    public static void main(String[] args) throws FileNotFoundException {

        String filename = "baskets.dat";
        ArrayList<ArrayList<Integer>> baskets = readFromFile(filename);
        for(ArrayList<Integer> list : baskets){
            for(int i = 0; i < list.size(); i++){
                System.out.print(list.get(i) + " ");
            }
            System.out.println();
        }

    }

    public static ArrayList<ArrayList<Integer>> readFromFile(String filename) throws FileNotFoundException {
        ArrayList<ArrayList<Integer>> baskets = new ArrayList<>();
            // pass the path to the file as a parameter
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] splitted = s.split(" ");
                ArrayList<Integer> basket = new ArrayList<>();
                for (int i = 0; i < splitted.length; i++){
                    basket.add(Integer.parseInt(splitted[i]));
                }
                baskets.add(basket);
            }
        sc.close();
        return baskets;
    }
}