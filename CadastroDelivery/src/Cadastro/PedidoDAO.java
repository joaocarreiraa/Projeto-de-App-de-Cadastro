package cadastro;

//Orientação de Objetos para Persistência de Dados em CSV
//Parte feita por João Paulo de Oliveira Araújo

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PedidoDAO {

    private String arquivo = "pedidos.csv";

    public void salvar(Pedido pedido) {

        File file = new File(arquivo);

        boolean escreverCabecalho = !file.exists() || file.length() == 0;

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(file, true))) {

            if (escreverCabecalho) {
                escritor.write("codigo;nome;categoria;preco");
                escritor.newLine();
            }

            escritor.write(pedido.toCSV());
            escritor.newLine();

            System.out.println("Pedido salvo com sucesso!");

        } catch (IOException e) {

            System.out.println("Erro ao salvar o pedido!");
            e.printStackTrace();

        }
    }
}