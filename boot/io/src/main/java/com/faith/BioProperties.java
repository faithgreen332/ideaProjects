package com.faith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BioProperties {

    private static final boolean S_REUSE_ADDR = false;
    private static final int RECEIVE_BUFFER = 10;
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;
    private static final int BACK_LOG = 2;
    //client socket listen property on server endpoint:
    private static final boolean CLI_KEEPALIVE = false;
    private static final boolean CLI_OOB = false;
    private static final int CLI_REC_BUF = 20;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 20;
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;
    private static final int CLI_TIMEOUT = 0;
    private static final boolean CLI_NO_DELAY = false;

    public static void main(String[] args) throws IOException {


        ServerSocket server = new ServerSocket(9090, BACK_LOG);
        server.setReceiveBufferSize(RECEIVE_BUFFER);
        server.setSoTimeout(SO_TIMEOUT);
        server.setReuseAddress(S_REUSE_ADDR);

        System.out.println("step1: new ServerSocket(9090)");
        while (true) {

            System.in.read();

            Socket client = server.accept();

            client.setKeepAlive(CLI_KEEPALIVE);
            client.setOOBInline(CLI_OOB);
            client.setReceiveBufferSize(CLI_REC_BUF);
            client.setReuseAddress(CLI_REUSE_ADDR);
            client.setSendBufferSize(CLI_SEND_BUF);
            client.setSoLinger(CLI_LINGER, CLI_LINGER_N);
            client.setSoTimeout(CLI_TIMEOUT);
            client.setTcpNoDelay(CLI_NO_DELAY);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        char[] data = new char[1024];
                        while (true) {
                            int read = br.read(data);
                            if (read > 0) {
                                System.out.println("read data is :" + read + " val: " + new String(data, 0, read));
                            } else if (read == 0) {
                            } else {
                                client.close(); // 读到-1说明没有accept或者连接断开了
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
