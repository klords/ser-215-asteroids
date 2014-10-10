package entity;

import java.applet.*;
import java.net.*;

/**
 * Created by gareth on 10/8/14.
 */
public class Sounds {

    private AudioClip clip;

    public Sounds(String filePath) {

        this.clip = Applet.newAudioClip(Sounds.class.getResource(filePath));
    }

    public void play() {
        clip.play();
    }

    public void loop() {
        clip.loop();
    }

    public void stop() {
        clip.stop();
    }



}
