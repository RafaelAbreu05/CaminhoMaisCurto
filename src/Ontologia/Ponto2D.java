package ontologia;

import jade.content.Concept;


public class Ponto2D implements Concept{

	// Vari·veis de Inst‚ncia
	private int x;
	private int y;

	/* Construtores */

	// Construtor vazio
	public Ponto2D() {
		x = 0;
		y = 0;
	}

	// Construtor por par‚metros
	public Ponto2D(int x, int y){
		this.x = x;
		this.y = y;
	}

	// Contrutor de cÛpia
	public Ponto2D(Ponto2D pt){

		this.x = pt.getX();
		this.y = pt.getY();
	}

	// Equals
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ponto2D other = (Ponto2D) obj;
		if (x != other.getX())
			return false;
		if (y != other.getY())
			return false;
		return true;
	}

	// Gets
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Sets
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Mover uma posiÁ„o no x
	public void mover(){
		x++;
	}

	// Clone
	public Ponto2D clone(){
		return new Ponto2D(this);
	}

	// To String
	public String toString(){
		StringBuilder s = new StringBuilder ();
		s.append("Pos x = "+y + " ");
		s.append("Pos y = "+x);
	return s.toString();
	}

	/*
	public boolean normalX(double p2){

		boolean result=false;

		if((Terrain.sizeW/2)>40 && p2>0) result=true;
		if((Terrain.sizeW/2)<40 && p2<0) result =true;
		return result;
	}

	public boolean normalX(int p1,double p2){

		boolean result=false;

		if(Math.abs(Terrain.end.getX()-p1)>Math.abs(Terrain.end.getX()-p2)) result=true;

		return result;
	}

	public boolean normalY(int p1,double p2){

		boolean result=false;
		if(Math.abs(Terrain.end.getY()-p1)>Math.abs(Terrain.end.getY()-p2)) result=true;

		return result;
	}


	public void rndMv2(){

		int rand;
        Random a= new Random();

        double xdir = 0;
        double ydir = 0;
        int xrand=0, yrand=0, randForce=-1;
        double force;
        double ang;
        double power;


            xdir=0;
            ydir=0;
                //campo gravitacionar atrai objecto para posi√ß√£o final
                power = 1000 / Math.pow(getRange(x, y, Terrain.sizeH, Terrain.sizeW), 2);
                ang = normaliseBearing(Math.PI / 2 - Math.atan2(y - Terrain.sizeH, x - Terrain.sizeW));
                xdir += Math.sin(ang) * power;
                ydir += Math.cos(ang) * power;

                //campo gravitacionar repele objecto random
                xrand = a.nextInt(50);
                yrand = a.nextInt(50);
                power = 1000 / Math.pow(getRange(x, y, xrand, yrand), 2);
                ang = normaliseBearing(Math.PI / 2 - Math.atan2(y - yrand, x - xrand));
                xdir += Math.sin(ang) * power;
                ydir += Math.cos(ang) * power;

                if(xdir <0 && x<Terrain.sizeW) x++;
                else if(xdir >0 && x>0) x--;
                if(ydir <0 && y>0) y--;
                else if(ydir >0 && y<Terrain.sizeH) y++;


    }

    double normaliseBearing(double ang) {
        if (ang > Math.PI) {
            ang -= 2 * Math.PI;
        }
        if (ang < -Math.PI) {
            ang += 2 * Math.PI;
        }
        return ang;
    }

    // verifica se √© possivel fazer este caminho sem ter zonas impossiveis pelo caminho
	public boolean impossiBro(int x2,int y2){
		//System.out.println(x + "," + y + "-ImpossiBro");
		boolean result=false;
		int x = this.x, y = this.y, D = 0, HX = x2 - x, HY = y2 - y, c, M, xInc = 1, yInc = 1;

		if (HX < 0){xInc = -1; HX = -HX;}
	    if (HY < 0){yInc = -1; HY = -HY;}
	    if (HY <= HX){
	    	c = 2 * HX;
	    	M = 2 * HY;
	    	while(true){
	    		if(Terrain.matrix[x][y].equals("p")) result=true;

	    		if (x == x2)
	    			break;
	    		x += xInc;
	    		D += M;
	    		if (D > HX){
	    			y += yInc;
	    			D -= c;
	    		}
	     	}
	    }
	    else{
	    	c = 2 * HY;
	    	M = 2 * HX;
	    	while(true){
	    		if(Terrain.matrix[x][y].equals("p")) result=true;

	    		if (y == y2)
	    			break;
	    		y += yInc;
	    		D += M;
	    		if (D > HY){
	    			x += xInc;
	    			D -= c;
	    		}
	    	}
	    }
	return result;
	}
	*/
}
