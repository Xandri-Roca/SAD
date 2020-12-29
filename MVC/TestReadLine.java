// Bernat Xandri Zaragoza & Ramon Roca Oliver


import java.io.*;
import java.util.Observable;
import java.util.Observer;

class TestReadLine {
    public static void main(String[] args) {
    	
        EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;

        System.out.println("INICI EXECUCIO: \n\n");



        in.setRaw();
        try {
            str = in.readLine();
        } catch (IOException e) { e.printStackTrace(); }
        in.unsetRaw();

        
        System.out.println("\nline is: " + str + "\n");
    }
}