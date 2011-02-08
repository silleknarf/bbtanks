package bedroombattletanks;


import jgame.platform.JGEngine;

public class RandomAudio {
	GameUtilities gameUtil =  new GameUtilities();
	int randomSongNumber;
	String[] songs = {
			"indiana-jones",
			"in-the-hall-of-the-mountain-king",		
			"palladio",
			"ride-of-the-valkyries",	
			"the-bringer-of-war",	
			"the-flight-of-the-bumblebee",
			"trepak"
	};
	RandomAudio(JGEngine eng){
		randomSongNumber = gameUtil.random(1, songs.length);
		eng.playAudio("channel1",songs[randomSongNumber-1],true);
	}
}
