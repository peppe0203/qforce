/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.qnh.qforce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.qnh.qforce.domain.Person;

import java.util.List;
import java.util.Optional;

/**
 * The person service to search and retrieve persons.
 *
 * @author QNH
 */
public interface PersonService {

    /**
     * Searches for persons.
     *
     * @param query the query string
     * @return the list of persons
     */
    List<Person> search(String query);

    /**
     * Returns the person with the provided id.
     *
     * @param id the id of the person
     * @return the person
     */
    Optional<Person> get(long id) throws JsonProcessingException;
}

