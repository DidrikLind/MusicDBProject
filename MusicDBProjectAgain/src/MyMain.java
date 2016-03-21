
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import Connection.MyJDBCCloser;
import Connection.MyJDBConnector;
import musicController.MyController;
import musicModel.TopModel.MyTopModel;
import musicView.MyMainView;





public class MyMain extends JFrame implements WindowListener{

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> 
        { 
            new MyMain();
        });
	}
	
	public MyMain() {
		MyMainView view = new MyMainView(this);
		add(view);
		MyTopModel topModel = new MyTopModel();
		MyController cont = new MyController(view, topModel);
		
		addWindowListener(this);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        
        
        
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
//		try {
//        	File file = new File("C:\\Users\\didrik\\workspace\\MusicDBProjectAgain\\media\\Gladiator.mp3");
//        	FileInputStream fis = new FileInputStream(file);
//        	BufferedInputStream bis = new BufferedInputStream(fis);
//        	try {
//				Player player  = new Player(bis);
//				player.play();
//			} catch (JavaLayerException ex) {
//				// TODO: handle exception
//				
//			}
//        }catch(IOException e) {
//        	
//        }
		
		
		//JavaFX doesnt work for me in eclipse, check this l8r!! :)
		
//		String song = "C:\\Users\\didrik\\workspace\\MusicDBProjectAgain\\media\\Gladiator.mp3";
//		Media hit = new Media(song));
//		MediaPlayer mediaPlayer = new MediaPlayer(hit);
//		mediaPlayer.play();
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		MyJDBCCloser.close(MyJDBConnector.getJDBCConnection());
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
