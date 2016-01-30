package no.webstep.consulting.modell;

import lombok.Data;

import java.util.Set;

@Data
public class Person {
    private String navn;
    private int age;
    private Set<Person> barn;
    private Set<Land> nasjonalitet;
    private Kjonn kjonn;

    public enum Kjonn {
        Mann, Kvinne
    }

    public Person(String navn, int age, Kjonn kjonn) {
        this.navn = navn;
        this.age = age;
        this.kjonn = kjonn;
    }

}
