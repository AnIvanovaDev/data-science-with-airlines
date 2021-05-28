package com.ivanova.parsers;

import com.ivanova.models.ColumnHeaders;
import com.ivanova.models.FlightTimeDetails;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightTimeDetailsParser implements Parser<FlightTimeDetails, String[]> {

    @Override
    public FlightTimeDetails parse(String[] input) {
        
        LocalTimeParser localTimeParser = new LocalTimeParser();
        LocalDateParser localDateParser = new LocalDateParser();
        IntegerParser integerParser = new IntegerParser();
        LocalDate flightDate = localDateParser.parse(input[ColumnHeaders.FLIGHT_DATE.ordinal()]);
        LocalTime depTime = localTimeParser.parse(input[ColumnHeaders.DEP_TIME.ordinal()]);
        int depDelayInMinutes = integerParser.parse(input[ColumnHeaders.DEP_DELAY.ordinal()]);
        LocalTime arrTime = localTimeParser.parse(input[ColumnHeaders.ARR_TIME.ordinal()]);
        int arrDelayInMinutes = integerParser.parse(input[ColumnHeaders.ARR_DELAY.ordinal()]);
        LocalTime wheelsOff = localTimeParser.parse(input[ColumnHeaders.WHEELS_OFF.ordinal()]);
        LocalTime wheelsOn = localTimeParser.parse(input[ColumnHeaders.WHEELS_ON.ordinal()]);
        int airTimeInMinutes = integerParser.parse(input[ColumnHeaders.AIR_TIME.ordinal()]);

        return new FlightTimeDetails.Builder().addFlightDate(flightDate)
                                              .addDepartureTime(depTime)
                                              .addDepartureDelayInMinutes(depDelayInMinutes)
                                              .addArrivalTime(arrTime)
                                              .addArrivalDelayInMinutes(arrDelayInMinutes)
                                              .addWheelsOff(wheelsOff)
                                              .addWheelsOn(wheelsOn)
                                              .addAirTimeInMinutes(airTimeInMinutes).build();
    }
}
