package demo.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import demo.app;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.service.PersonService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class objectOfService implements PersonService {
    // service for searching a person bij name

    @Override
    public List<Person> search(String query) {
        return searchFunc(query);
    }

    // the function used to search for a person bij name
    public List<Person> searchFunc(String query){
        try {
            // get the request data from the API
            JsonNode root = app.getMapper().readTree(app.getRequest("people?search=" + query).getBody());

            // make a list with persons
            List<Person> listOfPersons = new ArrayList();

            // for every person go through the loop and make a person object
            IntStream.range(0, root.path("results").size()).forEachOrdered(i -> {
                // a person can be in multiple movies, so a person has a movie list
                List<Movie> listOfMovies = new ArrayList();
                // for every movie go through the loop and make a movie object
                IntStream.range(0, root.path("results").get(i).path("films").size()).forEachOrdered(x -> {
                    try {
                        Object Movie = createMovie(String.valueOf(Integer.parseInt(root.path("results").get(i).path("films").get(x).toString().replaceAll("\\D", ""))));
                        // add the movie object to the list of movies
                        listOfMovies.add((nl.qnh.qforce.domain.Movie) Movie);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                // create the object of a person with the related movies
                // add the person to the list of persons
                objectOfPerson Person = createPerson(root.path("results").get(i), Integer.parseInt(String.valueOf(i)),listOfMovies);
                listOfPersons.add(Person);
            });
            return listOfPersons;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    // service for searching a person bij name
    @Override
    public Optional<Person> get(long id) throws JsonProcessingException {
        return getFuncId(id);
    }

    // the function used to search for a person bij id
    public Optional<Person> getFuncId(long id) throws JsonProcessingException {
        // if there is an error in the request this wil return null
        if (app.getRequest("people/" + id) != null){
            // get the request data from the API
            JsonNode root = app.getMapper().readTree(app.getRequest("people/" + id).getBody());

            // make a list for the movies where the person is playing in
            List<Movie> listOfMovies = new ArrayList(); //make a list to pass to the person
            // for every movie go thought the loop and collect the information of the movie and covert this to a movie object
            IntStream.range(0, root.path("films").size()).forEachOrdered(i -> {
                Object Movie = null;
                try {
                    Movie = createMovie(String.valueOf(Integer.parseInt(root.path("films").get(i).toString().replaceAll("\\D", ""))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                // add the movie object to the list of movies
                listOfMovies.add((nl.qnh.qforce.domain.Movie) Movie);
            });

            // create a person object and return this object
            return Optional.of(createPerson(root, Integer.parseInt(String.valueOf(id)), listOfMovies));}

        // if the request returns null, return an empty Optional object and print an error
        else {
            System.out.println("[!] ERROR occurred - see message above");
            return Optional.empty();
        }
    }

    // function to request the specific movie information with the movie number and create a movie object with this info
    private objectOfMovie createMovie(String movieNr) throws JsonProcessingException {
        // used for collecting the information of the movie
        JsonNode root = app.getMapper().readTree(app.getRequest("films/" + movieNr).getBody());

        // make the object of the movie with the mandatory info
        objectOfMovie movie = new objectOfMovie();
        movie.setTitle(root.path("title").asText());
        movie.setEpisode(root.path("episode_id").asInt());
        movie.setDirector(root.path("director").asText());
        movie.setRelease_date(LocalDate.parse(root.path("release_date").asText()));
        return movie;
    }

    // function to create a person object
    private objectOfPerson createPerson(JsonNode root, int id, List movies){

        // make the object of the person with the mandatory info
        objectOfPerson person = new objectOfPerson();
        person.setId(id);
        person.setName(root.path("name").asText());
        person.setGender(root.path("gender").asText());
        person.setBirthYear(root.path("birth_year").asText());
        person.setWeight(root.path("mass").asInt());
        person.setHeight(root.path("height").asInt());
        person.setMovies(movies);

        return person;
    }

}
