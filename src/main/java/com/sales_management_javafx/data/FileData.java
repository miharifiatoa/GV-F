package com.sales_management_javafx.data;

import com.sales_management_javafx.SalesApplication;
import org.sales_management.entity.ProductEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileData {
    public static void appendProductToShareInFile(ProductEntity product) {
        try {
            File file = new File(String.valueOf(SalesApplication.class.getResource("/data/product_shared.json")));
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(product);
            objectOutputStream.close();
            System.out.println("L'objet a été ajouté au fichier avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readProductToShareFromFile() {
        try {
            File file = new File(String.valueOf(SalesApplication.class.getResource("data/product_shared.json")));
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                while (true) {
                    try {
                        Object obj = objectInputStream.readObject();
                        if (obj instanceof ProductEntity product) {
                            System.out.println(product);
                        } else {
                            System.out.println("Objet inattendu dans le fichier product_shared.json : " + obj);
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
