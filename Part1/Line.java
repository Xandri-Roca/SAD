import java.io.*;

public class Line{
	// maintains the status of the line in edition with its corresponding methods.

	private char[] phrase;
	private int cursorPosition;
	private int maxSize = 500;  
	private int numLetters;
	//private int insertar;
	private boolean insertar;


	public Line(){
		phrase = new char[maxSize];
		cursorPosition = 0;
		numLetters = 0;
		//insertar = 0;
		insertar = false;

	}

	public void moveRight(){
		cursorPosition ++;
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
		// special situation when suprimir is activated.
		char letter = (char) letterId; // The char convertion follows the int Unicode - ASCII table (not like python)
		if(insertar == false){
			// if the cursor is not in the last position:
			if (cursorPosition != numLetters){
				for(int i = numLetters; i > cursorPosition; i--){
					phrase[i]=phrase[i+1];
				}
			}
			phrase[cursorPosition] = letter;
			numLetters ++;
			cursorPosition ++;
		}
		

		if(insertar == true){
			if (cursorPosition == numLetters){
				phrase[cursorPosition] = letter;
				numLetters ++;
				cursorPosition ++;
			}else{
				phrase[cursorPosition] = letter;
				cursorPosition ++;
			}
		}
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
