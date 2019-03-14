package ke.co.ekenya.ksiundu.moviemax.model;

public class MovieModel {
    private String backdrop_path;
    private String title;
    private String overview;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieModel() {
    }

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
