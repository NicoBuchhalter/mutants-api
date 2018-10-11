package com.mutantsApi.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    long countByMutant(boolean isMutant);
    List<Person> findByDna(String[] dna);
}
