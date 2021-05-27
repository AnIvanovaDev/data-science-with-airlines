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
        //FileWriter.write("corrupted_flights", corruptedFlights);
        //FileWriter.write("proper_flights", properFlights);
        //FileWriter.write("canceled_flights", canceledFlights);
        //FileWriter.write("finished_flights", finishedFlights);
        //1.-----------------------------------------------
        //canceledFlights.stream().collect(Collectors.groupingBy(f-> f.getAircraftDetails().getUniqueCarrier()));

        Map<String, Map<Boolean, List<Flight>>> flightsByCompanyThenCancellationState = properFlights.stream()
                .collect(Collectors
                        .groupingBy(f -> f.getAircraftDetails().getUniqueCarrier(), Collectors.groupingBy(f -> f.checkCancellation())));

        Optional<Map.Entry<String, Double>> max = flightsByCompanyThenCancellationState
                .entrySet()
                .stream()
                .map(Demo::apply)
                .max(Map.Entry.comparingByValue());

        max.ifPresent(System.out::println);
    }

    private static Map.Entry<String, Double> apply(Map.Entry<String, Map<Boolean, List<Flight>>> entry) {
        String key = entry.getKey();
        Map<Boolean, List<Flight>> map = entry.getValue();
        List<Flight> cancelledFl = map.get(Boolean.TRUE);
        List<Flight> finishedFl = map.get(Boolean.FALSE);
        int canceled = cancelledFl.size();
        int finished = finishedFl.size();
        double canceledPart = canceled /(double)(canceled + finished) * 100;
        return new AbstractMap.SimpleEntry<>(key, canceledPart);
    }
}
