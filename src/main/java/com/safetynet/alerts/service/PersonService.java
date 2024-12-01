//package com.safetynet.alerts.service;
//
//import com.safetynet.alerts.model.Person;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class PersonService {
//    private Map<String, Person> personRepository = new HashMap<>();
//
//    public Person addPerson(Person person) {
//        String key = generateKey(person.getFirstName(), person.getLastName());
//        personRepository.put(key, person);
//        return person;
//    }
//
//    public Person updatePerson(String firstName, String lastName, Person personDetails) {
//        String key = generateKey(firstName, lastName);
//        Person existingPerson = personRepository.get(key);
//        if (existingPerson != null) {
//            existingPerson.setAge(personDetails.getAge());
//            existingPerson.setEmail(personDetails.getEmail());
//            return existingPerson;
//        }
//        return null;
//    }
//
//    public void deletePerson(String firstName, String lastName) {
//        String key = generateKey(firstName, lastName);
//        personRepository.remove(key);
//    }
//
//    public Person getPerson(String firstName, String lastName) {
//        String key = generateKey(firstName, lastName);
//        return personRepository.get(key);
//    }
//
//    private String generateKey(String firstName, String lastName) {
//        return firstName.toLowerCase() + "_" + lastName.toLowerCase();
//    }
//}
//
//
//
//
