package Usuário;

import BancodeDados.PedidoDAO;
import Cadastro.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaDelivery extends JFrame implements ActionListener {
	// Componentes visuais
    private JTextField txtCodigo, txtNome, txtCategoria, txtPreco;
    private JButton btnCadastrar, btnListar, btnPesquisar;
    private JTable tabelaPedido;
    private DefaultTableModel modeloTabela;
    
    // Lista em memória
    private ArrayList<Pedido> listaPedidos;

    public TelaDelivery() {
        // Configurações da Janela Principal (Top-level container)
        setTitle("Sistema de Cadastro de Comida - Delivery");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Carrega os dados salvos do CSV automaticamente ao iniciar
        listaPedidos = PedidoDAO.carregarArquivo();

        // Rótulos e Campos de Texto
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 20, 150, 25);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 80, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 60, 250, 25);
        add(txtNome);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 100, 80, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 100, 200, 25);
        add(txtCategoria);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(20, 140, 80, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(100, 140, 150, 25);
        add(txtPreco);

        // Botões do CRUD
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(380, 20, 150, 30);
        btnCadastrar.addActionListener(this);
        add(btnCadastrar);

        btnListar = new JButton("Listar Todos");
        btnListar.setBounds(380, 65, 150, 30);
        btnListar.addActionListener(this);
        add(btnListar);

        btnPesquisar = new JButton("Pesquisar Nome");
        btnPesquisar.setBounds(380, 110, 150, 30);
        btnPesquisar.addActionListener(this);
        add(btnPesquisar);

        // Tabela para exibição organizada
        modeloTabela = new DefaultTableModel(new Object[]{"Código", "Nome", "Categoria", "Preço"}, 0);
        tabelaPedido = new JTable(modeloTabela);
        JScrollPane painelRolagem = new JScrollPane(tabelaPedido);
        painelRolagem.setBounds(20, 190, 540, 250);
        add(painelRolagem);

        // Atualiza a tabela com os dados que foram lidos do arquivo ao abrir
        atualizarTabela(listaPedidos);
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
