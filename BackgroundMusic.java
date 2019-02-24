/**
 *
 * Class name: BackgroundMusic
 *
 * Author: Jordan Young
 *
**/

public class BackgroundMusic{
	private static GenSound track;
	private static String SoundFile = "Sounds/BGM.wav";
	
	public BackgroundMusic() {
		track = new GenSound(SoundFile);
		track.loop(true);
	}
	
	public void play() {
		track.playSound();
	}
	
	public void stop() {
		track.stopSound();
	}
}