package jp.co.topgate.mizu.web;

import java.net.*;

public class Main {
    public static void main(String[] argv) throws Exception {
        try (ServerSocket server = new ServerSocket(8081)) {
            for (;;) {
                Socket socket = server.accept();
                ServerThread serverThread = new ServerThread(socket);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        }
    }
}
