package com.faith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    private static final boolean S_REUSE_ADDR = false;

    public static void main(String[] args) throws IOException {


        ServerSocket server = new ServerSocket(9090, 20);

        System.out.println("step1: new ServerSocket(9090)");
        while (true) {
            Socket client = server.accept();
            System.out.println("step2: client\t" + client.getPort());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                        charRead(bufferedReader,client);
                        String info;

                        while ((info = bufferedReader.readLine()) != null) {
                            System.out.println(info);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void charRead(BufferedReader bufferedReader, Socket client) throws IOException {
        char[] chars = new char[1024];
        while (true) {
            int read = bufferedReader.read(chars);
            if (read > 0) {
                System.out.println(new String(chars, 0, read));
            } else if (read == 0) {
                continue;
            } else {
                client.close();
            }
        }
    }
}
