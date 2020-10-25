package client;

import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JTextArea;

import util.Mensagem;

/**
 *
 * @author Emerson S. Paduan <emerson@paduan.pro.br>
 */

public class Escuta extends Thread {
    Socket socket;
    ObjectInputStream in;
    String historico;

    public Escuta(Socket sc, String hist) {
        socket = sc;
        historico = hist;
    }

    @Override
    public void run() {
        Mensagem msg;
        try {
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                msg = (Mensagem) in.readObject();
                if (msg != null) {
                    historico += (msg.getNome() + "> " + msg.getTexto() + "\n");
                    System.out.println(historico);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
