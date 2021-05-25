package com.ivanova;

public class Flight {
    private FlightTimeDetails timeDetails;
    private Airport originAirport;
    private Airport destAirport;

    @Override
    public String toString() {
        return "Flight{" +
                "timeDetails=" + timeDetails +
                ", originAirport=" + originAirport +
                ", destAirport=" + destAirport +
                ", aircraftDetails=" + aircraftDetails +
                ", isCanceled=" + isCanceled +
                ", cancellationCode=" + cancellationCode +
                ", isDiverted=" + isDiverted +
                ", distanceInMiles=" + distanceInMiles +
                '}';
    }

    private AircraftDetails aircraftDetails;
    private boolean isCanceled;
    private CancellationCode cancellationCode;
    private boolean isDiverted;
    private int distanceInMiles;

    public FlightTimeDetails getTimeDetails() {
        return timeDetails;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public AircraftDetails getAircraftDetails() {
        return aircraftDetails;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public CancellationCode getCancellationCode() {
        return cancellationCode;
    }

    public boolean isDiverted() {
        return isDiverted;
    }

    public int getDistanceInMiles() {
        return distanceInMiles;
    }

    private Flight() {
    }

    public boolean isCorrupted() {
        return false; // TODO: implement logic later
    }

    public static class Builder {
        private FlightTimeDetails timeDetails;
        private Airport originAirport;
        private Airport destAirport;
        private AircraftDetails aircraftDetails;
        private boolean isCanceled;
        private CancellationCode cancellationCode = CancellationCode.NONE;
        private boolean isDiverted;
        private int distanceInMiles;


        public Builder addFlightTimeDetails(FlightTimeDetails timeDetails) {
            this.timeDetails = timeDetails;
            return this;
        }

        public Builder addOriginAirport(Airport originAirport) {
            this.originAirport = originAirport;
            return this;
        }

        public Builder addDestinationAirport(Airport destAirport) {
            this.destAirport = destAirport;
            return this;
        }

        public Builder addAircraftDetails(AircraftDetails aircraftDetails) {
            this.aircraftDetails = aircraftDetails;
            return this;
        }

        public Builder addIsCanceled( boolean isCanceled) {
            this.isCanceled = isCanceled;
            return this;
        }

        public Builder addCancellationCode(CancellationCode cancellationCode) {
            this.cancellationCode = cancellationCode;
            return this;
        }

        public Builder addIsDiverted(boolean isDiverted) {
            this.isDiverted = isDiverted;
            return this;
        }

        public Builder addDistanceInMiles(int distanceInMiles) {
            this.distanceInMiles = distanceInMiles;
            return this;
        }

        public Flight build() {
            Flight flight = new Flight();
            flight.timeDetails = this.timeDetails;
            flight.originAirport = this.originAirport;
            flight.destAirport = this.destAirport;
            flight.aircraftDetails = this.aircraftDetails;
            flight.isCanceled = this.isCanceled;
            flight.cancellationCode = this.cancellationCode;
            flight.isDiverted = this.isDiverted;
            flight.distanceInMiles = this.distanceInMiles;
            return flight;
        }

    }
}
