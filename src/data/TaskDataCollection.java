package data;

import java.util.ArrayList;

import main.Common;

public class TaskDataCollection {
	private static ArrayList<TaskDataItem> dataItems = new ArrayList<TaskDataItem>(); 
	
	static {
		dataItems.add(
				new TaskDataItem(
					"Main task", 
					new Function( new double[] {40, 100}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {4, 4}, Common.C_LOE, 32 ), 
							new Constraint( new double[] {3, 15}, Common.C_LOE, 60 ),
							new Constraint( new double[] {8, 4}, Common.C_LOE, 52 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Lab #1", 
					new Function( new double[] {0, 1}, Common.F_MIN ),
					new Constraint[] {
							new Constraint( new double[] {3, 11}, Common.C_GOE, 15),
							new Constraint( new double[] {-0.2f, 1}, Common.C_LOE, 5),
							new Constraint( new double[] {3, 1}, Common.C_GOE, 21)
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 1", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 2 (sztuczna baza - 1)", 
					new Function( new double[] {240, 300, 200}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {1, 2, 1}, Common.C_GOE, 2 ),
							new Constraint( new double[] {4, 1, 1}, Common.C_GOE, 4 ),
							new Constraint( new double[] {3, 5, 1}, Common.C_GOE, 3 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 3 (sztuczna baza - 2)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
							new Constraint( new double[] {1, 2}, Common.C_GOE, 3 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 4 (no solution)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
							new Constraint( new double[] {1, 2}, Common.C_GOE, 8 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 5 (alternative solution)", 
					new Function( new double[] {2, 4}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"Example 6 (infinite number of solutions)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {1, 1}, Common.C_GOE, 3 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"uchimatchast.ru/teory/simplex_primer1.php", 
					new Function( new double[] {2, 5, 3, 8}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {3, 6, -4, 1}, Common.C_LOE, 12 ),
							new Constraint( new double[] {4, -13, 10, 5}, Common.C_GOE, 6 ),
							new Constraint( new double[] {3, 7, 1, 0}, Common.C_GOE, 1 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
						"uchimatchast.ru/teory/isk_bazis_primer1.php", 
					new Function( new double[] {1, 5, 4, -3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 7, 1, 0}, Common.C_LOE, 5 ),
							new Constraint( new double[] {1, 4, 2, 8}, Common.C_EQU, 6 ),
							new Constraint( new double[] {-1, 0, 2, 5}, Common.C_EQU, 9 )
					}
				)
			);
		
		dataItems.add(
				new TaskDataItem(
					"uchimatchast.ru/teory/isk_bazis_primer2.php", 
					new Function( new double[] {2, -1, 7, 11, 5}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {2, 0, 5, 1, 8}, Common.C_EQU, 12 ),
							new Constraint( new double[] {-3, 6, 2, -2, 0}, Common.C_LOE, 5 ),
					}
				)
			);
	}
	
	public static TaskDataItem getTaskData(int i) {
		return dataItems.get(i);
	}
	
	public static String[] getTasksTitles() {
		String[] res = new String[ dataItems.size() ];
		for (int i = 0; i < dataItems.size(); i++)
			res[i] = dataItems.get(i).getTitle();
		
		return res;
	}
	
	public static int getInitTaskNo() {
		return 0;
	}

}