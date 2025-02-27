package com.rme.utils;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*
 * Created by Raj on 4/4/2015.
 * this class use for converting Date Format
 */
public class ConvertDate {

    public static String yyTOdd(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy/MM/dd",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String yyTOdd2(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }
    public static String ymdTOdmmmy(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String yyTOdd3(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getDateToYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        return sdf.format(date);
    }

    public static String getDayByint(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return dateFormat.format(calendar.getTime());
    }

    public static Date getDateByInt(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static String getDMYByInt(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return dateFormat.format(calendar.getTime());
    }

    public static String addDays(Date date, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return dateFormat.format(calendar.getTime());
    }

    public static String yyTOdd2S(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String yyHHTOddhh(String dt) {
        try {

            String s = dt;

            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String HHTOhh(String dt) {
        try {

            String s = dt;

            SimpleDateFormat Oldformat = new SimpleDateFormat("HH:mm:ss",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("hh:mm a",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static Date getDateFromHH(String HHmmss) {
        Date date = new Date();
        try {
            SimpleDateFormat Oldformat = new SimpleDateFormat("HH:mm:ss",Locale.US);
            try {
                date = Oldformat.parse(HHmmss);
            } catch (ParseException e) {
            }
            return date;
        } catch (Exception e) {
            // TODO: handle exception
            return date;
        }
    }

    public static String DTToDD(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String DMY2DM(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
            DateFormat newFormat = new SimpleDateFormat("dd/MM",Locale.US);

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOyy(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy/MM/dd",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOmdy(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("MM/dd/yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String mmyy2MMMyyyy(String dt) {
        try {
            String s = dt;

            SimpleDateFormat oldFormate = new SimpleDateFormat("MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("MMM-yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String mmyy2yyyy(String dt) {
        try {
            String s = dt;

            SimpleDateFormat oldFormate = new SimpleDateFormat("MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOyy2(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOyy2S(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String sameYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String YMDToYM(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String sameDMY(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String sameDMY2(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }


    public static String GetTime(String ddmmyyyy) {
        try {
            String s = "";
            s = ddmmyyyy.substring(ddmmyyyy.indexOf(" "), ddmmyyyy.length());
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String MiliToDT(String MiliSec) {
        long milliSeconds = Long.parseLong(MiliSec);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a",Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    public static Date MiliToDate(long MiliSec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(MiliSec);
        return calendar.getTime();

    }

    public static String MiliToDT2(long MiliSec) {
        SimpleDateFormat formatter = null;
        Calendar calendar = null;
        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a",Locale.US);
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(MiliSec);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return formatter.format(calendar.getTime());

    }

    public static String YMD_TO_DMY(String dt) {

        SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a",Locale.US);
        DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a",Locale.US);
        String s = "";
        try {
            s = newFormat.format(Oldformat.parse(s));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            s = dt;
        }
        return s;

    }

    public static String YMD_TO_DMY2(String dt) {

        SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
        String s = "";
        try {
            s = newFormat.format(Oldformat.parse(s));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            s = dt;
        }
        return s;

    }

    public static String ConvertDuration(int duration) {

        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (duration / (1000 * 60 * 60));
        int minutes = (int) (duration % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }


        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;

    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static String calDate(String LastDate) {
        String s = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US);
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",Locale.US);
        DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale.US);

        //get current date time with Date()
        Date date = new Date();
        System.out.println();
        String dateStart = dateFormat.format(date);
        String dateStop = LastDate;

        //HH converts hour in 24 hours format (0-23), day calculation

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormat.parse(dateStart);

            boolean isAcc = false;
            if (!isAcc) {
                try {
                    d2 = dateFormat.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 1", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat2.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 2", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat3.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 3", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat4.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 4", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 5", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat2.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 6", "" + e.toString());

                }
            }

            if (!isAcc) {
                try {
                    d2 = dateFormat3.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 7", "" + e.toString());

                }
            }

            if (!isAcc) {
                try {
                    d2 = dateFormat4.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 8", "" + e.toString());

                }
            }


            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                if (s.equals("")) {
                    s = "" + diffDays + "d";
                } else {
                    s = s + " " + diffDays + "d";
                }
            }

            if (diffHours > 0) {
                if (s.equals("")) {
                    s = "" + diffHours + "h";
                } else {
                    s = s + " " + diffHours + "h";
                }
            }
            if (diffMinutes > 0) {
                if (s.equals("")) {
                    s = "" + diffMinutes + "m";
                } else {
                    s = s + " " + diffMinutes + "m";
                }
            }

//            Log.e("diffDays", "-" + diffDays);
//            Log.e("diffHours", "-" + diffHours);
//            Log.e("diffMinutes", "-" + diffMinutes);
//            Log.e("diffSeconds", "-" + diffSeconds);

        } catch (ParseException e) {
            s = "";
            e.printStackTrace();
        }

        return s;
    }

    public static String getDateToDMY(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            return sdf.format(date);
        }
    }

//    public static String getDateToDMMMY(Date date) {
//        if (date == null) {
//            return "";
//        } else {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
//
//            return sdf.format(date);
//        }
//    }
    public static String getDateToDMMMYFormat(Date date) {
        if (date == null) {
            return "";
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            //2nd of march 2015
            int day = cal.get(Calendar.DATE);
            if (!((day > 10) && (day < 19)))
                switch (day % 10) {
                    case 1:
                        return new SimpleDateFormat("d'st' MMMM yyyy").format(date);
                    case 2:
                        return new SimpleDateFormat("d'nd' MMMM yyyy").format(date);
                    case 3:
                        return new SimpleDateFormat("d'rd' MMMM yyyy").format(date);
                    default:
                        return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
                }
            return new SimpleDateFormat("d'th' MMMM yyyy").format(date);

        }
    }



    public static String getDateToDMMMYNULL(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
            return sdf.format(date);
        }
    }
    public static String getDateToMMMY(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy",Locale.US);
            return sdf.format(date);
        }
    }

    public static String getMonthYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy",Locale.US);
        return sdf.format(date);
    }

    public static String getDateToDMY2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
        return sdf.format(date);
    }

    public static String getDateToDMMMYhmsa(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a",Locale.US);
        return sdf.format(date);
    }
    public static String getDateTohma(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a",Locale.US);
        return sdf.format(date);
    }

    public static String getDateToDMYhmsa(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
        return sdf.format(date);
    }

    public static String getCurrentDateTime24() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrentDateTime12() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
        return sdf.format(new Date());
    }

    public static String getDateStringForWeb(Date date) {
        if (date==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        String sDate = sdf.format(date) + "+05:30";
        sDate = sDate.replace(" ", "T");
        return sDate;
    }
    public static String getDateFromddMMMyyyHHmmss(Date date) {
        if (date==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.US);
        String sDate = sdf.format(date);
        return sDate;
    }

    public static String getDiffrent(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long numOfDays = different / daysInMilli;
        different = different % daysInMilli;

        long hours = different / hoursInMilli;
        different = different % hoursInMilli;

        long minutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long seconds = different / secondsInMilli;


        String s = "", sDays = "", sHours = "", sMinutes = "", sSeconds = "";

        if (numOfDays > 0) {
            if (numOfDays == 1) {
                sDays = numOfDays + " Day";
            } else {
                sDays = numOfDays + " Days";
            }
        }
        if (hours > 0) {
            if (hours == 1) {
                sHours = hours + " Hour";
            } else {
                sHours = hours + " Hours";
            }
        }
        if (minutes > 0) {
            if (minutes == 1) {
                sMinutes = minutes + " Min";
            } else {
                sMinutes = minutes + " Min";
            }
        }
        if (seconds > 0) {
            if (seconds == 1) {
                sSeconds = seconds + " Sec";
            } else {
                sSeconds = seconds + " Sec";
            }
        }

        if (!sDays.equals("")) {
            if (s.equals("")) {
                s = sDays;
            } else {
                s = s + ", " + sDays;
            }
        }
        if (!sHours.equals("")) {
            if (s.equals("")) {
                s = sHours;
            } else {
                s = s + ", " + sHours;
            }
        }
        if (!sMinutes.equals("")) {
            if (s.equals("")) {
                s = sMinutes;
            } else {
                s = s + ", " + sMinutes;
            }
        }
        if (!sSeconds.equals("")) {
            if (s.equals("")) {
                s = sSeconds;
            } else {
                s = s + ", " + sSeconds;
            }
        }
        return s;
    }

    public static String getdmyhmsFromWeb(String ymdHmssss) {
        ymdHmssss = ymdHmssss.replace("T", " ");
        if (!ymdHmssss.equals("")&&!ymdHmssss.contains(".")){
            ymdHmssss = ymdHmssss+".000";
        }
        SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        DateFormat newFormate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
        String sDate = "";
        try {
            sDate = newFormate.format(oldFormate.parse(ymdHmssss));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sDate;
    }

    public static String getdmmyhmsFromWeb(String ymdHmssss) {
        ymdHmssss = ymdHmssss.replace("T", " ");
        if (!ymdHmssss.equals("")&&!ymdHmssss.contains(".")){
            ymdHmssss = ymdHmssss+".000";
        }
        SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        DateFormat newFormate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a",Locale.US);
        String sDate = "";
        try {
            sDate = newFormate.format(oldFormate.parse(ymdHmssss));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sDate;
    }

    public static String getdmyFromWeb(String ymdHmssss) {
        ymdHmssss = ymdHmssss.replace("T", " ");
        if (!ymdHmssss.equals("")&&!ymdHmssss.contains(".")){
            ymdHmssss = ymdHmssss+".000";
        }
        SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        DateFormat newFormate = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        String sDate = "";
        try {
            sDate = newFormate.format(oldFormate.parse(ymdHmssss));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sDate;
    }

    public static String getdmmyFromWeb(String ymdHmssss) {
        if (ymdHmssss.equals("")||ymdHmssss.toLowerCase(Locale.ROOT).equals("null")||ymdHmssss.contains("null")){
            return "";
        }
        ymdHmssss = ymdHmssss.replace("T", " ");
        ymdHmssss = ymdHmssss.replace("Z", "");
        if (!ymdHmssss.equals("")&&!ymdHmssss.contains(".")){
            ymdHmssss = ymdHmssss+".000";
        }
        SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        DateFormat newFormate = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        String sDate = "";
        try {
            sDate = newFormate.format(oldFormate.parse(ymdHmssss));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sDate;
    }

    public static Date getDateFromWeb(String ymdHmssss) {
        if (ymdHmssss.equals("")||ymdHmssss.toLowerCase(Locale.ROOT).equals("null")||ymdHmssss.contains("null")){
            return null;
        }
        ymdHmssss = ymdHmssss.replace("T", " ");
        if (!ymdHmssss.equals("")&&!ymdHmssss.contains(".")){
            ymdHmssss = ymdHmssss+".000";
        }
        SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);

        Date date = null;
        try {
            date = oldFormate.parse(ymdHmssss);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String getTimeStamp() {
        String utcTime = null;
        long lTimeStamp = new Date().getTime();
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            utcTime = sdf.format(new Date());
            Date date = sdf.parse(utcTime);
            lTimeStamp = date.getTime() / 1000;
        } catch (ParseException e) {
            lTimeStamp = new Date().getTime();
        }
        return "" + lTimeStamp;
    }

    public static String getCurrentUTC() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHss",Locale.US);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHHss",Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        utcTime = utcTime.replace("-", "18");
        return utcTime;
    }

    public static String getCurrendDateTimeYYMMDDHHMMSS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrendDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        return sdf.format(new Date());
    }
    public static String getCurrendDateMMM() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrentDateYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrentDateYMD2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy",Locale.US);
        return sdf.format(new Date());
    }

    public static String getCurrentMonthYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy",Locale.US);
        return sdf.format(new Date());
    }

    public static String getMonthYear2Year(String MMMyyyy) {
        SimpleDateFormat old = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        DateFormat newFormat = new SimpleDateFormat("yyyy",Locale.US);
        String dmy = "01-" + MMMyyyy;
        Log.e("dmy", "=" + dmy);
        String s = "";
        try {
            s = newFormat.format(old.parse(dmy));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int getLastDayOfMonth(int iMonth,int iYear) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, iYear);
        calendar.set(Calendar.MONTH, iMonth-1);
        return calendar.getActualMaximum(Calendar.DATE);
//                                Log.e("numDays","="+numDays);
//        SimpleDateFormat old = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
//        String dmy = "01-" + MMMyyyy;
//        Log.e("dmy", "=" + dmy);
//        Date dFirstDay = new Date();
//        try {
//            dFirstDay = old.parse(dmy);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(dFirstDay);
//        calendar.add(Calendar.MONTH, 1);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.add(Calendar.DATE, -1);
//        Log.e("dFirstDay", "=" + dFirstDay);
//        Date lastDayOfMonth = calendar.getTime();
//        Log.e("lastDayOfMonth", "=" + lastDayOfMonth);
//
//        DateFormat sdf = new SimpleDateFormat("dd",Locale.US);
//        int iDay = 0;
//        try {
//            iDay = Integer.parseInt(sdf.format(lastDayOfMonth));
//        } catch (NumberFormatException e) {
//        }
//        return iDay;
    }

    public static String getPreviousMonthYear(String MMMyyyy) {
        SimpleDateFormat old = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        DateFormat newFormat = new SimpleDateFormat("MMM-yyyy",Locale.US);
        String dmy = "01-" + MMMyyyy;
        Log.e("dmy", "=" + dmy);
        String s = "";

        Date d = new Date();
        try {
            d = old.parse(dmy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, -1);
        return newFormat.format(cal.getTime());
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM",Locale.US);
        return sdf.format(new Date());
    }

    public static int getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd",Locale.US);
        String sDay = sdf.format(new Date());
        int m = 0;

        try {
            m = Integer.parseInt(sDay);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return m;
    }

    public static String getCurrentTimeHHmmss() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("HH:mm:ss",Locale.US);
        return Oldformat.format(new Date());

    }

    public static String getCurrentTime() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("hh:mm:ss a",Locale.US);
        return Oldformat.format(new Date());

    }
    public static String getCurrentTimehhmma() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("hh:mm a",Locale.US);
        return Oldformat.format(new Date());

    }

    public static String GetDayInString(String yyyymmdd) {
        try {

            String s = "";
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            s = dayFormat.format(oldFormate.parseObject(yyyymmdd));
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getDayFromYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("dd",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getMonthFromYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            DateFormat newFormate = new SimpleDateFormat("MMMM-yyyy",Locale.US);

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static Date getDateFromDMY(String strDMY) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        Date date = null;
        try {
            date = sdf.parse(strDMY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
    public static Date getDateFromDMMMY(String strDMY) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        Date date = null;
        try {
            date = sdf.parse(strDMY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateFromDMYHms(String strDMYHms) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US);
        Date date = null;
        try {
            date = sdf.parse(strDMYHms);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateFromDMYhma(String sDMYhma) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.US);
        Date date = null;
        try {
            date = sdf.parse(sDMYhma);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateFromYMDTHms(String YMDTHms) {
        YMDTHms = YMDTHms.replace("T", " ");
        if (!YMDTHms.equals("")&&!YMDTHms.contains(".")){
            YMDTHms = YMDTHms+".000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.US);
        Date date = null;
        if (!YMDTHms.equals("") && !YMDTHms.toLowerCase(Locale.ROOT).equals("null")&& !YMDTHms.contains("null")) {
            try {
                date = sdf.parse(YMDTHms);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;

    }

    public static Date getDateFromYMD(String sYMD) {
        String s = sYMD;
        try {
            s = sYMD.substring(0, sYMD.lastIndexOf(" "));
        } catch (Exception e) {
            // TODO: handle exception
            s = sYMD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
