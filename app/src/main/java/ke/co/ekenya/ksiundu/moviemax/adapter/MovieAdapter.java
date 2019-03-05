package ke.co.ekenya.ksiundu.moviemax.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ke.co.ekenya.ksiundu.moviemax.R;
import ke.co.ekenya.ksiundu.moviemax.model.MovieModel;

public abstract class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private List<MovieModel> mMovieList;
    private LayoutInflater mInflater;
    private Context mCtx;

    public MovieAdapter(List<MovieModel> mMovieList, Context mContext) {
        this.mMovieList = mMovieList;
        mInflater = LayoutInflater.from(mContext);
        mCtx = mContext;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = mInflater.inflate(
                R.layout.movie_sample_card,
                parent,
                false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.mTitle.setText(mMovieList.get(position).getTitle());
        holder.mOverview.setText(mMovieList.get(position).getOverview());
        Picasso.with(mCtx).load(mMovieList.get(position).getBackdrop_path()).into(holder.mImageBg);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mOverview;
        ImageView mImageBg;
        View mView;
        MovieHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.mTitle = itemView.findViewById(R.id.textMovieTitle);
            this.mOverview = itemView.findViewById(R.id.textMovieOverview);
            this.mImageBg = itemView.findViewById(R.id.imageMovieBg);
        }
    }
}
