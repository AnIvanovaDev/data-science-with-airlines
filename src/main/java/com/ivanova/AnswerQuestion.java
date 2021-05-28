package com.ivanova;

import com.ivanova.util.Pair;

import java.time.temporal.ChronoField;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Anastasiia Ivanova
 * @since 1.0.0
 */
public class AnswerQuestion {

    //1.
    public static AbstractMap.SimpleEntry<String, Double> findCompanyWithHighestPercentOfCancelledFlights(
            List<Flight> source) {
        return source.stream().collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier(),
                                                             Collectors.groupingBy(f -> f.checkCancellation())))
                .entrySet().stream().map((e) -> {
                    Map<Boolean, List<Flight>> map = e.getValue();
                    int canceled = map.get(Boolean.TRUE).size();
                    int finished = map.get(Boolean.FALSE).size();
                    double canceledPart = Math.round(canceled * 10000 / (double) (canceled + finished)) / 100.0;
                    return new AbstractMap.SimpleEntry<>(e.getKey(), canceledPart);
                }).max(Map.Entry.comparingByValue()).orElseGet(() -> new AbstractMap.SimpleEntry<>("", 0.0));
    }

    //2.
    public static CancellationCode findTheMostFrequentCancellationReason(List<Flight> source) {
        return source.stream().collect(Collectors.groupingBy(Flight::getCancellationCode)).entrySet().stream()
                .map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().size()))
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(CancellationCode.NONE, 0)).getKey();
    }

    //3.
    public static String findTailNumWithTheMaxNumberOfMiles(List<Flight> source) {
        return source.stream().collect(Collectors.groupingBy(f -> f.getAircraftDetails().getTailNum())).entrySet()
                .stream().map(e -> {
                    long sum = e.getValue().stream().mapToLong(Flight::getDistanceInMiles).sum();
                    return new AbstractMap.SimpleEntry<String, Long>(e.getKey(), sum);
                }).max(Map.Entry.comparingByValue()).orElseGet(() -> new AbstractMap.SimpleEntry<>("", 0L)).getKey();
    }

    //4.
    public static int findMostLoadedAirport(List<Flight> source) {
        return source.stream().flatMap(it -> Stream.of(it.getDestAirport().getId(), it.getOriginAirport().getId()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0L))
                .getKey();
    }

    //5.
    public static int findMostLoadedSourceAirport(List<Flight> source) {
        return source.stream().flatMap(flight -> Stream
                .of(Pair.of(flight.getOriginAirport().getId(), 1), Pair.of(flight.getDestAirport().getId(), -1)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0))
                .getKey();
    }

    //6.
    public static int findMostLoadedSinkAirport(List<Flight> source) {
        return source.stream().flatMap(flight -> Stream
                .of(Pair.of(flight.getOriginAirport().getId(), -1), Pair.of(flight.getDestAirport().getId(), 1)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseGet(() -> new AbstractMap.SimpleEntry<>(0, 0))
                .getKey();
    }

    //7.
    public static long findNumberOfDelayedFlights(List<Flight> source) {
        return source.stream()
                .filter(f -> f.getAircraftDetails().getUniqueCarrier().equals("AA"))
                .filter(f ->(f.getTimeDetails().getArrDelayInMinutes() + f.getTimeDetails().getDepDelayInMinutes()) >=60)
                .count();
    }

    //8.
    public static String findMostOvertakenDelay(List<Flight> source) {
        Flight overtakenDelay = source.stream().filter(flight -> flight.getTimeDetails().getDepDelayInMinutes() > 0)
                .filter(flight -> flight.getTimeDetails().getArrDelayInMinutes() <= 0)
                .max((flight1, flight2) -> Comparator.<Integer>naturalOrder().compare(
                        flight1.getTimeDetails().getDepDelayInMinutes(), flight2.getTimeDetails().getDepDelayInMinutes()
                ))
                .get();
        return String.format("%d,%d,%s", overtakenDelay.getTimeDetails().getFlightDate().get(ChronoField.DAY_OF_MONTH),
                             overtakenDelay.getTimeDetails().getDepDelayInMinutes(),
                             overtakenDelay.getAircraftDetails().getTailNum());
    }

    //9. В каком аэропорту больше всего задержек вылета? В суммарном значении сколько минут задержек вылета?
    public static String findAirportWithTheBiggestSumOfDepDelay(List<Flight> source) {
        return source
                .stream()
                .collect(Collectors.groupingBy(f -> f.getAircraftDetails().getUniqueCarrier()))
                .entrySet()
                .stream()
                .map(f -> {
                    long sumDepDelay = f.getValue()
                            .stream()
                            .reduce(0L, (delay, fl) -> delay + fl.getTimeDetails().getDepDelayInMinutes(), (delay1, delay2) -> delay1 + delay2);
                    return Pair.of(f.getKey(), sumDepDelay);
                })
                .max((pair1, pair2) -> Comparator.<Long>naturalOrder().compare(pair1.getValue(), pair2.getValue()))
                .orElseGet(()->Pair.of("", 0L))
                .toString();
    }
}
