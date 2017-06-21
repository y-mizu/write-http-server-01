package jp.co.topgate.mizu.web;


import java.io.*;
import java.net.URLDecoder;

public class HTTPRequest {

    private String requestLine;

    private String requestMethod;

    private String requestURI;

    HTTPRequest(InputStream inputStream) throws Exception{
        this.setHTTPRequest(inputStream);
    }

    void setHTTPRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        this.requestLine = bufferedReader.readLine();
        System.out.println("リクエストラインは" + requestLine);
        if (requestLine == null) {
            System.out.println("リクエストラインがありません");
            throw new IOException("リクエストラインがありません");
        }
        int firstEmpty = this.requestLine.indexOf(" ");
        String secondSentence = this.requestLine.substring(firstEmpty + 1,
                this.requestLine.indexOf(" ", firstEmpty + 1));
        try {
            secondSentence = URLDecoder.decode(secondSentence, "UTF-8");
            this.requestURI = secondSentence;
            System.out.println("secondSentenceは" + secondSentence);
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e);
        }
        this.requestMethod = this.requestLine.substring(0, this.requestLine.indexOf(" "));
        System.out.println("リクエストメソッドは" + this.requestMethod);
    }

    File getRequestResource(){
        String requestResource;
        if ((this.getRequestURI().endsWith("/")) || !(this.getRequestURI().substring(this.getRequestURI().lastIndexOf("/"), this.getRequestURI().length()).contains("."))){
            requestResource = "src/main/resource" + this.getRequestURI() + "index.html";
        } else {
            requestResource = "src/main/resource" + this.getRequestURI();
        }
        if (requestResource.contains("?")){
            requestResource = requestResource.substring(requestResource.indexOf(""), requestResource.lastIndexOf("?"));
        }
        File file = new File(requestResource);

        System.out.println("要求されているファイルは" + requestResource);
        return file;
    }

    String getRequestResourceExtension(File requestResource){
        String path = requestResource.toString();
        String extension = path.substring(path.lastIndexOf(".") + 1, path.lastIndexOf(""));
        System.out.println("ファイルの拡張子は" + extension);

        return extension;
    }

    String getRequestMethod(){
        return this.requestMethod;
    }

    String getRequestURI(){
        return this.requestURI;
    }
}