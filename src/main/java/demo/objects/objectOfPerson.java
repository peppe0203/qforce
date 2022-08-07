package demo.objects;

import nl.qnh.qforce.domain.Gender;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.Person;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
public class objectOfPerson implements Person {

    // id: getters and setters for the person id (type Long)
    @JsonProperty("id")
    public long id;

    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    // name: getters and setters for the person name (type String)
    @JsonProperty("name")
    public String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // birthYear: getters and setters for the person birthYear (type String)
    @JsonProperty("birth_year")
    public String birthYear;

    public void setBirthYear(String birthYear){
        this.birthYear = birthYear;
    }

    public String getBirthYear() {
        return birthYear;
    }

    // gender: getters and setters for the person gender (type Gender)
    @JsonProperty("gender")
    public Gender gender;

    // There are only 4 possibilities for the gender, these possibilities are from the enum (group of constants) gender
    public void setGender(String gender){
        switch (gender) {
            case "male":
                this.gender = Gender.MALE;
                break;
            case "female":
                this.gender = Gender.FEMALE;
                break;
            case "n/a":
                this.gender = Gender.NOT_APPLICABLE;
                break;
            default:
                this.gender = Gender.UNKNOWN;
                break;
        }
    }

    public Gender getGender() {
        return gender;
    }

    // height: getters and setters for the person height (type int)
    @JsonProperty("height")
    public int height;

    public void setHeight(int height){
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    // weight: getters and setters for the person weight (type int)
    @JsonProperty("mass")
    public int weight;

    public void setWeight(int weight){
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    // movies: getters and setters for the movies in which the person plays a role (type List)
    // The List consists of multiple movies
    @JsonProperty("films")
    public List movies;

    public void setMovies(List movies){
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
