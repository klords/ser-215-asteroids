package entity;

/**
 * Created by gareth on 10/4/14.
 */
public class Missile extends MapObject {
    private int lifespan;

    public Missile() {

    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
}
