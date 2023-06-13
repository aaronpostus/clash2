package aaronpost.clashcraft.Interfaces;

public interface IUpdate {
    // Called to catch up in a period of absence of updates.
    void catchUpRequest(float hours);
    // Called to begin any update procedure.
    void startUpdates();
    // Called to stop an update procedure.
    void stopUpdates();
}
