package com.company;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        List<Resident> residentList;
        residentList = IntStream.rangeClosed(0, 3)
                .mapToObj(i -> new Resident("R" + i)).collect(Collectors.toList());
        Collections.sort(residentList, new Comparator<Resident>() {
            @Override
            public int compare(Resident u1, Resident u2) {
                return u1.toString().compareTo(u2.toString());
            }
        });
        int capacity[] = new int[]{1, 2, 2};
        Set<Hospital> hospitalSet = IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new Hospital("H" + i, capacity[i])).collect(Collectors.toCollection(TreeSet::new));

        List<Hospital> hospitalList = new ArrayList<Hospital>(hospitalSet);

        Map<Resident, List<Hospital>> resPrefMap = new HashMap<>();
        resPrefMap.put(residentList.get(0), Arrays.asList(hospitalList.get(0), hospitalList.get(1), hospitalList.get(2)));
        resPrefMap.put(residentList.get(1), Arrays.asList(hospitalList.get(0), hospitalList.get(1), hospitalList.get(2)));
        resPrefMap.put(residentList.get(2), Arrays.asList(hospitalList.get(0), hospitalList.get(1)));
        resPrefMap.put(residentList.get(3), Arrays.asList(hospitalList.get(0), hospitalList.get(2)));


        Map<Hospital, List<Resident>> hosPrefMap = new TreeMap<>();
        hosPrefMap.put(hospitalList.get(0), Arrays.asList(residentList.get(3), residentList.get(0), residentList.get(1), residentList.get(2)));
        hosPrefMap.put(hospitalList.get(2), Arrays.asList(residentList.get(0), residentList.get(1), residentList.get(3)));
        hosPrefMap.put(hospitalList.get(1), Arrays.asList(residentList.get(0), residentList.get(2), residentList.get(1)));

        Map<Hospital, Integer> capacityMap = new HashMap<>();

        /// exemplul din Laborator
        Problem problem = new Problem(resPrefMap, hosPrefMap);
        System.out.println(problem);

        Matching matching = new Matching(problem);
        System.out.println(matching);
        matching.StableCheck();

        // random instance
        problem = new Problem();
        System.out.println(problem);

        matching = new Matching(problem);
        System.out.println(matching);
        matching.StableCheck();

        /// Verify if the matching produced by my algorithm is stable.
        /// Valorile Error sunt de la algoritmul de matching: acesta afiseaza Error cand nu mai poate asigna niciun spital unei resedinte
        System.out.println("\nFOR 20 times check Stable");
        for (int i = 0; i < 20; i++) {
            problem = new Problem();
            matching = new Matching(problem);
            matching.StableCheck();
        }

        //Java Stream API
        /*
            System.out.println("Residinte pt h0 si h2");
            List<Resident> query = resPrefMap.entrySet().stream()
                    .filter(e -> e.getValue().contains(hospitalList.get(0)))
                    .filter(e -> e.getValue().contains(hospitalList.get(2)))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            List<Resident> query3 = residentList.stream()
                    .filter(resident -> resPrefMap.get(resident).contains(hospitalList.get(0)))
                    .filter(resident -> resPrefMap.get(resident).contains(hospitalList.get(2)))
                    .collect(Collectors.toList());
            for (Resident resident : query) {
                System.out.print(resident.getName() + " ");
            }
            System.out.println();
            for (Resident resident : query3) {
                System.out.print(resident.getName() + " ");
            }

            System.out.println("\nSpitale care au in top r0");
            List<Hospital> query2 = hospitalSet.stream()
                    .filter(hospital -> hosPrefMap.get(hospital).get(0).equals(residentList.get(0)))
                    .collect(Collectors.toList());
            for (Hospital hospital : query2) {
                System.out.print(hospital.getName() + " ");
            }
         */
    }
}
