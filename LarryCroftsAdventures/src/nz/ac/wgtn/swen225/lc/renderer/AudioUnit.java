package nz.ac.wgtn.swen225.lc.renderer;

import java.util.HashMap;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;


/**
 * Audio unit class is responsible for playing audio clips
 *
 * @author  Alex Manning (300600549)
 */
public class AudioUnit {
    public enum AudioClip{BACKGROUND, AMBIENCE, SEAGULL1, SEAGULL2, SEAGULL3, KEY_COLLECT, DOOR_UNLOCK, FISH_CAUGHT, ENEMY_ATTACK}
    private final HashMap<AudioClip, Clip> audioClips = new HashMap<>();

    public AudioUnit(){
        loadClips();

    }

    /**
     * Loads in audio clips into audioClips map
     */
    private void loadClips(){
        addClip(AudioClip.BACKGROUND, "bg.wav", -5f);
        addClip(AudioClip.AMBIENCE, "ambience.wav", -10f);
        addClip(AudioClip.SEAGULL1, "seagull1.wav", -5f);
        addClip(AudioClip.SEAGULL2, "seagull2.wav", -5f);
        addClip(AudioClip.SEAGULL3, "seagull3.wav", -5f);
    }

    /**
     * Adds an audio clip to the collection with the specified identifier, file path,
     * and volume level.
     *
     * @param id      The unique identifier for the audio clip.
     * @param file    The relative file path of the audio clip.
     * @param volume  The volume level for the audio clip.
     */
    private void addClip(AudioClip id, String file, float volume){
        String path = "LarryCroftsAdventures/assets/audioFiles/";
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path + file)));
            FloatControl backgroundVolumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            backgroundVolumeControl.setValue(volume);
            audioClips.put(id, clip);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * Starts background music (looping)
     */
    public void startBackgroundMusic(){
        Clip bg = audioClips.get(AudioClip.BACKGROUND);
        bg.loop(10);
    }

    /**
     * Starts ambience (looping)
     */
    public void startAmbience(){
        Clip ambience = audioClips.get(AudioClip.AMBIENCE);
        ambience.loop(10);
    }

    /**
     * Plays one of three possible seagull SFXs
     */
    public void playSeagullSFX(){
        Random random = new Random();
        // Generate a random number between 1 and 3
        int randomNumber = random.nextInt(3) + 1;
        AudioClip seagull = switch(randomNumber){
            case 1 -> AudioClip.SEAGULL1;
            case 2 -> AudioClip.SEAGULL2;
            case 3 -> AudioClip.SEAGULL3;
            default -> null;
        };
        audioClips.get(seagull).start();
    }

    /**
     * Stops all audio
     */
    public void stopAll(){
        for (Clip clip : audioClips.values()){
            clip.stop();
        }
    }
}
