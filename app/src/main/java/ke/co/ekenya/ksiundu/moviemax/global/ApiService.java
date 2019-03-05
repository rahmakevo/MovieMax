package ke.co.ekenya.ksiundu.moviemax.global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiService {
    public JSONArray getMovieResults(){
        JSONArray mResults = new JSONArray();
        HttpsURLConnection mConn;
        try {
            String url = "https://api.themoviedb.org/3/trending/all/day?api_key=726c1648f49672fa8c4302a2db524ff6";
            mConn = (HttpsURLConnection) (new URL(url)).openConnection();
            mConn.setRequestMethod("GET");
            mConn.connect();

            BufferedReader mReader = new BufferedReader(new InputStreamReader(mConn.getInputStream()));

            String response;
            while((response = mReader.readLine()) != null) {
                StringBuilder mBuilder = new StringBuilder();
                mBuilder.append(response);
                JSONObject mObject = new JSONObject(String.valueOf(mBuilder));
                mResults = mObject.getJSONArray("results");
                mReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mResults;
    }
}
