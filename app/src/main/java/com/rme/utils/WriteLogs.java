package com.rme.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Raj on 7/15/2015.
 * this class use for Write Text File.
 */
public class WriteLogs {
    //this method write Text file.
    public static File getFilePath(Context context) {
        File root = new File(URLString.getDownloadVisiblePath(context));
        if (!root.exists()) {
            root.mkdirs();
        }
        return new File(root, "Logs_File2.txt");
    }

    public static String readText(Context context) {
        File fileEvents = getFilePath(context);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\r\n");
            }
            br.close();
        } catch (IOException e) {
        }
        return text.toString();
    }

    public static void writeLogs(Context context,String str) {

        //Read text from file


        try {
            Writer output = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(getFilePath(context), true), "UTF-8"));
            //BufferedWriter output = new BufferedWriter(new FileWriter(gpxfile, true));
            output.append("\r\n" + str);
            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Text File Error:", e.toString());
        }
    }
}
