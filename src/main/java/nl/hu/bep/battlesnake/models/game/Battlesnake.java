package nl.hu.bep.battlesnake.models.game;

import nl.hu.bep.battlesnake.models.components.BattlesnakeCustomizations;
import nl.hu.bep.battlesnake.models.components.Coord;

import java.util.ArrayList;
import java.util.List;

public class Battlesnake {
    private String id;
    private String name;
    private int health;

    private List<Coord> body;
    private String latency;
    private Coord head;
    private int length;
    private String shout;
    private String squad;

    private BattlesnakeCustomizations customizations;

    public Battlesnake() {
        this.head = new Coord(0, 0);
        this.length = 3;
        this.shout = "why are we shouting??";
        this.squad = "1";
        this.customizations = new BattlesnakeCustomizations();
    }

    public Battlesnake(String id, String name, int health, List<Coord> body, String latency, Coord head, int length, String shout, String squad, BattlesnakeCustomizations customizations) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.body = body;
        this.latency = latency;
        this.head = head;
        this.length = length;
        this.shout = shout;
        this.squad = squad;
        this.customizations = customizations;
    }

    //    Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    Health
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    //    Body
    public List<Coord> getBody() {
        return body;
    }

    public void setBody(List<Coord> body) {
        this.body = body;
    }

    //    Latency
    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    //    Head
    public Coord getHead() {
        return head;
    }

    public void setHead(Coord head) {
        this.head = head;
    }

    //    Length
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    //    Shout
    public String getShout() {
        return shout;
    }

    public void setShout(String shout) {
        this.shout = shout;
    }

    //    Squad
    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    //    Customizations
    public BattlesnakeCustomizations getCustomizations() {
        return customizations;
    }

    public void setCustomizations(BattlesnakeCustomizations customizations) {
        this.customizations = customizations;
    }

    public String getDirection() {
        if (body == null || body.size() < 2) {
            return "unknown";
        }

        Coord head = body.get(0);
        Coord neck = body.get(1);

        if (head.getX() < neck.getX()) {
            return "left";
        } else if (head.getX() > neck.getX()) {
            return "right";
        } else if (head.getY() < neck.getY()) {
            return "down";
        } else {
            return "up";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Battlesnake{id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", health=").append(health);
        sb.append(", latency='").append(latency).append('\'');
        sb.append(", length=").append(length);
        sb.append(", shout='").append(shout).append('\'');
        sb.append(", squad='").append(squad).append('\'');

        sb.append(", head=").append(head);
        sb.append(", body=[");
        if (body != null) {
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.get(i));
                if (i < body.size() - 1) sb.append(", ");
            }
        }
        sb.append(']');

        sb.append(", customizations=").append(customizations);

        sb.append('}');
        return sb.toString();
    }
}
