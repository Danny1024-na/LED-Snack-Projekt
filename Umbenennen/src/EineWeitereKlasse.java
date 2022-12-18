import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ledControl.BoardController;
import ledControl.LedConfiguration;
import ledControl.gui.KeyBuffer;

public class EineWeitereKlasse{
	
	
	private BoardController controller;
	private static KeyBuffer buffer;
	private List<Points> pointList=new ArrayList<>();
	
	private int levelNumber;
	
	private int schlangeGrosse=2; //der Kopf und noch ein Stück
	
	private static int essenXPosition;
	private static int essenYPosition;
	
	private int XPositionVonletzenStück; // wird jedes mal an Automatischbewegungsfunktion definiert werden
	private int YPositionVonletzenStück;
	
	private boolean left=false;
	private boolean right=true;
	private boolean up=false;
	private boolean down=false;
	
	//das Essen bei jedem Level hat einige Farbe
	private int essenColor1;
	private int essenColor2;
	private int essenColor3;
	
	

	//Konstruktor
		public EineWeitereKlasse(BoardController controller,int levelNumber) {
			this.controller = controller;
			this.levelNumber=levelNumber;
			this.essenColor1=(int)(127/this.levelNumber);
			this.essenColor2 =(127-this.levelNumber*5);
			this.essenColor3 =(127-this.levelNumber*3);
			BordZeichnen();
			
			//start der Schlange Oben Links
			// (0,0),(0,1),(0,2).... ist für die Weiße Grenze bestzt
			// (0,0),(1,0),(2,0).... ist für die Weiße Grenze bestzt
			//Der Kopf hat die Nummer 0 , die nächste Stück hat 1 ,usw...
			pointList.add(new Points(2,1,new int[] {120,0,0}));
			controller.addColor(pointList.get(0).getXPosition(),pointList.get(0).getYPosition(),pointList.get(0).getColor());
			pointList.add(new Points(1,1,new int[] {115,0,0}));
			controller.addColor(pointList.get(1).getXPosition(),pointList.get(1).getYPosition(),pointList.get(1).getColor());
			EssenAufploppen();
			
			controller.updateBoard();
			while(true)
			{
				keyPressed();
				/**wenn der Kopf der Schlange in gleicher Position mit dem Essen ,
				    wird die Schlange grösser sein und neues Essen Aufploppen.
				**/
				if(pointList.get(0).getXPosition()==essenXPosition && pointList.get(0).getYPosition()==essenYPosition)
				{
					controller.setColor(essenXPosition, essenYPosition, new int[] {0,0,0});
					Schlangeverlängern();
					//hier muss das Essen gezeichnet werden
					EssenAufploppen();
				}
				
				if(pointList.get(0).getXPosition()<19 && pointList.get(0).getYPosition()<19 && pointList.get(0).getYPosition()>0 && pointList.get(0).getXPosition()>0 )
				{
					automatischeBewegungsfunktion();
				}
				else                       //verloren
				{
					EndedesSpieles();
					break;
				}

				if(levelNumber>10)
				{
					gewinnen();
					break;
				}
				
				if(schlangeGrosse==30)
				{
					naechsterlevel();
					break;
				}
				
				controller.updateBoard();
			}

		}
		
		//am Ende des Spieles (10 levels)
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
				EineWeitereKlasse ewk2 = new EineWeitereKlasse(this.controller,1);
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
				EineWeitereKlasse ewk2 = new EineWeitereKlasse(this.controller,++this.levelNumber);
			}
		}

		
		//game over : Kopf mit weißer Wand oder mit sich selbst kollidiert 
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
				//BoardController controller = BoardController.getBoardController(LedConfiguration.LED_20x20_EMULATOR);
				EineWeitereKlasse ewk2 = new EineWeitereKlasse(this.controller,1);
			}
		}
		
		//in einer bestimmten Zeit muss das Essen (Ein gefärbter Punkt ) aufploppen
		private void EssenAufploppen()
		{
			boolean x=true;
			while(x)
			{
				//random * (max (18)- min (1)) +1
				essenXPosition = (int) ((Math.random()*17 )+1);
				essenYPosition = (int) ((Math.random()*17 )+1);
				//damit das Essen nicht auf die gleiche Positionen, die zu der Schlange gehören , gezeichnet
				x=false;
				for(int i=0;i<pointList.size();i++)
					if(essenXPosition==pointList.get(i).getXPosition() && essenYPosition==pointList.get(i).getYPosition())
					{
						x=true;
					}
			}
			controller.addColor(essenXPosition, essenYPosition, new int[] {this.essenColor1,this.essenColor2,this.essenColor3});
		 }

		//reset alle Farben (ausßer) die Farbe des Bords und des Essens
		private void Farbenreset()
		{
			for(int i=1;i<19;i++)
				for(int j=1;j<19;j++)
					if(!Arrays.equals(controller.getColorAt(i, j), new int[] {this.essenColor1,this.essenColor2,this.essenColor3}))
						controller.setColor(i, j, new int[] {0,0,0});
		}
		
		//Danny
		//von links nach rechts ist als Default der Bewegung
		private void automatischeBewegungsfunktion()
		{
			//speichern der letzen Position von letzem Punkt ,bevor die Schlange sich bewegt
			XPositionVonletzenStück=pointList.get(pointList.size()-1).getXPosition();
			YPositionVonletzenStück=pointList.get(pointList.size()-1).getYPosition();
			
			Farbenreset();
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
		}
		
		//änderen der Position der Punkte in Pointlist ( von size-1 zu 1) , 0 wird alleine behandelt
		private void positionAendern()
		{
			for (int i=pointList.size()-1;i>0;i--)
			{
				pointList.get(i).SetXPosition(pointList.get(i-1).getXPosition());
				pointList.get(i).SetYPosition(pointList.get(i-1).getYPosition());
			}
			geschwindigkeit();
		}

		//die Geschwindigkeit wird erhöht wenn der level geschafft ist (30 Millisekunden) jedes mal 
		private void geschwindigkeit()
		{
			controller.sleep(500-(levelNumber*30));
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
			// {120 -(this.schlangeGrosse*2) beschreibt wie Gross muss jede neue Stück sein muss : je mehr sie weit von Kopf ist,desto kleiner Kreisradius hat
			pointList.add(new Points(XPositionVonletzenStück,YPositionVonletzenStück,new int[] {120-(this.schlangeGrosse*2),0,0}));
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
