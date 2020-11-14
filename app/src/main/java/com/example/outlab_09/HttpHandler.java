package com.example.outlab_09;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    // Empty constructor
    public HttpHandler() {}

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Th following line is important!
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.connect();
            // Status code:
            int temp = conn.getResponseCode();
            Log.d("code", Integer.toString(temp));
            // The response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reader.close();
            response = sb.toString();
        }
        catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        }
        catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        }
        catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }
        catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
}
