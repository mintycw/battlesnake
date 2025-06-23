package nl.hu.bep.battlesnake.models.game;

public class Game {
    private String id;
    private Ruleset ruleset;
    private String map;
    private int timeout;
    private String source;

    public Game() {}

    public Game(String id, Ruleset ruleset, String map, int timeout, String source) {
        this.id = id;
        this.ruleset = ruleset;
        this.map = map;
        this.timeout = timeout;
        this.source = source;
    }

    //    Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    Ruleset
    public Ruleset getRuleset() {
        return ruleset;
    }

    public void setRuleset(Ruleset ruleset) {
        this.ruleset = ruleset;
    }

    //    Map
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    //    Timeout
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    //    Source
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
