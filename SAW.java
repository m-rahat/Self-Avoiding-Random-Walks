import java.util.*;
import java.util.concurrent.Semaphore;

public class SAW {

	double attempt = 0;
	double num = 0;
	double r_square_total = 0;

	/*
	 * using a saw_storage to find number of unique walks nothing more
	 */
	Set<Set> saw_storage = new HashSet<>();

	public void update(Set hashSet, int steps, double r_square) {
		if (hashSet.size() == steps + 1) {
			num = num + 1;
			r_square_total += r_square;
			saw_storage.add(hashSet);
		} // if
	}// update

	public void updateSAP(Set hashSet, int steps, double r_square) {
		if (hashSet.size() == steps) {
			num = num + 1;
			r_square_total += r_square;
			saw_storage.add(hashSet);
		} // if
	}// update

}// SAW
