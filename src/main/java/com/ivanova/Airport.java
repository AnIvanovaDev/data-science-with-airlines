package com.ivanova;

public class Airport {
    private int id;

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", shortcut='" + shortcut + '\'' +
                ", stateName='" + stateName + '\'' +
                '}';
    }

    private String shortcut;
    private String stateName;

    public int getId() {
        return id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getStateName() {
        return stateName;
    }

    private Airport() {

    }

    public static class Builder {
        private int id;
        private String shortcut;
        private String stateName;

        public Builder addId(int id) {
            this.id = id;
            return this;
        }
        public Builder addShortcut(String shortcut) {
            this.shortcut = shortcut;
            return this;
        }
        public Builder addStateName(String stateName) {
            this.stateName = stateName;
            return this;
        }

        public Airport build() {
            Airport airport = new Airport();
            airport.id = this.id;
            airport.shortcut = this.shortcut;
            airport.stateName = this.stateName;
            return airport;
        }
    }
}
