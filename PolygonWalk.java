import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PolygonWalk implements Runnable {

	int x = 0;
	int y = 0;
	int z = 0;
	int w = 0;

	int number_of_steps = Globals.Number_of_Steps;

	public final int num_of_walks = Globals.Number_of_Walks;

	SAW saw = new SAW();

	double r_square = 0;

	boolean lastStep = false;

	/*
	 * contains the path of the steps
	 */
	Set<String> h_set = new HashSet<>();

	public PolygonWalk(SAW s) {

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

		Step step = new Step(x_coordinate, y_coordinate, z_coordinate, w_coordinate);

		if (lastStep) {
			if (x_coordinate == 0 && y_coordinate == 0 & z_coordinate == 0 && w_coordinate == 0) {
				x = x_coordinate;
				y = y_coordinate;
				z = z_coordinate;
				w = w_coordinate;
			} // if
			else {
				x = x_coordinate;
				y = y_coordinate;
				z = z_coordinate;
				w = w_coordinate;
				h_set.add(step.toString(Globals.dimensions));
			} // else
		} // if

		else {
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
		} // else
	}// step

	public void run() {

		for (int i = 0; i < Globals.Number_of_Walks; i++) {
			PolygonWalk walk = new PolygonWalk(saw);

			for (int j = 0; j < Globals.Number_of_Steps; j++) {
				if (j == Globals.Number_of_Steps - 1) {
					walk.lastStep = true;
				} // if
				walk.step();
			} // for j

			// walk.r_square = (walk.x * walk.x) + (walk.y * walk.y) + (walk.z * walk.z) +
			// (walk.num_of_walks * walk.w);

			// If back to origin (0,0)
			if (walk.x == 0 && walk.y == 0 && walk.z == 0 && walk.w == 0) {
				saw.updateSAP(walk.h_set, walk.number_of_steps, walk.r_square);
			} // if
		} // for i
	}// run

}// Walk
