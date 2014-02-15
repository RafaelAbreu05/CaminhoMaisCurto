package terreno;

import interacao.JFramePrincipal;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import ontologia.Caminho;
import ontologia.Ponto2D;

public class Terreno extends Observable {

	public static int linhas = 80;
	public static int colunas = 80;

	// pontos por defeito, são alterados com a leitura de terreno
	public static Ponto2D inicio = new Ponto2D(0, 0);
	public static Ponto2D fim = new Ponto2D(0, 79);

	// Número de ponto Intermédios
	public static int nPontosIntermedios = 0;
	public static List<Ponto2D> listaPontos = new ArrayList<>();

	/*
	 * letra "i" ponto inicial // verde letra "f" ponto final // vermelho letra
	 * "t" terreno // branco letra "p" parede // preto letra "1" caminho agente
	 * 1 // azul letra "2" caminho agente 2 // verde letra "c" caminho agente
	 * 1+2 // cinzento letra "a" água // azul claro letra "r" rampa // laranja
	 * letra "s" escadas // magenta
	 */

	// Matriz com as posições do terreno
	public static String matriz[][] = new String[colunas][linhas];

	// Constructor vazio
	public Terreno() {
		for (int i = 0; i < colunas; i++)
			for (int j = 0; j < linhas; j++)
				matriz[i][j] = null;
	}

	// Gets
	public String getPosicao(int i, int j) {
		return matriz[i][j];
	}

	public static Ponto2D getInicio() {
		return inicio;
	}

	public static Ponto2D getFim() {
		return fim;
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	// Sets
	public static void setInicio(Ponto2D inicio) {
		Terreno.inicio = inicio;
	}

	public static void setFim(Ponto2D fim) {
		Terreno.fim = fim;
	}

	public void setPosicao(int i, int j, String valor) {
		matriz[i][j] = valor;

		String nome = "" + i + "," + j;
		setChanged();
		notifyObservers(nome);
	}

	public void setLinha(String linha[], int pos) {
		for (int i = 0; i < linhas && pos < colunas; i++) {
			matriz[pos][i] = new String(linha[i]);
		}
	}

	// Leitura do mapa do terreno de um ficheiro ".txt"
	public void lerTerreno(String nome) {

		Scanner scan = null;
		String linha = null;
		int i = 0, x1 = 0, x2 = 0, y1 = 0, y2 = 0;

		try {
			scan = new Scanner(new FileReader(nome));

			scan.useDelimiter(System.getProperty("line.separator"));
			linha = scan.next();
			Scanner lineScan = new Scanner(linha);

			lineScan.useDelimiter(",|\r");

			listaPontos = new ArrayList<>();

			// Descobrir no ficheiro o ponto incial
			x1 = Integer.parseInt((lineScan.next()));
			inicio.setX(x1);
			y1 = Integer.parseInt((lineScan.next()));
			inicio.setY(y1);

			// Descobrir no ficheiro o ponto final
			x2 = Integer.parseInt((lineScan.next()));
			fim.setX(x2);
			y2 = Integer.parseInt((lineScan.next()));
			fim.setY(y2);

			listaPontos.add(new Ponto2D(x1, y1));

			if (lineScan.hasNext()) {
				// Descobrir no ficheiro caso exista os pontos intermédios
				nPontosIntermedios = Integer.parseInt((lineScan.next()));

				int aux = 0;
				while (aux != nPontosIntermedios) {
					// Descobrir no ficheiro os pontos intermédios
					int x = Integer.parseInt((lineScan.next()));
					int y = Integer.parseInt((lineScan.next()));
					Ponto2D ponto = new Ponto2D(x, y);
					listaPontos.add(ponto);
					aux++;
				}
			}
			listaPontos.add(new Ponto2D(x2, y2));

			// Ler linha a linha as posições do mapa
			while (scan.hasNext()) {
				linha = scan.next();
				setLinha(parseLine(linha), i);
				i++;
			}

			// Colocar no mapa a posição dos pontos Intermédios
			for (i = 1; i < nPontosIntermedios+1; i++) {
				int x = listaPontos.get(i).getX();
				int y = listaPontos.get(i).getY();
				setPosicao(x, y, Integer.toString(i));
			}

			// Colocar o ponto incial na matriz
			setPosicao(x1, y1, "i");
			// Colocar o ponto final na matriz
			setPosicao(x2, y2, "f");

		} catch (IOException ioExc) {
			System.out.println("Nao existe o ficheiro");
		} finally {
			scan.close();
		} // este bloco e sempre executado, haja erro ou nao !!
	}

	// Auxiliar na leitura de terreno
	public static String[] parseLine(String line) {

		int i = 0;
		String tipo[] = new String[linhas];
		Scanner linha = new Scanner(line);
		linha.useDelimiter(",|\r");

		// Enquanto houver posições para ler nessa linha
		while (linha.hasNext() && i < linhas) {
			// Descobrir tipo de letra para cada posição
			tipo[i] = linha.next();
			i++;
		}

		return tipo;
	}
}