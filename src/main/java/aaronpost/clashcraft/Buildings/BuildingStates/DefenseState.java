package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Raid;
import org.bukkit.ChatColor;

public class DefenseState extends IBuildingState {
    private IBuildingState previousState;
    private final transient Raid raid;
    private transient Building building;
    private transient float hp;
    private final transient int maxhp;
    public DefenseState(Building building, Raid raid, IBuildingState previousState) {
        this.previousState = previousState;
        this.building = building;
        this.raid = raid;
        this.maxhp = building.getMaxHitpoints();
        this.hp = maxhp;
    }
    public void restoreState(Building building) {
        building.state = previousState;
    }
    @Override
    public void update() {

    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
    }

    @Override
    public void click() {
        building.sendMessage("HP: " + (int) Math.ceil(hp) + "/" + maxhp);
    }

    @Override
    public void openMenu() {

    }

    @Override
    public void catchUp(float hoursToCatchUp) {

    }

    @Override
    public void visualUpdate() {

    }

    @Override
    public void place(int x, int z) {

    }

    @Override
    public void pickup() {

    }
    @Override
    public void damage(int amountToDamage) {
        this.hp -= amountToDamage;
        System.out.println("damage" + building.getDisplayName());
        // building dies
        if(this.hp <= 0) {
            this.hp = 0;
            building.getArena().sendActionBar(ChatColor.GRAY + "Destroyed " + building.getDisplayName());
            building.state = new DestroyedState(building, previousState);
            raid.destroyBuilding(building);
        }
    }
}
