package ee.codehouse.worxbetter.agent.service.feign.mower;

public enum ConfigurationElement {
    START("settaggi", 11), STOP("settaggi", 12), ZONE_TRAINING("settaggi", 13),
    MOW_AFTER_RAIN("rit_pioggia", 0), ENABLE_EDGE_CUT("enab_bordo", 0), WORKING_PERCENT("percent_programmatore", 0), GARDEN_SIZE("indice_area", 0);
    private final String name;
    private final int key;

    ConfigurationElement(String name, int key) {
        this.name = name; this.key = key;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }
}
