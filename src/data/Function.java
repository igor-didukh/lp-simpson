package data;

import main.Common;

// Parent for class "Constraint"
public class Function {
	double[] coefs;
	String dir, name;
	
	public Function(double[] c, String d) {
		coefs = c.clone();
		dir = d;
		name = Common.makePolynomName(c, "--> " + dir);
	}
	
	public void setCoefs(double[] c) {
		coefs = c.clone();
	}
	
	public double[] getCoefs() {
		return coefs;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String d) {
		dir = d;
	}
	
	public int getVarsAmount() {
		return coefs.length;
	}
	
	public String getVarsVector() {
		String res = "(";
		
		for (int i = 0; i < coefs.length; i++) {
			res += "x" + (i+1);
			if (i != coefs.length - 1)
				res += ", ";
		}
		
		res += ")";
		
		return res;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
