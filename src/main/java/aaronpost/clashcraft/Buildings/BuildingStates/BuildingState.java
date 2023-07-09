package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuilderHut;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.Troops.NPCFactory;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildingState extends IBuildingState {
    private transient Building building;
    private transient Schematic schematic;
    private transient boolean pastedUpdate = false;
    private NPC builderNPC = null;
    private transient List<Location> locations;
    private transient Random random;
    public BuildingState(Building building) {
        this.building = building;
        this.schematic = building.getSchematic();
        if(building instanceof Wall || building instanceof BuilderHut || building.getPercentageBuilt() >= 1f) {
            return;
        }
        this.locations = initializeLocations();
        this.random = new Random();
        this.builderNPC = NPCFactory.createBuilderNPC(locations.get(random.nextInt(locations.size())));
    }
    private List<Location> initializeLocations() {
        Arena arena = building.getArena();
        Island island = arena.getIsland();
        System.out.println(building.getGridLoc().first + " " + building.getGridLoc().second);
        List<Pair<Integer,Integer>> pairs = island.getOuterRing(building);
        List<Location> locations = new ArrayList<>();
        for(Pair<Integer,Integer> loc: pairs) {
            locations.add(arena.getAbsLocationFromGridLoc(loc.first,loc.second,0));
        }
        return locations;
    }
    @Override
    public void update() {
        float percentageBuilt = building.getPercentageBuilt();
        building.buildStep();
        if(percentageBuilt < 1f) {
            if(schematic.constructionNeedsUpdate(building.getLayersBuilt(), percentageBuilt)) {
                building.setLayersBuilt(schematic.layersToBuild(percentageBuilt));
                building.paste();
            }
            if(builderNPC != null) {
                if(random.nextInt(2)==1 && !builderNPC.getNavigator().isNavigating()) {
                    PlayerAnimation.ARM_SWING.play((Player) builderNPC.getEntity());
                    building.getArena().getPlayer().playSound(builderNPC.getEntity().getLocation(),
                            Sound.BLOCK_ANVIL_HIT, 1f,1f);
                    builderNPC.faceLocation(building.getArena().getIsland().getCenterBuildingLoc(building,1));
                }
                if(random.nextInt(10) == 0) {
                    builderNPC.getNavigator().setTarget(locations.get(random.nextInt(locations.size())));
                }
            }
        }
        else if (!pastedUpdate){
            // gift box
            if(builderNPC != null) {
                builderNPC.destroy();
            }
            building.setLayersBuilt(-1);
            building.paste();
            pastedUpdate = true;
        }
    }

    @Override
    public void refreshReferences(Building building) {
        float percentageBuilt = building.getPercentageBuilt();
        this.building = building;
        this.schematic = building.getSchematic();
        if(percentageBuilt < 1f && builderNPC == null) {
            this.locations = initializeLocations();
            this.random = new Random();
            this.builderNPC = NPCFactory.createBuilderNPC(locations.get(random.nextInt(locations.size())));
        }
    }
    public void finishBuilding() {
        building.state = new IslandState(building);
        building.finishBuilding();
        Schematic s;
        if(!(building instanceof Wall || building instanceof BuilderHut)) {
            switch (building.getGridLengthX()) {
                case 4:
                    s = Schematics.s.getSchematic("2x2Giftbox");
                case 6:
                    s = Schematics.s.getSchematic("3x3Giftbox");
                    break;
                case 8:
                    s = Schematics.s.getSchematic("4x4Giftbox");
                    break;
                default:
                    // shouldnt happen
                    return;
            }
            Pair<Integer,Integer> gridLoc = building.getGridLoc();
            s.resetToGrassLand(building.getArena().getAbsLocationFromGridLoc(gridLoc.first, gridLoc.second, 0));
        }
        building.completeUpgrade();
        building.paste();
    }
    @Override
    public void click() {
        float p = building.getPercentageBuilt();
        if(p >= 1f) {
            building.getArena().playSound(Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f,5f);
            finishBuilding();
            if(builderNPC != null) {
                builderNPC.destroy();
            }
            building.sendMessage("Finished building.");
        }
        else {
            building.getArena().playSound(Sound.BLOCK_NOTE_BLOCK_BASS, 1f,1f);
            building.sendMessage(ChatColor.GRAY + Globals.timeFromSeconds(building.getRemainingUpdateTime()) + " remaining.");
        }
    }
    @Override
    public void stopUpdates() {
        if(builderNPC != null) {
            builderNPC.destroy();
        }
        this.builderNPC = null;
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
        if(builderNPC != null) {
            builderNPC.destroy();
        }
        building.pickup();
        building.state = new InHandBuildingState(building);
    }
}
