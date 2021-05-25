package com.ivanova;

public class AircraftDetails {

    @Override
    public String toString() {
        return "AircraftDetails{" +
                "uniqueCarrier='" + uniqueCarrier + '\'' +
                ", tailNum='" + tailNum + '\'' +
                '}';
    }

    private String uniqueCarrier;
    private String tailNum;


    public String getUniqueCarrier() {
        return uniqueCarrier;
    }

    public String getTailNum() {
        return tailNum;
    }
    private AircraftDetails() {

    }

    public static class Builder {
        private String uniqueCarrier;
        private String tailNum;


        public Builder addUniqueCarrier(String uniqueCarrier) {
            this.uniqueCarrier = uniqueCarrier;
            return this;
        }

        public Builder addTailNumber(String tailNum) {
            this.tailNum = tailNum;
            return this;
        }

        public AircraftDetails build() {
            AircraftDetails aircraftDetails = new AircraftDetails();
            aircraftDetails.uniqueCarrier = this.uniqueCarrier;
            aircraftDetails.tailNum = this.tailNum;
            return aircraftDetails;
        }
    }
}
