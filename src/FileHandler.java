import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import static sun.misc.MessageUtils.out;

/**
 * Created by USER on 11/18/2016.
 */
public class FileHandler implements Serializable {

    public static void save(BinaryTree b1, BinaryTree b2) throws IOException {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);
        File f;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
            FileOutputStream out = new FileOutputStream(f.getAbsolutePath());
            ObjectOutputStream ost = new ObjectOutputStream(out);
            for (Game g : b1.getGameList()) {
                ost.writeObject(g);
            }

            ost.close();
        }

    }

    public static void load(File f) {//trying to upload new load function

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

