package com.company;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matching {
    Map<Resident, List<Hospital>> resPrefMap;
    Map<Hospital, List<Resident>> hosPrefMap;
    Map<Resident, Hospital> matchingMap = new HashMap<>();
    Map<Hospital, Integer> capacityMap = new TreeMap<>();

    public Matching(Problem problem) {
        resPrefMap = problem.resPrefMap;
        hosPrefMap = problem.hosPrefMap;
        capacityMap = problem.capacityMap;

        int index, size;
        Hospital theHospital;

        /// Ordonarea Mapului de preferinte a resedintelor dupa lista de preferinte, dupa nr de spitale preferate
        /// sursa: https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
        Stream<Map.Entry<Resident, List<Hospital>>> sortedStream =
                resPrefMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(new Comparator<List<Hospital>>() {
                            @Override
                            public int compare(List<Hospital> o1, List<Hospital> o2) {
                                return o1.size() - o2.size();
                            }
                        }));
        Map<Resident, List<Hospital>> resPrefMapSorted = sortedStream.collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        /// Se parcurg resedintele in ordine astfel: mai intai se aleg resedintele cu cele mai scurte liste de spitale preferate
        for (Resident resident : resPrefMapSorted.keySet()) {
            index = 0;
            size = resPrefMap.get(resident).size();
            /// Pentru fiecare resedinta se alege primul spital preferat care mai are capacitate disponibila
            while (index + 1 < size && capacityMap.get(resPrefMap.get(resident).get(index)) == 0) {
                index++;
            }
            /// cazul in care toate spitale preferate nu mai au capacitate
            if (capacityMap.get(resPrefMap.get(resident).get(index)) <= 0) {
                System.out.println("Error: matching imposibil");
                return;
            }
            theHospital = resPrefMap.get(resident).get(index);
            /// adaugam spitalul ales in solutie, mai exact in Map-ul de matching <Resident, Hospita>
            matchingMap.put(resident, theHospital);
            //decrementam capacitatea spitalului ales
            capacityMap.put(theHospital, capacityMap.get(theHospital) - 1);
        }
    }


    public void StableCheck() {
        Hospital selectedHospital;
        for (Resident selectedResident : matchingMap.keySet()) { 
            selectedHospital = matchingMap.get(selectedResident);
            for (Hospital hospital : resPrefMap.get(selectedResident)) {
                if (hospital == selectedHospital)
                    break;
                int residentFound = 0;
                for (Resident resident : hosPrefMap.get(hospital)) {
                    if (resident == selectedResident)
                        residentFound = 1;
                    else if (residentFound == 1)
                        if (matchingMap.get(resident) == hospital)
                            {
                                System.out.println("not stable");
                                return ;
                            }
                }
            }
        }
        System.out.println("stable");
        return ;
    }

    @Override
    public String toString() {
        return "Matching:\n" + matchingMap + "\ncapacity left:\n" + capacityMap;
    }
}
