package ke.co.ekenya.ksiundu.moviemax.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ke.co.ekenya.ksiundu.moviemax.R;
import ke.co.ekenya.ksiundu.moviemax.model.MovieModel;
import ke.co.ekenya.ksiundu.moviemax.ui.MovieDetailActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<MovieModel> mMovieList;
    private Context mCtx;

    public MovieAdapter(ArrayList<MovieModel> mMovieList, Context mContext) {
        this.mMovieList = mMovieList;
        mCtx = mContext;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_sample_card,
                parent,
                false);
        return new MovieHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        holder.bindMovies(mMovieList.get(position));
        holder.itemView.setOnClickListener(v -> {
            String id = mMovieList.get(position).getId();
            Intent mIntent = new Intent(mCtx, MovieDetailActivity.class);
            mIntent.putExtra("id", id);
            holder.itemView.getContext().startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mOverview;
        ImageView mBackground;
        String id;
        MovieHolder(View itemView) {
            super(itemView);
            mCtx = itemView.getContext();
            mTitle = itemView.findViewById(R.id.textMovieTitle);
            mOverview = itemView.findViewById(R.id.textMovieOverview);
            mBackground = itemView.findViewById(R.id.imageMovieBg);
        }

        void bindMovies(MovieModel movieModel) {
            Picasso.with(mCtx)
                    .load(movieModel.getBackdrop_path())
                    .into(mBackground);
            mTitle.setText(movieModel.getTitle());
            mOverview.setText(movieModel.getOverview());
            id = movieModel.getId();
        }
    }
}
