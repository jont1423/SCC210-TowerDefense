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
	private static String FontFile = "ARCADECLASSIC.ttf";
	
	private String FontPath;
	private Text text;



	public Word() {

		//Check whether funning from JDK or JRE
		if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
		else FontPath = JdkFontPath;
	}
	
	public void setWord(String sub, Color c, int FontSize) {
		// Load the font
		Font typeWriter = new Font();
		try {typeWriter.loadFromFile(Paths.get(FontFile));} 
		catch (IOException e) {System.out.println("Couldn't find font file");}
		
		text = new Text(sub, typeWriter, FontSize);
		text.setColor(c);
		//text.setStyle(Text.BOLD); Need to re enable for Jordans work

		// Store references to object and key methods
		obj = text;
		rotate = text::rotate;
		setPosition = text::setPosition;
		
	}
	
	public void setFont(String font)
	{
		FontFile = font;
	}
	
	public void setColor(Color c) {text.setColor(c);}
	
	public String scene1(int dialouge) {
		int StringSize = 24;
		String[] sub = new String[StringSize];
		sub[0] = "Silver: Crew, we've finally arrived into the Solaris system,";	//Frame 1
		sub[1] = "we'll be on approach to Solaris VII soon.";						//Frame 1
		sub[2] = "Blackbeard: Ahhh, good to stretch me legs after that";			//Frame 2
		sub[3] = "hypersleep, I can already smell them sweet sweet carbon";			//Frame 2
		sub[4] = "crystals. I'll be rich.. er I mean we, we'll be rich!";			//Frame 2
		sub[5] = "Silver: Don't get excited just yet Black', we still have the";	//Frame 3
		sub[6] = "asteroid belt to traverse, and with the Constellations";			//Frame 3
		sub[7] = "shields in their current state after our last mission, we";		//Frame 3
		sub[8] = "better stay on our toes.";										//Frame 3
		sub[9] = "Blackbeard: Old 'stellas shields will hold just fine, I just";	//Frame 4
		sub[10] = "finished fine tuning them.";										//Frame 4
		sub[11] = "**BOOOOOOOOOOOOOOOM**";											//Frame 5
		sub[12] = "(Ship Announcement) **HULL BREACH, AIRLOCK SEALED**";			//Frame 6
		sub[13] = "Blackbeard: Err... it must be that damned automaton of yours,";	//Frame 7
		sub[14] = "tampering with the shields it's a liability I say!";				//Frame 7
		sub[15] = "Silver: Enough of this Blackbeard, we've more urgent matters";	//Frame 8
		sub[16] = "at hand. Locate the hull breach and repair it.";					//Frame 8
		sub[17] = "**WE CANNOT APPROACH SOLARIS VII UNTIL THE";						//Frame 9 (Should be RED)
		sub[18] = "BREACH IS FULLY REPAIRED.**";									//Frame 9 (Should be RED)
		sub[19] = "(Announcement) **FOREIGN LIFEFORM DETECTED IN LOADING BAY**";	//Frame 10
		sub[20] = "Silver: Looks like we have a situation on our hands now!";		//Frame 11
		sub[21] = "**DEPLOY THE DEFENSE TURRETS WE SCAVENGED FROM THE LAST";		//Frame 12 (Should be RED)
		sub[22] = "MISSION, THEY SHOULD HELP QUARANTINE THE LOADING BAY.**";		//Frame 12 (Should be RED)
		sub[23] = "Blackbeard: Aye aye captain!";									//Frame 13
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	/*if (sub.count() > 60) {
		sub1 = sub.split(60);
		
	}*/
	
	
	public String scene2(int dialouge) {
		int StringSize = 14;
		String[] sub = new String[StringSize];
		sub[0] = "Silver: Wolf, the lifeforms have spread to the engineering";		//Frame 1
		sub[1] = "bay, you'll need to deploy your security forces to help.";		//Frame 1
		sub[2] = "Wolf: What on earth are these disgusting things, they make";		//Frame 2
		sub[3] = "my skin crawl.";													//Frame 2
		sub[4] = "Silver: Pull yourself together Wolf, you don't look much";		//Frame 3
		sub[5] = "better.";															//Frame 3
		sub[6] = "Wolf: You're as witty as you are wise captain.";					//Frame 4
		sub[7] = "Silver: Thank you wolf, now take Blackbeard with you and";		//Frame 5
		sub[8] = "head to engineering. It's not going to be pretty down there..";	//Frame 5
		sub[9] = "Goodluck.";														//Frame 5
		sub[10] = "**THERE IS NO WAY WE WILL BE ABLE TO REPAIR THE SHIP WHILE";		//Frame 6 (Should be RED)
		sub[11] = "THE ENEMY HAS OVERRUN OUR ESSENTIAL MACHINERY. DEFEND THE";		//Frame 6 (Should be RED)
		sub[12] = "ENGINEERING BAY WITH YOUR LIFE!**"; 								//Frame 6 (Should be RED)
		sub[13] = "A few moments later.....";										//Frame 7
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene3(int dialouge) {
		int StringSize = 5;
		String[] sub = new String[StringSize];
		sub[0] = "Kidd: (out of breath) CAPTAIN!!!";								//Frame 1
		sub[1] = "Kidd: (breathing heavy after stuffing his face with the cake";	//Frame 2
		sub[2] = "he had in hand) captain, captain, captain...There are aliens";	//Frame 2
		sub[3] = "in the bathroom! No way I can clean in there, I could die!";		//Frame 2
		sub[4] = "Silver: Seriously Kidd...THERE ARE ALIENS ON THE ENTIRE SHIP.";	//Frame 3
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene4(int dialouge) {
		int StringSize = 13;
		String[] sub = new String[StringSize];
		sub[0] = "Silver: Wolf, Blackbeard! Great job defending the engineering";	//Frame 1
		sub[1] = "bay.";															//Frame 1
		sub[2] = "Silver: We may actually get out of here alive. Now that we've";	//Frame 2
		sub[3] = "sealed off engineering from the aliens there's a chance";			//Frame 2
		sub[4] = "Blackbeard can get us running so we can land on Solaris.";		//Frame 2
		sub[5] = "Silver: Da Vinci, how many people have we lost?";					//Frame 3
		sub[6] = "Da Vinci: ...It's not good ma'am, although our men defended";		//Frame 4
		sub[7] = "the ship bravely and valiantly many lives were lost in the";		//Frame 4
		sub[8] = "first two advances.";												//Frame 4
		sub[9] = "Da Vinci: The only survivors are here, in the loading bay,";		//Frame 5
		sub[10] = "and engineering. The rest of the ship was lost.";				//Frame 5
		sub[11] = "Silver: A moment of silence for our fallen souls.";				//Frame 6
		sub[12] = "**BOOOOOOOOOOOOOOOM**";											//Frame 7
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene5(int dialouge) {
		int StringSize = 7;
		String[] sub = new String[StringSize];
		sub[0] = "Kidd: AHHHHHHHHHHHHHHHHHH captain the enemy broke through";		//Frame 1
		sub[1] = "our energy shield!";												//Frame 1
		sub[2] = "Silver: Hold them back men! We can't let them take this room.";	//Frame 2
		sub[3] = "**THE ONLY WAY TO MAKE IT SAFELY DOWN TO THE SURFACE OF";			//Frame 3 (Should be RED)
		sub[4] = "SOLARIS IS TO DEFEND THE ENEMY FROM THE BRIDGE UNTIL THE REST";	//Frame 3 (Should be RED)
		sub[5] = "OF THE CREW CAN GET THIS DAMNED SHIP STARTED.**";					//Frame 3 (Should be RED)
		sub[6] = "**GOODLUCK SOLDIER.**";											//Frame 4 (Should be RED)
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene6(int dialouge) {
		int StringSize = 19;
		String[] sub = new String[StringSize];
		sub[0] = "(Ship Shaking) (Everyone) **AHHHHHHHHHHHHHHHHHH**";				//Frame 1
		sub[1] = "Blackbeard: Ow, ow, ow, ow, ow, (yells) Quite the bumpy";			//Frame 2
		sub[2] = "right eh cap?";													//Frame 2
		sub[3] = "Silver: (voice rattling) I wouldn't have it any other way!";		//Frame 3
		sub[4] = "Blackbeard: I just have my mind on those beautiful crystals";		//Frame 4
		sub[5] = "waiting for me below.";											//Frame 4
		sub[6] = "**BOOOOOOOOOOOOOOOM**";											//Frame 5
		sub[7] = "Silver: Is everyone alright?!?";									//Frame 6
		sub[8] = "Blackbeard: I'm okay!";											//Frame 7
		sub[9] = "Da Vinci: Alright here...just a little banged up";				//Frame 8
		sub[10] = "Wolf: I think I've broken a few ribs but I'll be okay cap.";		//Frame 9
		sub[11] = "Silver: Okay boys I'm not sure if we will be save down here";	//Frame 10
		sub[12] = "so keep your eyes peeled. WAIT! Where's Kidd?!?!?!?";			//Frame 10
		sub[13] = "Blackbeard: Umm captain, I think I've found him...";				//Frame 11
		sub[14] = "(Everyone) .......................................";				//Frame 12
		sub[15] = "Silver: Oh no...I told his family I'd protect him with";			//Frame 13
		sub[16] = "my life...";														//Frame 13
		sub[17] = "Wolf: Guys! We have another problem! The aliens found";			//Frame 14
		sub[18] = "us again...";													//Frame 14
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene7(int dialouge) {
		int StringSize = 12;
		String[] sub = new String[StringSize];
		sub[0] = "(Aliens) **@#$%^&*(^#**";											//Frame 1
		sub[1] = "Da Vinci: These guys follow us everywhere can't we catch";		//Frame 2
		sub[2] = "a break, geez...";												//Frame 2
		sub[3] = "Silver: Wolf!";													//Frame 3
		sub[4] = "Wolf: Yeah cap?";													//Frame 4
		sub[5] = "Silver: I need you to push into the forest while we hold";		//Frame 5
		sub[6] = "them off here...find somewhere we can make a base camp and";		//Frame 5
		sub[7] = "get back here as quick as you can.";								//Frame 5
		sub[8] = "Wolf: I'm on it.";												//Frame 6
		sub[9] = "**YOU ARE IN THE WOODS OF SOLARIS VII, A PLANET YET TO BE";		//Frame 7 (Should be RED)
		sub[10] = "INHABITED, HOLD OFF THE ENEMY ADVANCE WHILE WOLF FINDS A";		//Frame 7 (Should be RED)
		sub[11] = "PLACE YOU CAN SET UP CAMP.**";									//Frame 7 (Should be RED)
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene8(int dialouge) {
		int StringSize = 10;
		String[] sub = new String[StringSize];
		sub[0] = "Wolf: Captain, I found it! It's PERFECT!! But we have to";		//Frame 1
		sub[1] = "move now. On my way back I saw the enemy trying to encircle";		//Frame 1
		sub[2] = "us and cut us off.";												//Frame 1
		sub[3] = "Silver: Crew! Get everything packed up and ready to go,";			//Frame 2
		sub[4] = "we need to move move move!!";										//Frame 2
		sub[5] = "Da Vinci: (out of breath) oh man, I really could use a nice";		//Frame 3
		sub[6] = "cuppa tea right now.";											//Frame 3
		sub[7] = "Silver: Come on Da Vinci you know hasn't existed in 15";			//Frame 4
		sub[8] = "years. Tough times since the war.";								//Frame 4
		sub[9] = "Da Vinci: Doesn't mean I can't fantasize cap!";					//Frame 5

		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
	public String scene9(int dialouge) {
		int StringSize = 10;
		String[] sub = new String[StringSize];
		sub[0] = "**ARRIVED FINAL DESTINATION.**";									//Frame 1
		sub[1] = "Wolf: Here we are guys, make yourself at home and let's get";		//Frame 2
		sub[2] = "these defenses built! The enemy should be here soon.";			//Frame 2
		sub[3] = "Wolf: And blackbeard there's a nice surprise for you on the";		//Frame 3
		sub[4] = "other side of this hill (chuckles).";								//Frame 3
		sub[5] = "Blackbeard: It's the crystals! I can smell em";					//Frame 4
		sub[6] = "from a mile away!";												//Frame 4
		sub[7] = "**YOU HAVE ONE FINAL MISSION AND FREEDOM IS YOURS! KEEP THE";		//Frame 5 (Should be RED)
		sub[8] = "ALIEN LIFEFORMS AWAY FROM YOUR BASE CAMP AND MAKE SURE";			//Frame 5 (Should be RED)
		sub[9] = "BLACKBEARD CAN REAP THE REWARDS OF YOUR LABOR.**";				//Frame 5 (Should be RED)
		
		if (dialouge > StringSize) {
			System.out.println("Array index out of bound.");
			System.out.println("No such line.");
		}
		return sub[dialouge];
	}
	
}