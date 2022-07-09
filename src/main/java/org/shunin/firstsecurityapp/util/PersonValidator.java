package org.shunin.firstsecurityapp.util;

import org.shunin.firstsecurityapp.models.Person;
import org.shunin.firstsecurityapp.services.PersonDetailsService;
import org.shunin.firstsecurityapp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService detailsService;
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonDetailsService detailsService, PersonService personService) {
        this.detailsService = detailsService;
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {   // объясняет валидатору, для каких объектов он нужен
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        /*try {
            detailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
           return; //все ок, пользователь с таким именем не найден
        }*/
        if(personService.loadUserByUsername(person.getUsername()).isPresent())
        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }


}
