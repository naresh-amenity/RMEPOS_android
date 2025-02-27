package com.rme.pos;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import com.rme.roomdb.DeliveryTable;
import com.rme.roomdb.OrderTable;
import com.rme.roomdb.ProductTable;
import com.rme.roomdb.StockCountTable;
import com.rme.roomdb.TransferDispatchTable;
import com.rme.roomdb.TransferReceiptTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.URLString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class AndroidWebServer extends NanoHTTPD {
    Context context;

    List<String> deliveryArray = new ArrayList<>();
    List<String> orderArray = new ArrayList<>();
    List<String> productArray = new ArrayList<>();
    List<String> stockArray = new ArrayList<>();
    List<String> transferDispatchArray = new ArrayList<>();
    List<String> transferReceiptArray = new ArrayList<>();

    String msg = "";
    private onSelectListener onSelectListener;
    public interface onSelectListener {
        public void onFileStored(String sFilePath);


    }

    public AndroidWebServer(Context context, int port, onSelectListener onSelectListener) {
        super(port);
        this.context = context;
        this.onSelectListener = onSelectListener;
    }

    public AndroidWebServer(Context context, String hostname, int port, onSelectListener onSelectListener) {
        super(hostname, port);
        this.onSelectListener = onSelectListener;
    }

    public void setData(List<String> deliveryArray, List<String> orderArray, List<String> productArray, List<String> stockArray, List<String> transferDispatchArray, List<String> transferReceiptArray) {
        this.deliveryArray = deliveryArray;
        this.orderArray = orderArray;
        this.productArray = productArray;
        this.stockArray = stockArray;
        this.transferDispatchArray = transferDispatchArray;
        this.transferReceiptArray = transferReceiptArray;
    }

    //    public void setMsgData(LinkedHashMap<String, ArrayList<P_SMS>> smsMapArray) {
//        this.smsMapArray = smsMapArray;
//    }
    void writeStreamToFile(InputStream input) {

        File file = new File(URLString.getDownloadVisiblePath(context), "PLU.EXP");
        if (file.exists()) {
            file.delete();
        }
        Log.e("file", "=" + file.getAbsoluteFile());
        try {
            try (OutputStream output = new FileOutputStream(file)) {
                Log.e("file2", "=" + file.getAbsoluteFile());
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                Log.e("file3", "=" + file.getAbsoluteFile());
                int read;
                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
                output.flush();
                Log.e("Success", "=" + file.getAbsoluteFile());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Response post_upload(IHTTPSession session) {
        System.out.println("received HTTP post with upload body...");
        int streamLength = Integer.parseInt(session.getHeaders().get("content-length"));
        System.out.println("Content length is: " + streamLength);
        byte[] fileContent = new byte[streamLength];
        try {
            InputStream input = session.getInputStream();
            int bytesRead = 0;
            int iterations = 0;
            while (bytesRead < streamLength) {
                int thisRead = input.read(fileContent, bytesRead, streamLength - bytesRead);
                bytesRead += thisRead;
                iterations++;
            }
            System.out.println("Read " + bytesRead + " bytes in " + iterations + " iterations.");
        } catch (Exception e) {
            System.out.println("We have other problems...");
        }
        return newFixedLengthResponse(Response.Status.OK, "application/json", "[\"no prob\"]");
    }

    public String copyFile(File src) throws IOException {

        File dst = new File(URLString.getDownloadVisiblePath(context), "PLU.EXP");
        FileInputStream var2 = new FileInputStream(src);
        FileOutputStream var3 = new FileOutputStream(dst);
        byte[] var4 = new byte[1024 * 1024];

        int var5;
        while ((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
        }

        var2.close();
        var3.close();
        return dst.getAbsolutePath();
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.e("getParameters", "=" + session.getParameters());
        if (session.getMethod() == Method.POST) {
            Map<String, String> files = new HashMap<String, String>();
            if (Method.POST.equals(session.getMethod()) || Method.PUT.equals(session.getMethod())) {
                Log.d("server", "inside receive file!");
                try {
                    session.parseBody(files);
                } catch (Exception e) {
                    Log.d("server", "error on parseBody" + e.toString());
                }
                if (files != null) {
                    if (files.containsKey("file")) {
                        File file = new File(files.get("file"));
                        if (file.exists()) {
                            try {
                               String sFileDest = copyFile(file);
                                onSelectListener.onFileStored(sFileDest);
                            } catch (IOException e) {
                            }
                        }
                    }
                }


            }
//           String extraData = session.getParms().get("extra_data");
//            Log.e("extraData", "=" + extraData);
//            writeStreamToFile(session.getInputStream());
            return newFixedLengthResponse(msg);
//            return post_upload(session);
        } else {

//        Map<String, List<String>> parameters = session.getParameters();
//        if (parameters.containsKey("listSelect")) {
//            String sListSelect = parameters.get("listSelect").get(0);
//            iSelectedMsg = Integer.parseInt(sListSelect);
//        }
            msg = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <title>RMePos</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: Arial, sans-serif;\n" +
                    "      background-color: #f5f5f5;\n" +
                    "      display: flex;\n" +
                    "      justify-content: center;\n" +
                    "      align-items: center;\n" +
                    "      height: 100vh;\n" +
                    "      margin: 0;\n" +
                    "    }\n" +
                    "\n" +
                    "    .button-container {\n" +
                    "      display: flex;\n" +
                    "      flex-direction: column;\n" +
                    "      gap: 10px;\n" +
                    "    }\n" +
                    "\n" +
                    "    button {\n" +
                    "      width: 100%;\n" +
                    "      max-width: 300px;\n" +
                    "      padding: 15px;\n" +
                    "      font-size: 16px;\n" +
                    "      font-weight: bold;\n" +
                    "      color: #67B7D0;\n" +
                    "      background-color: #135C7C;\n" +
                    "      border: none;\n" +
                    "      border-radius: 5px;\n" +
                    "      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);\n" +
                    "      cursor: pointer;\n" +
                    "    }\n" +
                    "\n" +
                    "    button:hover {\n" +
                    "      background-color: #0f4864;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"button-container\">\n" +
                    "    <h3 style=\"color:#135C7C;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Download Files</h3>" +
                    "    <button onclick=\"handleButtonClick('PRICEDAT')\">Price Maintenance</button>\n" +
                    "    <button onclick=\"handleButtonClick('STOCKDAT')\">Stock Take</button>\n" +
                    "    <button onclick=\"handleButtonClick('ORDERS')\">Orders</button>\n" +
                    "    <button onclick=\"handleButtonClick('DELIVRS')\">Deliveries</button>\n" +
                    "    <button onclick=\"handleButtonClick('TRANSDSP')\">Transfer Dispatch</button>\n" +
                    "    <button onclick=\"handleButtonClick('TRANSRCT')\">Transfer Receipt</button>\n" +
                    "<form method=\"post\" enctype=\"multipart/form-data\" action=\"?\">\n" +
                    "  <div class=\"button-container\">\n" +
                    "  &nbsp;\n" +
                    "  <label style=\"color:#135C7C;\">Upload PLU File</label>\n" +
                    "    <input type=\"file\" name=\"file\" />\n" +
                    "    <input type=\"submit\" value=\"Send To Device\" />\n" +
                    "    &nbsp;\n" +
                    "  </div>\n" +
                    "</form>" +
                    "  </div>\n" +
                    "\n" +
                    "  <script>\n" +
                    "    // Arrays for each button\n" +
                    "    const data = {\n";
            msg = msg + "      \"PRICEDAT\": [";
            if (!productArray.isEmpty()) {
                for (int i = 0; i < productArray.size(); i++) {
                    msg = msg + "\"" + productArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";

            msg = msg + "      \"STOCKDAT\": [";
            if (!stockArray.isEmpty()) {
                for (int i = 0; i < stockArray.size(); i++) {
                    msg = msg + "\"" + stockArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";

            msg = msg + "      \"ORDERS\": [";
            if (!orderArray.isEmpty()) {
                for (int i = 0; i < orderArray.size(); i++) {
                    msg = msg + "\"" + orderArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";

            msg = msg + "      \"DELIVRS\": [";
            if (!deliveryArray.isEmpty()) {
                for (int i = 0; i < deliveryArray.size(); i++) {
                    msg = msg + "\"" + deliveryArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";

            msg = msg + "      \"TRANSDSP\": [";
            if (!transferDispatchArray.isEmpty()) {
                for (int i = 0; i < transferDispatchArray.size(); i++) {
                    msg = msg + "\"" + transferDispatchArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";

            msg = msg + "      \"TRANSRCT\": [";
            if (!transferReceiptArray.isEmpty()) {
                for (int i = 0; i < transferReceiptArray.size(); i++) {
                    msg = msg + "\"" + transferReceiptArray.get(i) + "\",";
                }
            }
            msg = msg + " ";
            msg = msg + "],\n";


            msg = msg + "    };\n" +
                    "\n" +
                    "    // Function to handle button click\n" +
                    "    function handleButtonClick(buttonName) {\n" +
                    "      const textArray = data[buttonName];\n" +
                    "      if (!textArray) {\n" +
                    "        alert(\"No data found for this button.\");\n" +
                    "        return;\n" +
                    "      }\n" +
                    "\n" +
                    "      // Create content for the txt file\n" +
                    "      const fileContent = textArray.join(\"\\r\\n\");\n" +
                    "\n" +
                    "      // Create a Blob with the content\n" +
                    "      const blob = new Blob([fileContent], { type: \"text/plain\" });\n" +
                    "\n" +
                    "      // Create a link to download the file\n" +
                    "      const link = document.createElement(\"a\");\n" +
                    "      link.href = URL.createObjectURL(blob);\n" +
                    "      link.download = `${buttonName}.IMP`;\n" +
                    "\n" +
                    "      // Trigger the download\n" +
                    "      link.click();\n" +
                    "\n" +
                    "      // Clean up the URL object\n" +
                    "      URL.revokeObjectURL(link.href);\n" +
                    "    }\n" +
                    "  </script>\n" +
                    "</body>\n" +
                    "</html>";


            return newFixedLengthResponse(msg);
        }

    }

    public String escape(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}
