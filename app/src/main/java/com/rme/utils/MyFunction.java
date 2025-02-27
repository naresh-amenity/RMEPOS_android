package com.rme.utils;

import static android.content.Context.BATTERY_SERVICE;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyFunction {

    public static boolean isVPNPON(Context context) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            Network activeNetwork = connectivityManager.getActiveNetwork();
//            NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
//            return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
//        } else {
        return false;
//        }
    }
    public static void createFile(Context context,String sFileName,String sBody) {
        try {
            File root = new File(URLString.getDownloadVisiblePath(context), sFileName);
            if (root.exists()) {
                root.delete();
            }
            FileWriter writer = new FileWriter(root);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getBattery(Context context) {
        try {
            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String sImageCapturePath = "";

    public static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        sImageCapturePath = image.getAbsolutePath();
        return image;
    }

    public static void hideKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
    //    public static Bitmap addWaterMark(Bitmap bitmap) {
//        String sWaterMark = ConvertDate.getDateToDMMMYhmsa(new Date());
//        int bW = bitmap.getWidth();
//        int bH = bitmap.getHeight();
//        Bitmap newBitmap = Bitmap.createBitmap(bW, bH, bitmap.getConfig());
//        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawBitmap(bitmap, 0f, 0f, null);
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setTextSize(20f);
//        paint.setAntiAlias(true);
//        canvas.drawText(sWaterMark, 30f, bH - 30f, paint);
//        return newBitmap;
//    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        String Uid = "";
        try {
            Uid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            // TODO: handle exception
            Uid = "";
        }
        return Uid;
    }

    //this method return device APILEVEL
    public static String getAPILEVEL() {
        String Uid = "APILEVEL: " + Build.VERSION.SDK_INT;
        return Uid;
    }

    //this method return device deatls
    public static String getDEVICE() {
        String Uid = "Device: " + Build.DEVICE + "   Model: " + Build.MODEL;
        return Uid;
    }

    public static String getAllDetails() {
        String Uid = getMANUFACTURER() + "  " + getDEVICE() + "  " + getAPILEVEL();
        return Uid;
    }

    public static String getMANUFACTURER() {

        String Uid = "MANUFACTURER: " + Build.MANUFACTURER;
        return Uid;
    }

    public static String getFirstCharacter(String sText) {
        if (!sText.isEmpty()) {
            return sText.substring(0, 1).toUpperCase(Locale.ROOT);
        } else {
            return "";
        }
    }

    public static String getAddress(Context context, double latiTude, double longiTude) {
//        latiTude = 22.284206;
//        longiTude = 70.801777;
        //22.284206,70.801777
        String sFullAddress = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latiTude, longiTude, 1);
            if (addresses != null) {
                if (addresses.size() != 0) {
                    String sFeatureName = URLString.isNull(addresses.get(0).getFeatureName());
                    if (!sFeatureName.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = sFeatureName;
                        } else {
                            sFullAddress = sFullAddress + ", " + sFeatureName;
                        }
                    }
                    String sMaxLine = URLString.isNull(addresses.get(0).getMaxAddressLineIndex());
                    for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                        String address = URLString.isNull(addresses.get(0).getAddressLine(i));
                        if (!address.equals("")) {
                            if (sFullAddress.equals("")) {
                                sFullAddress = address;
                            } else {
                                sFullAddress = sFullAddress + ", " + address;
                            }
                        }
                    }
                    String sPremises = URLString.isNull(addresses.get(0).getPremises());
                    if (!sPremises.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = sPremises;
                        } else {
                            sFullAddress = sFullAddress + ", " + sPremises;
                        }
                    }
                    String sSubLocality = URLString.isNull(addresses.get(0).getSubLocality());

                    if (!sSubLocality.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = sSubLocality;
                        } else {
                            sFullAddress = sFullAddress + ", " + sSubLocality;
                        }
                    }
                    String cityName = URLString.isNull(addresses.get(0).getLocality());

                    if (!cityName.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = cityName;
                        } else {
                            sFullAddress = sFullAddress + ", " + cityName;
                        }
                    }
                    String stateName = URLString.isNull(addresses.get(0).getAdminArea());

                    if (!stateName.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = stateName;
                        } else {
                            sFullAddress = sFullAddress + ", " + stateName;
                        }
                    }
                    String sPostalCode = URLString.isNull(addresses.get(0).getPostalCode());
                    if (!sPostalCode.equals("")) {
                        if (sFullAddress.equals("")) {
                            sFullAddress = sPostalCode;
                        } else {
                            sFullAddress = sFullAddress + ", " + sPostalCode;
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sFullAddress;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String formatSecond(int SecondsCounter) {
        return String.format("%02d:%02d", ((SecondsCounter % 3600) / 60), (SecondsCounter % 60));
    }

    public static void dialNow(Activity activity, String sPhone) {
        sPhone = sPhone.replaceAll("\\+91", "");
        sPhone = sPhone.replaceAll(" ", "");
        sPhone = sPhone.replaceAll("-", "");
        if (sPhone.length() > 10) {
            sPhone = sPhone.substring(0, 10);
        }
        if (!sPhone.equals("")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + sPhone));
            activity.startActivity(intent);
        }
    }

    public static void shareImage(Activity activity, String sPDFFullPath, String sText) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sPDFFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(sPDFFullPath));
                        }
                        Log.e("myURI", "+" + myURI);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpg");
