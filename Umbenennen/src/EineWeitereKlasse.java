import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import ledControl.BoardController;

public class EineWeitereKlasse{
	
	
	private BoardController controller;
	private List<Points> pointList=new ArrayList<>();
	private static int levelnumber=1;
	private static int punkteZahl=0;
	private int schlangeGrosse=2; //der Kopf und noch ein Stück
	private int essenXPosition;
	private int essenYPosition;
	private int XPositionVonletzenStück; // wird jedes mal an Automatischbewegungsfunktion definiert werden
	private int YPositionVonletzenStück;
	private int XPositionVonerstenStück;
	private int YPositionVonerstenStück;
	

	//Konstruktor
		public EineWeitereKlasse(BoardController controller) {
			this.controller = controller;
			BordZeichnen();
			
			//start der Schlange Oben Links
			// (0,0),(0,1),(0,2).... ist für die Weiße Grenze bestzt
			// (0,0),(1,0),(2,0).... ist für die Weiße Grenze bestzt
			//Der Kopf hat die Nummer 0 , die nächste Stück hat 1 ,usw...
			pointList.add(new Points(2,1,new int[] {120,0,0}));
			controller.addColor(pointList.get(0).getXPosition(),pointList.get(0).getYPosition(),pointList.get(0).getColor());
			pointList.add(new Points(1,1,new int[] {110,0,0}));
			controller.addColor(pointList.get(1).getXPosition(),pointList.get(1).getYPosition(),pointList.get(1).getColor());
			
			keyPressed();
			
			controller.updateBoard();
			while(true)
			{
				/**wenn der Kopf der Schlange in gleicher Position mit dem Essen ,
				    wird die Schlange grösser sein und neues Essen Aufploppen.
				**/
				automatischeBewegungsfunktion();
				if(pointList.get(0).getXPosition()==essenXPosition && pointList.get(0).getXPosition()==essenYPosition)
				{
					controller.sleep(100);
					Schlangeverlängern();
					EssenAufploppen();
				}
				//der Level ist geschaft
				if(schlangeGrosse==10)
					break;
				controller.updateBoard();
			}
			
		}
		
		
		//der Anzahl von Snack gefrressenen Punkte 
		private void punkteZahl()
		{
			punkteZahl++;
		}
		
		//muss "level number" um 1 erhöhen, falls der Nutzer eine bestimmte Zahl von Punkte erreicht
		private void levelCheek()
		{
			levelnumber++;
		}
		
		//Danny
		//bewegung (nur nach rechts und links)
		private void bewegungsfunktion()
		{
			
		}
		
		//Danny
		private void automatischeBewegungsfunktion()
		{
			//speichern der letzen Position von letzem Punkt ,bevor die Schlange sich bewegt
			XPositionVonletzenStück=pointList.get(pointList.size()-1).getXPosition();
			YPositionVonletzenStück=pointList.get(pointList.size()-1).getYPosition();
			
			//von links nach rechts ist als Default der Bewegung
			if(pointList.get(0).getXPosition()<20 && pointList.get(0).getYPosition()<20)
			{
				
			}
		}
		
		//Bord muss Weiß umgeben 
		private void BordZeichnen()
		{
			
		}
		
		
		//game over : Kopf mit weißer Wand oder mit sich selbst kollidiert 
		private void EndedesSpieles()
		{
			
		}
		
		//in einer bestimmten Zeit muss das Essen (Ein gefärbter Punkt ) aufploppen
		private void EssenAufploppen()
		{
			
		}
		
		//Danny
		//sobald die Schlange etwas isst ,muss sie länger sein werden
		private void Schlangeverlängern()
		{
			//füg der neue Punkt an die gleiche Position von dem letzen Punkt hinzu ,denn der letzen alten Punkt ist schon bewegt werden
			pointList.add(new Points(XPositionVonletzenStück,YPositionVonletzenStück,new int[] {120,0,0}));
			controller.addColor(XPositionVonletzenStück,YPositionVonletzenStück,pointList.get(YPositionVonletzenStück).getColor());
			schlangeGrosse++; //wenn 10 -> level geschafft
		}
		

		//to know if a key pressed
		private void keyPressed() 
		{
			 KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
					 new KeyEventDispatcher() 
					 {

						 @Override
						 public boolean dispatchKeyEvent(KeyEvent e) {
							 // TODO Auto-generated method stub
							 switch (e.getKeyCode())
							 {
							 case 37:
								 System.out.println("left");
								 break;
							 case 38:
								 System.out.println("up");
								 break;
							 case 39:
								 System.out.println("right");
								 break;
							 case 40:
								 System.out.println("down");
								 break;
							 default:
								 break;
							 }
							 return false;
						 }
					 }
					);
		}
		
}
