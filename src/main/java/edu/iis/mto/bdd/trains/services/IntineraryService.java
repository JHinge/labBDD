package edu.iis.mto.bdd.trains.services;

import java.util.List;

import org.joda.time.LocalTime;

public interface IntineraryService {
     List<LocalTime> findNextDepartures(String departure, String destination, LocalTime startTime);

    

}
