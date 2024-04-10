package ru.russkikh.springcourse.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.russkikh.springcourse.FirstSecurityApp.models.Person;
import ru.russkikh.springcourse.FirstSecurityApp.security.PersonDetails;
import ru.russkikh.springcourse.FirstSecurityApp.services.PersonDetailsService;

import javax.validation.Valid;

@Component
public class PersonValidator implements Validator {

    PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person =(Person) o;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username","","Человек с таким именем уже существует!");

    }
}
