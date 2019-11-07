import java.util.*;

public class Main {

	public static void main(String[] args) {

		/*
		 * Please change the values in Globals class like dimensions, number of steps
		 * number of threads and number of walks to generate data. Otherwise to run a
		 * loop of data, just use the commented code in main
		 */

		//generate_SAW();
		generate_SAP();

		/*
		 * long startTime = System.currentTimeMillis();
		 * 
		 * //Change values of i = 2 to i = 3 or i = 4 for finding values in 2D, 3D or 4D
		 * for (int i = 2; i <= 2; i++) { Globals.dimensions = i; System.out.println(i +
		 * " DIMENSIONS ########################################");
		 * System.out.println("Number of Threads : " + Globals.Number_of_Threads);
		 * System.out.println("Number of Walk per thread : " + Globals.Number_of_Walks);
		 * System.out.println("Number of Dimensions : " + Globals.dimensions); //Change
		 * value of j to run SAW/SAP data for a lot of steps //For example, j = 10 ; j
		 * <= Globals.max_steps runs program from //step = 10 to step 40 for (int j = 2;
		 * j <= Globals.max_steps; j+= 2) { Globals.Number_of_Steps = j; generate_SAW();
		 * //generate_SAW(); }//for }//for
		 * 
		 * long endTime = System.currentTimeMillis();
		 * 
		 * System.out.println(); System.out.println("Total Run time : " + (endTime -
		 * startTime) / 1000);
		 */

	}// main

	public static void generate_SAW() {
		long begin = System.currentTimeMillis();

		System.out.println();

		Set<Set> saw_storage = new HashSet<>();
		double num = 0;
		double r_square_total = 0;

		/*
		 * Making individual SAW objects and passing one SAW per thread to maintain data
		 * concurrency
		 */
		SAW[] saw_array = new SAW[Globals.Number_of_Threads];
		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			SAW saw = new SAW();
			saw_array[i] = saw;
		} // for

		Thread[] thread_array = new Thread[Globals.Number_of_Threads];

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			Walk wk = new Walk(saw_array[i]);
			Thread wkthread = new Thread(wk);
			thread_array[i] = wkthread;
		} // for

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			thread_array[i].start();
		} // for

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			try {
				thread_array[i].join();
			} // try
			catch (Exception e) {
				// DO NOTHING
			} // catch
		} // for

		for (int i = 0; i < saw_array.length; i++) {
			num += saw_array[i].num;
			r_square_total += saw_array[i].r_square_total;

			/*
			 * Barely any impact on runtime so I will keep it
			 */
			Iterator<Set> it = saw_array[i].saw_storage.iterator();
			while (it.hasNext()) {
				saw_storage.add(it.next());
			} // while

		} // for

		long end = System.currentTimeMillis();

		System.out.println("Number of Steps : " + Globals.Number_of_Steps);
		System.out.println("Number of Threads : " + Globals.Number_of_Threads);
		System.out.println("Number of Walk per thread : " + Globals.Number_of_Walks);
		System.out.println("Number of Dimensions : " + Globals.dimensions);
		System.out.println("Number of unique SAW : " + saw_storage.size());
		System.out.println("Mean R^2 : " + r_square_total / num);
		System.out.println("fSAW(n) : " + num / (Globals.Number_of_Threads * Globals.Number_of_Walks));
		System.out.println("Run Time : " + (end - begin) / 1000);

		/*
		 * System.out.print(Globals.Number_of_Steps + ", ");
		 * System.out.print(r_square_total / num + ", "); System.out.print(num /
		 * (Globals.Number_of_Threads * Globals.Number_of_Walks) + ", ");
		 * System.out.print((end - begin) / 1000);
		 */

	}// generate_SAW

	public static void generate_SAP() {

		System.out.println();

		long begin = System.currentTimeMillis();

		// System.out.println("SAP");
		// System.out.println();

		Set<Set> saw_storage = new HashSet<>();
		double num = 0;
		double r_square_total = 0;

		SAW[] saw_array = new SAW[Globals.Number_of_Threads];
		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			SAW saw = new SAW();
			saw_array[i] = saw;
		} // for

		Thread[] thread_array = new Thread[Globals.Number_of_Threads];

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			PolygonWalk wk = new PolygonWalk(saw_array[i]);
			Thread wkthread = new Thread(wk);
			thread_array[i] = wkthread;
		} // for

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			thread_array[i].start();
		} // for

		for (int i = 0; i < Globals.Number_of_Threads; i++) {
			try {
				thread_array[i].join();
			} // try
			catch (Exception e) {
				// DO NOTHING
			} // catch
		} // for

		for (int i = 0; i < saw_array.length; i++) {
			num += saw_array[i].num;
			r_square_total += saw_array[i].r_square_total;

			/*
			 * Barely any impact on runtime so i'll keep it to check my data for unit
			 * testing
			 */
			Iterator<Set> it = saw_array[i].saw_storage.iterator();
			while (it.hasNext()) {
				saw_storage.add(it.next());
			} // while

		} // for

		long end = System.currentTimeMillis();

		System.out.println("Number of Steps : " + Globals.Number_of_Steps);
		System.out.println("Number of Threads : " + Globals.Number_of_Threads);
		System.out.println("Number of Walk per thread : " + Globals.Number_of_Walks);
		System.out.println("Number of Dimensions : " + Globals.dimensions);
		System.out.println("Number of unique SAP : " + saw_storage.size());
		System.out.println("Number of SAP : " + num);
		// System.out.println("Mean R^2 : " + Globals.master_SAW.r_square_total /
		// Globals.master_SAW.num);
		System.out.println("fSAP(n) : " + num / (Globals.Number_of_Threads * Globals.Number_of_Walks));

		System.out.println("Run Time : " + (end - begin) / 1000);

		/*
		 * System.out.print(Globals.Number_of_Steps + ", "); System.out.print(num /
		 * (Globals.Number_of_Threads * Globals.Number_of_Walks) + ", ");
		 * System.out.print((end - begin) / 1000); System.out.println();
		 */

	}// generate_SAP

}// Main
