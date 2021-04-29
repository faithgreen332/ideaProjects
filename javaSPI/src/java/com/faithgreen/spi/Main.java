package com.faithgreen.spi;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<IUpload> load = ServiceLoader.load(IUpload.class);
        for (IUpload upload : load) {
            upload.upload("filePath");
        }
    }
}
