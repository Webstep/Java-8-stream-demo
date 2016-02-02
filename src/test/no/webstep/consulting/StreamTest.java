package no.webstep.consulting;

import no.webstep.consulting.modell.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.stream.Collectors.groupingBy;
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
    public void finnAlleBarnOver9år() {
        Set<Person> alleBarn = new HashSet<>();
        for(Person forelder : foreldre)
            for(Person barn : forelder.getBarn()) {
                if(barn.getAge() > 9) {
                    alleBarn.add(barn);
                }
            }

        System.out.println(alleBarn);
        assertEquals(2, alleBarn.size());
    }

    @Test
    public void finnAlleBarnOver9årStream() {
        Set<Person> alleBarn = foreldre.stream()
                .flatMap(p -> p.getBarn().stream())
                .filter(p -> p.getAge() > 9)
                .collect(toSet());
        System.out.println(alleBarn);
        assertEquals(2, alleBarn.size());
    }

    @Test
    public void tellAntallBarnUnder10årMedForeldreFraNorge() {
        int teller = 0;
        for(Person forelder : foreldre) {
            if(forelder.getNasjonalitet().contains(Norge)) {
                for(Person barn : forelder.getBarn()) {
                    if(barn.getAge() < 10) {
                        teller++;
                    }
                }
            }
        }
        assertEquals(2, teller);
    }

    @Test
    public void tellAntallBarnUnder10årMedForeldreFraNorgeStream() {
        long teller = foreldre.stream()
                .filter(p -> p.getNasjonalitet().contains(Norge))
                .flatMap(p -> p.getBarn().stream())
                .distinct()
                .filter(p -> p.getAge() < 10)
                .count();
        assertEquals(2, teller);
    }

    @Test
    public void finnAlleBarnGrupperPåAlder() {
        Map<Integer, List<Person>> alleBarn = new HashMap<>();
        for(Person forelder : foreldre) {
            List<Person> barnList = new ArrayList<>();
            for(Person barn : forelder.getBarn()) {
                if(!alleBarn.containsKey(barn.getAge())) {
                    barnList.add(barn);
                    alleBarn.put(barn.getAge(), barnList);
                } else {
                    List<Person> alleBarnLista = alleBarn.get(barn.getAge());
                    alleBarnLista.add(barn);
                }
            }
        }
        System.out.println(alleBarn.keySet());
        assertEquals(3, alleBarn.get(3).size());
    }

    @Test
    public void finnAlleBarnGrupperPåAlderStream() {
        Map<Integer, List<Person>> alleBarn = foreldre.stream()
                .flatMap(p -> p.getBarn().stream())
                .distinct()
                .collect(groupingBy(Person::getAge));

        System.out.println(alleBarn.keySet());
        assertEquals(2, alleBarn.get(3).size());
    }

}
