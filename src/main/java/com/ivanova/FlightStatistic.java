package com.ivanova;

import com.ivanova.models.CancellationCode;
import com.ivanova.models.Flight;
import com.ivanova.parsers.FlightParser;
import com.ivanova.parsers.Parser;
import com.ivanova.util.Pair;

import java.time.temporal.ChronoField;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FlightStatistic {

    private List<Flight> allFlights;
    private List<Flight> properFlights;
    private List<Flight> cancelledFlights;
    private List<Flight> corruptedFlights;
    private List<Flight> finishedFlights;

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public List<Flight> getProperFlights() {return properFlights;}

    public List<Flight> getCancelledFlights() {
        return cancelledFlights;
    }

    public List<Flight> getCorruptedFlights() {
        return corruptedFlights;
    }

    public List<Flight> getFinishedFlights() {
        return finishedFlights;
    }

    private FlightStatistic(List<Flight> allFlights,
                            List<Flight> properFlights,
                            List<Flight> cancelledFlights,
                            List<Flight> corruptedFlights,
                            List<Flight> finishedFlights) {
        this.allFlights = allFlights;
        this.properFlights = properFlights;
        this.cancelledFlights = cancelledFlights;
        this.corruptedFlights = corruptedFlights;
        this.finishedFlights = finishedFlights;
    }

    public static FlightStatistic createInstance(List<String> input) {

        Parser<Flight, String[]> flightParser = new FlightParser();
        List<Flight> allFlights = input.stream()
                .map(line -> line.split(","))
                .map(flightParser::parse)
                .collect(Collectors.toList());

        Map<Boolean, List<Flight>> flightsByCorruption =
                allFlights.stream()
                        .collect(Collectors.groupingBy(Flight::isCorrupted));

        List<Flight> corruptedFlights = flightsByCorruption.get(true);
        List<Flight> properFlights = flightsByCorruption.get(false);

        Map<Boolean, List<Flight>> flightsByCancellation =
                properFlights.stream()
                        .collect(Collectors.groupingBy(Flight::checkCancellation));

        List<Flight> canceledFlights = flightsByCancellation.get(true);
        List<Flight> finishedFlights = flightsByCancellation.get(false);

        return new FlightStatistic(allFlights, properFlights, canceledFlights, corruptedFlights, finishedFlights);
    }

    //1.
    public String findCompanyWithHighestPercentOfCancelledFlights() {
        return properFlights.stream()
                .collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier(), Collectors.groupingBy(f -> f.checkCancellation())))
                .entrySet()
                .stream()
                .map((e) -> {
                    Map<Boolean, List<Flight>> map = e.getValue();
                    int canceled = map.get(Boolean.TRUE).size();
                    int finished = map.get(Boolean.FALSE).size();
                    double canceledPart = Math.round(canceled * 10000 / (double) (canceled + finished)) / 100.0;
                    return Pair.of(e.getKey(), canceledPart);
                })
                .max((pair1, pair2) -> Comparator.<Double>naturalOrder().compare(pair1.getValue(), pair2.getValue()))
                .orElseGet(() -> Pair.of("", 0.0))
                .toString();
    }

    //2.
    public String findTheMostFrequentCancellationReason() {
        return cancelledFlights.stream()
                .collect(Collectors.groupingBy(Flight::getCancellationCode))
                .entrySet().stream()
                .map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().size()))
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(CancellationCode.NONE, 0))
                .getKey()
                .toString();
    }

    //3.
    public String findTailNumWithTheMaxNumberOfMiles() {
        return finishedFlights.stream()
                .collect(Collectors.groupingBy(f -> f.getAircraftDetails().getTailNum()))
                .entrySet()
                .stream()
                .map(e -> {
                    long sum = e.getValue()
                            .stream()
                            .mapToLong(Flight::getDistanceInMiles)
                            .sum();
                    return Pair.of(e.getKey(), sum);
                })
                .max((pair1, pair2) -> Comparator.<Long>naturalOrder().compare(pair1.getValue(), pair2.getValue()))
                .orElseGet(() -> Pair.of("", 0L))
                .getKey();
    }

    //4.
    public int findMostLoadedAirport() {
        return finishedFlights.stream()
                .flatMap(it -> Stream.of(it.getDestAirport().getId(), it.getOriginAirport().getId()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0L))
                .getKey();
    }

    //5.
    public int findMostLoadedSourceAirport() {
        return finishedFlights.stream()
                .flatMap(flight -> Stream.of(Pair.of(flight.getOriginAirport().getId(), 1), Pair.of(flight.getDestAirport().getId(), -1)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0))
                .getKey();
    }

    //6.
    public int findMostLoadedSinkAirport() {
        return finishedFlights.stream()
                .flatMap(flight -> Stream.of(Pair.of(flight.getOriginAirport().getId(), -1), Pair.of(flight.getDestAirport().getId(), 1)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0))
                .getKey();
    }

    //7.
    public long findNumberOfDelayedFlights() {
        return finishedFlights.stream()
                .filter(f -> f.getAircraftDetails().getUniqueCarrier().equals("AA"))
                .filter(f -> (f.getTimeDetails().getArrDelayInMinutes() + f.getTimeDetails().getDepDelayInMinutes()) >= 60)
                .count();
    }

    //8.
    public String findMostOvertakenDelay() {
        Flight overtakenDelay = finishedFlights.stream()
                .filter(flight -> flight.getTimeDetails().getDepDelayInMinutes() > 0)
                .filter(flight -> flight.getTimeDetails().getArrDelayInMinutes() <= 0)
                .max((flight1, flight2) ->
                             Comparator.<Integer>naturalOrder().compare(
                                     flight1.getTimeDetails().getDepDelayInMinutes(),
                                     flight2.getTimeDetails().getDepDelayInMinutes())
                ).get();
        return String.format("%d,%d,%s", overtakenDelay.getTimeDetails().getFlightDate().get(ChronoField.DAY_OF_MONTH),
                             overtakenDelay.getTimeDetails().getDepDelayInMinutes(),
                             overtakenDelay.getAircraftDetails().getTailNum());
    }

    //9. В каком аэропорту больше всего задержек вылета? В суммарном значении сколько минут задержек вылета?
    public String findAirportWithTheBiggestSumOfDepDelay() {
        return finishedFlights.stream()
                .collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier()))
                .entrySet()
                .stream()
                .map(f -> {
                    long sumDepDelay = f.getValue()
                            .stream()
                            .reduce(0L,
                                    (delay, fl) -> delay + fl.getTimeDetails().getDepDelayInMinutes(),
                                    (delay1, delay2) -> delay1 + delay2);
                    return Pair.of(f.getKey(), sumDepDelay);
                })
                .max((pair1, pair2) -> Comparator.<Long>naturalOrder().compare(pair1.getValue(), pair2.getValue()))
                .orElseGet(() -> Pair.of("", 0L))
                .toString();
    }
}
