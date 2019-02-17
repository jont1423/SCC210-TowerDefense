/**
 *
 * Class name: Word
 *
 *
**/

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;
//import java.io.InputStream;

//import org.jsfml.system.*;
//import org.jsfml.window.*;
//import org.jsfml.audio.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
//import org.jsfml.graphics.Image;

public class Word extends Actor {
	
	//Determine fonts directory through java JDK or JRE version
	private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
	private static String JdkFontPath = "C:\\Program Files\\Java\\jdk" + JavaVersion + "\\jre\\lib\\fonts\\";
	private static String JreFontPath = "C:\\Program Files\\Java\\jre" + JavaVersion + "\\lib\\fonts\\";
	private static int FontSize = 24;
	private static String FontFile = "LucidaTypewriterRegular.ttf";
	
	private String FontPath;
	private Text text;

	public Word() {

		//Check whether funning from JDK or JRE
		if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
		else FontPath = JdkFontPath;
	
		// Load the font
		Font typeWriter = new Font();
		try {typeWriter.loadFromFile(Paths.get(FontPath+FontFile));} 
		catch (IOException e) {System.out.println("Couldn't find font file");}
	}
	
	public void setText(String sub, Color c) {
		text = new Text(message, typeWriter, FontSize);
		text.setColor(c);
		text.setStyle(Text.BOLD);

		FloatRect textBounds = text.getLocalBounds();
		// Find middle and set as origin/ reference point
		text.setOrigin(textBounds.width / 2, textBounds.height / 2);

		// Store references to object and key methods
		item = text;
		setPosition = text::setPosition;
	}
	
	public String scene1(int dialouge) {
		String sub1 = "Silver: Crew, we’ve finally arrived into the Solaris system, we’ll be on approach to Solaris VII soon.";
		String sub2 = "Blackbeard: Ahhh, good to stretch me legs after that hypersleep, I can already smell them sweet sweet carbon crystals. I’ll be rich.. er I mean we, we’ll be rich!";
		String sub3 = "Silver: Don’t get excited just yet Black’, we still have the asteroid belt to traverse, and with the Constellations shields in their current state after our last mission, we better stay on our toes.";
		String sub4 = "Blackbeard: Old ‘stellas shields will hold just fine, I just finished fine tuning them.";
		String sub5 = "**BOOOOOOOOOOOOOOOM**";
		String sub6 = "(Ship Announcement) **HULL BREACH, AIRLOCK SEALED**";
		String sub7 = "Blackbeard: Err… it must be that damned automaton of yours, tampering with the shields it’s a liability I say!";
		String sub8 = "Silver: Enough of this Blackbeard, we’ve more urgent matters at hand. Locate the hull breach and repair it, WE CANNOT APPROACH SOLARIS VII UNTIL THE BREACH IS FULLY REPAIRED.";
		String sub9 = "(Ship Announcement) **FOREIGN LIFEFORM DETECTED IN LOADING BAY**";
		String sub10 = "Silver: Looks like we have a situation on our hands now, DEPLOY THE DEFENSE TURRETS WE SCAVENGED FROM THE LAST MISSION, THEY SHOULD HELP QUARANTINE THE LOADING BAY.";
		String sub11 = "Blackbeard: Aye aye captain!";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else if (dialouge == 4) return sub4;
		else if (dialouge == 5) return sub5;
		else if (dialouge == 6) return sub6;
		else if (dialouge == 7) return sub7;
		else if (dialouge == 8) return sub8;
		else if (dialouge == 9) return sub5;
		else if (dialouge == 10) return sub9;
		else if (dialouge == 11) return sub10;
		else if (dialouge == 12) return sub11;
		else System.out.println("No such line.");
	}
	
