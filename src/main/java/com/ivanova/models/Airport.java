package com.ivanova.models;

public class Airport  implements Corruptable {
    private int id;
    private String shortcut;
    private String stateName;

    public Airport(int id, String shortcut, String stateName) {
        this.id = id;
        this.shortcut = shortcut;
        this.stateName = stateName;
    }

    public int getId() {
        return id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getStateName() {
        return stateName;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", shortcut='" + shortcut + '\'' +
                ", stateName='" + stateName + '\'' +
                '}';
    }

    @Override
    public boolean isCorrupted() {
        return (id == Integer.MIN_VALUE || shortcut == null || stateName == null);
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
            return new Airport(id, shortcut, stateName);
        }
    }
}
