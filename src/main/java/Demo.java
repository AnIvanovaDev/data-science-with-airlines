import com.ivanova.Flight;
import com.ivanova.parsers.FlightParser;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        FlightParser flightParser = new FlightParser();
        URL resource = Demo.class.getResource("flights_small.csv");
        Path path = Paths.get(resource.toURI());
        List<Flight> flights = Files.lines(path).skip(1).map(line -> line.split(",")).map(flightParser::parse).collect(Collectors.toList());
        Map<Boolean, List<Flight>> flightsByCorruption = flights.parallelStream().collect(Collectors.groupingBy(Flight::isCorrupted));
        System.out.println("Takes: " + (System.currentTimeMillis() - start));
        System.out.println("All flights: " + flights.size());
        List<Flight> corruptedFlights = flightsByCorruption.get(true);
        System.out.println("Corrupted flights: " + corruptedFlights.size());
        List<Flight> properFlights = flightsByCorruption.get(false);
        System.out.println("Proper flights: " + properFlights.size());
        System.out.println("Sum of corrupted and proper flights: " + (corruptedFlights.size() + properFlights.size()));
        Map<Boolean, List<Flight>> flightsByCancellation = properFlights.stream().collect(Collectors.groupingBy(Flight::checkCancellation));
        List<Flight> canceledFlights = flightsByCancellation.get(true);
        List<Flight> finishedFlights = flightsByCancellation.get(false);

        //1-------------------------------------------------
        System.out.println(findCompanyWithHighestPercentOfCancelledFlights(properFlights));

    }

    //1.
    public static AbstractMap.SimpleEntry<String, Double> findCompanyWithHighestPercentOfCancelledFlights(List<Flight> source) {
        return source.stream()
                     .collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier(), Collectors.groupingBy(f -> f.checkCancellation())))
                     .entrySet()
                     .stream()
                     .map((e)-> {
                         Map<Boolean, List<Flight>> map = e.getValue();
                         List<Flight> cancelledFl = map.get(Boolean.TRUE);
                         List<Flight> finishedFl = map.get(Boolean.FALSE);
                         int canceled = cancelledFl.size();
                         int finished = finishedFl.size();
                         double canceledPart = Math.round(canceled * 10000 /(double)(canceled + finished))/100.0;
                         return new AbstractMap.SimpleEntry<>(e.getKey(), canceledPart);
                     })
                     .max(Map.Entry.comparingByValue())
                     .orElseGet(()-> new AbstractMap.SimpleEntry<>("", 0.0));
    }
}
