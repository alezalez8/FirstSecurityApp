package org.shunin.firstsecurityapp.services;


import org.shunin.firstsecurityapp.models.Person;
import org.shunin.firstsecurityapp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public Optional<Person> loadUserByUsername(String username) {
      return   Optional.of(peopleRepository.findByUsername(username)).orElse(null);

    }
}
