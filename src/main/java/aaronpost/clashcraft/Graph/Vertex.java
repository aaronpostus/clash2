package aaronpost.clashcraft.Graph;

import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Vector;

public class Vertex<T> {
    int vertexId;
    // store a reference to the building that this node exists in, etc
    T cellValue;
    Vertex(int vertexId) {
        this.vertexId = vertexId; this.cellValue = null;
    }
    public int getVertexId() { return vertexId; }
    public void setCellValue(T cellValue) {
        this.cellValue = cellValue;
    }
    public T getCellValue() {
        return cellValue;
    }
    public boolean cellOccupied() {
        return cellValue != null;
    }
}
