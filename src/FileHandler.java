import java.io.*;
import java.util.ArrayList;

import static sun.misc.MessageUtils.out;

/**
 * Created by USER on 11/18/2016.
 */
public class FileHandler implements Serializable {

    public static void save(BinaryTree b1, BinaryTree b2) throws IOException {
        FileOutputStream out = new FileOutputStream("test.txt");
        ObjectOutputStream ost = new ObjectOutputStream(out);
        for (Game g : b1.getGameList()) {
            ost.writeObject(g);
        }

        ost.close();
    }

    public static void load(File f) {

        try {
            FileInputStream in = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream oit = new ObjectInputStream(in);
            ArrayList<Game> list= new ArrayList<>();
            try {
                while (true) {
                    list.add((Game) oit.readObject());
                }
            }catch (EOFException e){
                oit.close();

            }
            for(Game g : list){
                g.printGame();
            }
            out("g--");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }//

}

