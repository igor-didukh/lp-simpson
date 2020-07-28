package data;

public class TaskDataItem {
	String title;
	Function f;
	Constraint[] constraints;
	
	public TaskDataItem(String title, Function f, Constraint[] constraints) {
		this.title = title;
		this.f = f;
		this.constraints = constraints;
	}

	public String getTitle() {
		return title;
	}

	public Function getF() {
		return f;
	}

	public Constraint[] getConstraints() {
		return constraints;
	}

}