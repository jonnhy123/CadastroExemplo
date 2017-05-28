package br.univel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class TelaPrincipal extends TelaPrincipalBase {
	
	private Contato contatoSelecionado;
	
	private ContatoModel modelo;
	
	public TelaPrincipal() {
		super();
		limparCampos();
		configurarBotoes();
		configuraTabela();
		configurarMemus();
	}

	private void configurarMemus() {
		super.mntmImprimir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReportManager rm = new ReportManager();
			}
		});
		super.mntmExportarPdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}


	private void configuraTabela() {
		
		ContatoDao dao = new ContatoDao();
		List<Contato> lista = dao.getTodos();
		
		this.modelo = new ContatoModel(lista);
		// this.modelo = new ContatoModel();
		super.table.setModel(modelo);
		
		super.table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int idx = table.getSelectedRow();
					if (idx < 0) {
						System.out.println("N�o h� linha selecionada");
					} else {
						System.out.println("Linha " + idx);
						carregarLinha(idx);
					}
				}
			}
		});

			
		
	}


	protected void carregarLinha(int idx) {
		Contato c = this.modelo.getContato(idx);
		this.contatoSelecionado = c;
		this.labelAlerta.setText(CARREGADO_PARA_ALTERACAO);
		
		super.txfId.setText(String.valueOf(c.getId()));
		super.txfNome.setText(c.getNome());
		super.txfTelefone.setText(c.getTelefone());
		
		super.btnExcluir.setEnabled(true);
		
	}


	private void configurarBotoes() {
		super.btnNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicou Novo");
				novo();
			}
		});
		super.btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicou Salvar");
				salvar();
			}
		});
		super.btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicou Excluir");
				excluir();
			}
		});
	}


	protected void excluir() {
		this.modelo.remover(this.contatoSelecionado);
		limparCampos();
	}


	protected void salvar() {
		if (contatoSelecionado == null) {
			Contato c = new Contato();
			
			String strId = txfId.getText().trim();
			String strNome = txfNome.getText().trim();
			String strTelefone = txfTelefone.getText().trim();
			
			int intId = Integer.parseInt(strId);
			
			c.setId(intId);
			c.setNome(strNome);
			c.setTelefone(strTelefone);
			
			this.modelo.adicionar(c);
			
			limparCampos();
			
		} else {
			
			String strId = txfId.getText().trim();
			String strNome = txfNome.getText().trim();
			String strTelefone = txfTelefone.getText().trim();
			
			int intId = Integer.parseInt(strId);
			
			this.contatoSelecionado.setId(intId);
			this.contatoSelecionado.setNome(strNome);
			this.contatoSelecionado.setTelefone(strTelefone);
			
			limparCampos();
			this.modelo.fireTableDataChanged();
		}
		
	}


	protected void novo() {
		this.contatoSelecionado = null;
		
		limparCampos();
	}


	private void limparCampos() {
		super.labelAlerta.setText("");
		super.txfId.setText("");
		super.txfNome.setText("");
		super.txfTelefone.setText("");
		
		super.btnExcluir.setEnabled(false);
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
//			UIManager.setLookAndFeel(
//			        UIManager.getSystemLookAndFeelClassName());
			
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
