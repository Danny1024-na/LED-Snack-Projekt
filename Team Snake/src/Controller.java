import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import ledControl.BoardController;
import ledControl.gui.KeyBuffer;

public class Controller{
	
	
	private BoardController controller;
	private static KeyBuffer buffer;
	private List<Points> pointList=new ArrayList<>();
	
	private int levelNumber;
	
	private int schlangeGrosse=2; //der Kopf und noch ein Stueck (zwei Punkte)
	
	private static int essenXPosition;
	private static int essenYPosition;
	
	private int XPositionVonletzenStueck; // wird jedes mal durch die "Automatischbewegungsfunktion" initialisiert
	private int YPositionVonletzenStueck;
	
	private boolean left=false;
	private boolean right=true;
	private boolean up=false;
	private boolean down=false;
	
	//das Essen hat bei jedem Level eine andere Farbe
	private int essenColor1;
	private int essenColor2;
	private int essenColor3;
	
	

	//Konstruktor
		public Controller(BoardController controller,int levelNumber) {
			this.controller = controller;
			this.levelNumber=levelNumber;
			this.essenColor1=(int)(127/this.levelNumber);
			this.essenColor2 =(127-this.levelNumber*5);
			this.essenColor3 =(127-this.levelNumber*3);
			BordZeichnen();
			
			//start der Schlange ist Oben Links
			// (0,0),(0,1),(0,2).... ist für die Weisse Grenze bestzt
			// (0,0),(1,0),(2,0).... ist für die Weisse Grenze bestzt
			//Der Kopf hat die Nummer 0 in der Pointliste , das naechste Stueck hat die Nummer 1 ,usw...
			pointList.add(new Points(2,1,new int[] {120,0,0}));
			controller.addColor(pointList.get(0).getXPosition(),pointList.get(0).getYPosition(),pointList.get(0).getColor());
			pointList.add(new Points(1,1,new int[] {115,0,0}));
			controller.addColor(pointList.get(1).getXPosition(),pointList.get(1).getYPosition(),pointList.get(1).getColor());
			EssenAufploppen();
			
			controller.updateBoard();
			while(true)
			{
				keyPressed();
				/**wenn der Kopf der Schlange auf der gleichen Position mit dem Essen ist ,
				    wird die Schlange grösser sein und neues Essen Aufploppen.
				**/
				selbstEssen();
				if(pointList.get(0).getXPosition()==essenXPosition && pointList.get(0).getYPosition()==essenYPosition)
				{
					controller.setColor(essenXPosition, essenYPosition, new int[] {0,0,0});
					Schlangeverlaengern();
					//hier wird das Essen gezeichnet (ein Punkt)
					EssenAufploppen();
				}
				
				if(pointList.get(0).getXPosition()<19 && pointList.get(0).getYPosition()<19 && pointList.get(0).getYPosition()>0 && pointList.get(0).getXPosition()>0 )
				{
					automatischeBewegung();
				}
				else                       //verloren
				{
					EndedesSpieles();
					break;
				}

				if(schlangeGrosse==30)
				{
					if(levelNumber==7) {
						gewinnen();
						break;
					}
					naechsterlevel();
					break;
				}
				
				controller.updateBoard();
			}

		}
		
		//die Schlange verkleinert sich, wenn sie gegen sich selbst kollidiert
		private void selbstEssen()
		{
			
			for(int i=1;i<pointList.size();i++)
			{
				//Ueberpruefung ob der Kopf mit einem anderem Koerperteil kollidiert 
				if(pointList.get(0).getXPosition()==pointList.get(i).getXPosition() && pointList.get(0).getYPosition()==pointList.get(i).getYPosition())
				{
					//wird die "schalngeGrosse" zu (i-1) verringert und alle die nach i-1 Punkte von pointList geloescht
					this.schlangeGrosse=i-1;
					for(int j=pointList.size()-1;j>=i-1;j--) //die for-Schleife muss absteigend sein, denn die size der "pointList" verringert sich jedes mal um 1 
						pointList.remove(j);
				}
			}
		}
		
