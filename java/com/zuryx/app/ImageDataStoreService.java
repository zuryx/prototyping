package com.zuryx.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageDataStoreService implements IImageDataStoreService {


    public void saveImagesOnDatabase(Collection<String> urls) {
        //Guardar el resultado del encoding a base64
        Collection<Future<String>> results = new HashSet<>();
        double numberOfImages = urls.size();
        //Numero de threads que se va a ocupar el executor.
        //En este caso sera el 10% de las imagenes totales.
        int numberOfThreads = (int) Math.ceil(numberOfImages / 10.0);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (String url : urls){
            //Se añade la tarea para que los Threads los ejecuten
            Future<String> base64Result = executorService.submit(new Base64Callable(url));
            results.add(base64Result);
        }
        executorService.shutdown();
        //TODO: Guardar los registros en la base de datos.
    }

    private class Base64Callable implements Callable<String> {
        private String url;

        public Base64Callable(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                URL website = new URL(url);
                BufferedImage bufferedImage = ImageIO.read(website);
                ImageIO.write(bufferedImage, "jpg", outputStream);
                return Base64.getEncoder().encodeToString(outputStream.toByteArray());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
