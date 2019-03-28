package ke.co.ekenya.ksiundu.moviemax.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ke.co.ekenya.ksiundu.moviemax.R;
import ke.co.ekenya.ksiundu.moviemax.global.ApiService;
import ke.co.ekenya.ksiundu.moviemax.model.MovieCompanies;

public class MovieDetailActivity extends AppCompatActivity {
    private String id;
    private ArrayList<MovieCompanies> mCompanyList = new ArrayList<>();
    private TextView mTitle, mOverview;
    private ImageView mMovieBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        id = getIntent().getStringExtra("id");
        runOnUiThread(() -> new getMovieDetails().execute());

        mTitle = findViewById(R.id.textMovieDetailTitle);
        mOverview = findViewById(R.id.textMovieDetailOverview);
        mMovieBg = findViewById(R.id.imageMovieDetailBg);

    }

    @SuppressLint("StaticFieldLeak")
    private class getMovieDetails extends AsyncTask<Void, Void, Void> {
        String title, overview, vote_average, vote_count, release_date, poster_path;
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=726c1648f49672fa8c4302a2db524ff6";
            ApiService mMovieService = new ApiService();
            String response = mMovieService.getMovieResults(url);

            try {
                JSONObject mResponseObject = new JSONObject(response);

                title = mResponseObject.getString("original_title");
                overview = mResponseObject.getString("overview");
                vote_average =  mResponseObject.getString("vote_average");
                vote_count = String.valueOf(mResponseObject.getInt("vote_count"));
                release_date = mResponseObject.getString("release_date");
                poster_path = mResponseObject.getString("backdrop_path");

                JSONArray mProductionCompanies = mResponseObject.getJSONArray("production_companies");
                int id = mProductionCompanies.getInt(0);
                String logo_path = mProductionCompanies.getString(1);
                String name = mProductionCompanies.getString(2);

                MovieCompanies mCompanyDetail = new MovieCompanies();
                mCompanyDetail.setId(id);
                mCompanyDetail.setLogo_path(logo_path);
                mCompanyDetail.setName(name);

                mCompanyList.add(mCompanyDetail);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mTitle.setText(title);
            mOverview.setText(overview);
            String image = "https://image.tmdb.org/t/p/w500"+poster_path;
            Picasso.with(getApplicationContext())
                    .load(image)
                    .into(mMovieBg);
        }
    }
}
