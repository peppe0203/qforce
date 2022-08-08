package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.controllers.errorController;
import demo.objects.objectOfService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

import static demo.controllers.dbController.insertRecord;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
 RestController: Makes mapping possible
 SpringBootApplication: Makes autoconfiguration, component scan and additional configuration for the Spring framework possible
*/
@RestController
@SpringBootApplication
@PropertySource("classpath:app.properties")
public class app {
    private Object jsonObject;

    public static void main(String[] args){
        SpringApplication.run(app.class, args);
    }

    // function is used for making the connection to the API
    // (static means that the particular member belongs to a type itself, rather than to an instance of that type.)
    // PARAM: query - gives the query string used in the API call
    public static ResponseEntity<String> getRequest(String query) {
        try {
            String baseUrl = "https://swapi.dev/api/";
            // RestTemplate used for the REST API (Representational State Transfer architectural style)
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + query, String.class);
            return response;
        }
        catch (HttpClientErrorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // function for marshalling and unmarshalling JSON.
    public static ObjectMapper getMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // this part is used for making it possible to use the LocalDate otherwise java makes this a list item
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }


    // function used to call searchPersonName and present the output
    // PARAM: q - variable used to define the search name
    @RequestMapping(value = "/Persons", produces = MediaType.TEXT_PLAIN_VALUE)
    private Object getPersonBySearch(@RequestParam String q) throws JsonProcessingException, SQLException {
        // create object from the object service
        objectOfService personServiceBySearch = new objectOfService();
        // if listOfPersons is empty this means there are no result from the API
        if (personServiceBySearch.searchFunc(q).isEmpty() ==  false){

            // add the search url by name to the database
            insertRecord("https://swapi.dev/api/people?search=" + q);

            // get the searchFunc and display this
            // getMapper is the function that's returns the jackson mapper for marshalling and unmarshalling JSON
            // fix the presentation of the api so birthYear and releaseDate are presented in snake-case
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(personServiceBySearch.searchFunc(q)).replace("birthYear", "birth_year"). replace("releaseDate", "release_date");
        }
        else{
            return new errorController();
        }
    }

    // function used to call getPersonId and present the output
    // PARAM: id - variable used to get specific person
    @RequestMapping(value = "/Persons/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    private Object getPersonById(@PathVariable String id) throws JsonProcessingException, SQLException {
        objectOfService personServiceById= new objectOfService();
        // check if the response of the service is not null
        if (personServiceById.getFuncId((Long.parseLong(id))).isEmpty() == false ){
            // add the search with id to the database
            insertRecord("https://swapi.dev/api/people/" + id);

            // get the getFuncId and display this
            // fix the presentation of the api so birthYear and releaseDate are presented in snake-case
            // try to add the url and date to the database
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(personServiceById.getFuncId(Long.parseLong(id)).get()).replace("birthYear", "birth_year"). replace("releaseDate", "release_date");
        }
        else {
            return new errorController();
        }
    }

    // The welcome page of the demo application
    @RequestMapping(value= "/", method = GET)
    @ResponseBody
    private String homepage(){
        return "Welcome to the demo application!".toUpperCase();
    }
}
