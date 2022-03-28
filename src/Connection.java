import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Connection {

    public Connection() {

    }

    public String makeGETRequest (String urlName){
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String parseJSON (String jsonString, String key){
        StringBuilder var = new StringBuilder();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                var.append(curObject.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return var.toString();
    }

    public ArrayList<Integer> parseJSONtoList (String jsonString, String key){
        ArrayList<Integer> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                list.add(curObject.getInt(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String parseJSONputdatain (String jsonString){
        String var = "";
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                var += curObject.getString("Password" + ", ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return var;
    }

    public ArrayList<String> parseJSONAlarm (String jsonString){
        ArrayList<String> var = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                var.add(curObject.getString("alarm_datetime"));
                var.add(curObject.getString("temp"));
                var.add(String.valueOf(curObject.getInt("volume")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return var;
    }
}
