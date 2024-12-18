package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Repository pour gérer les opérations CRUD sur les entités {@link Person}.
 * Cette classe permet d'ajouter, de trouver, de supprimer et de lister des personnes.
 */
@Repository
public class PersonRepository {
    /**
     * Liste en mémoire contenant les entités {@link Person}.
     */
    private List<Person> persons = new ArrayList<>();
    /**
     * Ajoute une nouvelle personne ou met à jour une personne existante.
     * Si une personne avec le même prénom et nom existe, elle est remplacée.
     *
     * @param person l'entité {@link Person} à ajouter ou mettre à jour
     * @return l'entité {@link Person} sauvegardée
     */
    // Ajouter une personne
    public Person save(Person person) {
        findByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .ifPresent(existingPerson -> persons.remove(existingPerson));
        persons.add(person);
        return person;
    }
    /**
     * Trouve une personne par prénom et nom de famille.
     *
     * @param firstName le prénom de la personne
     * @param lastName le nom de famille de la personne
     * @return un {@link Optional} contenant la personne si elle est trouvée, sinon un {@link Optional#empty()}
     */
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }
    /**
     * Supprime une personne par prénom et nom de famille.
     *
     * @param firstName le prénom de la personne à supprimer
     * @param lastName le nom de famille de la personne à supprimer
     * @return {@code true} si la suppression a réussi, {@code false} si aucune personne n'a été trouvée
     */
    public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
        return persons.removeIf(person ->
                person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName));
    }
    /**
     * Récupère la liste de toutes les personnes.
     *
     * @return une liste contenant toutes les entités {@link Person}
     */
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }
}
