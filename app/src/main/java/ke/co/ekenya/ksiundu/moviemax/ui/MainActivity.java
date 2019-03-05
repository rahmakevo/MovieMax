package ke.co.ekenya.ksiundu.moviemax.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ke.co.ekenya.ksiundu.moviemax.R;
import ke.co.ekenya.ksiundu.moviemax.adapter.MovieAdapter;
import ke.co.ekenya.ksiundu.moviemax.global.ApiService;
import ke.co.ekenya.ksiundu.moviemax.model.MovieModel;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressFetch;
    ArrayList<MovieModel> movieModel = new ArrayList<>();
    MovieAdapter movieAdapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressFetch = new ProgressDialog(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetMovieList().execute(new ApiService());

        mProgressFetch.setTitle("Fetching Movies...");
        mProgressFetch.setMessage("Kindly wait as we fetch available Movies from our servers. Thank you!");
        mProgressFetch.setCanceledOnTouchOutside(false);
        mProgressFetch.show();

    }

    @SuppressLint("StaticFieldLeak")
    private class GetMovieList extends AsyncTask<ApiService, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiService... apiServices) {
            return apiServices[0].getMovieResults();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            Log.v("response: ", String.valueOf(jsonArray));

            for (int i = 0; i > jsonArray.length(); i++) {
                try {
                    JSONObject mResultObject = jsonArray.getJSONObject(i);
                    MovieModel mMovieList = new MovieModel();

                    mMovieList.setTitle(mResultObject.getString("title"));
                    mMovieList.setOverview(mResultObject.getString("overview"));
                    mMovieList.setBackdrop_path(mResultObject.getString("backdrop_path").substring(1));

                    movieModel.add(mMovieList);
                    onTaskCompleted(movieModel);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }
    }

    private void onTaskCompleted(ArrayList<MovieModel> movieModel) {
        Log.v("movie_list: ", String.valueOf(movieModel));
        if (movieModel.isEmpty()) {
            mProgressFetch.hide();
            Toast.makeText(this, "Kindly try restarting the Application Again", Toast.LENGTH_SHORT).show();
        } else {
            mProgressFetch.dismiss();

            mRecyclerView = findViewById(R.id.recyclerViewMain);

            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(movieAdapter);
        }
    }
}
