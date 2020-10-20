import java.io.*;

public class Line{
	// maintains the status of the line in edition with its corresponding methods.

	private char[] phrase;
	private int cursorPosition;
	private int maxSize = 500;  // random value??
	private int numLetters;
	//private int insertar;
	private boolean insertar;


	public Line(){
		phrase = new char[maxSize];
		cursorPosition = 0;
		numLetters = 0;
		//

	}

	public void moveRight(){
		cursorPosition ++;
		//maybe revisar que la posicio no pugui estar mes enlla del num de chars que te la frase????
	}

	public void moveLeft(){
		cursorPosition --;
	}

	public void moveToInicio(){
		cursorPosition = 0;
	}

	public void moveToFin(){
		cursorPosition = numLetters;
	}

	public void insertar(){
		//insertar ++;
		//insertar ^= insertar;
		if(insertar == true){
			insertar = false;
		}else{
			insertar = true;
		}
		System.out.println(insertar);
	}

	public void suprimir(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i] = phrase[i + 1];
		}

		numLetters --;
	}

	public void retroceso(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i - 1] = phrase[i]; 
		}

		cursorPosition --;
		numLetters --;
	}

	public void write(int letterId) {
		// special situation when the suprimir is activated.
		char letter = (char) letterId; // The char convertion follows the int Unicode - ASCII table (not like python)

		numLetters ++;
		phrase[cursorPosition] = letter;
	}

	public String getPhrase(){
		return String.valueOf(phrase);
	}

	public int getCursorPosition(){
		return cursorPosition;
	}

	public int getNumLetters(){
		return numLetters;
	}

	public boolean getInsertarState(){
		return insertar;
	}

}