import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Walk implements Runnable {

	int x = 0;
	int y = 0;
	int z = 0;
	int w = 0;

	int number_of_steps = Globals.Number_of_Steps;

	public final int num_of_walks = Globals.Number_of_Walks;

	SAW saw = new SAW();

	double r_square = 0;

	/*
	 * contains the path of the steps without any order
	 */
	Set<String> h_set = new HashSet<>();

	public Walk(SAW s) {

		// if number of steps = 0, there is no SAW
		if (number_of_steps == 0) {
			return;
		} // if
		else {
			saw = s;
			if (Globals.dimensions == 2) {
				Step st = new Step(0, 0);
				h_set.add(st.toString(Globals.dimensions));
			} // if
			else if (Globals.dimensions == 3) {
				Step st = new Step(0, 0, 0);
				h_set.add(st.toString(Globals.dimensions));
			} // else if
			else if (Globals.dimensions == 4) {
				Step st = new Step(0, 0, 0, 0);
				h_set.add(st.toString(Globals.dimensions));
			} // else if
		} // else
	}// Walk

	public void step() {

		int r = ThreadLocalRandom.current().nextInt(2 * Globals.dimensions);

		int x_coordinate = x;
		int y_coordinate = y;
		int z_coordinate = z;
		int w_coordinate = w;
		if (r == 0)
			x_coordinate = x + 1;
		else if (r == 1)
			x_coordinate = x - 1;
		else if (r == 2)
			y_coordinate = y + 1;
		else if (r == 3)
			y_coordinate = y - 1;
		else if (r == 4)
			z_coordinate = z + 1;
		else if (r == 5)
			z_coordinate = z - 1;
		else if (r == 6)
			w_coordinate = w + 1;
		else if (r == 7)
			w_coordinate = w - 1;

		/*
		 * DON'T WORRY ABOUT THIS NEW Step step BEING A 4D STEP. I WILL ONLY BE STORING
		 * THE APPROPRIATE VALUES IN THE HASHSET FOR THE APPROPRIATE DIMENSIONS.
		 */
		Step step = new Step(x_coordinate, y_coordinate, z_coordinate, w_coordinate);
		if (h_set.contains(step.toString(Globals.dimensions))) {
			return;
		} // if
		else {
			x = x_coordinate;
			y = y_coordinate;
			z = z_coordinate;
			w = w_coordinate;
			h_set.add(step.toString(Globals.dimensions));
		} // else

	}// step

	public void run() {

		for (int i = 0; i < Globals.Number_of_Walks; i++) {
			Walk walk = new Walk(saw);

			for (int j = 0; j < Globals.Number_of_Steps; j++) {
				walk.step();
			} // for j

			walk.r_square = (walk.x * walk.x) + (walk.y * walk.y) + (walk.z * walk.z) + (walk.w * walk.w);
			saw.update(walk.h_set, walk.number_of_steps, walk.r_square);
		} // for i
	}// run

}// Walk
