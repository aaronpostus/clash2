package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Displayable;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;


public abstract class Building extends Displayable implements IFixedUpdatable {
    boolean place() {
        // try to place, if i can't i return false
        return false;
    }
    boolean pickup() {
        // try to pickup, if i cant i return false

        return false;
    }
    boolean upgrade() {
        // try to upgrade, if i cant i return false
        return false;
    }
    void click() {

    }
}
