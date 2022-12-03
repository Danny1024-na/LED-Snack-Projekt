import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ledControl.BoardController;

public class EineWeitereKlasse {
	//Von UmbennnenMain übergebener Boardcontroller
	private BoardController controller;
	private List<Points> pointList=new ArrayList<>();
	private static int levelnumber=1;
	private static int punkteZahl=0;
	private int SchlangeGrosse=2; //der Kopf und noch ein St�ck
	private int[] xYCordinaten=new int[2];
	private int[] colors=new int[3];
	
	//Konstruktor
		public EineWeitereKlasse(BoardController controller) {
			this.controller = controller;
			BordZeichnen();
			
			//start der Schlange Oben Links
			// (0,0),(0,1),(0,2).... ist f�r die Wei�e Grenze bestzt
			// (0,0),(1,0),(2,0).... ist f�r die Wei�e Grenze bestzt
			//Der Kopf hat die Nummer 0 , die n�chste St�ck hat 1 ,usw...
			pointList.add(new Points(2,1,new int[] {120,0,0}));
			controller.addColor(pointList.get(0).getXPosition(),pointList.get(0).getYPosition(),pointList.get(0).getColor());
			pointList.add(new Points(1,1,new int[] {110,0,0}));
			controller.addColor(pointList.get(1).getXPosition(),pointList.get(1).getYPosition(),pointList.get(1).getColor());
			
			
			
			controller.updateBoard();
			
			
		}
		
		//der Anzahl von Snack gefrressenen Punkte 
		private void punkteZahl()
		{
			punkteZahl++;
		}
		
		//muss "level number" um 1 erh�hen, falls der Nutzer eine bestimmte Zahl von Punkte erreicht
		private void levelCheek()
		{
			levelnumber++;
		}
		
		//Danny
		//bewegung (nur nach rechts und links)
		private void bewegungsfunktion()
		{
			
		}
		
		//Bord muss Wei� umgeben 
		private void BordZeichnen()
		{
			
		}
		
		
		//game over : Kopf mit wei�er Wand oder mit sich selbst kollidiert 
		private void EndedesSpieles()
		{
			
		}
		
		//in einer bestimmten Zeit muss das Essen (Ein gef�rbter Punkt ) aufploppen
		private void EssenAufploppen()
		{
			
		}
		
		//Danny
		//sobald die Schlange etwas isst ,muss sie l�nger sein werden
		private void Schlangeverl�ngern()
		{
			
		}
		
}
