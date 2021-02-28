package distributore;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import distributore.Amministratore.Memento;

/**
 * Classe GraficaAdmin che estende la classe JFrame (per l'interfaccia grafica con swing)
 *  @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 */
@SuppressWarnings("serial")
public class GraficaAdmin extends JFrame implements ActionListener{
ArrayList<Categoria> categoria = new ArrayList<>(); 
ArrayList<Prodotto> prodotto = new ArrayList<>();
ArrayList<String> cronologia = new ArrayList<>();
Amministratore amm1 = new Amministratore();
Categoria c = new Categoria();
Prodotto prod = new Prodotto();
Distributore dist = new Distributore();

/**
 *  costruttore della classe {@link GraficaAdmin} per gestire il login dell'amministratore al distributore
 */
	public GraficaAdmin() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		JFrame frame = new JFrame(); //creazione del frame
		frame.setResizable(false); //metodo utilizzato per non ridimensionare la finestra del JFrame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(350, 250);
		frame.setLayout(null);
		
		frame.setLocationRelativeTo(null); //metodo che permette di centrare la finestra
		JTextField textFieldNome = new JTextField();
		textFieldNome.setBounds(138, 50, 128, 20);
		frame.getContentPane().add(textFieldNome); //il metodo add viene utilizzato per aggiungere le componenti sul frame
		textFieldNome.setColumns(10);
		
		JTextArea textNomeAdmin = new JTextArea("Nome");
		textNomeAdmin.setFont(new Font("Monospaced", Font.BOLD, 13));
		textNomeAdmin.setEditable(false); //metodo non permette di effettuare modifiche in fase di esecuzione alla JTextArea
		textNomeAdmin.setBackground(UIManager.getColor("Button.background"));
		textNomeAdmin.setBounds(91, 48, 37, 22);
		frame.getContentPane().add(textNomeAdmin);
		
		JTextField textFieldCognome = new JTextField();
		textFieldCognome.setColumns(10);
		textFieldCognome.setBounds(138, 86, 128, 20);
		frame.getContentPane().add(textFieldCognome);
		
		JTextArea textCognomeAdmin = new JTextArea("Cognome");
		textCognomeAdmin.setFont(new Font("Monospaced", Font.BOLD, 13));
		textCognomeAdmin.setEditable(false);
		textCognomeAdmin.setBackground(SystemColor.menu);
		textCognomeAdmin.setBounds(67, 84, 61, 22);
		frame.getContentPane().add(textCognomeAdmin);
		