		//Das Ende des Spieles (7 levels)
		private void gewinnen()
		{
			new JOptionPane();
			int dialogeResult=JOptionPane.showConfirmDialog(null, "You have copmleted all the levels " 
															+ "Do you want another roud?",
														"YOU WIN !!! ", JOptionPane.YES_NO_OPTION);
			
			if(dialogeResult==JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			if(dialogeResult==JOptionPane.YES_OPTION)
			{
				controller.resetColors();
				Controller ewk2 = new Controller(this.controller,1);
			}
		}
		
		
		public void naechsterlevel()
		{
			new JOptionPane();
			int dialogeResult=JOptionPane.showConfirmDialog(null, "next level?",
														"YOU WIN !!! ", JOptionPane.YES_NO_OPTION);
			
			if(dialogeResult==JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			if(dialogeResult==JOptionPane.YES_OPTION)
			{
				controller.resetColors();
				Controller ewk2 = new Controller(this.controller,++this.levelNumber);
			}
		}

		
		//game over :wenn der Kopf mit der weissen Abgrenzung kollidiert
		private void EndedesSpieles()
		{
			new JOptionPane();
			int dialogeResult=JOptionPane.showConfirmDialog(null, "one more round?",
														"YOU LOST !!! ", JOptionPane.YES_NO_OPTION);
			
			if(dialogeResult==JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			if(dialogeResult==JOptionPane.YES_OPTION)
			{
				controller.resetColors();
				// hier wird abgefragt, ob man einen neuen Versuch starten moechte
				Controller ewk2 = new Controller(this.controller,1);
			}
		}
		
		//das Essen (ein gefaerbter Punkt) ploppt in einer bestimmten Zeit auf
		private void EssenAufploppen()
		{
			boolean x=true;
			while(x)
			{
				//random * (max (18)- min (1)) +1
				essenXPosition = (int) ((Math.random()*17 )+1);
				essenYPosition = (int) ((Math.random()*17 )+1);
				//damit das Essen nicht auf den gleichen Positionen, wie die Positionen von der Schlange, gezeichnet wird
				x=false;
				for(int i=0;i<pointList.size();i++)
					if(essenXPosition==pointList.get(i).getXPosition() && essenYPosition==pointList.get(i).getYPosition())
					{
						x=true;
					}
			}
			controller.addColor(essenXPosition, essenYPosition, new int[] {this.essenColor1,this.essenColor2,this.essenColor3});
		 }

		//resettet alle Farben >> ausser << die Farbe des Bords und des Essens
		private void Farbenreset()
		{
			for(int i=1;i<19;i++)
				for(int j=1;j<19;j++)
					if(!Arrays.equals(controller.getColorAt(i, j), new int[] {this.essenColor1,this.essenColor2,this.essenColor3}))
						controller.setColor(i, j, new int[] {0,0,0});
		}

		//aenderen der Position, der Punkte, in Pointlist ( von size-1 zu 1) , 0 wird alleine behandelt siehe unten (Zeile 226)
				private void positionAendern()
				{
					for (int i=pointList.size()-1;i>0;i--)
					{
						pointList.get(i).SetXPosition(pointList.get(i-1).getXPosition());
						pointList.get(i).SetYPosition(pointList.get(i-1).getYPosition());
					}
					geschwindigkeit();
				}
				
		// die standard Richtung der Bewegung im Spiel ist von links nach rechts
		private void automatischeBewegung()
		{
			// bevor die Schlange sich bewegt, wird hier die letzte Position vom letzem Punkt gespeichert
			XPositionVonletzenStueck=pointList.get(pointList.size()-1).getXPosition();
			YPositionVonletzenStueck=pointList.get(pointList.size()-1).getYPosition();
			
			Farbenreset();
			positionAendern();
			
			//Handlug aus der "Position 0"
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
		}
		

		// sobald das level geschafft ist, wird die Geschwindigkeit erhoeht (jedes mal um 60 Millisekunden)  
		private void geschwindigkeit()
		{
			controller.sleep(500-(levelNumber*60));
		}
		
		//die abgrenzung des Spielfeldes wird eingezeichnet (in weiss) 
		private void BordZeichnen()
		{
			for( int i = 0; i < 20; i++) 
			{
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
		
		//sobald die Schlange etwas isst ,wird sie laenger
		private void Schlangeverlaengern()
		{
			//Fuegt einen neue Punkt an das Ende der Schlange hinzu. 
			// {120 -(this.schlangeGrosse*2) beschreibt wie Gross jedes neue Stueck sein muss : je weiter das Stueck (Point) vom Kopf entfernt ist, desto kleiner ist der Kreisradius
			pointList.add(new Points(XPositionVonletzenStueck,YPositionVonletzenStueck,new int[] {120-(this.schlangeGrosse*2),0,0}));
			controller.addColor(XPositionVonletzenStueck,YPositionVonletzenStueck,pointList.get(pointList.size()-1).getColor());
			schlangeGrosse++; //wenn 30 -> level geschafft
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
						if(down) //beim druecken der Entgegengesetze Taste wird der Befehl nicht funktionieren
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
			buffer.clear(); // Loescht alle (Buffered) Anweisungen  : Im Fall dass verschiedene Tasten zu schnell nacheinander gedrueckt werden, wird der Befehl nicht ausgefuert
		}
		
}