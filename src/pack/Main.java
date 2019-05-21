package pack;

import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.concurrent.Semaphore;


public class Main {
	
	public static int[] placar = new int[25];

	public static void main(String[] args) {
		
		int perm = 5;
		Semaphore semaforo =new Semaphore(perm);
		for (int i = 0; i < 25; i++) {
			ThreadTriatlo TTrio = new ThreadTriatlo(i,semaforo);
			TTrio.start();
		}

		
	}

}
