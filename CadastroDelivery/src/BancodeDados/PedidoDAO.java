package BancodeDados;
//Orientação de Objetos para Persistência de Dados em CSV
//Parte feita por João Paulo de Oliveira Araújo

import Cadastro.Pedido;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PedidoDAO {
	private static final String NOME_ARQUIVO = "cardapio.csv";

    // Método para carregar os dados do arquivo CSV para a memória (ArrayList)
    public static ArrayList<Pedido> carregarArquivo() {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        File arquivo = new File(NOME_ARQUIVO);
        
        if (!arquivo.exists()) {
            return listaPedidos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    String codigo = dados[0];
                    String nome = dados[1];
                    String categoria = dados[2];
                    double preco = Double.parseDouble(dados[3]);
                    
                    Pedido prato = new Pedido (codigo, nome, categoria, preco);
                    listaPedidos.add(prato);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro na conversão de números do arquivo: " + e.getMessage());
        }

        return listaPedidos;
    }

    // Método para salvar toda a lista atualizada de volta no arquivo CSV
    public static void salvarArquivo(ArrayList<Pedido> listaPedidos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Pedido pedido : listaPedidos) {
                bw.write(pedido.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no arquivo CSV: " + e.getMessage());
        }
    }
}
