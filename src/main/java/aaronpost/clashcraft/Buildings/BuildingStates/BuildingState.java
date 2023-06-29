package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Schematics.Schematic;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class BuildingState extends IBuildingState {
    private transient Building building;
    private transient Schematic schematic;
    private transient boolean pastedUpdate = false;
    public BuildingState(Building building) {
        this.building = building;
        this.schematic = building.getSchematic();
    }

    @Override
    public void update() {
        float percentageBuilt = building.getPercentageBuilt();
        if(percentageBuilt < 1f) {
            building.buildStep();
            if(schematic.constructionNeedsUpdate(building.getLayersBuilt(), percentageBuilt)) {
                building.setLayersBuilt(schematic.layersToBuild(percentageBuilt));
                building.paste();
            }
        }
        else if (!pastedUpdate){
            // gift box
            building.setLayersBuilt(-1);
            building.paste();
            pastedUpdate = true;
        }
    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
        this.schematic = building.getSchematic();
    }
    public void finishBuilding() {
        building.state = new IslandState(building);
        building.finishBuilding();
        building.paste();
    }
    @Override
    public void click() {
        float p = building.getPercentageBuilt();
        if(p >= 1f) {
            building.getArena().playSound(Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f,5f);
            finishBuilding();
            building.sendMessage("Finished building.");
        }
        else {
            building.getArena().playSound(Sound.BLOCK_NOTE_BLOCK_BASS, 1f,1f);
            building.sendMessage(ChatColor.GRAY + Globals.timeFromSeconds(building.getRemainingUpdateTime()) + " remaining.");
        }
    }

    @Override
    public void openMenu() {

    }

    @Override
    public void catchUp(float hoursToCatchUp) {
        building.buildCatchup(hoursToCatchUp);
        float percentageBuilt = building.getPercentageBuilt();
        if(percentageBuilt >= 1) {
            building.setLayersBuilt(-1);
        }
        else {
            building.setLayersBuilt(schematic.layersToBuild(percentageBuilt));
        }
        building.paste();
    }

    @Override
    public void visualUpdate() {

    }

    @Override
    public void place(int x, int z) {

    }

    @Override
    public void pickup() {
        building.pickup();
        building.state = new InHandBuildingState(building);
    }
}
