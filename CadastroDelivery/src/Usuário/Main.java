package Usuário;
//Parte feita por Joao Lucas Carreira
import Usuário.TelaDelivery;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
        // Garante a execução correta dos componentes gráficos Swing em thread segura
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaDelivery().setVisible(true);
            }
        });
    }

}
