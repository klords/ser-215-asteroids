package entity;

/**
 * Created by gareth on 10/4/14.
 */
public class Asteroid extends MapObject {
    private int size;
    private int value;

    public Asteroid() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
