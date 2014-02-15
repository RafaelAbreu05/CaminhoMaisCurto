package interacao;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import terreno.Terreno;

public class JFramePrincipal extends JFrame implements Observer {

	private JPanel contentPane;
	private JPanel[][] matriz;
	public static Terreno terreno;
	private static int matrizLinhas;
	private static int matrizColunas;
	private JTextField textFieldSpeed;
	private static int tipo;
	private static Runtime rt;
	public static ContainerController cc;
	public static boolean end = true, set = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFramePrincipal frame = new JFramePrincipal();
					frame.setTitle("Projeto Integrado");
					frame.setVisible(true);
					frame.setResizable(false);

					// Inicializacao da plataforma JADE (pode se utilizar o
					// metodo jade boot)
					rt = Runtime.instance();
					Profile p = new ProfileImpl();
					p.setParameter(Profile.MAIN_HOST, "localhost");
					p.setParameter(Profile.MAIN_PORT, "80");
					cc = rt.createMainContainer(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		terreno = new Terreno();
		terreno.addObserver(this);
		matrizColunas = terreno.getColunas();
		matrizLinhas = terreno.getLinhas();

		// Termina a plataforma
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				rt.shutDown();
			}
		});

		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		setBounds(0, 0, winSize.width, winSize.height);

		/* Componentes */
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 15, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 50, 0 };
		gbl_contentPane.rowHeights = new int[] { 50, 0, 0, 0, 50, 50, 50, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 25, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		// Recuperar todos os ficheito ".txt" que se encontram dentro da pasta
		// "mapas"
		File folder = new File("mapas");
		FilenameFilter ff = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (name.contains(".txt"))
					return true;
				else
					return false;
			}
		};
		String[] pattern = folder.list(ff);

		JLabel lblTerreno = new JLabel("Terreno:");
		lblTerreno.setForeground(Color.WHITE);
		lblTerreno.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,
				25));
		GridBagConstraints gbc_lblTerreno = new GridBagConstraints();
		gbc_lblTerreno.gridwidth = 3;
		gbc_lblTerreno.insets = new Insets(0, 0, 5, 5);
		gbc_lblTerreno.gridx = 1;
		gbc_lblTerreno.gridy = 0;
		contentPane.add(lblTerreno, gbc_lblTerreno);

		final JPanel matrizTerreno = new JPanel();
		GridBagConstraints gbc_matrixTerreno = new GridBagConstraints();
		matrizTerreno.setBackground(Color.DARK_GRAY);
		gbc_matrixTerreno.gridheight = 15;
		gbc_matrixTerreno.gridwidth = 3;
		gbc_matrixTerreno.anchor = GridBagConstraints.NORTHWEST;
		gbc_matrixTerreno.insets = new Insets(5, 1, 5, 5);
		gbc_matrixTerreno.gridx = 1;
		gbc_matrixTerreno.gridy = 1;
		contentPane.add(matrizTerreno, gbc_matrixTerreno);
		matrizTerreno.setLayout(new GridLayout(80, 80));

		criarMatriz(matrizTerreno);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox comboBoxSelectTerreno = new JComboBox(pattern);
		comboBoxSelectTerreno.setForeground(Color.BLACK);
		comboBoxSelectTerreno.setSelectedIndex(-1);
		comboBoxSelectTerreno
				.setFont(new Font("Times New Roman", Font.BOLD, 14));

		comboBoxSelectTerreno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxSelectTerreno.getSelectedIndex() != -1) {
					String item = (String) comboBoxSelectTerreno
							.getSelectedItem();
					//for (int i = 0; i < matrizLinhas; i++)
					//	for (int j = 0; j < matrizColunas; j++)
					//		terreno.setPosicao(i, j, null);
					String ficheiro = new String("mapas/".concat(item));
					terreno.lerTerreno(ficheiro);
					pintarTerreno(matriz);
				}
			}
		});

		JLabel lblEscolhaUmTerreno = new JLabel("Terreno:");
		lblEscolhaUmTerreno.setForeground(Color.WHITE);
		lblEscolhaUmTerreno.setFont(new Font("Times New Roman", Font.BOLD
				| Font.ITALIC, 18));
		GridBagConstraints gbc_lblEscolhaUmTerreno = new GridBagConstraints();
		gbc_lblEscolhaUmTerreno.insets = new Insets(0, 0, 5, 5);
		gbc_lblEscolhaUmTerreno.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblEscolhaUmTerreno.gridx = 8;
		gbc_lblEscolhaUmTerreno.gridy = 4;
		contentPane.add(lblEscolhaUmTerreno, gbc_lblEscolhaUmTerreno);

		GridBagConstraints gbc_comboBoxSelectTerreno = new GridBagConstraints();
		gbc_comboBoxSelectTerreno.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxSelectTerreno.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSelectTerreno.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxSelectTerreno.gridx = 9;
		gbc_comboBoxSelectTerreno.gridy = 4;
		contentPane.add(comboBoxSelectTerreno, gbc_comboBoxSelectTerreno);

		JLabel lblTipoDeUtilizador = new JLabel("Tipo de Utilizador:");
		lblTipoDeUtilizador.setForeground(Color.WHITE);
		lblTipoDeUtilizador.setFont(new Font("Times New Roman", Font.BOLD
				| Font.ITALIC, 18));
		GridBagConstraints gbc_lblTipoDeUtilizador = new GridBagConstraints();
		gbc_lblTipoDeUtilizador.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDeUtilizador.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeUtilizador.gridx = 8;
		gbc_lblTipoDeUtilizador.gridy = 5;

		contentPane.add(lblTipoDeUtilizador, gbc_lblTipoDeUtilizador);

		final JComboBox<String> comboBoxSelectTipo = new JComboBox<String>(
				new String[] { "Pessoa sem Acesso Restrito",
						"Pessoa com Acesso Restrito",
						"Defeciente motor sem Acesso Restrito",
						"Defeciente motor com Acesso Restrito"});
		comboBoxSelectTipo.setSelectedIndex(-1);
		comboBoxSelectTipo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comboBoxSelectTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxSelectTipo.getSelectedIndex() != -1) {
					tipo = comboBoxSelectTipo.getSelectedIndex() + 1;
				}
			}
		});

		comboBoxSelectTipo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBoxSelectTipo.setForeground(Color.BLACK);

		GridBagConstraints gbc_comboBoxSelectTipo = new GridBagConstraints();
		gbc_comboBoxSelectTipo.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxSelectTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSelectTipo.gridx = 9;
		gbc_comboBoxSelectTipo.gridy = 5;
		contentPane.add(comboBoxSelectTipo, gbc_comboBoxSelectTipo);

		JLabel lblSpeed = new JLabel("Velocidade do Agente:");
		lblSpeed.setForeground(Color.WHITE);
		lblSpeed.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,
				18));
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 8;
		gbc_lblSpeed.gridy = 6;
		contentPane.add(lblSpeed, gbc_lblSpeed);

		textFieldSpeed = new JTextField("4");
		textFieldSpeed.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textFieldSpeed.setForeground(Color.BLACK);
		GridBagConstraints gbc_textFieldSpeed = new GridBagConstraints();
		gbc_textFieldSpeed.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSpeed.ipadx = 50;
		gbc_textFieldSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSpeed.gridx = 9;
		gbc_textFieldSpeed.gridy = 6;
		contentPane.add(textFieldSpeed, gbc_textFieldSpeed);
		textFieldSpeed.setColumns(10);
		textFieldSpeed.setHorizontalAlignment(JTextField.CENTER);

		JLabel label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 7;
		gbc_label_1.gridy = 10;
		contentPane.add(label_1, gbc_label_1);

		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridwidth = 2;
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.ipadx = 50;
		gbc_btnNext.ipady = 40;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 6;
		gbc_btnNext.gridy = 9;

		final JButton btnStart = new JButton("Iniciar");
		btnStart.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,
				16));
		btnStart.setBackground(Color.LIGHT_GRAY);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (comboBoxSelectTerreno.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Escolha o terreno");
				else if (comboBoxSelectTipo.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Escolha o tipo de utilizador");
				else {
					// Criar agente SuperVisor e Caminhante
					try {
						int numSpeed = Integer.parseInt(textFieldSpeed
								.getText());
						boolean numOK = true;
						comboBoxSelectTerreno.setSelectedIndex(-1);
						comboBoxSelectTerreno.setEnabled(true);
						comboBoxSelectTipo.setSelectedIndex(-1);
						comboBoxSelectTipo.setEnabled(true);
						long sp = 1;
						switch (numSpeed) {
						case 1:
							sp = 500;
							numOK = true;
							break;
						case 2:
							sp = 250;
							numOK = true;
							break;
						case 3:
							sp = 125;
							numOK = true;
							break;
						case 4:
							sp = 75;
							numOK = true;
							break;
						case 5:
							sp = 40;
							numOK = true;
							break;
						default:
							numOK = false;
							JOptionPane
									.showMessageDialog(null,
											"O valor da velocidade deve estar entre 1 e 5");
							break;
						}
						if (numOK) {
							Object argW[] = new Object[3];
							// Velocidade
							argW[0] = sp;
							// Algoritmo AStar
							argW[1] = 1;
							// TIPO
							argW[2] = tipo;
							AgentController agente1 = cc.createNewAgent(
									"Agente", "agentes.Caminhante", argW);
							agente1.start();

							AgentController superVisor = cc.createNewAgent(
									"SuperVisor", "agentes.SuperVisor", null);
							superVisor.start();
							comboBoxSelectTipo.setEnabled(false);
							comboBoxSelectTerreno.setEnabled(false);
							textFieldSpeed.setEnabled(false);
							set = false;
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,
								"Insira a velocidade que deseja");
					} catch (StaleProxyException e) {
						JOptionPane
								.showMessageDialog(null,
										"Os agentes já estão a realizar o caminho mais curto");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.ipadx = 50;
		gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStart.ipady = 40;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 8;
		gbc_btnStart.gridy = 13;
		contentPane.add(btnStart, gbc_btnStart);

		JButton btnEnd = new JButton("Fim");
		btnEnd.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		btnEnd.setBackground(Color.LIGHT_GRAY);
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// voltar a desenhar a matriz sem nada
				comboBoxSelectTerreno.setSelectedIndex(-1);
				comboBoxSelectTerreno.setEnabled(true);
				comboBoxSelectTipo.setSelectedIndex(-1);
				comboBoxSelectTipo.setEnabled(true);
				textFieldSpeed.setEnabled(true);
				end = true;
				set = false;
				textFieldSpeed.setText("4");
				for (int i = 0; i < matrizLinhas; i++)
					for (int j = 0; j < matrizColunas; j++)
						terreno.setPosicao(i, j, null);
				pintarTerreno(matriz);

				// Mata os agentes existentes no container
				try {
					AgentController supervisor = cc.getAgent("SuperVisor");
					supervisor.kill();
					AgentController agente = cc.getAgent("Agente");
					agente.kill();
				} catch (Exception e1) {
				}

			}
		});

		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnd.ipadx = 50;
		gbc_btnEnd.ipady = 40;
		gbc_btnEnd.gridx = 9;
		gbc_btnEnd.gridy = 13;
		contentPane.add(btnEnd, gbc_btnEnd);
	}

	// Método para desenhar a matriz do terreno
	public void criarMatriz(JPanel matrizTerreno) {
		matriz = new JPanel[matrizLinhas][matrizColunas];
		for (int i = 0; i < matrizColunas; i++) {
			for (int j = 0; j < matrizLinhas; j++) {
				matriz[i][j] = new JPanel();
				matriz[i][j].setBorder(BorderFactory
						.createLineBorder(Color.black));
				matrizTerreno.add(matriz[i][j]);
			}
		}
	}

	// Metódo para pintar todas as posições do JPanel
	public void pintarTerreno(JPanel[][] jp) {
		for (int i = 0; i < matrizLinhas; i++) {
			for (int j = 0; j < matrizColunas; j++) {
				try {
					String ex = new String(terreno.getPosicao(i, j));
					switch (ex.charAt(0)) {
					// / Ponto Inicial -> Amarelo
					case 'i':
						jp[i][j].setBackground(Color.YELLOW);
						break;
					// Ponto Final -> Amarelo
					case 'f':
						jp[i][j].setBackground(Color.YELLOW);
						break;
					// Terreno -> Branco
					case 't':
						jp[i][j].setBackground(Color.WHITE);
						break;
					// Parede -> Preto
					case 'p':
						jp[i][j].setBackground(Color.BLACK);
						break;
					// Agente 1 -> Azul
					case 'b':
						jp[i][j].setBackground(Color.BLUE);
						break;
					// Agua -> Azul Claro
					case 'a':
						jp[i][j].setBackground(Color.CYAN);
						break;
					// Rampa -> Cinzento
					case 'r':
						jp[i][j].setBackground(Color.GRAY);
						break;
					// Escadas -> Magenta
					case 's':
						jp[i][j].setBackground(Color.MAGENTA);
						break;
					// Acesso Restrito -> Vermelho
					case 'x':
						jp[i][j].setBackground(Color.RED);
						break;
					// Zona Verde -> Jardins
					case 'v':
						jp[i][j].setBackground(Color.GREEN);
						break;
					// Ponto Intermédios -> Amarelo
					default:
						jp[i][j].setBackground(Color.YELLOW);
						// jp[i][j].;
						break;
					}
				} catch (NullPointerException e) {
					jp[i][j].setBackground(Color
							.getHSBColor(0, 0, (float) 0.93));
				}
			}
		}
	}

	// Método para pintar apenas uma posição (i,j) do JPanel
	public void pintarPosicao(JPanel[][] jp, int i, int j) {
		try {
			String ex = new String(terreno.getPosicao(i, j));
			switch (ex.charAt(0)) {
			// / Ponto Inicial -> Amarelo
			case 'i':
				jp[i][j].setBackground(Color.YELLOW);
				break;
			// Ponto Final -> Amarelo
			case 'f':
				jp[i][j].setBackground(Color.YELLOW);
				break;
			// Terreno -> Branco
			case 't':
				jp[i][j].setBackground(Color.WHITE);
				break;
			// Parede -> Preto
			case 'p':
				jp[i][j].setBackground(Color.BLACK);
				break;
			// Agente 1 -> Azul
			case 'b':
				jp[i][j].setBackground(Color.BLUE);
				break;
			// Agua -> Azul Claro
			case 'a':
				jp[i][j].setBackground(Color.CYAN);
				break;
			// Rampa -> Laranja
			case 'r':
				jp[i][j].setBackground(Color.ORANGE);
				break;
			// Escadas -> Cinzento Escuro
			case 's':
				jp[i][j].setBackground(Color.MAGENTA);
				break;
			// Acesso Restrito -> Cinzento Escuro
			case 'x':
				jp[i][j].setBackground(Color.RED);
				break;
			// Zona Verde -> Jardins
			case 'v':
				jp[i][j].setBackground(Color.GREEN);
				break;
			// Ponto Intermédios -> Amarelo
			default:
				jp[i][j].setBackground(Color.YELLOW);
				// jp[i][j].;
				break;
			}
		} catch (NullPointerException e) {
			jp[i][j].setBackground(Color.getHSBColor(0, 0, (float) 0.93));
		}
	}

	// Atualizar a posição do Mapa com a nova posicao
	public void update(Observable obs, Object obj) {

		String arg = (String) obj;
		Scanner lineScan = new Scanner(arg);
		lineScan.useDelimiter(",");

		int coord[] = new int[2];
		int i = 0;

		while (lineScan.hasNext()) {
			coord[i] = lineScan.nextInt();
			i++;
		}

		pintarPosicao(matriz, coord[0], coord[1]);
	}

}