		JTextField textFieldID = new JTextField();
		//id = textFieldID.getText();
		textFieldID.setColumns(10);
		textFieldID.setBounds(138, 122, 128, 20);
		frame.getContentPane().add(textFieldID);
		
		
		JTextArea textNomeAdmin_1 = new JTextArea("ID");
		textNomeAdmin_1.setFont(new Font("Monospaced", Font.BOLD, 13));
		textNomeAdmin_1.setEditable(false);
		textNomeAdmin_1.setBackground(SystemColor.menu);
		textNomeAdmin_1.setBounds(107, 120, 21, 22);
		frame.getContentPane().add(textNomeAdmin_1);
		
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.setBounds(54, 165, 89, 23);
		frame.getContentPane().add(btnAccedi);

		
		btnAccedi.addActionListener(new ActionListener() { //metodo per andare a gestire JButton 
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String uNome = textFieldNome.getText();
				String uCognome = textFieldCognome.getText();
				String uID= textFieldID.getText();
				if(uNome.equals("admin") && uCognome.equals("admin") && uID.equals("01"))
				{
					frame.setVisible(false);
					amm1.setNome(uNome);
					amm1.setCognome(uCognome);
					amm1.setCodice(uID);
					JOptionPane.showMessageDialog(null,"Amministratore loggato come: "+ amm1.getNome() + " " +amm1.getCognome()+ " Codice: "+amm1.getCodice() , "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
					 try {
						interfacciaAdmin();
					} catch (SQLException | InterruptedException e) {
						
						e.printStackTrace();
					}
				} else {
					frame.setVisible(false);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					JOptionPane.showMessageDialog(null, "Accesso negato.", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
					frame.setVisible(true);
				}

			}
		});
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(189, 165, 89, 23);
		frame.getContentPane().add(btnAnnulla);
		
		btnAnnulla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				frame.setVisible(false);
				DistributoreTest.scelta();
				
			}
		});
		
			
		
		JLabel lblNewLabel = new JLabel("Login Amministratore", SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(107, 11, 159, 24);
		frame.getContentPane().add(lblNewLabel);
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				}catch (Exception e) {
				e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * metodo utilizzato per implementare l'interfaccia grafica dell'elenco, quindi la scelta, delle funzionalità dell'amministratore
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public void interfacciaAdmin() throws SQLException, InterruptedException
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					amm1.setStatoDistributore(new Dist_InFunzione());
					Memento mementoStato1 = amm1.createMemento();
					amm1.controlloQuantita();
					
					JFrame frame1 = new JFrame();
					frame1.setResizable(false);
					frame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame1.setVisible(true);
					frame1.setSize(348,195);
					frame1.getContentPane().setLayout(null);
					frame1.setLocationRelativeTo(null);
					JButton btnProcedi = new JButton("Procedi");
					btnProcedi.setBounds(58, 99, 99, 23);
					frame1.getContentPane().add(btnProcedi);
					
					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(181, 99, 99, 23);
					frame1.getContentPane().add(btnChiudi);
					
					JLabel lblNewLabel = new JLabel("Scegli operazione:");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
					lblNewLabel.setBounds(33, 28, 121, 14);
					frame1.getContentPane().add(lblNewLabel);
					
					Choice choice = new Choice(); //è una combobox equivalente ad una checkbox che permette di viusalizzare le informazioni tramite un menu a tendina
					choice.setBounds(33, 48, 272, 20);
					choice.add("1) Aggiungi una nuova categoria");
					choice.add("2) Aggiungi un nuovo prodotto");
					choice.add("3) Rimuovi una categoria");
					choice.add("4) Rimuovi un prodotto");
					choice.add("5) Modifica categoria");
					choice.add("6) Modifica prodotto(quantita'/prezzo)");
					choice.add("7) Modifica prodotto(codice, nome, categoria)");
					choice.add("8) Mostra tutte le categorie");
					choice.add("9) Mostra tutti i prodotti");
					choice.add("10) Mostra lo stato del distributore");
					choice.add("11) Modifica lo stato del distributore");
					choice.add("12) Visualizza cronologia acquisti");
					frame1.getContentPane().add(choice);
					
					
					btnChiudi.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							frame1.setVisible(false);
							JOptionPane.showMessageDialog(null, "Uscita in corso... Arrivederci!","DISTRIBUTORE", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
					});
					
					btnProcedi.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							String scelta = choice.getItem(choice.getSelectedIndex());
							frame1.setVisible(false);
							switch(scelta)
							{
								case "1) Aggiungi una nuova categoria":
								{
									String codice;
									String nome;
									 codice = JOptionPane.showInputDialog(null,"Inserisci codice nuova categoria: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									 
									nome = JOptionPane.showInputDialog("Inserisci nome nuova categoria: ");
									 if(nome == null)
									 {
											break;
									 }
									c.setCodiceCat(codice);
									c.setNomeCat(nome);
									try {
										amm1.insertCategoria(c);
										//amm1.mostraListaCategoria();
										JOptionPane.showMessageDialog(null,"Categoria inserita correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
									} catch (SQLException   e) {
										
										e.printStackTrace();
									}

								}break;
								case "2) Aggiungi un nuovo prodotto":
								{
									String codice;
									String nome;
									String prezzo;
									String quantita;
									String categoria;
									double p;
									double q;
									
									codice= JOptionPane.showInputDialog(null, "Inserisci codice nuovo prodotto: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									nome = JOptionPane.showInputDialog(null, "Inserisci nome nuovo prodotto: ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(nome == null)
									 {
											break;
									 }
									prezzo = JOptionPane.showInputDialog(null, "Inserisci il prezzo: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(prezzo == null)
									 {
											break;
									 }
									p = Double.parseDouble(prezzo);
								
									quantita = JOptionPane.showInputDialog(null, "Inserisci quantita': ","AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(quantita == null)
									 {
											break;
									 }
									q = Double.parseDouble(quantita);
									
									categoria = JOptionPane.showInputDialog(null, "Insersici la categoria di appartenenza: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(categoria == null)
									 {
											break;
									 }
									prod.setCodice(codice);
									prod.setNome(nome);
									prod.setPrezzo(p);
									prod.setQuantita(q);
									prod.setCodiceCat(categoria);
									try {
										amm1.insertProdotto(prod);
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Prodotto inserito correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "3) Rimuovi una categoria":
								{
									String codice = JOptionPane.showInputDialog(null, "Inserisci il codice della categoria che vuoi rimuovere: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									c.setCodiceCat(codice);
									try {
										amm1.deleteCategoria(c);
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Categoria rimossa correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "4) Rimuovi un prodotto":
								{
									String codice;
									codice = JOptionPane.showInputDialog(null,"Inserisci il codice del prodotto che vuoi rimuovere: ", "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									prod.setCodice(codice);
									try {
										amm1.deleteProdotto(prod);
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Prodotto rimosso correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "5) Modifica categoria":
								{
									String codice;
									String valore;
									String campo;
								
									codice = JOptionPane.showInputDialog("Inserisci il codice della categoria che vuoi modificare: ");
									 if(codice == null)
									 {
											break;
									 }
									campo = JOptionPane.showInputDialog("Quale campo vuoi modificare? ");
									 if(campo == null)
									 {
											break;
									 }
									
									valore = JOptionPane.showInputDialog("Inserisci il nuovo valore: ");
									 if(valore == null)
									 {
											break;
									 }
									
									c.setCodiceCat(codice);
									c.setCampo(campo);
									c.setValore(valore);
									try {
										amm1.updateCategoria(c);
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Categoria modificata correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "6) Modifica prodotto(quantita'/prezzo)":
								{
									String codice; 
									String valore;
									String campo;
									
									 codice = JOptionPane.showInputDialog(null, "Inserisci il codice del prodotto che vuoi modificare: ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									
									 campo = JOptionPane.showInputDialog(null, "Quale campo vuoi modificare? ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(campo == null)
									 {
											break;
									 }
									valore = JOptionPane.showInputDialog(null, "Inserisci il nuovo valore: ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(valore == null)
									 {
											break;
									 }
									
									prod.setCodice(codice);
									prod.setCampo(campo);
									prod.setValore(valore);
									try {
										amm1.updateProdotto(prod);
									} catch (SQLException e) {
									
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Prodotto modificato correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "7) Modifica prodotto(codice, nome, categoria)":
								{
									String codice; 
									String valore;
									String campo;
									
									 codice = JOptionPane.showInputDialog(null, "Inserisci il codice del prodotto che vuoi modificare: ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(codice == null)
									 {
											break;
									 }
									 campo = JOptionPane.showInputDialog(null, "Quale campo vuoi modificare? ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(campo == null)
									 {
											break;
									 }
									 valore = JOptionPane.showInputDialog(null, "Inserisci il nuovo valore: ",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(valore == null)
									 {
											break;
									 }
									
									prod.setCodice(codice);
									prod.setCampo(campo);
									prod.setValore(valore);
									try {
										amm1.updateProdottoString(prod);
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Prodotto modificato correttamente.", "DISTRIBUTORE",JOptionPane.QUESTION_MESSAGE);
								}break;
								case "8) Mostra tutte le categorie": 
								{
									mostraCategorie();
									
								}break;
								case "9) Mostra tutti i prodotti":
								{
									mostraProdotti();
								}break;
								case "10) Mostra lo stato del distributore":
								{
									amm1.currentStatoDistributore();
								}break;
								case "11) Modifica lo stato del distributore":
								{
									String stato;
									
									 stato = JOptionPane.showInputDialog(null, "I due stati disponibili sono: in funzione, guasto.\n"
																				+ "Inserisci lo stato del distributore",  "AMMINISTRATORE",JOptionPane.QUESTION_MESSAGE);
									 if(stato == null)
									 {
											break;
									 }
									 stato = stato.replaceAll(" ","");
									if(stato.equals("infunzione"))
									{
										amm1.setStatoDistributore(new Dist_InFunzione());
											
									}else if(stato.equals("guasto")){
										amm1.setStatoDistributore(new Dist_Guasto());
										JOptionPane.showMessageDialog(null, "Il distributore e' guasto.");
										amm1.restoreMemento(mementoStato1);
										JOptionPane.showMessageDialog(null, "Il distributore e' in fase di riparazione...", "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
										try {
											TimeUnit.SECONDS.sleep(3);
										} catch (InterruptedException e) {
											
											e.printStackTrace();
										}
										
										JOptionPane.showMessageDialog(null, "Il distributore e' stato riparato");
										amm1.getOriginatorState();
	
										
									}else {
										
									}
								}break;
								case "12) Visualizza cronologia acquisti":
								{
										mostraCronologia();
								}break;				
							}
							try {
								interfacciaAdmin();
							} catch (SQLException | InterruptedException e) {
								
								e.printStackTrace();
							}
						}
					});
					
				}catch(Exception e )
				{
					e.printStackTrace();
				}
				setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}
	
	/**
	 * metodo per implementare la finestra verificare e mostrare la cronologia dei prodotti acquistati dai clienti
	 */
	public void mostraCronologia() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame f = new JFrame();
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setBounds(100, 100, 394, 366);
					f.getContentPane().setLayout(null);
					List list = new List();
					cronologia = dist.mostraCronologia();
					list.removeAll();
					for(int i=0; i< cronologia.size(); i++)
					{
						list.add(+(i+1)+ "-->  Codice: "+cronologia.get(i));
					}
					list.setBounds(31, 47, 317, 237);
					f.getContentPane().add(list);

					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(148, 293, 89, 23);
					f.getContentPane().add(btnChiudi);
					JLabel lblNewLabel = new JLabel("Cronologia acquisti");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
					lblNewLabel.setBounds(110, 11, 175, 31);
					f.getContentPane().add(lblNewLabel);
					
					
					btnChiudi.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							f.setVisible(false);
						}
					});
				}catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});
	}
	
	/**
	 * metodo per implementare la finestra per verificare e mostrare l'elenco delle categorie nel distributore
	 */
	public void mostraCategorie() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame f = new JFrame();
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setBounds(100, 100, 394, 366);
					f.getContentPane().setLayout(null);
					List list = new List();
					categoria = dist.mostraCategorie();
					list.removeAll();
					for(int i=0; i< categoria.size(); i++)
					{
						list.add(+(i+1)+ "-->  Codice: "+categoria.get(i).getCodiceCat() +" Nome: "+ categoria.get(i).getNomeCat());
					}
					list.setBounds(31, 47, 317, 237);
					f.getContentPane().add(list);

					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(148, 293, 89, 23);
					f.getContentPane().add(btnChiudi);
					JLabel lblNewLabel = new JLabel("Lista categorie");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
					lblNewLabel.setBounds(120, 11, 141, 31);
					f.getContentPane().add(lblNewLabel);
					
					
					btnChiudi.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							f.setVisible(false);
						}
					});
				}catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});
	}
	
	/**
	 * metodo per implementare la finestra per verificare e mostrare l'elenco di prodotti all'amministratore nel distributore
	 */
	public void mostraProdotti()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame f = new JFrame();
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setBounds(100, 100, 477, 366);
					f.getContentPane().setLayout(null);
					List list = new List();
					prodotto = dist.mostraProdotti();
					list.removeAll();
					for(int i=0; i< prodotto.size(); i++)
					{
						list.add(+(i+1)+ "-->  Codice: "+prodotto.get(i).getCodice() +" Nome: "+ prodotto.get(i).getNome() +" Prezzo: "+ prodotto.get(i).getPrezzo()+"€"+" Quantita: "+prodotto.get(i).getQuantita()+" Categoria: "+prodotto.get(i).getCodiceCat());
					}
					list.setBounds(31, 47, 398, 237);
					f.getContentPane().add(list);

					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(185, 293, 89, 23);
					f.getContentPane().add(btnChiudi);
					JLabel lblNewLabel = new JLabel("Lista prodotti");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
					lblNewLabel.setBounds(168, 11, 123, 31);
					f.getContentPane().add(lblNewLabel);
					
					
					btnChiudi.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							f.setVisible(false);
							
						}
					});
				}catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});
	}
	}



