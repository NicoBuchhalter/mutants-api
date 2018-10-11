package com.mutantsApi.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PersonResource {
    @Autowired
    private PersonRepository personRepository;


    @PostMapping("/people/mutant")
    public ResponseEntity<Object> createMutant(@RequestBody Person person) {
        if(! validDna(person.getDna())) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "Must provide a valid NxN DNA");
            return ResponseEntity.unprocessableEntity().body(map);
        }
        List<Person> savedPersonList = personRepository.findByDna(person.getDna());
        Person savedPerson;
        if(savedPersonList.isEmpty()) {
            // I'm sure this is not the correct way, but I cant figure out how to do it in an "after save" trigger.
            person.detectMutancy();
            savedPerson = personRepository.save(person);
        }
        else {
            savedPerson = savedPersonList.get(0);
        }



        if (savedPerson.isMutant()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/people/stats")
    public Map<String, Object> getStats() {
        long mutantCount = personRepository.countByMutant(true);
        long total = personRepository.count();
        float ratio = (float) mutantCount / (total == 0 ? 1 : total);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count_mutant_dna", mutantCount);
        map.put("count_human_dna", total);
        map.put("ratio", ratio);
        return  map;
    }

    private boolean validDna(String[] dna) {
        if (dna.equals(null) || dna.length == 0) {
            return false;
        }
        for (String sequence : dna) {
            if (sequence.length() != dna.length) {
                return false;
            }
        }
        return true;
    }
}
