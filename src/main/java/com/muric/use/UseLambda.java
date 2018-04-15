package com.muric.use;

import com.muric.setup.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UseLambda {
    private static final Logger log = LogManager.getLogger(UseLambda.class);

    private static final UseLambda app = new UseLambda();

    public static void main(String[] args){
        app.useExisting();
    }

    private void useExisting(){
        useRunnable();
        useComparator();
        usePredicate();
    }

    private void useRunnable(){
        int poolSize = 5;
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        log.info("Using lambda");
        for(int ii=0; ii<poolSize; ii++){
            pool.execute(()->{
                log.info("lambda");
                try{Thread.currentThread().sleep(200);}catch (Exception ee){}
            });
        }
        pool.shutdown();


        pool = Executors.newFixedThreadPool(poolSize);
        log.info("Old way:");
        for(int ii=0; ii<poolSize; ii++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("old way");
                    try{Thread.currentThread().sleep(200);}catch (Exception ee){}
                }
            });
        }
        pool.shutdown();
    }

    private void useComparator(){
        List<Person> people = getPeople(100);

        printPeople("Unsorted:", people);
        people.sort((a, b) -> a.getAge() -  b.getAge());
        printPeople("Sorted by age:", people);
        people.sort((a, b) -> a.getFirstName().compareTo(b.getFirstName()));
        printPeople("Sorted by First:", people);
        people.sort(Comparator.comparing(Person::getFirstName));
        printPeople("Sorted by Last:", people);
        people.sort((a, b) -> a.getGender().ordinal() - b.getGender().ordinal());
        printPeople("Sorted by gender:", people);
    }

    private void usePredicate(){
        List<Person> people = getPeople(100);
        Predicate<Person> female = a -> a.getGender() == Person.Gender.Female;
        Predicate<Person> mature = a -> a.getAge() > 20;
        Predicate<Person> stillYoung = a -> a.getAge() < 40;

        Collection<Person> streamed = people.stream()
                .filter(female.and(mature).and(stillYoung))
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());

        printPeople("All by age", people.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList()));
        printPeople("Young females by Last name:", streamed);
    }

    private List<Person> getPeople(int number){
        List<Person> people = new ArrayList<>();
        for (int ii=0; ii<number; ii++){
            people.add(new Person());
        }
        return people;
    }

    private void printPeople (String prefix, Collection<Person> people){
        StringBuilder buf = new StringBuilder();
        for(Person pp : people){
            buf.append("\n\t" + pp);
        }
        log.info(prefix + buf.toString());
    }
}
