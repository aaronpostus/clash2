package aaronpost.clashcraft.Singletons;

import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;

import java.time.Duration;

public class TestUpdatable implements IUpdatable, IFixedUpdatable {
    int counter = 0;
    @Override
    public void update() {

    }

    @Override
    public void fixedUpdate() {
        System.out.println("Update no " + (++counter));
    }

    @Override
    public void catchUp(Duration duration) {

    }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
