package com.ivanova.parsers;

import com.ivanova.*;

public final class FlightParser implements Parser<Flight, String[]> {

    @Override
    public Flight parse(String[] input) {
        IntegerParser integerParser = new IntegerParser();
        BooleanParser booleanParser = new BooleanParser();
        FlightTimeDetailsParser flightTimeDetailsParser = new FlightTimeDetailsParser();

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
