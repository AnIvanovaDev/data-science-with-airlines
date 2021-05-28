package com.ivanova.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightTimeDetails  implements Corruptable {
    private LocalDate flightDate;
    private LocalTime depTime;
    private int depDelayInMinutes;
    private LocalTime arrTime;
    private int arrDelayInMinutes;
    private LocalTime wheelsOff;
    private LocalTime wheelsOn;
    private int airTimeInMinutes;

    private FlightTimeDetails(LocalDate flightDate,
                              LocalTime depTime,
                              int depDelayInMinutes,
                              LocalTime arrTime,
                              int arrDelayInMinutes,
                              LocalTime wheelsOff,
                              LocalTime wheelsOn,
                              int airTimeInMinutes) {
        this.flightDate = flightDate;
        this.depTime = depTime;
        this.depDelayInMinutes = depDelayInMinutes;
        this.arrTime = arrTime;
        this.arrDelayInMinutes = arrDelayInMinutes;
        this.wheelsOff = wheelsOff;
        this.wheelsOn = wheelsOn;
        this.airTimeInMinutes = airTimeInMinutes;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public LocalTime getDepTime() {
        return depTime;
    }

    public int getDepDelayInMinutes() {
        return depDelayInMinutes;
    }

    public LocalTime getArrTime() {
        return arrTime;
    }

    public int getArrDelayInMinutes() {
        return arrDelayInMinutes;
    }

    public LocalTime getWheelsOff() {
        return wheelsOff;
    }

    public LocalTime getWheelsOn() {
        return wheelsOn;
    }

    public int getAirTimeInMinutes() {
        return airTimeInMinutes;
    }

    @Override
    public String toString() {
        return "FlightTimeDetails{" +
                "flightDate=" + flightDate +
                ", depTime=" + depTime +
                ", depDelayInMinutes=" + depDelayInMinutes +
                ", arrTime=" + arrTime +
                ", arrDelayInMinutes=" + arrDelayInMinutes +
                ", wheelsOff=" + wheelsOff +
                ", wheelsOn=" + wheelsOn +
                ", airTimeInMinutes=" + airTimeInMinutes +
                '}';
    }

    @Override
    public boolean isCorrupted() {
        return (flightDate == null
                || depTime == null
                || depDelayInMinutes == Integer.MIN_VALUE
                || arrTime == null
                || arrDelayInMinutes == Integer.MIN_VALUE
                || wheelsOff == null
                || wheelsOn == null
                || airTimeInMinutes == Integer.MIN_VALUE);
    }

    public static class Builder {
        private LocalDate flightDate;
        private LocalTime depTime;
        private int depDelayInMinutes;
        private LocalTime arrTime;
        private int arrDelayInMinutes;
        private LocalTime wheelsOff;
        private LocalTime wheelsOn;
        private int airTimeInMinutes;

        public Builder addFlightDate(LocalDate flightDate) {
            this.flightDate = flightDate;
            return this;
        }

        public Builder addDepartureTime(LocalTime depTime) {
            this.depTime = depTime;
            return this;
        }

        public Builder addDepartureDelayInMinutes(int depDelayInMinutes) {
            this.depDelayInMinutes = depDelayInMinutes;
            return this;
        }

        public Builder addArrivalTime(LocalTime arrTime) {
            this.arrTime = arrTime;
            return this;
        }

        public Builder addArrivalDelayInMinutes(int arrDelayInMinutes) {
            this.arrDelayInMinutes = arrDelayInMinutes;
            return this;
        }

        public Builder addWheelsOff(LocalTime wheelsOff) {
            this.wheelsOff = wheelsOff;
            return this;
        }

        public Builder addWheelsOn(LocalTime wheelsOn) {
            this.wheelsOn = wheelsOn;
            return this;
        }

        public Builder addAirTimeInMinutes(int airTimeInMinutes) {
            this.airTimeInMinutes = airTimeInMinutes;
            return this;
        }

        public FlightTimeDetails build() {
            return  new FlightTimeDetails(flightDate,
                                          depTime,
                                          depDelayInMinutes,
                                          arrTime,
                                          arrDelayInMinutes,
                                          wheelsOff,
                                          wheelsOn,
                                          airTimeInMinutes);
        }
    }
}
