package myveryOwnproject.src.airplane;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AirSpace {

	private JFrame window;
	private int linhasi;
	private RunAwayLanding rwl;
	private JTextField screen;

	public AirSpace() {
		window = new JFrame("My very own airplane Project");
		window.setSize(500, 500);
		window.setLocation(10, 50);
		Container container = window.getContentPane();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.setLayout(new BorderLayout());
		screen = new JTextField("WELCOME TO AIRPLANE SIMULTOR", getNumColumns());
		String linhas = JOptionPane.showInputDialog(window,
				"Insira o numero de Colunas desejado");
		linhasi = Integer.parseInt(linhas);

		rwl = new RunAwayLanding(linhasi, linhasi, this);
		rwl.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out
							.println("CONTOU 2 CLICKES E está a definir o destino!");
					rwl.setDestination(e.getPoint());
				}
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				// rwl.setDestination(e.getPoint());

				if (e.getClickCount() == 1) {

					rwl.isPlaneSelected(e.getPoint());// ve se onde cliquei ha
					// aviao e devolve true
					rwl.planeSelected(e.getPoint());// devolve o aviao que la
					// estáA
					System.out
							.println("NO MOUSE CLICKED o PONTO CARREGADO E = a onde esta o aviao! x,y: " + e.getPoint().x/getNumColumns() + ", "+e.getPoint().y/getNumRows());

				}

				if (e.getClickCount() == 2) {
					System.out
							.println("CONTOU 2 CLICKES E está a definir o destino!");
					rwl.setDestination(e.getPoint());// deveria atribuir destino
					// ao aviao que plane
					// selected devolveu
				}
				// if(e.getClickCount()==2){
				// System.out.println(
				// "CONTOU 2 CLICKES E está a definir o destino!");
				// rwl.setDestination(e.getPoint());
				//				

				// rwl.setSelected(e.getPoint());
				// }
				// }
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		container.add(rwl, BorderLayout.CENTER);
		container.add(screen, BorderLayout.SOUTH);// IDEIA È METER APARECER
		// AQUI O FUEL DO AVIAO E
		// DESTINO

	}

	public int getNumColumns() {
		return linhasi;
	}

	public int getNumRows() {
		return linhasi;
	}

	public int maxNumCells() {
		int numMax = 0;
		numMax = linhasi * linhasi;
		return numMax;
	}

	public void execute() {
		window.setVisible(true);
	}

	public static void main(String[] args) {
		new AirSpace().execute();
	}

	public RunAwayLanding getRwl() {
		// TODO Auto-generated method stub
		return rwl;
	}

	public void setScreen(String text) {
		screen.setText(text);
	}

	public boolean isEmpty(int i) {
		return rwl.isEmpty(i);
	}

}
