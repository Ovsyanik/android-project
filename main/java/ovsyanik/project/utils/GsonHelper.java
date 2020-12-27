package ovsyanik.project.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ovsyanik.project.data.Notes;

public class GsonHelper {
    private Gson gson = new Gson();

    public List<Notes> readNotes(Context context) {
        InputStreamReader isr = null;
        FileInputStream fis = null;
        List<Notes> list = new ArrayList<>();
        try {
            fis = new FileInputStream(FileHelper.getFile(context));
            isr = new InputStreamReader(fis, "UTF-8");

            Type type = new TypeToken<ArrayList<Notes>>(){}.getType();
            list = gson.fromJson(isr, type);
            return list;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void writeNotes(Context context, List<Notes> notes) {
        String jsonString = gson.toJson(notes);
        FileHelper.writeToFile(context, jsonString.toCharArray());
    }

}
