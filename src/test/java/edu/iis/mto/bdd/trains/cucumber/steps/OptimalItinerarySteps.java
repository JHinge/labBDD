package edu.iis.mto.bdd.trains.cucumber.steps;

import java.util.List;

import org.joda.time.LocalTime;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;
import edu.iis.mto.bdd.trains.services.BasicItineraryService;
import edu.iis.mto.bdd.trains.services.InMemoryTimetableService;
import edu.iis.mto.bdd.trains.services.IntineraryService;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;;

public class OptimalItinerarySteps {

    private IntineraryService intineraryService;
    private LocalTime startTime;
    private String destination;
    private String departure;

    @Zakładając("^pociągi linii \"(.*)\" z \"(.*)\" odjeżdżają ze stacji \"(.*)\" do \"(.*)\" o$")
    public void givenArrivingTrains(String line, String lineStart, String departure, String destination,
            @Transform(JodaLocalTimeConverter.class) List<LocalTime> departureTimes) {
        this.departure = departure;
        this.destination = destination;
        InMemoryTimetableService inMemoryTimetableService = new InMemoryTimetableService();
        intineraryService = new BasicItineraryService(inMemoryTimetableService);

    }

    @Gdy("^chcę podróżować z \"([^\"]*)\" do \"([^\"]*)\" o (.*)$")
    public void whenIWantToTravel(String departure, String destination, @Transform(JodaLocalTimeConverter.class) LocalTime startTime) {
        this.startTime = startTime;
    }

    @Wtedy("^powinienem uzyskać informację o pociągach o:$")
    public void shouldBeInformedAbout(@Transform(JodaLocalTimeConverter.class) List<LocalTime> expectedTrainTimes) {
        assertThat(intineraryService.findNextDepartures(departure, destination, startTime), equalTo(expectedTrainTimes));
    }

}
