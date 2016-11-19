import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by USER on 11/18/2016.
 */
public class Save implements Serializable {
    public static void scrapeTree(BinaryTree b1, BinaryTree b2) throws IOException {
        FileOutputStream out = new FileOutputStream("test.txt");
        ObjectOutputStream ost= new ObjectOutputStream(out);
        ost.writeObject(b1);
        ost.writeObject(b2);
        ost.close();
    }

}

