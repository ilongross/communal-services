package communal_services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Distribution {

    static String PATH = System.getProperty("user.dir");

    public static final String DATE_FOR_FILENAME = new SimpleDateFormat("yyyyMMdd")
            .format(Calendar.getInstance().getTime());
    public static final String DATE_FOR_OUTPUT = new SimpleDateFormat("dd.MM.yyyy")
            .format(Calendar.getInstance().getTime());

    public static void copyFile (String from, String to) {
        File fileTxt = new File(from);
        File dir = new File(PATH + "/" + DATE_FOR_FILENAME);
        if(!dir.exists())
            dir.mkdir();
        File fileTxtCopied = new File(PATH + "/" + DATE_FOR_FILENAME + "/" + to);
        try {
            Files.copy(fileTxt.toPath(), fileTxtCopied.toPath());
        } catch (IOException e) {
            System.out.println("Error while copying files");
            e.printStackTrace();
        }
    }

    public static void createDir (File newDir) {

    }

    public static void createFile (File newFile) {
        if(!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating the new file");
                e.printStackTrace();
            }
        }
        else
            System.out.println("File " + newFile + " is exists");
    }

}