	public String scene2(int dialouge) {
		String sub1 = "Silver: Wolf, the lifeforms have spread to the engineering bay, you’ll need to deploy your security forces to help.";
		String sub2 = "Wolf: What on earth are these disgusting things, they make my skin crawl.";
		String sub3 = "Silver: Pull yourself together Wolf, you don’t look much better.";
		String sub4 = "Wolf: You’re as witty as you are wise captain.";
		String sub5 = "Silver: Thank you wolf, now take Blackbeard with you and head to engineering. It’s not going to be pretty down there...Goodluck. THERE IS NO WAY WE WILL BE ABLE TO REPAIR THE SHIP WHILE THE ENEMY HAS OVERRUN OUR ESSENTIAL MACHINERY. DEFEND THE ENGINEERING BAY WITH YOUR LIFE!";
		String sub6 = "A few moments later.....";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else if (dialouge == 4) return sub4;
		else if (dialouge == 5) return sub5;
		else if (dialouge == 6) return sub6;
		else System.out.println("No such line.");
	}
	
	public String scene3() {
		String sub1 = "Kidd: (out of breath) CAPTAIN!!!";
		String sub2 = "Kidd: (breathing heavy after stuffing his face with the cake he had in hand) captain, captain, captain...There are aliens in the bathroom! No way I can clean in there, I could die!";
		String sub3 = "Silver: Seriously Kidd...THERE ARE ALIENS ON THE ENTIRE SHIP.";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else System.out.println("No such line.");
	}
	
	public String scene4() {
		String sub1 = "Silver: Wolf, Blackbeard! Great job defending the engineering bay. We may actually get out of here alive. Now that we’ve sealed off engineering from the aliens there’s a chance Blackbeard can get us running so we can land on Solaris. Da Vinci, how many people have we lost?";
		String sub2 = "Da Vinci: ...It’s not good ma’am, although our men defended the ship bravely and valiantly many lives were lost in the first two advances. The only survivors are here, in the loading bay, and engineering. The rest of the ship was lost.";
		String sub3 = "Silver: A moment of silence for our fallen souls.";
		String sub4 = "**BOOOOOOOOOOOOOOOM**";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else if (dialouge == 4) return sub4;
		else System.out.println("No such line.");
	}
	
	public String scene5() {
		String sub1 = "Kidd: AHHHHHHHHHHHHHHHHHH captain the enemy broke through our energy shield!";
		String sub2 = "Silver: Hold them back men! We can’t let them take this room.";
		String sub3 = "Silver: THE ONLY WAY TO MAKE IT SAFELY DOWN TO THE SURFACE OF SOLARIS IS TO DEFEND THE ENEMY FROM THE BRIDGE UNTIL THE REST OF THE CREW CAN GET THIS DAMNED SHIP STARTED. GOODLUCK SOLDIER.";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else System.out.println("No such line.");
	}
	
	public String scene6() {
		String sub1 = "(Ship Shaking) (Everyone) **AHHHHHHHHHHHHHHHHHH**";
		String sub2 = "Blackbeard: Ow, ow, ow, ow, ow, (yells) Quite the bumpy right eh cap?";
		String sub3 = "Silver: (voice rattling) I wouldn’t have it any other way!";
		String sub4 = "Blackbeard: I just have my mind on those beautiful crystals waiting for me below.";
		String sub5 = "Silver: Thank you wolf, now take Blackbeard with you and head to engineering. It’s not going to be pretty down there...Goodluck. THERE IS NO WAY WE WILL BE ABLE TO REPAIR THE SHIP WHILE THE ENEMY HAS OVERRUN OUR ESSENTIAL MACHINERY. DEFEND THE ENGINEERING BAY WITH YOUR LIFE!";
		String sub6 = "A few moments later.....";
		
		if (dialouge == 1) return sub1;
		else if (dialouge == 2) return sub2;
		else if (dialouge == 3) return sub3;
		else if (dialouge == 4) return sub4;
		else if (dialouge == 5) return sub5;
		else if (dialouge == 6) return sub6;
		else System.out.println("No such line.");
	}
	
	public String scene7() {
		String sub = "";
		return sub;
	}
}