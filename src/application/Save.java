package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Save {

	private PrintWriter writer;

	public Save(Stage stage,Controller controller) {
		try {
		FileChooser chooser =new FileChooser();
		chooser.setInitialDirectory(new File("./file"));
		chooser.setInitialFileName("exmple");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("EXPERT", "*.exp"));
		File file=chooser.showSaveDialog(stage);
			file.createNewFile();
			writer = new PrintWriter(file);
			writer.println(controller.code.getText());
			writer.flush();
		} catch (IOException e) {
			
		}
		
	}
}
