package com.ivanova;

import java.util.List;

public class FlightStatistic {

    private List<Flight> allFlights;
    private List<Flight> cancelledFlights;
    private List<Flight> corruptedFlights;

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(List<Flight> allFlights) {
        this.allFlights = allFlights;
    }

    public List<Flight> getCancelledFlights() {
        return cancelledFlights;
    }

    public void setCancelledFlights(List<Flight> cancelledFlights) {
        this.cancelledFlights = cancelledFlights;
    }

    public List<Flight> getCorruptedFlights() {
        return corruptedFlights;
    }

    public void setCorruptedFlights(List<Flight> corruptedFlights) {
        this.corruptedFlights = corruptedFlights;
    }

    public FlightStatistic() {
    }
}
