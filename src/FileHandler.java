import java.io.*;
import java.util.ArrayList;


/**
 * Created by USER on 11/18/2016.
 */
public class FileHandler implements Serializable {

    public static void save(BinaryTree b1, BinaryTree b2) {
        Main.out("\nAttempt to save");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream ost = null;
        try {
            ost = new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ost != null)//if Stream loaded successfully
        {
            Main.out("PRINTING TO FILE---------------------");
            for (Game g : b1.getGameList()) {
                writeGametoFile(ost, g);
            }
            for (Game g : b2.getGameList()) {

                writeGametoFile(ost, g);
            }
            Main.out("END OF FILE---------------------");

            try {
                ost.close();
                Main.out("Output Stream Closed successfully ");
            } catch (IOException e) {
                Main.out("Failed to close OutputStream");
                e.printStackTrace();
            }


        } else Main.out("Stream failed to initalize");
        Main.out("End of save function");
    }

    private static void writeGametoFile(ObjectOutputStream stream, Game game) {
        game.printGame();
        try {
            stream.writeObject(game);
        } catch (IOException ex) {
            Main.out("failed to write game to file");
            Main.out(game.getGameNumber());
        }
    }

    public static void load(File f) {

        Main.out("\nAttempt to Load");
        FileInputStream in = null;
        try {
            in = new FileInputStream(f.getAbsolutePath());
            Main.out("File successfully initialized");
        } catch (FileNotFoundException e) {
            Main.out("File failed to be initialize");
            e.printStackTrace();
        }
        ObjectInputStream oit = null;
        try {
            oit = new ObjectInputStream(in);
            Main.out("Input Stream successfully initialized");
        } catch (IOException e) {
            Main.out("Input Stream failed to initialize");
            e.printStackTrace();
        }
        if(oit !=null) {//if input stream loaded successfully
            ArrayList<Game> list = new ArrayList<>();
            Main.out("LOADING FILE---------------------");
            try {
                while (oit.readObject() != null) {
                    Game g = (Game) oit.readObject();
                    g.printGame();
                    list.add(g);
                }
            } catch (IOException e) {
                Main.out("Filed to read shit from file");

            } catch (ClassNotFoundException e) {
                Main.out("Could not find object type");
            }
            Main.out("Printing stored list");
            for (Game g : list) {
                g.printGame();
            }
            Main.out("END OF FILE---------------------");
            try {
                oit.close();
                Main.out("Stream closed successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else Main.out("Failed To load data from file.");
        Main.out("End of Load function");

    }


}

