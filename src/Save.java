import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by USER on 11/18/2016.
 */
public class Save implements Serializable {
    static BinaryTree winner, loser;
    static BinaryNode winners, losers;
    static ArrayList<ArrayList<Game>> list;
    static ArrayList<ArrayList<Game>> loseList;

    static ArrayList<Game> splitNodeList(BinaryTree tree) {
        ArrayList<Game> blist = new ArrayList<>();
        ArrayList<ArrayList<Game>> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Game> list1 = list.get(i);
            for (Game g : list1) {
                blist.add(g);
            }
        }
        return blist;
    }

    public static void scrapeTree(BinaryTree b1, BinaryTree b2) {


        try {
           PrintWriter out = new PrintWriter("test.txt");

            for (Game x : splitNodeList(b1)) {
                out.println(x);
            }
            for (Game y : splitNodeList(b2)) {
                out.println(y);
            }
            out.close();


        } catch (FileNotFoundException v) {
            v.printStackTrace();
        }


    }

}

