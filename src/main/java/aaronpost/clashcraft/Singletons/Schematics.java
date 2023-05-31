package aaronpost.clashcraft.Singletons;

import aaronpost.clashcraft.Schematics.Schematic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schematics implements Serializable {
    private List<Schematic> schematics = new ArrayList<>();
    public static Schematics s = new Schematics();

    public void addSchematic(Schematic s) {
        schematics.add(s);
    }

    public void addSchematic(List<Schematic> s) {
        schematics.addAll(s);
    }
    public Schematic getSchematic(String name) {
        for(Schematic schematic : schematics) {
            if(schematic.getName().equals(name)) {
                return schematic;
            }
        }
        return null;
    }
    public List<Schematic> getSchematics() {
        return schematics;
    }
}
