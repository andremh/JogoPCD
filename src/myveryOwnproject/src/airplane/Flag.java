package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Toolkit;

public class Flag {

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image flag;
	private Cell cell;
	private int id;
	public Flag(AirSpace asp, Cell cell,int id) {



		flag = toolkit.createImage("flag.png");
		this.cell = cell;
		this.id=id;



	}


	public Image getImage(){
		return this.flag;
	}


	public Cell getFlagCell() {
		// TODO Auto-generated method stub
		return cell;
	}

	public int getId() {
		return id;
	}



}
