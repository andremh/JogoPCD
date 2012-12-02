package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.LinkedList;

public class Airport extends Thread {

	private Cell cell;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image plane;
	private AirSpace asp;
	private int cellNumber;
	int rowNumber;
	int colunmNumber;

	private LinkedList<Cell> route = new LinkedList<Cell>();
	private boolean clicked = false;

	public Airport(AirSpace asp, Cell cell) {
		plane = toolkit.createImage("Airport.png");
		this.asp = asp;
		this.cell = cell;
	}

	public Airport(AirSpace asp, int cellNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.cellNumber = cellNumber;
	}

	public Airport(AirSpace asp, int rowNumber, int colunmNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.rowNumber = rowNumber;
		this.colunmNumber = colunmNumber;

	}

	@Override
	public void run() {
		while (true) {// cell.getCelula() < asp.maxNumCells()-1
			// cell.advanceCell();

			asp.getRwl().repaint();

		}
	}

	public Cell getAirportCell() {
		return cell;
	}

	public void setPositionByCellNumber(int newCellNumber) {
		this.cellNumber = newCellNumber;
	}

	public Image getImage() {
		return plane;
	}

	public boolean airportIsSelected(Point point) {
		int col = (int) (point.getX() / (asp.getRwl().getWidth() / asp
				.getNumColumns()));// divide coord x pla largura cada celula->
									// indica qual a coluna onde estou
		int lin = (int) (point.getY() / (asp.getRwl().getHeight() / asp
				.getNumRows()));// ->indica qual a linha onde estou a contar
								// cima, =0
		int selected = col + lin * asp.getNumColumns();
		System.out.println("AIRPORT SELECTED ->" + " selected : " + selected
				+ " cell.getcelula :" + cell.getCelula());
		if (cell.getCelula() == selected) {

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
}
