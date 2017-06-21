package jp.co.topgate.mizu.web;

import java.io.File;
import java.io.IOException;

class Handler {

    static void handleGET(HTTPRequest httpRequest, HTTPResponse httpResponse)throws IOException{

        System.out.println("GETハンドルを移行しました");
        System.out.println("requestURIは"+ httpRequest.getRequestURI());

        File requestResource = httpRequest.getRequestResource();


        System.out.println ("リクエストリソースは" + requestResource);

        if (requestResource.exists()){
            System.out.println("ファイルを見つけました");
            System.out.println("レスポンスを送ります");
            httpResponse.makeResponseBody(requestResource);
            httpResponse.sendResponse(HTTPResponse.MESSAGE_OK, httpRequest.getRequestResourceExtension(requestResource));
        } else {
            ErrorPage errorPage = new ErrorPage();
            System.out.println("ファイルが見つかりませんでした");
            errorPage.setErrorMessage("404 NOT Found");
            httpResponse.makeResponseBody(requestResource);
            httpResponse.sendResponse(HTTPResponse.MESSAGE_NOT_FOUND, httpRequest.getRequestResourceExtension(requestResource));
        }
    }

    static void handlerError(HTTPRequest httpRequest, HTTPResponse httpResponse) throws IOException{
        System.out.print("エラーページを表示します");
        File requestResource = httpRequest.getRequestResource();
        httpResponse.sendResponse(HTTPResponse.MESSAGE_INTERNAL_SERVER_ERROR, httpRequest.getRequestResourceExtension(requestResource));
    }
}