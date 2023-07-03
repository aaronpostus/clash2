package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Raiding.Troop;

public interface IDefenseBuilding {
    void addTroopToDamage(Troop troopFromPlayer);
    public enum DefenseType { SINGLE_TARGET, MULTI_TARGET }
    public float getAttackSpeed();
    public float getAttackRange();
    public float getDamagePerShot();
    public DefenseType getDamageType();
}
