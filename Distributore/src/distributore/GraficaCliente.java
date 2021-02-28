package distributore;


import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import distributore.Amministratore.Memento;


/**
 * 
 *Classe GraficaCliente che estende la classe JFrame (per l'interfaccia grafica con swing) 
 *
 */
public class GraficaCliente extends JFrame {
	ArrayList<Prodotto> prodotto= new ArrayList<>();
	Prodotto prod = new Prodotto();
	Cliente cli = new Cliente();
	Distributore dist = new Distributore();

		/**
		 * costruttore della clasit rese {@link GraficaCliente} gestire le funzionalità del cliente 
		 */
	public GraficaCliente() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

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

					JLabel lblNewLabel = new JLabel("Scegli operazione:");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
					lblNewLabel.setBounds(33, 28, 121, 14);
					frame1.getContentPane().add(lblNewLabel);
				
					
					Choice choice = new Choice();
					choice.setBounds(33, 48, 272, 20);
					choice.add("1) Scegli prodotto");
					choice.add("2) Mostra tutti i prodotti");
					frame1.getContentPane().add(choice);
				
					
					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(181, 99, 99, 23);
					frame1.getContentPane().add(btnChiudi);
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
							case "1) Scegli prodotto":
							{
								//boolean flag;
								int n;
								do
								{
									n = -2; //valore di default (non valido)
								
										String value = JOptionPane.showInputDialog(null, "Quanti prodotti vuoi acquistare?", "CLIENTE", JOptionPane.QUESTION_MESSAGE );
									if(value!=null)
									{
										try {
											n = Integer.parseInt(value);
									}catch(NumberFormatException e)
									{
										n=-2;
									}
										if(n<0)
										{
											JOptionPane.showMessageDialog(null, "Inserire un valore intero", "DISTRIBUTORE", JOptionPane.WARNING_MESSAGE);
										}
									}
									else {
										n=-1;
										new GraficaCliente();
										break;
									}
								}while(n<-1);
								
								int i = 0;
								String codice = null;
								while(i<n)
								{
									 codice = JOptionPane.showInputDialog(null, "Inserisci il codice del prodotto scelto: ", "CLIENTE", JOptionPane.QUESTION_MESSAGE);
									if(codice == null)
									 {
										new GraficaCliente();
											break;
									 }
									prod.setCodice(codice);
									try {
										cli.selezionaProdotto(prod);
									} catch (SQLException e) {

										e.printStackTrace();
									}
									i++;
								}
								if(cli.getTotale()!=0)
									try {
										dist.erogaProdotto(prod, cli.getTotale());
									} catch (SQLException | InterruptedException e) {

										e.printStackTrace();
									}
								if(codice!=null)
									new GraficaCliente();
							}break;
							
							case "2) Mostra tutti i prodotti":
							{
								//setVisible(false);
								mostraProdotti();
								new GraficaCliente();
								
							}break;
							
							}
						}
					});
				}catch(Exception e )
				{
					e.printStackTrace();
				}
			}
		});

	}
	
	
	/**
	 * metodo per implementare la finestra per mostrare l'elenco di prodotti al cliente presenti nel distributore
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
					f.setBounds(100, 100, 394, 366);
					f.getContentPane().setLayout(null);
					List list = new List();
					prodotto = dist.mostraProdotti();
					list.removeAll();
					for(int i=0; i< prodotto.size(); i++)
					{
						list.add(+(i+1)+ "-->  Codice: "+prodotto.get(i).getCodice() +" Nome: "+ prodotto.get(i).getNome() +" Prezzo: "+ prodotto.get(i).getPrezzo()+"€");
					}
					list.setBounds(31, 47, 317, 237);
					f.getContentPane().add(list);

					JButton btnChiudi = new JButton("Chiudi");
					btnChiudi.setBounds(148, 293, 89, 23);
					f.getContentPane().add(btnChiudi);
					JLabel lblNewLabel = new JLabel("Lista prodotti");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
					lblNewLabel.setBounds(132, 11, 123, 31);
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