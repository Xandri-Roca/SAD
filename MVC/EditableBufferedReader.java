// Bernat Xandri Zaragoza & Ramon Roca Oliver


import java.io.*;
import java.lang.Runtime;
import java.lang.Process;
import java.util.Observable;
import java.util.Observer;


//right, left: caràcter dreta, caràcter esquerra amb les fletxes.
//home, end: principi, final de línia.
//ins: commuta mode inserció/sobre-escriptura.
//del, bksp: esborra caràcter actual o caràcter a l’esquerra.

public class EditableBufferedReader extends BufferedReader {
    // Acts on both model and view. It controls the data flow into model object and updates the view whenever data changes.
    // It keeps view and model separate.
    private Line line;
    private Console cons;

    public EditableBufferedReader(Reader r){
        super(r);
        line = new Line();
        cons = new Console();

        line.addObserver(cons);
    }

    // switch the terminal console into raw mode and desactivate echo.
    public void setRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Process newProcess1=Runtime.getRuntime().exec(cmd);
            newProcess1.waitFor();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // switch the terminal console into cooked mode and activate echo.
    public void unsetRaw(){
        try{

        	String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Process newProcess=Runtime.getRuntime().exec(cmd);
            newProcess.waitFor();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

//input of the keys pressed in terminal
    public int read() throws IOException{
    	int length = 20; 
        char[] ch = new char[length];
        int numChars = super.read(ch, 0, length); 
        int[] ch_int = new int[numChars]; 

        for(int i = 0; i < numChars; i++){
            ch_int[i] = (int) ch[i];
        }

        // Only ESC sequences.
        if(ch_int[0] == 27){
            String code = "";
            for(int i = 0; i < numChars; i++){
                code += String.valueOf(ch_int[i]);    
            }
            //System.out.println(code);
            return Integer.parseInt(code);
        }

        // in case that it's just a letter, we return it's KeyID as an integer.
        return ch_int[0];
    }

// reads and updates the line we are writting
    public String readLine() throws IOException{
    	int keyId = 0;

    	while(keyId != KeyValues.ENTER){
    		keyId = this.read();

    		switch(keyId){

    			case KeyValues.RIGHT:
                    line.moveRight();
                    break;

    			case KeyValues.LEFT:
                    line.moveLeft();
                    break;

    			case KeyValues.INICIO:
    				line.moveToInicio();
                    //System.out.print(KeyValues.INICIO);
    				break;

    			case KeyValues.FIN:
	    			line.moveToFin();
    				break;

    			case KeyValues.INSERT:
                    line.insertar();
                    break;

    			case KeyValues.SUPRIMIR:
                    line.suprimir();
    				break;

    			case KeyValues.RETROCESO:
                    line.retroceso();
    				break;

    			case KeyValues. ENTER:
    				// out of the while
    				break;

    			default:
    				line.write(keyId);
    				break;
    		}

    	}

    	return line.getPhrase();
    }

}