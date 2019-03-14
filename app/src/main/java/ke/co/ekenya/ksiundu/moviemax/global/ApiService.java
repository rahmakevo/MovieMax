package ke.co.ekenya.ksiundu.moviemax.global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class ApiService {

    public ApiService() {
    }

    public String getMovieResults(String url){
        String response;
        HttpsURLConnection mConn;
        StringBuilder mBuilder = new StringBuilder();
        try {
            mConn = (HttpsURLConnection) (new URL(url)).openConnection();
            mConn.setRequestMethod("GET");
            mConn.connect();

            BufferedReader mReader = new BufferedReader(new InputStreamReader(mConn.getInputStream()));

            while((response = mReader.readLine()) != null) {
                mBuilder.append(response);
                mReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBuilder.toString();
    }
}
