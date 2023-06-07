package aaronpost.clashcraft.Interfaces;

public interface IUpdate {
    // Called to catch up in a period of absence of updates.
    public void catchUpRequest(float hours);
    // Called to begin any update procedure.
    public void startUpdates();
    // Called to stop an update procedure.
    public void stopUpdates();
}
