package com.zuryx.app;

import java.util.ArrayList;
import java.util.Collection;

public class MainRunner {
    public static void main(String[] args) {
           IImageDataStoreService service = new ImageDataStoreService();
           service.saveImagesOnDatabase(getDummyCollection());
    }

    private static Collection<String> getDummyCollection(){
        Collection<String> collection = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            collection.add("https://pbs.twimg.com/media/CdlFCYmXIAAGkiH.jpg");
        }
        return collection;
    }
}
