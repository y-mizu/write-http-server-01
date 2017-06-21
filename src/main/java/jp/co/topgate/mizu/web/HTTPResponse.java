package jp.co.topgate.mizu.web;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class HTTPResponse {
    // クライアントとのsocketを格納したOutputStream
    private OutputStream outputStream;

    // リクエストが成功した場合のコード
    static final int MESSAGE_OK = 200;

    // 該当のページが存在しない場合
    static final int MESSAGE_NOT_FOUND = 404;

    // サーバー内部エラーの時
    static final int MESSAGE_INTERNAL_SERVER_ERROR = 500;

    // コンストラクタ
    HTTPResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    // レスポンスボディ
    private byte[] responseBody;

    // レスポンスボディを設定するメソッド
    void setResponseBody(byte[] responseBody){
        this.responseBody = responseBody;
    }

    // レスポンスボディを取得するメソッド
    byte[] getResponsebody(){
        return this.responseBody;
    }

    // レスポンスボディを送るメソッド
    void sendResponse(int statusCode, String fileEx) throws IOException{
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        byte[] statusLine = ("HTTP/1.1" + statusCode + " " + createReasonPhrase(statusCode) + "\n").getBytes();
        byte[] entityHeader = (createEntityHeader(fileEx).getBytes());
        dataOutputStream.write(statusLine);
        dataOutputStream.write(entityHeader);
        dataOutputStream.write(this.responseBody);
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    // Content-TyoeやContent-Lengthなどのエンティティを設定するメソッド
    String createEntityHeader(String fileEx){
        //createAllow();
        //createContentEncoding()
        //createContentLength()

        return createContentTypeHeader(fileEx) + "\n";
    }

    // レスポンスボディを生成するメソッド
    void makeResponseBody(File requestResource) throws IOException {
        byte[] body;

        if (requestResource.exists()){
            body = Files.readFile(requestResource);
        } else {
            body = ErrorPage.makeErrorMessage();
        }
        setResponseBody(body);
    }

    //
    //

    String createReasonPhrase(int statusCode){
        String phrase = null;
        switch (statusCode){
            case 200:
                phrase = "OK";
                break;
            case 400:
                phrase = "Bad Request";
                break;
            case 404:
                phrase = "Not Found";
                break;
            case 500:
                phrase = "Internal Server Error";
                break;
            default:
                System.out.println("reasonPhraseは見つかりませんでした");
        }
        return phrase;
    }

    String createContentTypeHeader(String fileExtension){
        String fileType = createContentTypeHeader(fileExtension);
        return "Content-Type:" + fileType + "\n";
    }

    String createContentType(String file){
        String fileType = null;
        switch (file) {
            case "html":
                fileType = "text/html";
                break;
            case "css":
                fileType = "text/css";
                break;
            case "text/js":
                fileType = "text/js";
                break;
            case "jpeg":
            case "jpg":
                fileType = "image/jpeg";
                break;
            case "png":
                fileType = "image/png";
                break;
            case "gif":
                fileType = "image/gif";
                break;
            default:
                fileType = "text/html";
        }
        return fileType;
    }
}