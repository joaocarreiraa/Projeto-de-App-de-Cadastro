
package cadastro;

public class TesteCSV {

    public static void main(String[] args) {

        Pedido produto = new Pedido(
            "P001",
            "Bolo de chocolate",
            "Bolos",
            35.00
        );

        PedidoDAO dao = new PedidoDAO();

        dao.salvar(produto);
    }
}
