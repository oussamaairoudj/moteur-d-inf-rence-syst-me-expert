package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Open{
	
	private Scanner scanner;
	
	public Open(Stage stage,Controller controller) {
		try {
		FileChooser chooser =new FileChooser();
		chooser.setInitialDirectory(new File("./file"));
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("EXPERT", "*.exp"));
		File file=chooser.showOpenDialog(stage);
		stage.setTitle("System Expert : ( "+file.getAbsolutePath()+" ) ");
			scanner=new Scanner(file);
			while (scanner.hasNextLine()) {
				controller.code.appendText(scanner.nextLine()+"\n");
			}
		} catch (FileNotFoundException e) {
			
		}
		
	}

}
