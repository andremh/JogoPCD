package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Plane extends Thread {

	private Cell cell;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image plane;
	private AirSpace asp;
	private int cellNumber;
	int rowNumber;
	int colunmNumber;
	private int nextCell;
	private int destinationCell;
	private boolean clicked = false;
	private int fuel = 100;
	private boolean hasDestination = false;
	private Image planeBack;
	private Image planeUp;
	private Image planeDown;
	private Image planeForward;
	private Image planeRed;
	private int sinalMove;
	private boolean gotStuck=false;
	private static  int id; //(NOVO
	/**
	 * @param asp
	 * @param cell
	 * @see Plane
	 * 
	 */
	public Plane(AirSpace asp, Cell cell) {

		this.nextCell = cell.getCelula();

		plane = toolkit.createImage("plane.png");
		planeBack = toolkit.createImage("planeBack.png");
		planeUp = toolkit.createImage("planeUp.png");
		planeDown = toolkit.createImage("planeDown.png");
		planeForward = toolkit.createImage("planeForward.png");
		planeRed = toolkit.createImage("planeRed.png");


		this.id=id++;//NOVO
		this.asp = asp;
		this.cell = cell;
		destinationCell = cell.getCelula();
	}

	public Plane(AirSpace asp, int cellNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.cellNumber = cellNumber;
		this.nextCell = cell.getCelula();
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public Plane(AirSpace asp, int rowNumber, int colunmNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.rowNumber = rowNumber;
		this.colunmNumber = colunmNumber;
	}

	private void moveInCir() {
		//System.out.println("Parado");
		try {

			sleep(700);
			plane = planeDown;
			asp.getRwl().repaint();//que que isto ta aqui a fazer???
			sleep(700);
			plane = planeBack;
			asp.getRwl().repaint();
			sleep(700);
			plane = planeUp;
			asp.getRwl().repaint();
			sleep(700);
			plane = planeForward;
			asp.getRwl().repaint();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see run
	 * @param nextCell
	 */
	@Override
	public void run() {

		while (true) {
			try {
				timeGoesBy();
				sleep(1000);

				if (cell.getCelula() != destinationCell) {
					sinalMove = 0;
					move();
					asp.getRwl().reachedTheFlag();//TESTAR
					System.out.println("cell: " + cell.getCelula() + ", NextCell: " + nextCell);
					if (asp.isEmpty(nextCell) ) {
						System.out.println("cell: " + cell.getCelula() + ", " + nextCell);
						sinalMove = 1;
						move();
						asp.getRwl().reachedTheFlag();//TESTAR
					} else{
						gotStuck=true;
						moveInCir();
					}
				}else{
					//1)ALterado-------------------------
					moveInCir();
				}
				asp.getRwl().repaint();
			} catch (InterruptedException e) {
				System.out.println("EXCEPCAOOO");
				e.printStackTrace();
			}
		}
	}

	public Cell getPlaneCell() {
		return cell;
	}

	public void setPositionByCellNumber(int newCellNumber) {
		this.cellNumber = newCellNumber;
	}

	public int getCellColumn(Cell cell2) {
		// d� coluna da celulaX
		return (cell2.getCelula() % cell2.getNumColumns());
	}

	public int getCellLine(Cell cell2) {
		// d� linha da celulaY
		return (cell2.getCelula() / cell2.getNumColumns());
	}

	public int move() {
		// destinationCell=150;//testando!!
		// System.out.println("encontra o X: " + cell.getCelula() + "%20: " + (cell.getCelula() % cell.getNumColumns()));
		// System.out.println("encontra o Y: " + cell.getCelula() + "/20: " + (cell.getCelula() / cell.getNumColumns()));
		int dx = (destinationCell % cell.getNumColumns()) - getCellColumn(cell);
		int dy = (destinationCell / cell.getNumColumns()) - getCellLine(cell);
		int totalX = (destinationCell % cell.getNumColumns());
		int totalY = (destinationCell / cell.getNumColumns());
		nextCell = cell.getCelula();
		// System.out.println(" dx: " + (totalX - Math.abs(dx)) + ">= dy: "
		// + (totalY - Math.abs(dy)));

		System.out.println("1) dx: " + Math.abs(dx) + " dy " + Math.abs(dy)
				+ ", getCellX: " + totalX);
		if (Math.abs(dx) > Math.abs(dy)) {
			// if (totalY > totalX && (dy != 0)) {
			// || dx !=0 ou && dy==0
			System.out.println("1) dx>dy: " + dx + ">" + dy);
			if (dx > 0) {
				// System.out.println("dx>0: " + dx);
				// 3
				// alterado-------------------------------------------------------------------
				if (sinalMove == 1) {
					cell.advanceCells(1);
					plane = planeForward;
				}else//2)alterado
					nextCell = cell.getCelula() + 1;
			} else {
				if (sinalMove == 1) {
					cell.backWardCell();
					plane = planeBack;
				}else
					nextCell = cell.getCelula() - 1;
			}
		} else {
			System.out.println("2) dy>0: " + dy);
			if (dy > 0) {

				if (sinalMove == 1) {
					cell.advanceCells(cell.getNumColumns());
					plane = planeDown;
				}else
					nextCell = cell.getCelula() + cell.getNumColumns();
			} else {
				if (sinalMove == 1) {
					cell.advanceCells(-cell.getNumColumns());
					plane = planeUp;
				}else
					nextCell = cell.getCelula() - cell.getNumColumns();
			}
		}
		return nextCell;

	}

	public Image getImage() {
		return plane;
	}

	public void setDestination(int destination) {
		destinationCell = destination;
	}

	public boolean airplaneIsSelected(Point point) {
		int col = (int) (point.getX() / (asp.getRwl().getWidth() / asp
				.getNumColumns()));// divide coord x pla largura cada celula->
		// indica qual a coluna onde estou
		int lin = (int) (point.getY() / (asp.getRwl().getHeight() / asp
				.getNumRows()));// ->indica qual a linha onde estou a contar
		// cima, =0
		int selected = col + lin * asp.getNumColumns();
		System.out.println("AIRPLANE SELECTED ->" + " selected : " + selected
				+ " cell.getcelula :" + cell.getCelula());
		if (cell.getCelula() == selected) {
			System.out
			.println("DENTRO DA CLASSE PLANE -> o aviao está na celula que indicámos!!");
			clicked = true;
			return true;

		}
		clicked = false;
		return false;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setNotClicked() {
		clicked = false;

	}

	public void timeGoesBy() {

		Timer time = new Timer(30000, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				fuel--;

				System.out.println("You are out of fuel!");

				// ImageFilter filter = new GrayFilter(true, 50);
				//
				//
				// ImageProducer producer = new FilteredImageSource
				// (plane.getSource(), filter);
				// Image mage = toolkit.createImage(producer);;
				//
				//
				// plane=mage;
				// POSSO APROVEITAR ESTE FILTRO PARA QD EM MOVIMENTO FICAR COM
				// MENOS COR
				plane = planeRed;// NOVO
				asp.setScreen("The plane is out of fuel!");// NOVO


			}

		});
		time.start();
	}

	public int getFuelLevel() {
		return fuel;

	}

	public int getID(){//NOVO
		return id;
	}


	public boolean gotStuck() {
		// TODO Auto-generated method stub
		return gotStuck;
	}
}
