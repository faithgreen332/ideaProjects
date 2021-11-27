package com.faith;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer1 {

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
                        while (true) {
                            BufferedInputStream inputStream = new BufferedInputStream(client.getInputStream());
                            byte[] bytes = new byte[1024];
                            int read = inputStream.read(bytes, 0, 1024);
                            String result = new String(bytes, 0, read);
                            System.out.println(">>>" + result);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
