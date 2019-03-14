package ke.co.ekenya.ksiundu.moviemax.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ke.co.ekenya.ksiundu.moviemax.R;
import ke.co.ekenya.ksiundu.moviemax.adapter.MovieAdapter;
import ke.co.ekenya.ksiundu.moviemax.global.ApiService;
import ke.co.ekenya.ksiundu.moviemax.model.MovieModel;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mMovieDialog;
    RecyclerView mRecyclerView;
    ArrayList<MovieModel> mMovieList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieDialog = new ProgressDialog(this);


        runOnUiThread(() -> new getMovieList().execute());

        mRecyclerView = findViewById(R.id.recyclerViewMain);

    }

    @SuppressLint("StaticFieldLeak")
    private class getMovieList extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieDialog.setTitle("Fetching Movies");
            mMovieDialog.setMessage("Dear Client, Kindly wait as we prepare the most recent Movies for you");
            mMovieDialog.setCanceledOnTouchOutside(false);
            mMovieDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://api.themoviedb.org/3/trending/all/day?api_key=726c1648f49672fa8c4302a2db524ff6";
            ApiService mMovieService = new ApiService();
            String response = mMovieService.getMovieResults(url);

            try {
                JSONObject mResponseObject = new JSONObject(response);
                JSONArray mResultsArray = mResponseObject.getJSONArray("results");

                for (int i = 0; i < mResultsArray.length(); i++ ){
                    JSONObject mSingleMovieObject = mResultsArray.getJSONObject(i);

                    String title = mSingleMovieObject.getString("title");
                    String image = "https://image.tmdb.org/t/p/w500"+mSingleMovieObject.getString("backdrop_path");
                    String desc = mSingleMovieObject.getString("overview");

                    String id = String.valueOf(mSingleMovieObject.getInt("id"));

                    MovieModel mMovieModel = new MovieModel();
                    mMovieModel.setTitle(title);
                    mMovieModel.setBackdrop_path(image);
                    mMovieModel.setOverview(desc);
                    mMovieModel.setId(id);

                    mMovieList.add(mMovieModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(new MovieAdapter(mMovieList, getApplicationContext()));
            mMovieDialog.dismiss();
        }
    }

}
