package com.rme.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Rajnish on 10/4/2015.
 * this class use for set Server URL.
 */
public class URLString {


    public static final String strMainFolderName = "RMePOS";

    public static DecimalFormat decimalFormat = new DecimalFormat("##.##");
    public static DecimalFormat decimalFormat00 = new DecimalFormat("##.00");



    public static String getRootPath(Context context) {
        String strInternalPath = "";

        try {
            File ff = null;
            try {
                strInternalPath = "" + Environment.getExternalStorageDirectory().getAbsolutePath();
                strInternalPath = strInternalPath + "/" + strMainFolderName;
                ff = new File(strInternalPath);
                if (!ff.exists()) {
                    ff.mkdirs();
                } else {
                    String sTempFile = ff.getAbsolutePath() + "/temp.txt";
                    File ftest = new File(sTempFile);
                    ftest.createNewFile();
                    try {
                        if (ftest.exists()) {
                            InputStream myInput = new FileInputStream(ftest);
                        }
                    } catch (FileNotFoundException e) {
                        ff = null;
                        e.printStackTrace();
                    } catch (Exception e) {
                        ff = null;
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                ff = null;
                e.printStackTrace();
            }
            if (ff == null || !ff.exists()) {


                strInternalPath = "";
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    strInternalPath = "" + Environment.getExternalStorageDirectory().getAbsolutePath();
                } else {
                    File f = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                    if (f != null) {
                        strInternalPath = "" + f.getAbsolutePath();
                    }
                }
                if (strInternalPath.equals("") || strInternalPath.toLowerCase().equals("null")) {
                    File f = context.getCacheDir();
                    if (f != null) {
                        strInternalPath = "" + f.getAbsolutePath();
                    }
                }
                if (strInternalPath.equals("") || strInternalPath.toLowerCase().equals("null")) {
                    File f = context.getExternalCacheDir();
                    if (f != null) {
                        strInternalPath = "" + f.getAbsolutePath();
                    }
                }

                if (!strInternalPath.equals("") && !strInternalPath.toLowerCase().equals("null")) {
                    strInternalPath = strInternalPath + "/" + strMainFolderName;
                    File fileDir = new File(strInternalPath);
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return strInternalPath;
    }
    public static String checkWriteFile(String s,Context context) {
        File f1 = new File(s);
        if (f1.exists()){
            File f2 = new File(s+"/.Temp");
            if (!f2.exists()){
                f2.mkdirs();
            }
            if (f2.exists()){
                return s;
            }
        }
        return "";
    }
    public static String getDownloadVisiblePath(Context context) {
        String ss = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + strMainFolderName;
        ss = checkWriteFile(ss,context);
        if (ss.equalsIgnoreCase("")){
            ss = getRootPath(context);
            ss = checkWriteFile(ss,context);
        }
        if (ss.equalsIgnoreCase("")){
            ss = context.getFilesDir().getAbsolutePath();
            ss = checkWriteFile(ss,context);
        }
        if (ss.equalsIgnoreCase("")){
            ss = Environment.getExternalStorageDirectory().getAbsolutePath();
            ss = checkWriteFile(ss,context);
            if (!ss.equals("")){
                ss = ss+ "/" + strMainFolderName;
                File f = new File(ss);
                if (!f.exists()){
                    f.mkdirs();
                }
                ss = checkWriteFile(ss,context);
            }
        }
//        Log.e("Final","="+ss);



//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
//            ss = getRootPath(context);
////            String path = getfil().getAbsolutePath();
//            Log.e("ss","2="+ss);
//        }

        return ss;

    }
    public static String checkDownloadPath(Context context,String sFolderName) {
        String ss = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + strMainFolderName+"/"+sFolderName;
        ss = checkWriteFile(ss,context);

        return ss;

    }
    public static String getDownloadTempPath(Context context) {
        String strInternalPath = getDownloadVisiblePath(context);


        if (!strInternalPath.equals("") && !strInternalPath.equalsIgnoreCase("null")) {
            strInternalPath = strInternalPath + "/.TempDownload/";
            File fileDir = new File(strInternalPath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        Log.e("DownloadTemp","="+strInternalPath);
        if (new File(strInternalPath).exists()){
            Log.e("DownloadTemp","=Root File Avail");
        }else{
            Log.e("DownloadTemp","=Root File Not Avail");
        }
        return strInternalPath;
    }
    public static String getDownloadPath(Context context) {
        String strInternalPath = getDownloadVisiblePath(context);
        if (!strInternalPath.equals("") && !strInternalPath.toLowerCase().equals("null")) {
            strInternalPath = strInternalPath + "/Download/";
            File fileDir = new File(strInternalPath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        return strInternalPath;
    }
    public static String getImageHidePath(Context context) {
        String strInternalPath = getRootPath(context);
        if (!strInternalPath.equals("") && !strInternalPath.toLowerCase().equals("null")) {
            strInternalPath = strInternalPath + "/.Image/";
            File fileDir = new File(strInternalPath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        return strInternalPath;
    }


    public static ArrayList<String> splitCommaArray(String s) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] sArray = s.split(",");
        for (int i = 0; i < sArray.length; i++) {
            String s1 = isNull(sArray[i]);
            arrayList.add(s1);
        }
        if (arrayList.size()>0){
            String s1 = arrayList.get(arrayList.size()-1);
            if (s1.equals("")){
                arrayList.remove(arrayList.size()-1);
            }
        }
        return arrayList;
    }

    public static ArrayList<String> string2Array(String s) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] sArray = s.split(",");
        for (int i = 0; i < sArray.length; i++) {
            String s1 = isNull(sArray[i]);
            arrayList.add(s1);
        }
        if (arrayList.size()>0){
            String s1 = arrayList.get(arrayList.size()-1);
            if (s1.equals("")){
                arrayList.remove(arrayList.size()-1);
            }
        }
        return arrayList;
    }
    public static String isNull(String s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        return s1;
    }

    public static String getFileName(String sFullPath) {
        String s1 = "";
        try {
            s1 = sFullPath.substring(sFullPath.lastIndexOf("/"),sFullPath.length());
            s1 = s1.replaceAll("/","");
        } catch (Exception e) {
        }
        return s1;
    }
    public static String isNull(Object s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        return s1;
    }

    public static void checkVisibilityText(TextView txt) {
        String s1 = isNull("" + txt.getText().toString());
        if (s1.equals("")) {
            txt.setVisibility(View.GONE);
        } else {
            txt.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isTrue(Object s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        if (s1.toLowerCase(Locale.ROOT).contains("true")||s1.toLowerCase(Locale.ROOT).contains("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTrue(String s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        if (s1.toLowerCase(Locale.ROOT).contains("true")||s1.toLowerCase(Locale.ROOT).contains("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static int getInteger(String s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        int iCount = 0;
        try {
            iCount = Integer.parseInt(s1);
        } catch (NumberFormatException e) {
        }
        return iCount;
    }
    public static int getInteger(Object s) {
        String s1 = s.toString();
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        int iCount = 0;
        try {
            iCount = Integer.parseInt(s1);
        } catch (NumberFormatException e) {
        }
        return iCount;
    }

    public static float getFloat(String s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        float iCount = 0;
        try {
            iCount = Float.parseFloat(s1);
        } catch (NumberFormatException e) {
        }
        return iCount;
    }


    public static float getFloat(Object s) {

        if (s == null){
            return 0;
        }
        String s1 = "" + s.toString();
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        float iCount = 0;
        try {
            iCount = Float.parseFloat(s1);
        } catch (NumberFormatException e) {
        }
        return iCount;
    }

    public static double getDouble(String s) {
        String s1 = "" + s;
        if (s1.toLowerCase().equals("null")) {
            s1 = "";
        }
        double iCount = 0;
        try {
            iCount = Double.parseDouble(s1);
        } catch (NumberFormatException e) {
        }
        return iCount;
    }

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

}
