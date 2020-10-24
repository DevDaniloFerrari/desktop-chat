package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import util.Comunicacao;
import util.Mensagem;

/**
 *
 * @author Emerson S. Paduan <emerson@paduan.pro.br>
 */

public class Atende extends Thread {
    Socket cliente;
    Comunicacao comunicacao;
    ArrayList<Atende> atendimentos;

    public Atende(Socket cliente, ArrayList<Atende> atendimentos) {
        this.cliente = cliente;
        this.atendimentos = atendimentos;
        comunicacao = new Comunicacao(cliente);
    }

    @Override
    public void run() {
        boolean online = true;
        Mensagem msg;

        while (online) {
            msg = (Mensagem) comunicacao.receive();
            if(msg == null){
                atendimentos.remove(this);
                online = false;
            }else{
                for (Atende atende : atendimentos) {
                    atende.enviar(msg);
                }
            }
        }

        try {
            cliente.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    public void enviar(Mensagem msg) {
        comunicacao.send(msg);
    }

}
