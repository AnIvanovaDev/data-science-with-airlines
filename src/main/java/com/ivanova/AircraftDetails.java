package com.ivanova;

public class AircraftDetails implements Corruptable {

    private String uniqueCarrier;
    private String tailNum;

    private AircraftDetails(String uniqueCarrier, String tailNum) {
        this.uniqueCarrier = uniqueCarrier;
        this.tailNum = tailNum;
    }

    public String getUniqueCarrier() {
        return uniqueCarrier;
    }
    public String getTailNum() {
        return tailNum;
    }

    @Override
    public boolean isCorrupted() {
        return (uniqueCarrier == null || tailNum == null);
    }

    @Override
    public String toString() {
        return "AircraftDetails{" +
                "uniqueCarrier='" + uniqueCarrier + '\'' +
                ", tailNum='" + tailNum + '\'' +
                '}';
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
            return new AircraftDetails(uniqueCarrier, tailNum);
        }
    }
}