//                            share.setDataAndType(myURI, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, myURI);
                        if (!sText.equals("")) {
                            share.putExtra(Intent.EXTRA_TEXT, sText);
                        }
//                            share.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(Intent.createChooser(share, "Send"));


                    });
        } catch (Exception e) {

        }
    }

    public static void shareImageWhatsappPhone(Activity activity, String sPDFFullPath, String sText, String sPhone) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sPDFFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(sPDFFullPath));
                        }
                        Log.e("myURI", "+" + myURI);
                        Intent share = new Intent(Intent.ACTION_SEND);

                        share.setType("image/jpg");
                        if (!sPhone.equals("")) {
                            share.putExtra("jid", sPhone);
                        }
                        boolean isWhatsappBussiness = appInstalledOrNot(activity, "com.whatsapp.w4b");
                        if (isWhatsappBussiness) {
                            share.setPackage("com.whatsapp.w4b");
                        } else {
                            boolean isWhatsapp = appInstalledOrNot(activity, "com.whatsapp");
                            if (isWhatsapp) {
                                share.setPackage("com.whatsapp");
                            }
                        }

//                            share.setDataAndType(myURI, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, myURI);
                        if (!sText.equals("")) {
                            share.putExtra(Intent.EXTRA_TEXT, sText);
                        }
//                            share.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(Intent.createChooser(share, "Send"));


                    });
        } catch (Exception e) {

        }
    }

    public static void shareImageWhatsappPhone2(Activity activity, String sPDFFullPath, String sText, String sPhone) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sPDFFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(sPDFFullPath));
                        }
                        Log.e("myURI", "+" + myURI);
//                        Intent share = new Intent(Intent.ACTION_SEND);
                        Intent share = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + sPhone + "?body=" + ""));


                        share.setType("image/jpg");
//                        if (!sPhone.equals("")){
//                            try {
//                                String  url = "https://api.whatsapp.com/send?phone="+ sPhone +"&text=" + URLEncoder.encode(sText, "UTF-8");
////                                share = new Intent(Intent.ACTION_VIEW);
////                                share.setType("image/jpg");
//                                share.setData(Uri.parse(url));
//                            } catch (UnsupportedEncodingException e) {
//                            }
//                        }else{
//                        }


                        if (!sText.equals("")) {
                            share.putExtra(Intent.EXTRA_TEXT, sText);
                        }
                        boolean isWhatsappBussiness = appInstalledOrNot(activity, "com.whatsapp.w4b");
                        if (isWhatsappBussiness) {
                            share.setPackage("com.whatsapp.w4b");
                        } else {
                            boolean isWhatsapp = appInstalledOrNot(activity, "com.whatsapp");
                            if (isWhatsapp) {
                                share.setPackage("com.whatsapp");
                            }
                        }

//                            share.setDataAndType(myURI, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, myURI);

//                            share.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(Intent.createChooser(share, "Send"));


                    });
        } catch (Exception e) {

        }
    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static void sharePDF(Activity activity, String sPDFFullPath) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sPDFFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(sPDFFullPath));
                        }
                        Log.e("myURI", "+" + myURI);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("application/pdf");
//                            share.setDataAndType(myURI, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, myURI);
//                            share.putExtra(Intent.EXTRA_TEXT, "ABC");
//                            share.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(Intent.createChooser(share, "Send"));


                    });
        } catch (Exception e) {

        }
    }

    public static void shareFile(Activity activity, String sFullPath) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(sFullPath));
                        }
                        Log.e("myURI", "+" + myURI);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("*/*");
