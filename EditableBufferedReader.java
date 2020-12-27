import java.io.*;
import java.lang.Runtime;
import java.lang.Process;


//right, left: caràcter dreta, caràcter esquerra amb les fletxes.
//home, end: principi, final de línia.
//ins: commuta mode inserció/sobre-escriptura.
//del, bksp: esborra caràcter actual o caràcter a l’esquerra.

public class EditableBufferedReader extends BufferedReader {


    public EditableBufferedReader(Reader r){
        super(r);
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
        char[] ch = new char[length]; // we only need this to be able to super(). We won't use offset.
        // we invoke the inheritance of the parent class to get the input
        int numChars = super.read(ch, 0, length); // array, offset, max length
        int[] ch_int = new int[numChars]; // int array where we will save the code of the key pressed

        for(int i = 0; i < numChars; i++){
            ch_int[i] = (int) ch[i];
            //System.out.println("chars: " + String.valueOf(ch_int[i])); // we can see the different KeyID of the key
        }

        // We filter out ch_int s that doesn't start with the 27. We have seen that characters (letters) only take 1 position
        // in the ch_int.
        if(ch_int[0] == 27){
            String code = "";
            for(int i = 0; i < numChars; i++){
                code += String.valueOf(ch_int[i]);    
            }
            //System.out.println("code: " + code);  // used to obtain the keyIDs

            return Integer.parseInt(code); // we return the string code trnslated into a int.
        }

        // in case that it's just a letter, we return it's KeyID as an integer.
        return ch_int[0];
    }

// reads and updates the line we are writting
    public String readLine() throws IOException{
    	Line line = new Line();
    	int keyId = 0;

    	while(keyId != KeyValues.ENTER){

    		keyId = this.read();

    		switch(keyId){

    			// ESC = "\u001b"  escape code
				// "left" => "[1D"			 "right" => "[1C"		"up" => "[1A"		 			"down" => "[1B"
				// "linestart" => "[9D"		 "topleft" => "[H"		"bottomright" => "[24;79H"		"delete" = > "[P"

    			case KeyValues.RIGHT:
    				if(line.getCursorPosition() < line.getNumLetters()){
                        System.out.print("\u001b[1C");
                        line.moveRight();
                    }
                    break;

    			case KeyValues.LEFT:
                    if(line.getCursorPosition() > 0){
                        System.out.print("\u001b[1D");
                        line.moveLeft();
                    }
                    break;

    			case KeyValues.INICIO:

    				System.out.print("\u001b[" + String.valueOf(line.getCursorPosition()) + "D");
    				line.moveToInicio();
    				break;

    			case KeyValues.FIN:
    				if(line.getCursorPosition() != line.getNumLetters()){
	    				System.out.print("\u001b[" + String.valueOf(line.getNumLetters() - line.getCursorPosition()) + "C");
	    				line.moveToFin();
	    			}
    				break;

    			case KeyValues.INSERT:
                    if(line.getInsertarState()){
                        System.out.print("\u001b[4h");
                    }else{
                        System.out.print("\u001b[4l");
                    }
                    line.insertar();
                    break;

    			case KeyValues.SUPRIMIR:
                    if(line.getCursorPosition() < line.getNumLetters()){
                        System.out.print("\u001b[P");
                        line.suprimir();
                    }
    				break;

    			case KeyValues.RETROCESO:
                    if(line.getCursorPosition() > 0){
                        System.out.print("\u001b[1D"); //move one position to the left
                        System.out.print("\u001b[P"); //erase the character located on cursor's position
                        line.retroceso();
                    }
    				break;

    			case KeyValues. ENTER:
    				// out of the while
    				break;

    			default:
    				System.out.print((char) keyId);
    				line.write(keyId);
    				break;
    		}

    	}

    	return line.getPhrase();
    }

}