package game;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import prog.io.ConsoleInputManager;
import prog.io.FileInputManager;
import prog.io.FileNonPresenteException;
import prog.utili.Orario;

public class TypingSpeedGame {
	public static final String FILE = "parole_italiane.txt";
	public static final int DIM = 60453;
	String [] words;
	long maxTime;
	int wordCounter;
	int charCounter;
	int rightChars;

	public TypingSpeedGame(long maxTime) {
		this.maxTime = maxTime;
		this.wordCounter = 0;
		this.charCounter = 0;
		this.rightChars = 0;
	}

	public void run() {
		init();
		gamePlay();
		results();
	}

	public void init() {
		if(FileInputManager.exists(FILE)) {
			FileInputManager in = new FileInputManager(FILE);
			words = new String[DIM];
			int i = -1;
			while((words[++i] = in.readLine()) != null) {}
			in.close();
		} else {
			throw new FileNonPresenteException("File non trovato!");
		}
	}

	public void gamePlay() {
		Random indexChooser = new Random();
		ConsoleInputManager in = new ConsoleInputManager();
		String writtenWord = new String();
		printTimer();
		Instant start = Instant.now().truncatedTo(ChronoUnit.SECONDS);

		while(Instant.now().truncatedTo(ChronoUnit.SECONDS).isBefore(start.plusSeconds(maxTime))) {
			int index = indexChooser.nextInt(DIM-1);

			writtenWord = in.readLine(words[index] + " ");

			if(writtenWord.equals(words[index])){
				rightChars += writtenWord.length();
			} else {
				if(writtenWord.length() > words[index].length()) {
					for(int i = 0; i < words[index].length(); i++) {
						if(writtenWord.charAt(i) == words[index].charAt(i)) {
							rightChars++;
						}
					}
					rightChars -= writtenWord.length() - words[index].length();
				} else if(writtenWord.length() < words[index].length()) {
					for(int i = 0; i < writtenWord.length(); i++) {
						if(writtenWord.charAt(i) == words[index].charAt(i)) {
							rightChars++;
						}
					}
					rightChars -= words[index].length() - writtenWord.length();
				} else {
					for(int i = 0; i < words[index].length(); i++) {
						if(writtenWord.charAt(i) == words[index].charAt(i)) {
							rightChars++;
						}
					}
				}
			}

			wordCounter++;
			charCounter += words[index].length();
		}
	}

	public void results() {
		float precision = (float) ((rightChars/(float)charCounter*100) > 0.0 ? (rightChars/(float)charCounter*100) : 0.00);
		System.out.println("\nTempo: " + maxTime + "s");
		System.out.println("Parole scritte: " + wordCounter);
		System.out.println("Caratteri digitati: " + charCounter);
		System.out.println("Velocita' (parole al minuto): " + (int)(wordCounter/((float)maxTime/60f)));
		System.out.println("Velocita' (caratteri al minuto): " + (int)(charCounter/((float)maxTime/60f)));
		System.out.println("Correttezza: " + precision + " %");
	}
	
	private void printTimer() {
		Orario inizio = new Orario();
		System.out.println("Inizio: " + inizio.toString() + " | Tempo: " + this.maxTime + "s");
		System.out.println();
	}
}
