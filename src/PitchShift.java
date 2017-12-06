import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Created by nevix on 06/12/17.
 */
public class PitchShift {
    public static void main(String args[]) throws IOException, UnsupportedAudioFileException {
        final File file1 = new File("/home/nevix/dev/hackathon/AudioLog/01_-_from_the_rooftops.wav");
        final File file2 = new File("/home/nevix/dev/hackathon/AudioLog/out.wav");
        final AudioInputStream in1 = getAudioInputStream(file1);

        final AudioFormat inFormat = getOutFormat(in1.getFormat());

        final AudioInputStream in2 = getAudioInputStream(inFormat, in1);
        AudioSystem.write(in2, AudioFileFormat.Type.WAVE, file2);

    }

    private static AudioFormat getOutFormat(AudioFormat inFormat) {
        int ch = inFormat.getChannels();
        float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, 72000, 16, ch, ch * 2, rate,
                inFormat.isBigEndian());

    }
}
