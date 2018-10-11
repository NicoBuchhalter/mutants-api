package com.mutantsApi.person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String[] dna;
    private boolean mutant = false;

    private static int CONSECUTIVE_CHARACTERS_FOR_MUTANCY = 4;

    public Person() { super(); }

    public Person(Long id, String[] dna) {
        super();
        this.id = id;
        this.dna = dna;
        detectMutancy();
    }

    public Person(String[] dna) {
        super();
        this.dna = dna;
        detectMutancy();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String[] getDna() {
        return dna;
    }
    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public boolean isMutant() {
        return mutant;
    }

    // Have into account that dna is a square (NXN) matrix, so all strings have `dna.length` characters.
    public void detectMutancy() {
        if(dna.length == 0) {
            return;
        }
        for(int i = 0; i < dna.length; i++) {
            for(int j = 0; j< dna.length; j++) {
                if (checkAllDirections(i, j)) {
                    this.mutant = true;
                    return;
                }
            }
        }
    }

    private boolean checkAllDirections(int i, int j) {
        int[][] directions = {{i, j+1}, {i +1,j -1 }, {i +1, j}, {i+1, j+1}};
        for (int[] direction : directions) {
            if(checkNext(i, j, direction[0], direction[1], 0)) {
                return true;
            }
        }
        return false;

    }

    private boolean checkNext(int currentI, int currentJ, int nextI, int nextJ, int count) {
        if(count == CONSECUTIVE_CHARACTERS_FOR_MUTANCY - 1) {
            return true;
        }
        if (nextI < 0 || nextI >= dna.length || nextJ < 0 || nextJ >= dna.length) {
            return false;
        }
        if (dna[currentI].charAt(currentJ) == dna[nextI].charAt(nextJ)) {
            return checkNext(nextI, nextJ, nextI + (nextI - currentI), nextJ + (nextJ - currentJ), count + 1);
        }
        return false;
    }

}
