package com.ivanova;

public class Flight {
    private FlightTimeDetails timeDetails;
    private Airport originAirport;
    private Airport destAirport;
    private AircraftDetails aircraftDetails;
    private boolean isCanceled;
    private CancellationCode cancellationCode;
    private boolean isDiverted;

    private Flight(FlightTimeDetails timeDetails,
                   Airport originAirport,
                   Airport destAirport,
                   AircraftDetails aircraftDetails,
                   boolean isCanceled,
                   CancellationCode cancellationCode,
                   boolean isDiverted,
                   int distanceInMiles) {

        this.timeDetails = timeDetails;
        this.originAirport = originAirport;
        this.destAirport = destAirport;
        this.aircraftDetails = aircraftDetails;
        this.isCanceled = isCanceled;
        this.cancellationCode = cancellationCode;
        this.isDiverted = isDiverted;
        this.distanceInMiles = distanceInMiles;
    }

    private int distanceInMiles;


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

    public boolean isCorrupted() {
            if(!checkCancellation()) {
                return timeDetails.isCorrupted()
                        || originAirport.isCorrupted()
                        || destAirport.isCorrupted()
                        || aircraftDetails.isCorrupted()
                        || distanceInMiles == Integer.MIN_VALUE
                        || cancellationCode != CancellationCode.NONE;
            }

           return cancellationCode == CancellationCode.NONE
                   || this.timeDetails.getDepTime() != null
                   || this.timeDetails.getArrTime() !=null;
    }

    public boolean checkCancellation() {
        return this.isCanceled
                && cancellationCode != CancellationCode.NONE
                && this.timeDetails.getDepTime() == null
                && this.timeDetails.getArrTime() == null ;
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
            return new Flight(timeDetails,
                                       originAirport,
                                       destAirport,
                                       aircraftDetails,
                                       isCanceled,
                                       cancellationCode,
                                       isDiverted,
                                       distanceInMiles);
        }

    }
}
