package nl.hu.bep.battlesnake.models.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesetSettings {
    private int foodSpawnChance;
    private int minimumFood;
    private int hazardDamagePerTurn;

    private String hazardMap;
    private String hazardMapAuthor;

    private RoyaleSettings royale;
    private SquadSettings squad;

    public RulesetSettings() {}

    public RulesetSettings(int foodSpawnChance, int minimumFood, int hazardDamagePerTurn, RoyaleSettings settings, SquadSettings squad) {
        this.foodSpawnChance = foodSpawnChance;
        this.minimumFood = minimumFood;
        this.hazardDamagePerTurn = hazardDamagePerTurn;
        this.royale = settings;
        this.squad = squad;
    }

    //    Food spawn chance
    public int getFoodSpawnChance() {
        return foodSpawnChance;
    }

    public void setFoodSpawnChance(int foodSpawnChance) {
        this.foodSpawnChance = foodSpawnChance;
    }

    //    Minimum food
    public int getMinimumFood() {
        return minimumFood;
    }

    public void setMinimumFood(int minimumFood) {
        this.minimumFood = minimumFood;
    }

    //    Hazard damage per turn
    public int getHazardDamagePerTurn() {
        return hazardDamagePerTurn;
    }

    public void setHazardDamagePerTurn(int hazardDamagePerTurn) {
        this.hazardDamagePerTurn = hazardDamagePerTurn;
    }

    //    Royale settings
    public static class RoyaleSettings {
        private int shrinkEveryNTurns;

        public RoyaleSettings() {}

        public RoyaleSettings(int shrinkEveryNTurns) {
            this.shrinkEveryNTurns = shrinkEveryNTurns;
        }

        //        Shrink every N turns
        public int getShrinkEveryNTurns() {
            return shrinkEveryNTurns;
        }

        public void setShrinkEveryNTurns(int shrinkEveryNTurns) {
            this.shrinkEveryNTurns = shrinkEveryNTurns;
        }
    }

    //    Hazard map
    public String getHazardMap() {
        return hazardMap;
    }

    public void setHazardMap(String hazardMap) {
        this.hazardMap = hazardMap;
    }

    //    Hazard map author
    public String getHazardMapAuthor() {
        return hazardMapAuthor;
    }

    public void setHazardMapAuthor(String hazardMapAuthor) {
        this.hazardMapAuthor = hazardMapAuthor;
    }

    //    Royale
    public RoyaleSettings getRoyale() {
        return royale;
    }

    public void setRoyale(RoyaleSettings royale) {
        this.royale = royale;
    }

    public static class SquadSettings {
        private boolean allowBodyCollisions;
        private boolean sharedElimination;
        private boolean sharedHealth;
        private boolean sharedLength;

        public SquadSettings() {}

        public SquadSettings(boolean allowBodyCollisions, boolean sharedElimination, boolean sharedHealth, boolean sharedLength) {
            this.allowBodyCollisions = allowBodyCollisions;
            this.sharedElimination = sharedElimination;
            this.sharedHealth = sharedHealth;
            this.sharedLength = sharedLength;
        }

        //        Is allow body collision
        public boolean isAllowBodyCollisions() {
            return allowBodyCollisions;
        }

        public void setAllowBodyCollisions(boolean allowBodyCollisions) {
            this.allowBodyCollisions = allowBodyCollisions;
        }

        //        Shared elimination
        public boolean isSharedElimination() {
            return sharedElimination;
        }

        public void setSharedElimination(boolean sharedElimination) {
            this.sharedElimination = sharedElimination;
        }

        //        Shared health
        public boolean isSharedHealth() {
            return sharedHealth;
        }

        public void setSharedHealth(boolean sharedHealth) {
            this.sharedHealth = sharedHealth;
        }

        //        Shared length
        public boolean isSharedLength() {
            return sharedLength;
        }

        public void setSharedLength(boolean sharedLength) {
            this.sharedLength = sharedLength;
        }
    }

    //    Squad
    public SquadSettings getSquad() {
        return squad;
    }

    public void setSquad(SquadSettings squad) {
        this.squad = squad;
    }
}