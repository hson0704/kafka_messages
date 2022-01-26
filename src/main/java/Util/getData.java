package Util;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class getData {
    private static final String LINK = "https://cdnvda.mhsolution.vn/downloadFile/comment.txt";

    public static List<JSONObject> readData() {
        List<JSONObject> ob = new ArrayList<>();

        try {
            //Khoi tao ket noi
            URL dataURL = new URL(LINK);
            URLConnection dc = dataURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream()));

            //Doc du lieu
            String data;
            while((data = in.readLine()) != null) {
                ob.add(new JSONObject(data));
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ob;
    }


}
