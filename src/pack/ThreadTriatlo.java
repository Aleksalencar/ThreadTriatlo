package pack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadTriatlo extends Thread {
	
	public Random r = new Random();
	protected int id;
	protected Semaphore tiro;
	protected int pontuacao=0;
	protected static int placar[][] = new int[25][2]; // [x][0] = pontuação , [x][1] = competidor
	protected static int pontosRestantes =250;
	protected static int chegada=0;
	
	public ThreadTriatlo(int i, Semaphore semaforo) {
		id = i;
		tiro =semaforo;
	}
	
	public void run() {
		Corrida();
		Tiro();
		Ciclismo();
		Chegada();
		if (chegada == 25) {
			Arrays.sort(placar,new Comparator<int[]>() {
				 public int compare(int[] o1, int[] o2) {
					 return Integer.compare(o2[0], o1[0]);
				 }
			});
			Exibir();	

		}
		
	}

	

	private void Corrida() {
		int velo =r.nextInt(5)+20;
		System.out.println("O competidor "+id+" saiu a "+velo+" na corrida");
		Movimento(velo, 30, 3000);
	
	}

	private void Tiro() {
		int tempo=0;
		try {
			tiro.acquire();
			System.out.println("O competidor "+id+" entrou no estande de tiro");
			long t = System.nanoTime();
			for (int i = 0; i < 3; i++) {
				tempo = r.nextInt(1500)+500;
				Thread.sleep(tempo);
				pontuacao =pontuacao+r.nextInt(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long tf = System.nanoTime();
			tiro.release();
		}
	}

	private void Ciclismo() {
		int velo = r.nextInt(10)+40;
		System.out.println("O competidor "+id+" saiu a "+velo+" no ciclismo");
		Movimento(velo, 40, 5000);
	}
	
	private void Chegada() {
		
		pontuacao =pontuacao + pontosRestantes;
		placar[chegada][1]=id;
		placar[chegada][0]=pontuacao;
		chegada++;
		pontosRestantes -=10;
		
		
	}
	private void Sort() {
		boolean troca = true;
	    int aux;
	    while (troca) {
	    	troca = false;
	        for (int i = 0; i < placar.length - 1; i++) {
	        	if (placar[i] [0]< placar[i + 1][0]) {
	        		
	        		aux = placar[i][0];
	        		placar[i][0] = placar[i + 1][0];
	        		placar[i + 1][0] = aux;
	        		
	        		aux = placar[i][1];
	        		placar[i][1] = placar[i + 1][1];
	        		placar[i + 1][1] = aux;
	                       
	        		troca = true;
	        	}
	        }
	    }
	}
	
	private static void Exibir() {
		for (int i = 0; i < 25; i++) {
			for (int j = 1; j >= 0; j--) {
				System.out.print("|"+placar[i][j]+"| ");
			}
			System.out.println();
		}
	}
	
	

	
	private void Movimento(int dist, int tempo, int lim) {
		int distPerc = 0;
		while (distPerc <= lim) {
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
			distPerc =distPerc+ dist;
		
			}
		}
		
	}
	
}
