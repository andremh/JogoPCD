package myveryOwnproject.src.airplane;

import java.util.LinkedList;

public class Cell {

	private int column;// numero da COLUNA
	private int totalNumColumns;
	private int row;// numero da LINHA
	private int cell;
	private boolean occupied;
	
	public Cell(int column, int row, int totalNumColumns) {
		this.column = column;
		this.row = row;
		this.totalNumColumns = totalNumColumns;
		this.cell = column + row * totalNumColumns;

	}

	public int getCelula() {
		return this.cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public void advanceOneCell() {
		// System.out.println("no metodo advance cell" + cell);
		this.cell++;
	}

	public void advanceCells(int numCellstOAdvance) {
		// System.out.println("no metodo advance cell" + cell);
		this.cell = this.cell + numCellstOAdvance;
	}

	public int getNumColumns() {
		return this.totalNumColumns;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public void backWardCell() {
		this.cell--;
	}

	public void setOccupied() {
		occupied=true;
	}
	
	public int getCellColumn() {
		return (this.getCelula()/ this.getNumColumns());
		
	}
	public int getCellLine() {
		// dá coordenada da celulaY
		return (this.getCelula()/ this.getNumColumns());
	}
	 public int[] celToColLin(Cell point) {
	 int[] nn = { (point.getCelula() % 20), (point.getCelula() / 20) };
	 return nn;
	 }

	// selected = col + lin * nCol;
}
