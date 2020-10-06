import java.io.*;
import java.lang.Runtime;
import java.lang.Process;
public class EditableBufferedReader extends BufferedReader {


    public EditableBufferedReader(Reader r){
        super(r);
    }

    // switch from cooked mode to raw mode in the console
    public void setRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Process newProcess1=Runtime.getRuntime().exec(cmd);
            newProcess1.waitFor();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void unsetRaw(){
        try{

            String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"}
            Process newProcess=Runtime.getRuntime().exec(cmd);
            newProcess.waitFor();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
