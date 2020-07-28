package data;

import main.Common;

public class Constraint extends Function {
	double c;
	
	public Constraint(double[] coefs, String dir, double c) {
		super(coefs, dir);
		this.c = c;
		super.name = Common.makePolynomName(coefs, dir) + " " + Common.clip(c);
	}
	
	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
}