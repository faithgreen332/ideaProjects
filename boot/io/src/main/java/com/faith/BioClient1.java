package com.faith;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BioClient1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);
        socket.setSendBufferSize(20);
        socket.setTcpNoDelay(true);

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        String nsg;
        BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
        while (scanner.hasNext()) {
            nsg = scanner.nextLine();
            outputStream.write((nsg + "\n").getBytes());
            outputStream.flush();
        }
        outputStream.close();
        socket.close();

       /* OutputStream out = socket.getOutputStream();
        InputStream in = System.in;

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line = br.readLine();
            if (line != null) {
                byte[] bb = line.getBytes();
                for (byte b : bb) {
                    System.out.println(b);
                    out.write(b);
                }
            }
        }*/
    }
}
