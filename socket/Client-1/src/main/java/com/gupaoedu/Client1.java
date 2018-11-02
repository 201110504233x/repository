package com.gupaoedu;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket("localhost",8088);

        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    String message = null;
                    while((message = bufferedReader.readLine())!=null){
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        PrintWriter pw = new PrintWriter(osw,true);//true 刷新输出缓冲区

        String message = null;
        while(true){
            message = scanner.nextLine();
            pw.println("Client1: "+message);
        }

    }
}
