package nl.hu.bep.battlesnake.models.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nl.hu.bep.battlesnake.models.components.RulesetSettings;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Ruleset {
    public String name;
    public String version;
    public RulesetSettings settings;

    public Ruleset() {}

    public Ruleset(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public Ruleset(String name, String version, RulesetSettings settings) {
        this(name, version);
        this.settings = settings;
    }

    //    Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    Version
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    //    Settings
    public RulesetSettings getSettings() {
        return settings;
    }

    public void setSettings(RulesetSettings settings) {
        this.settings = settings;
    }
}
