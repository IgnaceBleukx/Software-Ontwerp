package canvaswindow;

import Utils.DebugPrinter;


public class Program {

	public static void main(String[] args) {
		      java.awt.EventQueue.invokeLater(() -> {
		         MyCanvasWindow mcw = new MyCanvasWindow("Tablr");
		         mcw.show();
		         ////Voor het maken van nieuwe recodings: 
		         //maak de passende folder manueel aan en verander in de volgende lijn het pad
		         
		         mcw.recordSession("recordings/VerticalScrollListenersUITableTest/test.test");
		      });
		      
		      
	}
	
}
