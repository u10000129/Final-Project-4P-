package game;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame{
	
	public final static int windowWidth = 800, windowHeight = 600;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Transmission transmission = new Transmission();
		MyApplet myApplet = new MyApplet(transmission);
		myApplet.init();
		
		JFrame window = new JFrame("Final Project");
		window.setContentPane(myApplet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		window.setVisible(true);
		window.setLocation(300, 50);		
		
	}

}
