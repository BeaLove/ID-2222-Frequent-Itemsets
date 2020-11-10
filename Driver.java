import java.util.ArrayList;

public class Driver{

    public static void main(String[] args) {
        String filename = args[0];
        ArrayList<ArrayList<Integer>> baskets = readFromFile(filename);
        for (int i = 0; i < 5; i++){
            int item = baskets.get(i).get(i);
            System.out.print(item + " ");
        }
        System.out.println(" ");
    }

    public static ArrayList<ArrayList<Integer>> readFromFile(String filename){
        ArrayList<ArrayList<Integer>> baskets = new ArrayList<>();
        try {
            // pass the path to the file as a parameter
            File file =
                    new File(filename);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baskets;
    }
}