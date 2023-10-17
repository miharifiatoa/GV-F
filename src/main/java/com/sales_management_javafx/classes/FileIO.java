package com.sales_management_javafx.classes;

import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.entity.ProductEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileIO {
    public static <T> void writeTo(String filename, T o){
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\IdeaProjects\\Sales_management_javafx\\src\\main\\resources\\com\\sales_management_javafx\\data\\"+filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(o);
            System.out.println("object written to file... ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Collection<PriceVariationEntity> readPricesFromFile(String filename) {
        Collection<PriceVariationEntity> priceList = new ArrayList<>();
        Object object = readFrom(filename);
        if (object instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof PriceVariationEntity) {
                    priceList.add((PriceVariationEntity) item);
                }
            }
        }
        return priceList;
    }

    public static Object readFrom(String filename) {
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\Sales_management_javafx\\src\\main\\resources\\com\\sales_management_javafx\\data\\"+filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            System.out.println("Object read from file... ");
            return objectInputStream.readObject();
        } catch (EOFException e) {
            System.out.println("The file is empty.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
