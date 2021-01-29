package communal_services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOMethods<E> {

    public static BufferedReader read (String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
//        System.out.println("(read file " + filename + ")");
        return br;
    }

    public static BufferedWriter write (String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
//        System.out.println("(write file " + filename + ")");
        return bw;
    }

    public static BufferedWriter rewrite (String filename) throws IOException {
        File f = new File(filename);
        FileWriter fw = new FileWriter(f, false);
        BufferedWriter bw = new BufferedWriter(fw);
//        System.out.println("(rewrite file " + filename + ")");
        return bw;
    }

    public static void updateBalance () {

    }

}
