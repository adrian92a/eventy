package pl.net.rogala.eventy.model;

public enum EventType {

    FUTURE("przyszłe"),
    CURRENT("aktualne"),
    ALL("wszystkie");

    private String label;

    EventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
