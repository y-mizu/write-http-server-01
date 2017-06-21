package jp.co.topgate.mizu.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// 1 EchoServerクラスをつくる
public class WebServer {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        //2 ServerSocketを初期化
        ServerSocket serverSocket = null;

        try {
            //3 ServerSocketのインスタンスを生成　
            serverSocket = new ServerSocket(PORT);

            while (true) {
                //4 Socketを初期化
                Socket socket = null;
                try {
                    //5 サーバーソケットに対する接続要求を待機して、それを受け取る
                    socket = serverSocket.accept();

                    //5 InputStream型のインスタンスisに通信ソケットから受信するバイトストリームを取得
                    InputStream is = socket.getInputStream();

                    //6 OutputStream型のインスタンスosに通信ソケットに送信するバイトストリームを取得
                    OutputStream os = socket.getOutputStream();

                    //7 BufferedReaderのインスタンスの生成及びPrintStream型のインスタンスの生成
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    PrintStream ps = new PrintStream(os);

                    //8 クライアントから文字列を受信し、サーバーの標準出力に出力する
                    String receive = br.readLine();
                    System.out.println(receive + "が入力されました。");

                    //9 クライアントにメッセージを送る
                    ps.println(receive);

                    //10　ストリームを閉じる
                    // is.close(); 大枠にあるbrがcloseされると閉じる
                    // os.close(); 大枠にあるbrがcloseされると閉じる
                    br.close();
                    ps.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    // 次のクライアントへ
                } finally {
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // serverSocketの例外処理によって終了させる
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            // serverSocketがうまく動かなかった時に閉じる処理
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}