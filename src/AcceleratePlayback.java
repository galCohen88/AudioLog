import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Date;

class AcceleratePlayback {

    public static void main(String[] args) throws Exception {
        int playBackSpeed = 3;
        if (args.length>0) {
            try {
                playBackSpeed = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        int skip = playBackSpeed-1;
        System.out.println("Playback Rate: " + playBackSpeed);

//        URL url = new URL("http://pscode.org/media/leftright.wav");
//        System.out.println("URL: " + url);
        final File ifile = new File("/home/nevix/dev/hackathon/AudioLog/01_-_from_the_rooftops.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(ifile);
        AudioFormat af = ais.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());

        int frameSize = af.getFrameSize();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2^16];
        int read = 1;
        while( read>-1 ) {
            read = ais.read(b);
            if (read>0) {
                baos.write(b, 0, read);
            }
        }
        System.out.println("End entire: \t" + new Date());

        byte[] b1 = baos.toByteArray();
        byte[] b2 = new byte[b1.length/playBackSpeed];
        for (int ii=0; ii<b2.length/frameSize; ii++) {
            for (int jj=0; jj<frameSize; jj++) {
                b2[(ii*frameSize)+jj] = b1[(ii*frameSize*playBackSpeed)+jj];
            }
        }
        System.out.println("End sub-sample: \t" + new Date());

        ByteArrayInputStream bais = new ByteArrayInputStream(b2);
        AudioInputStream aisAccelerated =
                new AudioInputStream(bais, af, b2.length);
//        Clip clip = AudioSystem.getClip();
//        clip.open(aisAccelerated);
//        clip.loop(2*playBackSpeed);
//        clip.start();
        Clip clip = null;

        clip = (Clip)AudioSystem.getLine(info);
        clip.open(aisAccelerated);
        clip.loop(2*playBackSpeed);
        clip.start();

        JOptionPane.showMessageDialog(null, "Exit?");
    }
}