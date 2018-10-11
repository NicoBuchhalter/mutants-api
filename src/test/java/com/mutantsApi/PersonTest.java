package com.mutantsApi;


import com.mutantsApi.person.Person;
import org.junit.Test;


public class PersonTest {

    String[] nonMutantDna = {"CAGTGC", "TTATTT", "AGACGG", "GCGCTA", "TCACTG"};
    String[] mutantDna = {"CTGCCA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

    @Test
    public void mutantDetectorTest() {
        Person mutant = new Person(mutantDna);
        Person human = new Person(nonMutantDna);
        assert (mutant.isMutant());
        assert (!human.isMutant());
    }
}