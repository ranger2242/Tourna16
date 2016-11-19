import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    public static void scrapeTree(BinaryTree b1, BinaryTree b2) throws IOException {
        FileOutputStream out = new FileOutputStream("test.txt");
        ObjectOutputStream ost= new ObjectOutputStream(out);
        for (Game x : b1.getGameList()) {
            ost.writeObject(x);
        }
        for (Game x : b2.getGameList()) {
            ost.writeObject(x);
        }
        ost.close();
/*
        try {
           PrintWriter out = new PrintWriter("test.txt");


            for (Game y : splitNodeList(b2)) {
                out.println(y);
            }
            out.close();


        } catch (FileNotFoundException v) {
            v.printStackTrace();
        }
*/

    }

}

