package ovsyanik.project.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    private static String nameFile = "notes.json";

    public static void createFile(Context context) {
        File file = new File(context.getFilesDir(), nameFile);
        try {
            file.createNewFile();
            Log.d("createFile_FileHelper", "File created");
        } catch (IOException ex) {
            Log.i("createFile_FileHelper", "Error to create the file");
        }
    }

    public static File getFile(Context context) {
        File file = new File(context.getFilesDir(), nameFile);
        return file;
    }

    public static boolean existFile(Context context) {
        File file = new File(context.getFilesDir(), nameFile);
        return file.exists();
    }

    public static void writeToFile(Context context, char[] chars) {
        File file = new File(context.getFilesDir(), nameFile);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(chars);
            writer.close();
            Log.i("writeToFile_FileHelper", "Successfully wrote to the file.");
        } catch (IOException e) {
            Log.i("writeToFile_FileHelper", "An error occurred.");
            e.printStackTrace();
        }
    }
}
