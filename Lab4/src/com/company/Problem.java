package com.company;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem {
    Map<Resident, List<Hospital>> resPrefMap;
    Map<Hospital, List<Resident>> hosPrefMap;
    Map<Hospital, Integer> capacityMap = new TreeMap<>();

    public Problem(Map<Resident, List<Hospital>> resPrefMap, Map<Hospital, List<Resident>> hosPrefMap) {
        this.resPrefMap = resPrefMap;
        this.hosPrefMap = hosPrefMap;
        for (Hospital hospital : hosPrefMap.keySet())
            capacityMap.put(hospital, hospital.capacity);
    }

    public Problem() {
        /// Use a third-party library in order to generate random fake names for residents and hospitals:
        Faker faker = new Faker();
        int numberOfH = (int) (Math.random() * 4) + 2;
        int numberOfR = (int) (Math.random() * 5) + numberOfH;
        int randomNumber, randomIndex;

        List<Resident> residentList = IntStream.rangeClosed(0, numberOfR)
                .mapToObj(i -> new Resident(faker.address().secondaryAddress())).collect(Collectors.toList());
        Set<Hospital> hospitalSet = IntStream.rangeClosed(0, numberOfH)
                .mapToObj(i -> new Hospital(faker.university().name(), (int) (Math.random() * numberOfR) + 1)).collect(Collectors.toCollection(TreeSet::new));

        /// Map<Resident, List<Hospital>
        List<Hospital> copyOfHospitalSet;
        List<Hospital> selectedHospitals = new ArrayList<>();
        resPrefMap = new HashMap<>();
        for (Resident resident : residentList) {
            randomNumber = (int) (Math.random() * (numberOfH)) + 1;
            copyOfHospitalSet = new ArrayList<>(hospitalSet);
            selectedHospitals.clear();
            for (int i = 0; i < randomNumber; i++) {
                randomIndex = (int) (Math.random() * copyOfHospitalSet.size());
                selectedHospitals.add(copyOfHospitalSet.get(randomIndex));
                copyOfHospitalSet.remove(randomIndex);
            }
            resPrefMap.put(resident, new ArrayList<>(selectedHospitals));
        }
        /// Map<Hospital, List<Resident>
        List<Resident> copyOfResidentList;
        List<Resident> selectedResident = new ArrayList<>();
        hosPrefMap = new TreeMap<>();
        for (Hospital hospital : hospitalSet) {
            randomNumber = (int) (Math.random() * (numberOfR)) + 1;
            copyOfResidentList = new ArrayList<>(residentList);
            selectedHospitals.clear();
            for (int i = 0; i < randomNumber; i++) {
                randomIndex = (int) (Math.random() * copyOfResidentList.size());
                selectedResident.add(copyOfResidentList.get(randomIndex));
                copyOfResidentList.remove(randomIndex);
            }
            hosPrefMap.put(hospital, new ArrayList<>(selectedResident));
        }
        ///  Map<Hospital, Integer> capacityMap
        for (Hospital hospital : hosPrefMap.keySet())
            capacityMap.put(hospital, hospital.capacity);
    }

    @Override
    public String toString() {
        return "\nProblem\n" +
                resPrefMap +
                "\n" + hosPrefMap +
                "\n" + capacityMap;
    }
}
