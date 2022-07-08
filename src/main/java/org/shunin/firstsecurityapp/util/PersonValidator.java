package org.shunin.firstsecurityapp.util;

import org.shunin.firstsecurityapp.models.Person;
import org.shunin.firstsecurityapp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService detailsService;

    @Autowired
    public PersonValidator(PersonDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            detailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
           return; //все ок, пользователь с таким именем не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
