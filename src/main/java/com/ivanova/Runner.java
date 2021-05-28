package com.ivanova;

import com.ivanova.io.FileService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Anastasiia Ivanova
 * @since 1.0.0
 */
public class Runner {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<String> strings =  fileService.readFileFromClassPath("/flights.csv");
        FlightStatistic flightStatistic = FlightStatistic.createInstance(strings);
        List<String> results = new ArrayList<>();
        FormattedOutput formattedOutput = new FormattedOutput();
        String answer1 = flightStatistic.findCompanyWithHighestPercentOfCancelledFlights();
        results.add(answer1);
        formattedOutput.addAnswer(1, answer1);
        String answer2 = flightStatistic.findTheMostFrequentCancellationReason();
        results.add(answer2);
        formattedOutput.addAnswer(2, answer2);
        String answer3 = flightStatistic.findTailNumWithTheMaxNumberOfMiles();
        results.add(answer3);
        formattedOutput.addAnswer(3,answer3);
        Integer answer4 = flightStatistic.findMostLoadedAirport();
        results.add(answer4.toString());
        formattedOutput.addAnswer(4, answer4);
        Integer answer5 = flightStatistic.findMostLoadedSourceAirport();
        results.add(answer5.toString());
        formattedOutput.addAnswer(5,answer5);
        Integer answer6 = flightStatistic.findMostLoadedSinkAirport();
        results.add(answer6.toString());
        formattedOutput.addAnswer(6,answer6);
        Long answer7 = flightStatistic.findNumberOfDelayedFlights();
        results.add(answer7.toString());
        formattedOutput.addAnswer(7, answer7.toString());
        String answer8 = flightStatistic.findMostOvertakenDelay();
        results.add(answer8);
        formattedOutput.addAnswer(8,answer8);
        String answer9 = flightStatistic.findAirportWithTheBiggestSumOfDepDelay();
        results.add(answer9);
        formattedOutput.addAnswer(9, answer9);
        formattedOutput.writeAnswers();
    }
}
