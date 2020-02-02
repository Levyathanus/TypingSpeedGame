package main;

import game.TypingSpeedGame;
import prog.io.ConsoleInputManager;

public class Main {

	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		int time = in.readInt("Inserire tempo di gioco (secondi): ");
		double adjustment = 0.0; 
		adjustment = in.readDouble("Inserire tempo bonus: ");
		if(adjustment < 0.0 || time < 0 || time > 300 || adjustment > 2.0) {
			throw new RuntimeException("Tempi inseriti non validi!");
		} else {
			do {
				TypingSpeedGame myGame = new TypingSpeedGame((long)(time + adjustment));
				myGame.run();
			} while(in.readChar("\nDigitare una lettera per rigiocare, 'q' per uscire: ") != 'q');
		}
	}

}
