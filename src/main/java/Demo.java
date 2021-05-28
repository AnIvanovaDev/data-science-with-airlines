import com.ivanova.CancellationCode;
import com.ivanova.Flight;
import com.ivanova.parsers.FlightParser;
import com.ivanova.util.Pair;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoField;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) throws Exception {
        FlightParser flightParser = new FlightParser();
        URL resource = Demo.class.getResource("flights_small.csv");
        Path path = Paths.get(resource.toURI());
        List<Flight> flights = Files.lines(path).skip(1).map(line -> line.split(",")).map(flightParser::parse)
                .collect(Collectors.toList());
        Map<Boolean, List<Flight>> flightsByCorruption =
                flights.stream().collect(Collectors.groupingBy(Flight::isCorrupted));
        System.out.println("Takes: " + (System.currentTimeMillis() - start));
        System.out.println("All flights: " + flights.size());
        List<Flight> corruptedFlights = flightsByCorruption.get(true);
        System.out.println("Corrupted flights: " + corruptedFlights.size());
        List<Flight> properFlights = flightsByCorruption.get(false);
        System.out.println("Proper flights: " + properFlights.size());
        System.out.println("Sum of corrupted and proper flights: " + (corruptedFlights.size() + properFlights.size()));
        Map<Boolean, List<Flight>> flightsByCancellation =
                properFlights.stream().collect(Collectors.groupingBy(Flight::checkCancellation));
        List<Flight> canceledFlights = flightsByCancellation.get(true);
        List<Flight> finishedFlights = flightsByCancellation.get(false);
        System.out.println("Canceled flights: " + canceledFlights.size());
        System.out.println("Finished flights: " + finishedFlights.size());
        Map<String, Integer> collect =
                canceledFlights.stream().collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier()))
                        .entrySet().stream()
                        .map(e -> new SimpleEntry<String, Integer>(e.getKey(), e.getValue().size()))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        Map<String, Integer> collect2 =
                finishedFlights.stream().collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier()))
                        .entrySet().stream()
                        .map(e -> new SimpleEntry<String, Integer>(e.getKey(), e.getValue().size()))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        System.out.println(collect);
        System.out.println(collect2);
//        //1-------------------------------
//        System.out.println(findCompanyWithHighestPercentOfCancelledFlights(properFlights));
//        //2-------------------------------
//        System.out.println(findTheMostFrequentCancellationReason(canceledFlights));
//        //3-------------------------------
//        System.out.println(findTailNumWithTheMaxNumberOfMiles(finishedFlights));
//        //4-------------------------------
//        System.out.println(findMostLoadedAirport(finishedFlights));
//        //5-------------------------------
//        System.out.println(findMostLoadedSourceAirport(finishedFlights));
//        //6-------------------------------
//        System.out.println(findMostLoadedSinkAirport(finishedFlights));
//        //7-------------------------------
//        System.out.println(findNumberOfDelayedFlights(finishedFlights));
//        //8-------------------------------
//        System.out.println(findMostOvertakenDelay(finishedFlights));
//        //9-------------------------------
//        System.out.println(findAirportWithTheBiggestSumOfDepDelay(finishedFlights));
    }

}
