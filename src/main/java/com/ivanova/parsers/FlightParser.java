package com.ivanova.parsers;

import com.ivanova.*;

import java.util.ArrayList;
import java.util.List;

public final class FlightParser implements Parser<Flight, String[]> {

    Parser<Integer, String> integerParser;
    Parser<Boolean, String> booleanParser;
    Parser<FlightTimeDetails, String[]> flightTimeDetailsParser;

    public FlightParser() {
        integerParser = new IntegerParser();
        booleanParser = new BooleanParser();
        flightTimeDetailsParser = new FlightTimeDetailsParser();
    }

    @Override
    public Flight parse(String[] input) {
        FlightTimeDetails flightTimeDetails = flightTimeDetailsParser.parse(input);

        int originAirportId = integerParser.parse(input[ColumnHeaders.ORIGIN_AIRPORT_ID.ordinal()]);

        Airport originAirport = new Airport.Builder().addId(originAirportId)
                .addShortcut(input[ColumnHeaders.ORIGIN.ordinal()])
                .addStateName(input[ColumnHeaders.ORIGIN_STATE_NAME.ordinal()]).build();

        int destAirportId = integerParser.parse(input[ColumnHeaders.DEST_AIRPORT_ID.ordinal()]);

        Airport destAirport = new Airport.Builder().addId(destAirportId)
                .addShortcut(input[ColumnHeaders.DEST.ordinal()])
                .addStateName(input[ColumnHeaders.DEST_STATE_NAME.ordinal()]).build();

        AircraftDetails aircraftDetails = new AircraftDetails.Builder().addUniqueCarrier(input[ColumnHeaders.UNIQUE_CARRIER.ordinal()])
                .addTailNumber(input[ColumnHeaders.TAIL_NUM.ordinal()]).build();

        boolean isCanceled = booleanParser.parse(input[ColumnHeaders.CANCELLED.ordinal()]);
        CancellationCode cancellationCode = CancellationCode.parse(input[ColumnHeaders.CANCELLATION_CODE.ordinal()]);
        boolean isDiverted = booleanParser.parse(input[ColumnHeaders.DIVERTED.ordinal()]);
        int distanceInMiles = integerParser.parse(input[ColumnHeaders.DISTANCE.ordinal()]);

        return new Flight.Builder().addFlightTimeDetails(flightTimeDetails)
                .addOriginAirport(originAirport)
                .addDestinationAirport(destAirport)
                .addAircraftDetails(aircraftDetails)
                .addIsCanceled(isCanceled)
                .addCancellationCode(cancellationCode)
                .addIsDiverted(isDiverted)
                .addDistanceInMiles(distanceInMiles).build();
    }
}
