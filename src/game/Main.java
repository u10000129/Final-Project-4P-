package game;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame implements Observer{
	
	public final static int windowWidth = 800, windowHeight = 600;
	private static JFrame window;
	
	public Main() {
			
		window = new JFrame("Final Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		window.setVisible(true);	
		window.setLocation(300, 50);		
	}
	
	public void update(Observable obs, Object o) {
		System.out.println(obs.getClass()+" informed the observer");
		showMyApplet();
	}
	
	private void showMyApplet() {
		
		Transmission transmission = new Transmission();
		MyApplet myApplet = new MyApplet(transmission);
		myApplet.init();
		
		window.setContentPane(myApplet);
	}
	
	
	public static void main(String[] args) {
	
		Main main = new Main();
		StartApplet startApplet = new StartApplet();
		startApplet.init();
		startApplet.size(MyApplet.width, MyApplet.height);
		
		StartScreen startScreen = new StartScreen(startApplet);
		
		startScreen.display();
		window.setContentPane(startApplet);
		startScreen.addObserver(main);
	}

}
