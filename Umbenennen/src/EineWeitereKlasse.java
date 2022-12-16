
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import ledControl.BoardController;
import ledControl.gui.KeyBuffer;

public class EineWeitereKlasse{
	
	
	private BoardController controller;
	private static KeyBuffer buffer;
	private List<Points> pointList=new ArrayList<>();
	
	private static int levelnumber=1;
	
	private int schlangeGrosse=2; //der Kopf und noch ein Stück
	
	private static int essenXPosition;
	private static int essenYPosition;
	
	private int XPositionVonletzenStück; // wird jedes mal an Automatischbewegungsfunktion definiert werden
	private int YPositionVonletzenStück;
	
	private boolean left=false;
	private boolean right=true;
	private boolean up=false;
	private boolean down=false;
	
	
	private final int x[] = new int[400];
    private final int y[] = new int[400];
	
	

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
			pointList.add(new Points(1,1,new int[] {115,0,0}));
			controller.addColor(pointList.get(1).getXPosition(),pointList.get(1).getYPosition(),pointList.get(1).getColor());
			
			
			controller.updateBoard();
			while(true)
			{
				keyPressed();
				/**wenn der Kopf der Schlange in gleicher Position mit dem Essen ,
				    wird die Schlange grösser sein und neues Essen Aufploppen.
				**/
				if(pointList.get(0).getXPosition()==essenXPosition && pointList.get(0).getYPosition()==essenYPosition)
				{
					Schlangeverlängern();
				}
				
				if(pointList.get(0).getXPosition()<19 && pointList.get(0).getYPosition()<19 && pointList.get(0).getYPosition()>0 && pointList.get(0).getXPosition()>0 )
				{
					automatischeBewegungsfunktion();
				}
				//muss "level number" um 1 erhöhen, falls der Nutzer eine bestimmte Zahl (10)von Punkte erreicht
				else if(schlangeGrosse==12)
				{
					levelnumber++;
					EndedesSpieles();
				}
				else                          //verloren
				{
					EndedesSpieles();
					break;
				}
				
				controller.updateBoard();
			}

		}
		

		
		//game over : Kopf mit weißer Wand oder mit sich selbst kollidiert 
		private void EndedesSpieles()
		{
			
		}
		
		//in einer bestimmten Zeit muss das Essen (Ein gefärbter Punkt ) aufploppen
		private void EssenAufploppen()
		{
			essenXPosition = (int) (Math.random() * 20);
			essenYPosition = (int) (Math.random() * 20);
		 }
		
		
		//Danny
		//von links nach rechts ist als Default der Bewegung
		private void automatischeBewegungsfunktion()
		{
			//speichern der letzen Position von letzem Punkt ,bevor die Schlange sich bewegt
			XPositionVonletzenStück=pointList.get(pointList.size()-1).getXPosition();
			YPositionVonletzenStück=pointList.get(pointList.size()-1).getYPosition();
			
			//reset alle Farben ausßer die Farbe des Bords
			for(int i=1;i<19;i++)
				for(int j=1;j<19;j++)
					controller.setColor(i, j, new int[] {0,0,0});
			//änderen der Position der Punkte in Pointlist ( von size-1 zu 1) , 0 wird alleiene behandelt
			positionAendern();
			//Handlugs von 0 Position
			if(right)
			{
				pointList.get(0).SetXPosition(pointList.get(0).getXPosition()+1);
			}
			if(left)
			{
				pointList.get(0).SetXPosition(pointList.get(0).getXPosition()-1);
			}
			if(up)
			{
				pointList.get(0).SetYPosition(pointList.get(0).getYPosition()-1);
			}
			if(down)
			{
				pointList.get(0).SetYPosition(pointList.get(0).getYPosition()+1);
			}
			
			newPositionenZeichnen();
			
		}
		
		private void newPositionenZeichnen()
		{
			for(int i=0;i<pointList.size();i++)
			{
				controller.addColor(pointList.get(i).getXPosition(),pointList.get(i).getYPosition(),pointList.get(i).getColor());
			}
			
			//hier muss das Essen gezeichnet werden
			EssenAufploppen();
		}
		
		
		private void positionAendern()
		{
			for (int i=pointList.size()-1;i>0;i--)
			{
				pointList.get(i).SetXPosition(pointList.get(i-1).getXPosition());
				pointList.get(i).SetYPosition(pointList.get(i-1).getYPosition());
			}
			//Geschwindigkeit 
			//die Geschwindigkeit wird erhöht wenn der level geschafft ist (50 Millisekunden) jedes mal 
			controller.sleep(700-(levelnumber*50));
		}
		
		//Bord muss Weiß umgeben 
		private void BordZeichnen()
		{
			for( int i = 0; i < 20; i++) {
				controller.setColor(i, 0, 127, 127, 127);
				controller.setColor(i, 19, 127, 127, 127);
				controller.updateBoard();
			}
			
			for( int i = 0; i < 20; i++) {
				controller.setColor(0, i, 127, 127, 127);
				controller.setColor(19, i, 127, 127, 127);
				controller.updateBoard();
			}
		}
		
		//sobald die Schlange etwas isst ,muss sie länger sein werden
		private void Schlangeverlängern()
		{
			//füg der neue Punkt an die gleiche Position von dem letzen Punkt hinzu ,denn der letzen alten Punkt ist schon bewegt werden
			// {120 -(this.schlangeGrosse*5) beschreibt wie Gross muss jede neue Stück sein muss : je mehr sie weit von Kopf ist,desto kleiner Kreisradius hat
			pointList.add(new Points(XPositionVonletzenStück,YPositionVonletzenStück,new int[] {120-(this.schlangeGrosse*5),0,0}));
			controller.addColor(XPositionVonletzenStück,YPositionVonletzenStück,pointList.get(pointList.size()-1).getColor());
			schlangeGrosse++; //wenn 12 -> level geschafft
		}
		

		//to know if a key pressed
		private void keyPressed() 
		{
			buffer = controller.getKeyBuffer();
			KeyEvent event = buffer.pop();
			if (event != null){
				if (event.getID() == java.awt.event.KeyEvent.KEY_PRESSED){
					switch (event.getKeyCode()){
					case java.awt.event.KeyEvent.VK_UP:
						if(down) //Darf keine Gegenrichtung ausgeführt werden
							break;
						left = right = down =false;
						up =true;
						break;
					case java.awt.event.KeyEvent.VK_DOWN:
						if(up)
							break;
						up = left = right =false;
						down =true;
						break;
					case java.awt.event.KeyEvent.VK_LEFT:
						if(right)
							break;
						right = up = down =false;
						left =true;
						break;
					case java.awt.event.KeyEvent.VK_RIGHT:
						if(left)
							break;
						up = down =left =false;
						right =true;
						break;
					default:
					}
				}
			}
			buffer.clear(); // Lösch alle (Buffered) Anweisungen  : Im Fall ein Key mehrmals gedrückt ist 
		}
		
}
