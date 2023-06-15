package aaronpost.clashcraft.Raiding.TroopAI;

public class TroopAgentOptions {
    enum AIMode { Ground, Ground_Jump_Walls, Flying }
    enum AIBias { None, Defenses, Resources, Walls, Troops }
    AIMode aiMode;
    AIBias aiBias;
    float biasStrength;
    int blocksBetweenWaypoints;
    public TroopAgentOptions() {
        this.aiMode = AIMode.Ground;
        this.aiBias = AIBias.None;
        this.biasStrength = 1f;
        this.blocksBetweenWaypoints = 0;
        // default options
    }
    public void setAIMode(AIMode aiMode) {
        this.aiMode = aiMode;
    }
    public void setAIBias(AIBias aiBias) {
        this.aiBias = aiBias;
    }
    /**
     * @param strength is a float between 0f and 1f
     */
    public void setBiasStrength(float strength) {
        assert strength >= 0f && strength <= 1f;
        this.biasStrength = strength;
    }
    public void setBlocksBetweenWaypoints(int blocks) {
        this.blocksBetweenWaypoints = blocks;
    }
}
