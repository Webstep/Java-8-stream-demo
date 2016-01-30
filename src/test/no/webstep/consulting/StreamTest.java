package no.webstep.consulting;

import no.webstep.consulting.modell.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.stream.Collectors.toSet;
import static no.webstep.consulting.modell.Land.*;
import static no.webstep.consulting.modell.Person.Kjonn.Kvinne;
import static no.webstep.consulting.modell.Person.Kjonn.Mann;
import static org.junit.Assert.assertEquals;

public class StreamTest {

    static Person mohammed = new Person("Mohammed", 44, Mann);
    static Person hossein = new Person("Hossein", 10, Mann);
    static Person ali = new Person("Ali", 8, Mann);
    static Person pedram = new Person("Pedram", 60, Mann);
    static Person reza = new Person("Reza", 19, Mann);
    static Person sara = new Person("Sara", 40, Kvinne);
    static Person sherry = new Person("Sherry", 38, Kvinne);
    static Person bahare = new Person("Bahare", 3, Kvinne);
    static Person azadeh = new Person("Azadeh", 3, Kvinne);

    static Set<Person> foreldre = of(mohammed, sara, pedram, sherry);

    @BeforeClass
    public static void init() {
        mohammed.setNasjonalitet(of(Norge, Frankrike));
        mohammed.setBarn(of(ali, hossein));
        sara.setNasjonalitet(of(Frankrike, Iran));
        sara.setBarn(of(ali, azadeh));

        pedram.setNasjonalitet(of(Norge, Iran));
        pedram.setBarn(of(reza, bahare));
        sherry.setNasjonalitet(of(Iran));
        sherry.setBarn(of(reza, bahare));
    }

    @Test
    public void finnAlleBarna() {
        Set<Person> alleBarn = new HashSet<>();
        for(Person forelder : foreldre)
            for(Person barn : forelder.getBarn())
                alleBarn.add(barn);

        System.out.println(alleBarn);
        assertEquals(5, alleBarn.size());
    }

    @Test
    public void finnAlleBarnaStream() {
        Set<Person> alleBarn = foreldre.stream()
                .flatMap(p -> p.getBarn().stream())
                .collect(toSet());
        System.out.println(alleBarn);
        assertEquals(5, alleBarn.size());
    }

    @Test
    public void finnAlleBarnOver10år() {
        Set<Person> alleBarn = new HashSet<>();
        for(Person forelder : foreldre)
            for(Person barn : forelder.getBarn()) {
                if(barn.getAge() > 10) {
                    alleBarn.add(barn);
                }
            }

        System.out.println(alleBarn);
        assertEquals(1, alleBarn.size());
    }

    @Test
    public void finnAlleBarnaOver10årFraNorge() {

    }

    @Test
    public void finnAlleBarnGrupperPåAlder() {

    }

}
