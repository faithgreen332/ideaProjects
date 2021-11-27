package com.faith;

import java.io.*;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.228.134", 9090);
        socket.setSendBufferSize(20);
        socket.setTcpNoDelay(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 5);
        String line;
        while ((line = br.readLine()) != null) {
            bw.write(line + "\r\n"); // readLine() 在客户端写得时候控制台按下回车回去掉，而客户端readLine()没有在行尾收到回车就一直阻塞，所以手动加上
            bw.flush(); // 没有flush得话只停留在缓冲区，不会写进输出流里，除非客户端断掉
        }
    }
}
