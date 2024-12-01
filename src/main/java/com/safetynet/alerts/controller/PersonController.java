//package com.safetynet.alerts.controller;
//
//import com.safetynet.alerts.model.Person;
//import com.safetynet.alerts.service.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/person")
//public class PersonController {
//    @Autowired
//    private PersonService personService;
//
//    @PostMapping
//    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
//        Person createdPerson = personService.addPerson(person);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
//    }
//
//    @PutMapping
//    public ResponseEntity<Person> updatePerson(@RequestParam String firstName, @RequestParam String lastName, @RequestBody Person personDetails) {
//        Person updatedPerson = personService.updatePerson(firstName, lastName, personDetails);
//        if (updatedPerson != null) {
//            return ResponseEntity.ok(updatedPerson);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
//        personService.deletePerson(firstName, lastName);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<Person> getPerson(@RequestParam String firstName, @RequestParam String lastName) {
//        Person person = personService.getPerson(firstName, lastName);
//        if (person != null) {
//            return ResponseEntity.ok(person);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//}
//
//