//                            share.setDataAndType(myURI, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, myURI);
//                            share.putExtra(Intent.EXTRA_TEXT, "ABC");
//                            share.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(Intent.createChooser(share, "Send"));


                    });
        } catch (Exception e) {

        }
    }

    public static void shareText(Activity activity, String sText) {
        try {

            Intent txtIntent = new Intent(Intent.ACTION_SEND);
            txtIntent.setType("text/plain");
            txtIntent.putExtra(Intent.EXTRA_TEXT, sText);
            activity.startActivity(Intent.createChooser(txtIntent, "Share Via"));
        } catch (Exception e) {

        }
    }

    public static void openPDF(Activity activity, String sPDFFullPath) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            MediaScannerConnection.scanFile(activity,
                    new String[]{sPDFFullPath}, null,
                    (path, uri) -> {

                        Uri myURI = uri;
                        String sURL = "" + uri;
                        if (sURL.equals("") && sURL.toLowerCase().equals("null")) {
                            myURI = Uri.fromFile(new File(path));
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(myURI, "application/pdf");
                        intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(intent);
                    });
        } catch (Exception e) {

        }
    }

    public static Drawable changeDrawableColor(Context context, int icon, String color) {
        String sColor = URLString.isNull("" + color);
        if (sColor.length() < 6) {
            sColor = "#f61b1f";
        }
        if (!sColor.contains("#")) {
            sColor = "#" + sColor;
        }
        int iColor = Color.parseColor(sColor);
        Drawable mDrawable = context.getResources().getDrawable(icon).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(iColor, PorterDuff.Mode.SRC_IN));
        return mDrawable;
    }


    public static boolean isDeviceRooted() {
        if (checkRootMethod1()) {
            return true;
        } else if (checkRootMethod2()) {
            return true;
        } else if (checkRootMethod3()) {
            return true;
        } else if (Debug.isDebuggerConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isvm() {


        StringBuilder deviceInfo = new StringBuilder();
        deviceInfo.append("Build.PRODUCT " + Build.PRODUCT + "\n");
        deviceInfo.append("Build.FINGERPRINT " + Build.FINGERPRINT + "\n");
        deviceInfo.append("Build.MANUFACTURER " + Build.MANUFACTURER + "\n");
        deviceInfo.append("Build.MODEL " + Build.MODEL + "\n");
        deviceInfo.append("Build.BRAND " + Build.BRAND + "\n");
        deviceInfo.append("Build.DEVICE " + Build.DEVICE + "\n");
        String info = deviceInfo.toString();


        Log.i("LOB", info);


        Boolean isvm = false;
        if (
                "google_sdk".equals(Build.PRODUCT) ||
                        "sdk_google_phone_x86".equals(Build.PRODUCT) ||
                        "sdk".equals(Build.PRODUCT) ||
                        "sdk_x86".equals(Build.PRODUCT) ||
                        "vbox86p".equals(Build.PRODUCT) ||
                        Build.FINGERPRINT.toLowerCase().contains("generic") ||
                        Build.MANUFACTURER.contains("Genymotion") ||
                        Build.MODEL.contains("Emulator") ||
                        Build.MODEL.contains("Android SDK built for x86")
        ) {
            isvm = true;
        }

        if (!isvm) {
            if (Build.BRAND.contains("generic") && Build.DEVICE.contains("generic")) {
                isvm = true;
            }
        }

        if (!isvm) {
            if ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                    || Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.HARDWARE.contains("goldfish")
                    || Build.HARDWARE.contains("ranchu")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || Build.PRODUCT.contains("sdk_google")
                    || Build.PRODUCT.contains("google_sdk")
                    || Build.PRODUCT.contains("sdk")
                    || Build.PRODUCT.toLowerCase().contains("nox")
                    || Build.BOARD.toLowerCase().contains("nox")
                    || Build.PRODUCT.contains("sdk_x86")
                    || Build.PRODUCT.contains("sdk_gphone64_arm64")
                    || Build.PRODUCT.contains("vbox86p")
                    || Build.PRODUCT.contains("emulator")
                    || Build.PRODUCT.contains("simulator")) {
                isvm = true;
            }
        }


        return isvm;
    }

    public static String file2Text(Context context, File file) {
        String content = "";
        if (file.exists()) {
            try {
                InputStream inputStream = new FileInputStream(file);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                content = new String(buffer, "UTF-8");
            } catch (FileNotFoundException e) {
            } catch (IOException ex) {
            }
        } else {
            ToastMsg.mToastMsg(context, "File not found.", 1);
        }
        return content;

    }

    public static ArrayList<String> splitCommaArray(String s) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] sArray = s.split(",");
        for (int i = 0; i < sArray.length; i++) {
            String s1 = isNull(sArray[i]).trim();
            s1 = s1.replaceAll(",", "");
            arrayList.add(s1);
        }
        if (arrayList.size() > 0) {
            String s1 = arrayList.get(arrayList.size() - 1);
            if (s1.equals("")) {
                arrayList.remove(arrayList.size() - 1);
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

    public static ArrayList<String> string2Array(String sText, String sSplitBy) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] sArray = sText.split(sSplitBy);
        for (int i = 0; i < sArray.length; i++) {
            String s1 = isNull(sArray[i]);
            s1 = s1.replaceAll("\\\\n", "");
            s1 = s1.replaceAll("\n", "");
            s1 = s1.replaceAll("\r", "");
            s1 = s1.replaceAll("\t", "");
            arrayList.add(s1);
        }
        if (arrayList.size() > 0) {
            String s1 = arrayList.get(arrayList.size() - 1);
            if (s1.equals("")) {
                arrayList.remove(arrayList.size() - 1);
            }
        }
        return arrayList;
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
                "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}
