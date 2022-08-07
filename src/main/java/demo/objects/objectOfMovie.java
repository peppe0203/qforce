package demo.objects;

import nl.qnh.qforce.domain.Movie;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDate;

public class objectOfMovie implements Movie {
    // title: getters and setters for the movie title (type String)
    @JsonProperty("title")
    public String title;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // episode: getters and setters for the movie episode (type Integer)
    @JsonProperty("episode_id")
    public Integer episode;

    public void setEpisode (Integer episode){
        this.episode = episode;
    }

    public Integer getEpisode() {
        return episode;
    }

    // director: getters and setters for the movie director (type String)
    @JsonProperty("director")
    public String director;

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    // releaseDate: getters and setters for the movie releaseDate (type LocalDate)
    @JsonProperty("release_date")
    public LocalDate releaseDate;

    public void setRelease_date(LocalDate releaseDate){
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
