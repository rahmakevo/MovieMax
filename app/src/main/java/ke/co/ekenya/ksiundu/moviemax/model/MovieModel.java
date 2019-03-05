package ke.co.ekenya.ksiundu.moviemax.model;

public class MovieModel {
    private String backdrop_path;
    private String title;
    private String overview;

    public String getBackdrop_path() {
        return backdrop_path;
    }


    public String getTitle() {
        return title;
    }


    public String getOverview() {
        return overview;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
