package org.danielchesters.javase8;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by daniel on 06/05/14.
 */
public class Chapter2 {

    //Exercise 1 : Too complex for demonstrate stream pro

    //Exercise 2
    public static void exercise2() throws URISyntaxException, IOException {
        System.out.println("Exercise 2");
        List<String> words = getWordsFromFile("alice.txt");

        long time = System.nanoTime();
        words.stream().filter(w -> {
            System.out.printf("Predicate %s%n", w);
            return w.length() > 8;
        }).limit(5).forEach(System.out::println);//parallel 4 times slow
        System.out.println(System.nanoTime() - time);
    }

    //Private method for Exercise 2, 3, 12 and 13
    private static List<String> getWordsFromFile(String filename) throws IOException, URISyntaxException {
        String contents = new String(Files.readAllBytes(new File(Chapter2.class.getClassLoader().getResource(filename).toURI()).toPath()), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }

    //Exercise 3
    public static void exercise3() throws IOException, URISyntaxException {
        System.out.println("Exercise 3");
        List<String> words = getWordsFromFile("war-and-peace.txt");
        long startSeq = System.currentTimeMillis();
        long count1 = words.stream().filter(w -> w.length() > 12).count();
        System.out.printf("Duration (seq) %d%n", System.currentTimeMillis()- startSeq);
        System.out.println(count1);
        long startParral = System.currentTimeMillis();
        long count2 = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.printf("Duration (parallel) %d%n", System.currentTimeMillis() - startParral);//4 time faster
        System.out.println(count2);
    }

    //Exercise 4
    public static void exercise4() {
        System.out.println("Exercise 4");
        int[] values = {1, 4, 9, 16};
        Stream<Object> s = Stream.of(values);
        IntStream intStream = IntStream.of(values);
        System.out.println(s.peek(System.out::println).count());
        System.out.println(intStream.peek(System.out::println).count());
    }

    //Exercise 5
    public static Stream<Long> randomStream(long a, long c, long m, long seed) {
        return Stream.iterate(seed, l -> (a * l + c) % m);
    }

    public static void exercise5() {
        System.out.println("Exercise 5");
        Stream<Long> stream = randomStream(25214903917l, 11, (long) Math.pow(2, 48), System.currentTimeMillis());
        stream.limit(100).forEach(System.out::println);
    }

    //Exercise 6
    public static Stream<Character> characterStream(String s) {
        return IntStream.rangeClosed(0, s.length() - 1).mapToObj(s::charAt);
    }

    public static void exercise6() {
        System.out.println("Exercise 6");
        characterStream("toto").forEach(System.out::println);
    }

    //Exercise 7 (reflection)
    public static <T> boolean isFinite(Stream<T> stream) {
        return stream.isParallel();//false;
    }

    //Exercise 8 (not my solution, I had no idea how start)
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> iterSecond = second.iterator();
        return first.flatMap(t -> {
                    if (iterSecond.hasNext()) {
                        return Arrays.asList(t, iterSecond.next()).stream();
                    } else {
                        first.close();
                        return null;
                    }
                }
        );
    }

    public static void exercise8() {
        System.out.println("Exercise 8");
        Stream<Integer> first = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> second = Stream.of(1, 2, 3, 4, 5, 6, 7);
        zip(first, second).forEach(System.out::println);
    }

    //Exercise 9
    public static <T> ArrayList<T> join1(Stream<ArrayList<T>> stream) {
        return stream.reduce((l1, l2) -> {
                    l1.stream().forEach(l->System.out.println("l1 "+l));
                    l2.stream().forEach(l->System.out.println("l2 "+l));
                    l1.addAll(l2);
                    return l1;
                }
        ).orElse(new ArrayList<T>());
    }

    public static <T> ArrayList<T> join2(Stream<ArrayList<T>> stream) {
        return stream.reduce(new ArrayList<T>(), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }
        );
    }

    public static <T> ArrayList<T> join3(Stream<ArrayList<T>> stream) {
        return stream.reduce(new ArrayList<T>(),
                (a, l) -> {
                    a.addAll(l);
                    return a;
                },
                (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }
        );
    }

    //Exercise 10 (It is not a solution with reduce but it is a simple solution)
    public static double average(Stream<Double> stream) {
        return stream.mapToDouble(d -> d).average().getAsDouble();
    }

    public static void exercise10() {
        System.out.println("Exercise 10");
        Stream<Double> stream = Stream.of(1.0, 2.0, 3.0);
        System.out.println("Average : " + average(stream));
    }

    //Exercise 11 : Maybe one day…

    //Exercise 12 (with a sort of array of AtomicInteger)
    public static void exercise12() throws IOException, URISyntaxException {
        System.out.println("Exercise 12");
        List<String> words = getWordsFromFile("war-and-peace.txt");
        AtomicIntegerArray shortWords = new AtomicIntegerArray(12);
        words.parallelStream().forEach(w -> {
            if (w.length() < 12) shortWords.getAndIncrement(w.length());
        });
        for (int i = 0; i < shortWords.length(); i++) {
            System.out.println(i + " -> " + shortWords.get(i));
        }
    }

    //Exercise 13
    public static void exercise13() throws IOException, URISyntaxException {
        System.out.println("Exercise 13");
        List<String> words = getWordsFromFile("war-and-peace.txt");
        Map<Integer, Long> shortWordsBySize =
                words.parallelStream()
                        .filter(w -> w.length() < 12)
                        .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        shortWordsBySize.entrySet().forEach(System.out::println);

    }

    public static void main(String... args) throws IOException, URISyntaxException {
//        exercise2();
//        exercise3();
//        exercise4();
//        exercise5();
//        exercise6();
//        System.out.println("is parallel "+isFinite(Stream.of(1.0, 2.0, 3.0).parallel()));
//        exercise8();
//        exercise10();
//        exercise12();
//        exercise13();


            ArrayList<Pair<? extends Serializable, ? extends Serializable>> pairs1
                    = new ArrayList<>();
            pairs1.add(new Pair<String, Integer>("Вася", 8));
            pairs1.add(new Pair<String, Integer>("Петя", 9));
            pairs1.add(new Pair<Integer, String>(1, "Валя"));


            ArrayList<Pair<? extends Serializable, ? extends Serializable>> pairs2 = new ArrayList<>();
            pairs2.add(new Pair<String, Integer>("Коля", 3));
            pairs2.add(new Pair<String, Integer>("Рома", 5));
            pairs2.add(new Pair<Integer, String>(1, "Варя"));

            ArrayList<Pair<? extends Serializable, ? extends Serializable>> pairs3 = new ArrayList<>();
            pairs3.add(new Pair<String, Integer>("Саня", 8));
            pairs3.add(new Pair<String, Integer>("Ваня", 2));
            pairs3.add(new Pair<Integer, String>(1, "Катя"));

        System.out.println(join1(Stream.of(pairs1,pairs2,pairs3)));
        System.out.println(join2(Stream.of(pairs1,pairs2,pairs3)));
        System.out.println(join3(Stream.of(pairs1,pairs2,pairs3)));
    }

    static class Pair<T,K>{
        final T t;
        final K k;
        Pair(T t , K k){
            this.t = t;
            this.k = k;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "t=" + t +
                    ", k=" + k +
                    '}';
        }
    }
}
