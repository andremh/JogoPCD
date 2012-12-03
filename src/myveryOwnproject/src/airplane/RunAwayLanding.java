package myveryOwnproject.src.airplane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

public class RunAwayLanding extends JComponent {

	private static final int n_avioes = 10;
	private static final int n_airports = 5;
	private int nLines;
	private int nCol;
	private static int selected = -1;// apenas um sera selecionado de cada
	// vez!!!
	private AirSpace asp;
	Plane plane;
	private int temporaryDestination=-1;
	private Flag flag;
	private List<Flag> flags = new ArrayList<Flag>();
	private Image aa;
	private boolean occupied;

	// ATENCAO QUE A CELULA RECEBE UMA COLUNA, UMA LINHA E NUMERO COLUNAS
	// TOTAL!!!
	private int selectedDestination = -1;
	private List<Plane> aircraftFleet = new ArrayList<Plane>();

	private int selectedWithOneClick = -1;
	private List<Airport> airportFleet = new ArrayList<Airport>();
	private Airport airp;

	public RunAwayLanding(int nLines, int nCol, AirSpace asp) {
		this.nLines = nLines;
		this.nCol = nCol;
		this.asp = asp;
		//4)alterado
		insertAirportsRandomLy();

		insertPlanesRandomLy();


	}
	//5)alterado
	private void insertAirportsRandomLy() {
		for (int i = 0; i != n_airports; i++) {
			Random r = new Random();
			int r1 = (r.nextInt(nCol));
			int r2 = (r.nextInt(nLines));
			Cell cell = new Cell(r2, r1, asp.getNumColumns());

			airp = new Airport(asp, cell);
			airportFleet.add(airp);
		}

	}
	//6)alterado
	public void insertPlanesRandomLy() {
		for (int i = 0; i != n_avioes; i++) {
			Random r = new Random();
			int r1 = (r.nextInt(nCol));
			int r2 = (r.nextInt(nLines));
			Cell cell = new Cell(r2, r1, asp.getNumColumns());

			plane = new Plane(asp, cell);
			aircraftFleet.add(plane);
			plane.start();

			int airp_dest = (r.nextInt(airportFleet.size()));

			plane.setDestination(airportFleet.get(airp_dest).getAirportCell().getCelula());

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int dx = getWidth() / (nCol);// largura cada celula
		int dy = getHeight() / (nLines);// altura cada celula

		

		// DRAW AIRPORTS
		//7)alterado
		for (Airport airports : airportFleet) {
			int x1 = (int) ((airports.getAirportCell().getCelula() % nCol) * dx);
			int y1 = (int) ((airports.getAirportCell().getCelula() / nCol) * dy);
			g.drawImage(airports.getImage(), x1, y1, dx, dy, this);
		}
		// DRAW PLANES
		//8)alterado
		for (Plane airplanes : aircraftFleet) {
			int x1 = (int) ((airplanes.getPlaneCell().getCelula() % nCol) * dx);
			int y1 = (int) ((airplanes.getPlaneCell().getCelula() / nCol) * dy);
			g.drawImage(airplanes.getImage(), x1, y1, dx, dy, this);
		}

		// LINHAS
		for (int i = 1; i < nLines; i++) {
			g.drawLine(0, i * dy, getWidth(), i * dy);
		}

		// COLUNAS
		for (int i = 1; i < nCol; i++) {
			g.drawLine(i * dx, 0, i * dx, getHeight());
		}
		//DRAW FLAGS NOVO
		for (Flag flag : flags) {
			int x1 = (int) ((flag.getFlagCell().getCelula() % nCol) * dx);
			int y1 = (int) ((flag.getFlagCell().getCelula() / nCol) * dy);
			g.drawImage(flag.getImage(), x1, y1, dx, dy, this);

		}

	}

	public boolean isEmpty(int i) {
		//9)alterado
		for (Plane airplanes : aircraftFleet) {
			if (i == airplanes.getPlaneCell().getCelula()) {
				occupied = false;
				return occupied;
			}
		}

		// 3)alterado
		// for (Airport airports : airportFleet) {
		// if (i == airports.getAirportCell().getCelula()) {
		// occupied = false;
		// return occupied;
		// }
		// }
		
		return true;
	}
	
	public boolean reachedTheFlag(){//nota ter um id para o aviao e bandeira para qd coincidirdesaparecer a bandeira e dar nova destination
		for (Plane airplane : aircraftFleet) {
			for(Flag flag: flags){
			//System.out.println("REACHED FLAG!!!!!!!!!!!");
			System.out.println("celula da flag"+ flag.getFlagCell().getCelula());
			System.out.println("celula do aviao" +airplane.getPlaneCell().getCelula());
			System.out.println("id da flag" +flag.getID());
			System.out.println("id do aviao" +airplane.getID());
			
				if (flag.getFlagCell().getCelula() == airplane.getPlaneCell().getCelula() && flag.getID()==airplane.getID()) {//
					airplane.setStuck(false);//qd chega a bandeira nao esta mais stuck saiu dessa situaçao
					flag.setInvisible();//NOVO
			System.out.println("REACHED THE FLAG");
			airplane.setTemporaryDestination(-1);//NOVO
			airplane.setDestination(airplane.getDestinationCellNumber());//NOVO 
			//airplane.setDestination(airplane.getDestinationCellNumber());//NOVO-> mudar 	
			//temporaryDestination=selectedDestination;//NOVO testar
			System.out.println("Selected destination is" + airplane.getDestinationCellNumber());
			return true;
			}
			}
		}
		return false;
	}
	
	
	

	public void setSelected(Point point) {// este metodo mete o selected
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		System.out.println("selecionei a celula + " + selected
				+ ": que esta na coluna= " + col + ", e na linha= " + lin);
		repaint();
	}

	public void setDestination(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual

		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		
//		System.out
//		.println("selecionei a celula PARA DESTINO DENTRO DO METODO SET DESTINATION "
//				+ selectedDestination
//				+ ": que esta na coluna= "
//				+ col
//				+ ", e na linha= " + lin);


		for (Plane airplane : aircraftFleet) {
		Cell destinationCell;
			Cell temporaryCell;
			if(airplane.isClicked()&&!airplane.gotStuck()){
				
				// a linha onde estou a contar cima, =0
				selectedDestination = col + lin * nCol;// ->selected da me o numero de
				destinationCell = new Cell((int)(selectedDestination%nCol),(int) (selectedDestination/nCol), nCol);//NOVO

				airplane.setDestination(selectedDestination);
				//				flag = new Flag(asp,cell );
				//				flags.add(flag);
				System.out
				.println("NO METODO SET DESTINATION FOI ATRIBUIDO DESTINO AO AVIAO CLICADO");
			}
			else if (airplane.isClicked()&&airplane.gotStuck()) {
				temporaryDestination = col + lin * nCol;



				temporaryCell = new Cell((int)(temporaryDestination%nCol),(int) (temporaryDestination/nCol), nCol);//NOVO
				//airplane.setDestination(temporaryDestination);
				airplane.setTemporaryDestination(temporaryDestination);
			//airplane.setDestination(temporaryDestination);//NOVO ???
				flag = new Flag(asp,temporaryCell, airplane.getID() );//mudei de cell 
				flags.add(flag);

				
//				System.out
//				.println("NO METODO SET TEMPORARY DESTINATION FOI ATRIBUIDO DESTINO AO AVIAO CLICADO");

			}
			System.out.println("aviao STUCK???" + airplane.gotStuck());
			airplane.setNotClicked();
		}
		repaint();		
	}


	public boolean isPlaneSelected(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)

		for (Plane airplanes : aircraftFleet) {
			if (selectedWithOneClick == airplanes.getPlaneCell().getCelula()) {// necessário
				// fazer com todos os avioes
				airplanes.airplaneIsSelected(point);// diz ao aviao que esta
				// clicado!!
//				System.out
//				.println("dentro do IS SELECTED:"
//						+ "o selected imprime:"
//						+ selected
//						+ "devolve true pq a selecionada é a mesma onde está o aviao");
				return true;
			}
		}

		return false;
	}

	public Plane planeSelected(Point point) {
		int col = (int) (point.getX() / (getWidth() / nCol));// divide coord x
		// pla largura cada celula-> indica qual a coluna onde estou
		int lin = (int) (point.getY() / (getHeight() / nLines));// ->indica qual
		// a linha onde estou a contar cima, =0
		selectedWithOneClick = col + lin * nCol;// ->selected da me o numero de
		// cada celula seleciona que vai de 0 ao nr de linhas dado ao
		// inicio ao quadrado (linhasi*linhasi)
		System.out.println();
		for (Plane airplane : aircraftFleet) {

			if (selectedWithOneClick == airplane.getPlaneCell().getCelula()) {// necessário
				// fazer com todos os avioes
				return airplane;

			}

		}
		return null;
	}

}
