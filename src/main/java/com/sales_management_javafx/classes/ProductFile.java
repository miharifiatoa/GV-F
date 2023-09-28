package com.sales_management_javafx.classes;

import org.sales_management.entity.ProductEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductFile {
    public static Collection<ProductEntity> readProductsFromFile() {
        Collection<ProductEntity> productList = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\Sales_management_javafx\\src\\main\\resources\\com\\sales_management_javafx\\data\\product.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            if (object instanceof List<?> list) {
                for (Object item : list) {
                    if (item instanceof ProductEntity) {
                        productList.add((ProductEntity) item);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }
    public static void writeProductsToFile(Collection<ProductEntity> products) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\IdeaProjects\\Sales_management_javafx\\src\\main\\resources\\com\\sales_management_javafx\\data\\product.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(products);
            System.out.println("Products written to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
