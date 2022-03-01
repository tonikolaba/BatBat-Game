package al.tonikolaba.main;

import java.awt.EventQueue;
import java.util.logging.Level;

import javax.swing.JFrame;

import al.tonikolaba.handlers.LoggingHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatBatGame extends JFrame implements CommandLineRunner {

	private static final long serialVersionUID = -437004379167511593L;

	@Override
	public void run(String... arg0) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrame window = new JFrame("BatBat Game \u2122");
					window.add(new GamePanel());
					window.setContentPane(new GamePanel());
					window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					window.setResizable(true);
					window.pack();
					window.setLocationRelativeTo(null);
					window.setVisible(true);
				} catch (Exception e) {
					LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
				}
			}
		});
	}

}
