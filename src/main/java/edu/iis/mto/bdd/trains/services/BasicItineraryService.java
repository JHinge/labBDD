package edu.iis.mto.bdd.trains.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import edu.iis.mto.bdd.trains.model.Line;



public class BasicItineraryService implements IntineraryService {
    private final InMemoryTimetableService timetableService;
    private static final int MAXIMAL_TIME_TO_NEXT_DEPARTURE = 30;
    public BasicItineraryService(InMemoryTimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime departureTime) {
        Line line = timetableService.findLinesThrough(departure, destination).get(0);
        return timetableService.findArrivalTimes(line, departure).stream()
                .filter(time -> time.isAfter(departureTime) && time.isBefore(departureTime.plusMinutes(MAXIMAL_TIME_TO_NEXT_DEPARTURE)))
                .collect(Collectors.toList());
    }

}
