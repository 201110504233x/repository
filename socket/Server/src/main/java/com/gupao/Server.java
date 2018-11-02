package com.gupao;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @Author: BinZhang
 * @Date: 2018/11/2 22:41
 * @Description:服务端
 * 
 */
public class Server {
    private static Vector<PrintWriter> vector = new Vector<PrintWriter>();
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("等待客户连接....");

        while(true){

            final Socket socket = serverSocket.accept();
            new Thread(new Runnable() {
                PrintWriter pw = null;
                String ip = "";
                public void run() {
                    try {
                        ip = socket.getInetAddress().getHostAddress();
                        System.out.println("ip: "+ip+"已经连接！");

                        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                        PrintWriter pw = new PrintWriter(osw,true);
                        vector.add(pw);
                        InputStream in = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(in,"UTF-8");
                        BufferedReader br = new BufferedReader(isr);

                        String message = null;
                        while((message=br.readLine())!=null){
                            for (PrintWriter printWriter : vector) {
                                printWriter.println(message);
                            }
                        }
                    } catch (IOException e) {

                    }finally {
                        vector.remove(pw);
                        System.out.println("ip:"+ ip +"已经下线！");
                        if(socket != null){
                            try {
                                socket.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }).start();
        }


    }


}
