package edu.iis.mto.bdd.trains.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import edu.iis.mto.bdd.trains.model.Line;



public class BasicItineraryService implements IntineraryService {
    private final InMemoryTimetableService timetableService;

    public BasicItineraryService(InMemoryTimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime departureTime) {
        Line line = timetableService.findLinesThrough(departure, destination).get(0);
        return timetableService.findArrivalTimes(line, departure).stream()
                .filter(time -> time.isAfter(departureTime) && time.isBefore(departureTime.plusMinutes(25)))
                .collect(Collectors.toList());
    }

}
