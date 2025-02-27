package com.rme.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class JsonData {

    public static String Data(String sResult) {
        String s = "";
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("Data")) {
                s = jsonObject.getString("Data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        s = s.replaceAll("\"NA\"", "\"\"");
        s = s.replaceAll("\"null\"", "\"\"");
        return s;
    }

    public static String Message(String sResult) {
        String s = "";
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("Message")) {
                s = jsonObject.getString("Message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int CurrentUserID(String sResult) {
        int s = 0;
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("CurrentUserID")) {
                s = jsonObject.getInt("CurrentUserID");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int Code(String sResult) {
        int s = 0;
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("Code")) {
                String sss = URLString.isNull(jsonObject.get("Code"));
                s = URLString.getInteger(sss);
//                if (sss.equals("")) {
//                    if (IsSuccess(sResult)) {
//                        s = 1;
//                    }else{
//                        s = 0;
//                    }
//                }else{
//                }
            }
        } catch (JSONException e) {
            s = 0;
        }
        return s;
    }

    public static boolean IsData(String sResult) {
        boolean isData = false;
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("Data")) {
                isData = true;
            }
        } catch (JSONException e) {
            isData = false;
        }
        return isData;
    }

    public static boolean IsSuccess(String sResult) {
        boolean s = false;
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            if (jsonObject.has("IsSuccess")) {
                s = jsonObject.getBoolean("IsSuccess");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }


    public static ArrayList<HashMap<String, String>> getArray(String Result) {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(Result);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobjData = jsonarray
                        .getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                Iterator<String> iter = jsonobjData.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        Object value = jsonobjData.get(key);
                        map.put(key, value.toString());
                    } catch (JSONException e) {
                        // Something went wrong!
                    }
                }
                array.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<HashMap<String, Object>> getArrayAsObject(String Result) {
        ArrayList<HashMap<String, Object>> array = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(Result);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobjData = jsonarray.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<String, Object>();
                Iterator<String> iter = jsonobjData.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        map.put(key, jsonobjData.get(key));
                    } catch (JSONException e) {
                        // Something went wrong!
                    }
                }
                array.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<HashMap<String, String>> getArray(String Result, String obj) {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
                                map.put(key, value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<HashMap<String, Object>> getArrayAsObject(String Result, String obj) {
        ArrayList<HashMap<String, Object>> array = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject.getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray.getJSONObject(i);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                map.put(key, jsonobjData.get(key));
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static HashMap<String, Object> getObjectAsObject(String Result, String obj) {
        HashMap<String, Object> object = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {

                    JSONObject jsonObject2 = jsonObject.getJSONObject(obj);
                    Iterator<String> iter = jsonObject2.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            object.put(key, jsonObject2.get(key));
                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return object;
    }
    public static HashMap<String, Object> getObjectAsObject(JSONObject jsonObject) {
        HashMap<String, Object> object = new HashMap<>();
        try {
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    object.put(key, jsonObject.get(key));
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return object;
    }
    public static HashMap<String, Object> getObjectAsObject(String sResult) {
        HashMap<String, Object> object = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(sResult);
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    object.put(key, jsonObject.get(key));
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return object;
    }

}
