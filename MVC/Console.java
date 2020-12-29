// Bernat Xandri Zaragoza & Ramon Roca Oliver


import java.util.Observable;
import java.util.Observer;

public class Console implements Observer{
	// represents the visualization of the data that model contains.
	
	private int a;

	public Console(){
		a = 0;
	}

	public void update(Observable observable, Object o){
		int[] order = (int[]) o; // We need 3 int values: KeyID, cursor position update and Insert on/off
		int keyId = order[0];
		String cursorUpdate = String.valueOf(order[1]);
		int insert = order[2];

		switch(keyId){

			case KeyValues.RIGHT:
				System.out.print("\u001b[1C");
				break;

    			case KeyValues.LEFT:
				System.out.print("\u001b[1D");
				break;

    			case KeyValues.INICIO:
    				System.out.print("\u001b[" + cursorUpdate + "D");
    				//System.out.print(cursorUpdate);
    				break;

    			case KeyValues.FIN:
	    			System.out.print("\u001b[" + cursorUpdate + "C");
    				break;

			case KeyValues.INSERT:
				if(insert == 0){
			    		System.out.print("\u001b[4h");
				}else{
			    		System.out.print("\u001b[4l");
				}
				break;

			case KeyValues.SUPRIMIR:
				System.out.print("\u001b[P");
				System.out.print("\u001b[1D");
				break;

			case KeyValues.RETROCESO:
				System.out.print("\u001b[1D");
				System.out.print("\u001b[P");
				break;

			case KeyValues.ENTER:
				// out of the while
				break;

			default:
				System.out.print((char) keyId);
				break;
		}
	}
}
