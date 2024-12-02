package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private List<Person> persons = new ArrayList<>();

    // Ajouter une personne
    public Person save(Person person) {
        findByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .ifPresent(existingPerson -> persons.remove(existingPerson));
        persons.add(person);
        return person;
    }

    // Trouver une personne par prénom et nom
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    // Supprimer une personne par prénom et nom
    public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
        return persons.removeIf(person ->
                person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName));
    }

    // Obtenir toutes les personnes
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }
}
