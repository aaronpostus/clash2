package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Schematics.Schematic;
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
            building.setLayersBuilt(schematic.layersToBuild(percentageBuilt) + 1);
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
            building.sendMessage(Math.floor(p*100)+"% complete.");
        }
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
        building.pickup();
        building.state = new InHandBuildingState(building);
    }
}
