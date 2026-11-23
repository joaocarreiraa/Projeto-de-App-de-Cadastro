package Usuário;
// Parte feita por Joao Lucas Carreira e João Paulo Araújo
import BancodeDados.PedidoDAO;
import Cadastro.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

public class TelaDelivery extends JFrame implements ActionListener {
	// Componentes visuais
    private JTextField txtCodigo, txtNome, txtCategoria, txtPreco;
    private JButton btnCadastrar, btnListar, btnPesquisar;
    private JTable tabelaPedido;
    private DefaultTableModel modeloTabela;
    private JLabel logo;
    
    // Lista em memória
    private ArrayList<Pedido> listaPedidos;

    public TelaDelivery() {
    	//fontes usadas no programa
    	Font fonteLabel = new Font("Segoe UI", Font.BOLD, 13);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 13);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 12);
        
        // configurações da janela principal (top-level container)
        setTitle("Sistema de Cadastro de Comida - Delivery");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255,182,193));

        // carrega os dados salvos do CSV automaticamente ao iniciar
        listaPedidos = PedidoDAO.carregarArquivo();

        // rótulos e campos de texto
        // mudar +70 px no y pra encaixar a header
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 90, 80, 25);
        lblCodigo.setFont(fonteLabel);
        lblCodigo.setForeground(new Color(199,21,133));
        add(lblCodigo);
        

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 90, 150, 25);
        txtCodigo.setFont(fonteCampo);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 120, 80, 25);
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(new Color(199,21,133));
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 120, 250, 25);
        txtNome.setFont(fonteCampo);
        add(txtNome);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 150, 80, 25);
        lblCategoria.setFont(fonteLabel);
        lblCategoria.setForeground(new Color(199,21,133));
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 150, 200, 25);
        txtCategoria.setFont(fonteCampo);
        add(txtCategoria);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(20, 190, 80, 25);
        lblPreco.setFont(fonteLabel);
        lblPreco.setForeground(new Color(199,21,133));
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(100, 190, 150, 25);
        txtPreco.setFont(fonteCampo);
        add(txtPreco);

        // Botões do CRUD
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(380, 90, 150, 30);
        btnCadastrar.setFont(fonteBotao);
        btnCadastrar.setBackground(new Color (255,182,193));
        btnCadastrar.setForeground(new Color (199,21,133));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.addActionListener(this);
        add(btnCadastrar);

        btnListar = new JButton("Listar Todos");
        btnListar.setBounds(380, 135, 150, 30);
        btnListar.setFont(fonteBotao);
        btnListar.setBackground(new Color (255,182,193)); 
        btnListar.setForeground(new Color (199,21,133));
        btnListar.setFocusPainted(false);
        btnListar.addActionListener(this);
        add(btnListar);

        btnPesquisar = new JButton("Pesquisar Nome");
        btnPesquisar.setBounds(380, 180, 150, 30);
        btnPesquisar.setFont(fonteBotao);
        btnPesquisar.setBackground(new Color (255,182,193)); 
        btnPesquisar.setForeground(new Color (199,21,133));
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.addActionListener(this);
        add(btnPesquisar);
        
       

        // tabela para exibição organizada
        modeloTabela = new DefaultTableModel(new Object[]{"Código", "Nome", "Categoria", "Preço"}, 0);
        tabelaPedido = new JTable(modeloTabela);
        tabelaPedido.setFont(fonteCampo);
        tabelaPedido.setBackground(new Color (255,182,193)); 
        tabelaPedido.setRowHeight(24);
        tabelaPedido.getTableHeader().setFont(fonteLabel);
        JScrollPane painelRolagem = new JScrollPane(tabelaPedido);
        painelRolagem.setBounds(20, 230, 540, 250);
        add(painelRolagem);

        // atualiza a tabela com os dados que foram lidos do arquivo ao abrir
        atualizarTabela(listaPedidos);
        
        //imagem que fica na header
        ImageIcon iconeGif = new ImageIcon(getClass().getResource("/logo.gif"));
        logo = new JLabel(iconeGif);
        logo.setBounds(16, 8, 580, 80);
        add(logo);
    }

    // Tratamento de Eventos dos Botões (ActionListeners)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrar) {
            cadastrarPedido();
        } else if (e.getSource() == btnListar) {
            atualizarTabela(listaPedidos);
        } else if (e.getSource() == btnPesquisar) {
            pesquisarPrato();
        }
    }

    private void cadastrarPedido() {
        try {
            String codigo = txtCodigo.getText();
            String nome = txtNome.getText();
            String categoria = txtCategoria.getText();
            
            // Tratamento de exceção de conversão numérica (NumberFormatException)[cite: 3]
            double preco = Double.parseDouble(txtPreco.getText());

            if (codigo.isEmpty() || nome.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cria o objeto instanciando a classe de domínio
            Pedido novoPedido = new Pedido(codigo, nome, categoria, preco);
            
            // Adiciona na lista em memória
            listaPedidos.add(novoPedido);
            
            // Salva de forma contínua no arquivo CSV
            PedidoDAO.salvarArquivo(listaPedidos);
            
            // Atualiza a interface visual
            atualizarTabela(listaPedidos);
            limparCampos();

            JOptionPane.showMessageDialog(this, "Prato cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: O preço deve conter apenas números válidos (use ponto para decimais).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarPrato() {
        String busca = JOptionPane.showInputDialog(this, "Digite o nome ou categoria do prato para buscar:");
        if (busca != null && !busca.trim().isEmpty()) {
            ArrayList<Pedido> resultados = new ArrayList<>();
            for (Pedido p : listaPedidos) {
                if (p.getNome().toLowerCase().contains(busca.toLowerCase()) || 
                    p.getCategoria().toLowerCase().contains(busca.toLowerCase())) {
                    resultados.add(p);
                }
            }
            atualizarTabela(resultados);
        }
    }

    private void atualizarTabela(ArrayList<Pedido> pedidos) {
        modeloTabela.setRowCount(0); // Limpa a tabela
        for (Pedido p : pedidos) {
            modeloTabela.addRow(new Object[]{p.getCodigo(), p.getNome(), p.getCategoria(), p.getPreco()});
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtCategoria.setText("");
        txtPreco.setText("");
        txtCodigo.requestFocus();
    }
}
