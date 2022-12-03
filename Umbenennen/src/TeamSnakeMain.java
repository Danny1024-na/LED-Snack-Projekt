import java.awt.event.KeyEvent;
import ledControl.BoardController;
import ledControl.LedConfiguration;
import ledControl.gui.KeyBuffer;

public class TeamSnakeMain {

	public static void main(String[] args) {
		//größeres Board falls gewünscht
//		BoardController.getBoardController(LedConfiguration.LED_20x20_EMULATOR);
		BoardController controller = BoardController.getBoardController(LedConfiguration.LED_20x20_EMULATOR);
		
		//Das Objekt in der anderen Klasse wird erstellt
		EineWeitereKlasse ewk = new EineWeitereKlasse(controller);
		
	}
}
