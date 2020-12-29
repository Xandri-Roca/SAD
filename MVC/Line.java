// Bernat Xandri Zaragoza & Ramon Roca Oliver


import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Line extends Observable{
	// Line have the logic to update controller if its data changes.

	private ArrayList<Character> phrase; // We will use an arraylist instead of an array to make modifying characters easier.
	private int cursorPosition;
	private boolean insertar;
	private int[] data; // We'll send the parameters to the controller using this vector.

	public Line(){
		super();
		phrase = new ArrayList<Character>();
		cursorPosition = 0;
		insertar = false;
		data = new int[3]; // We need 3 int values: KeyID, cursor position update and Insert on/off
	}

	public void moveRight(){
		if(cursorPosition < phrase.size()){
			cursorPosition ++;
			data[0] = KeyValues.RIGHT;
			setChanged();
			notifyObservers(data);
		}
	}

	public void moveLeft(){
		if(cursorPosition > 0){
			cursorPosition --;
			data[0] = KeyValues.LEFT;
			setChanged();
			notifyObservers(data);
		}
	}

	public void moveToInicio(){
		if(cursorPosition > 0){
			data[0] = KeyValues.INICIO;
			data[1] = cursorPosition;
			cursorPosition = 0;
			setChanged();
			notifyObservers(data);
		}
	}

	public void moveToFin(){
		if(cursorPosition < phrase.size()){
			data[0] = KeyValues.FIN;
			data[1] = phrase.size() - cursorPosition;
			cursorPosition = phrase.size(); //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
			setChanged();
			notifyObservers(data);
		}
	}

	public void insertar(){
		insertar = !insertar;
		data[0] = KeyValues.INSERT;
		if(insertar = true){
			data[2] = 1;
		}else{
			data[2] = 0;
		}
		setChanged();
		notifyObservers(data);
	}

	public void suprimir(){
		if(cursorPosition < phrase.size()){
			phrase.remove(cursorPosition);
			cursorPosition --;
			data[0] = KeyValues.SUPRIMIR;
			setChanged();
			notifyObservers(data);
		}
	}

	public void retroceso(){
		if(cursorPosition > 0){
			phrase.remove(cursorPosition - 1);
			cursorPosition --;
			data[0] = KeyValues.RETROCESO;
			setChanged();
			notifyObservers(data);
		}
	}

	public void write(int letterId) {
		char letter = (char) letterId; // The char convertion follows the int Unicode - ASCII table
		if(insertar == true){
			if(cursorPosition < phrase.size()){
				phrase.set(cursorPosition, letter);
			}else{
				phrase.add(cursorPosition, letter);
			}
		}else{
			phrase.add(cursorPosition, letter);
		}
		cursorPosition ++;
		data[0] = letterId;
		setChanged();
		notifyObservers(data);
	}

	public String getPhrase(){
		StringBuffer sb = new StringBuffer();
		for (Character s : phrase) {
			sb.append(s);
		}
		String str = sb.toString();
		return str;
	}
}