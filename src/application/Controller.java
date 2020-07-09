package application;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
	@FXML TextArea code,console;
	@FXML ListView<String> br;
	private Scanner scanner;
	private PrintWriter writer;
	private Random random=new Random();
	String Alph="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz<>=1234567890";
	
	@FXML
	public void newFile(ActionEvent e) {
		Main.stage.setTitle("System Expert");
		code.setText("");
		console.setText("");
	}
	
	@FXML
    public void saveFile(ActionEvent e) {
		new Save(Main.stage,this);
	}
	

	@FXML
    public void openFile(ActionEvent e) {
		code.setText("");
		console.setText("");
		new Open(Main.stage,this);
	}
	
	@FXML
    public void close(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	public void mouseNewFile(MouseEvent e) {
		Main.stage.setTitle("System Expert");
		code.setText("");
		console.setText("");
	}
	
	@FXML
	public void mouseSaveFile(MouseEvent e) {
		new Save(Main.stage,this);
	}
	
	@FXML
	public void mouseOpenFile(MouseEvent e) {
		code.setText("");
		console.setText("");
		new Open(Main.stage,this);
		
	}
	
	@FXML
	public void consulter(MouseEvent e) {
		br.getItems().clear();
		File file=new File("./br/baseregle.exp");
		try {
			scanner = new Scanner(file);
			int i=0;
			while (scanner.hasNext()) {
				br.getItems().add("R "+(++i)+" : "+scanner.nextLine());
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	

	
	@FXML
	public void ajouter(MouseEvent e) {
		TextInputDialog alert =new TextInputDialog();
		Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("./icon/expert.png"));
		alert.setTitle("Ajouter un regle");
		alert.setHeaderText("Ajouter un regle ex : ( Si Conditions alors Conclustion )");
		Optional<String> result = alert.showAndWait();
		if(compile(result.get())) {
		if (result.isPresent()){
			try {
				 writer =new PrintWriter(new FileWriter("./br/baseregle.exp",true));
				 writer.println(result.get());
				 writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	  }
		else {
		 System.out.println("Erreur compelation"); 
	  }
	}
	
	@FXML
	public void modifier(MouseEvent e) {
		int index=br.getSelectionModel().getSelectedIndex();
		if(index>=0) {
			TextInputDialog alert =new TextInputDialog();
			Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("./icon/expert.png"));
			alert.setTitle("Modifier un regle");
			alert.setHeaderText("Modifier un regle ex : ( Si Conditions alors Conclustion )");
			Optional<String> result = alert.showAndWait();
			if(compile(result.get())) {
			if (result.isPresent()){
			   br.getItems().set(index,br.getSelectionModel().getSelectedItem().substring(0, 6)+result.get() );	
			}
			}else {
				System.out.println("Erreur compelation"); 	
			}
			
		} else{
			Alert alert=new Alert(AlertType.ERROR);
			Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("./icon/expert.png"));
			alert.setHeaderText(null);
			alert.setContentText("aucun regle sélectionnée");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void suprimer(MouseEvent e) {
		try {
			br.getItems().remove(br.getSelectionModel().getSelectedIndex());
			br.getSelectionModel().clearSelection();
			Alert alert=new Alert(AlertType.INFORMATION);
			Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("./icon/expert.png"));
			alert.setHeaderText(null);
			alert.setContentText("regle est suprimer");
			alert.showAndWait();
		} catch (Exception e2) {
			Alert alert=new Alert(AlertType.ERROR);
			Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("./icon/expert.png"));
			alert.setHeaderText(null);
			alert.setContentText("aucun regle sélectionnée");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void enregistrer(MouseEvent e) {
		try {
			PrintWriter hh=new PrintWriter(new File("./br/baseregle.exp"));
			hh.print("");
			writer =new PrintWriter(new FileWriter("./br/baseregle.exp",true));
			for (int i = 0; i < br.getItems().size(); i++) {
				writer.println(br.getItems().get(i).substring(6));
			}
			writer.close();
			Alert alert=new Alert(AlertType.INFORMATION);
			Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("./icon/expert.png"));
			alert.setHeaderText(null);
			alert.setContentText("La base des regles est bien modifier");
			alert.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void pointAleatoire(MouseEvent e) {
		code.setText("");
		
	}
	
	@FXML
	public void exuete(MouseEvent e) {
		ArrayList<String>bf=new ArrayList<String>();
		ArrayList<String>br=new ArrayList<String>();
		File file=new File("./br/baseregle.exp");
		try {
			scanner = new Scanner(file);
			int i=0;
			while (scanner.hasNext()) {
				br.add(scanner.nextLine());
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	
		console.setText("");
		console.setStyle("-fx-text-fill: blue ");
		String co[]=code.getText().split(";\n");
		String but=co[co.length-1];
		boolean inf=true;
	    for (int i = 0; i < co.length-1; i++) {
			bf.add(co[i]);
			if(!compilecode(co[i],""))inf=false;
		}
	    if(inf && bf.size()==(co.length-1)) {
	    	chainage_arr(bf, br, but);
	    	
	    }else {
	    	console.appendText("Erreur de compelation ");
	    }
			
			
		}
			
	public boolean compile(String s) {
		boolean b=false;
		String t[]=s.split(" ");
		try {
			if(t[0].equals("si") && t[t.length-2].equals("alors")) {
				  if(compilecode(t[t.length-1],s)) {
					  boolean bn=true;
					  boolean bm=true;
					  for (int i = 1; i < t.length-2; i++) {
						if(i%2!=0) {
							if(!compilecode(t[i],s))bn=false;
						}else {
						    if(!t[i].equals("&")) {
						    	if(!t[i].equals("|"))bm=false;
						    }
						}
					}
					  if(bn && bm)b=true;
				  }
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public boolean isPredicat(String s) {
		boolean b=false;
		if(!s.startsWith("(") && s.endsWith(")") && s.contains("(") && test(s)) {
			b=true;
		}
		return b;
	}
	
	public boolean test(String s) {
		boolean b=false;
		int x=0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='(') {
				x=i;
				break;
			}
		}
		int j=0;
		for (int i = 0; i < x; i++) {
			if(Alph.contains(""+s.charAt(i)))j++;
		}
		String ss=s.substring(x+1,s.length()-1);
		String tab[]=ss.split(",");
		HashSet<String>has=new HashSet<String>();
		for (int i = 0; i < tab.length; i++) {
			has.add(tab[i]);
		}
		boolean bool=true;
		for (int i = 0; i < tab.length; i++) {
			for (int k = 0; k < tab[i].length(); k++) {
				if(!Alph.contains(""+tab[i].charAt(k)))bool=false;
			}
		}
		if(bool && j==x && !ss.contains(" ") && tab.length>=1 && !ss.startsWith(",") && !ss.endsWith(",") && has.size()==tab.length) {
		b=true;
		}
		return b;
	}
	
	public boolean compilecode(String string ,String ss) {
		HashSet<String>set=new HashSet<String>();
		boolean b=false;
		if(isPredicat(string)) {
			File file=new File("./br/baseregle.exp");
			try {
				scanner = new Scanner(file);
				while (scanner.hasNext()) {
					String string2 = (String) scanner.nextLine();
					String t[]=string2.split(" ");
					for (int i = 0; i < t.length; i++) {
						if(i % 2!=0) {
							set.add(t[i]);
						}
					}
					set.add(t[t.length-1]);
				}
				String pp[]=ss.split(" ");
				for (int i = 0; i < pp.length; i++) {
					if(i % 2!=0) {
						set.add(pp[i]);
					}
				}
				Object tt[]=set.toArray();
		        int x=0;
		        for (int i = 0; i < string.length(); i++) {
					if(string.charAt(i)!='(') {
						x=i;
					}else break;
				}
				boolean bb=false;
			for (int i = 0; i < tt.length; i++) {
				int v=0;
				for (int j = 0; j < tt[i].toString().length(); j++) {
					if(tt[i].toString().charAt(j)!='(') {
						v=j;
					}else break;
				}
				if(tt[i].toString().substring(0,v+1).equals(string.substring(0,x+1))) {
					String ry[]=string.substring(x+2,string.length()-1).split(",");
					String rx[]=tt[i].toString().substring(v+2,tt[i].toString().length()-1).split(",");
					if(ry.length==rx.length)bb=true;break;
				}
			}
			if(bb)b=true;
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return b;
	}
	boolean rech (ArrayList a,String c)
    {
      boolean trouve=false;
        for (int i = 0; i <a.size(); i++) 
        {
         String cc=(String)a.get(i);
        if(cc.equals(c))
        {
         trouve=true;
            
        }
        }
        return trouve;
    }
	
	boolean chainage_arr(ArrayList bf,ArrayList br,String but)
    {
      ArrayList temp=new ArrayList();
      boolean s=false; 
      String R;
     if(rech(bf, but))
     {
       return true;
     }else
     {
         for (int i = 0; i < br.size(); i++) 
          {
            String a=br.get(i).toString().split("alors ")[1];
            if(but.split("\\(")[0].equals(a.split("\\(")[0]))
            {
                temp.add(br.get(i));
             }
          }
        
         if(temp.isEmpty())
         {
          console.appendText(but+"n'est pas trouver\n");
         }else
         {
             for (int ii = 0; ii < temp.size(); ii++) 
             {        
               R=(String)temp.get(ii);
               console.appendText(R +" : est declonchable\n");
               String ss=R.split("alors")[1];
               String [] tab=R.split("alors")[0].split(" & ");
               s=true;
               for (int i = 1; i < tab.length; i++)
               {
                  if(rech(bf,tab[i]))
                  {
                      s=s && true;                     
                      console.appendText(tab[i]+"  dans la base de fait\n");
                      
                  }else
                  {
                    console.appendText("on va deduire "+tab[i]+"\n");
                    boolean c=chainage_arr(bf, br,tab[i]);
                    s=s && c;
                    if(c==false)
                    {
                     break;   
                    }
                      
                  }
                  
              }
              if(s)
              {       
                       console.appendText(ss+" est deduit\n");
                       bf.add(ss);
                       break;
               }else
               {
                console.appendText(ss+" ne peut etre deduit\n");
               }
            
            }
         
         }
     }
     return s;
    }
    public void sub(String predicat) {
		
	}
	
}
