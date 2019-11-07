
public class Step {

	int x = 0;
	int y = 0;
	int z = 0;
	int w = 0;

	public Step(int x_value, int y_value) {
		x = x_value;
		y = y_value;
	}// Step Constructor

	public Step(int x_value, int y_value, int z_value) {
		x = x_value;
		y = y_value;
		z = z_value;
	}// Step Constructor

	public Step(int x_value, int y_value, int z_value, int w_value) {
		x = x_value;
		y = y_value;
		z = z_value;
		w = w_value;
	}// Step Constructor

	public String toString(int num_of_dimension) {
		String statement = "";
		if (num_of_dimension == 2) {
			statement = "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
		} // if
		else if (num_of_dimension == 3) {
			statement = "(" + Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z) + ")";
		} // else if
		else if (num_of_dimension == 4) {
			statement = "(" + Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z) + ","
					+ Integer.toString(w) + ")";
		} // else if
		return statement;
	}// toString

}// Step
