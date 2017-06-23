package jp.co.topgate.mizu.web;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] argv) throws Exception{
        try (ServerSocket server = new ServerSocket(8080)){
            for(;;){
                Socket socket = server.accept();
                ServerThread serverThread = new ServerThread(socket);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        }
    }
}
