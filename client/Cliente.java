package client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import util.Mensagem;

public class Cliente {
    private static final long serialVersionUID = 1L;
    private static Socket socket;
    private static Escuta escuta;
    private static ObjectOutputStream out;
    private static String historico;

    public static void main(String[] args) {
        historico = "";
        String ip = "127.0.0.1";
        int porta = 9876;

        try {
            socket = new Socket(ip, porta);
            out = new ObjectOutputStream(socket.getOutputStream());
            escuta = new Escuta(socket, historico);
            escuta.start();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);

            Mensagem msg = new Mensagem("Danilo", scanner.nextLine());
            try {
                out.writeObject(msg);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
