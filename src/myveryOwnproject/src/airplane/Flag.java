package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Toolkit;

public class Flag {

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image flag;
	private Cell cell;
	private int planeId;
	
	public Flag(AirSpace asp, Cell cell,int planeId) {//NOVO



		flag = toolkit.createImage("flag.png");
		this.cell = cell;
		this.planeId=planeId;//NOVO



	}


	public Image getImage(){
		return this.flag;
	}


	public Cell getFlagCell() {
	
		return cell;
	}

	public int getID() {
		return planeId;
	}


	public void setInvisible() {//REVER CORRIGIR
		flag = null;
		
	}



}
