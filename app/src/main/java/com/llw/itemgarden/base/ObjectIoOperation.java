package com.llw.itemgarden.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Created by liulewen on 2015/4/11.
 */
public class ObjectIoOperation {

    public static void writeObject(Object inputObject, String filePath){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(inputObject);
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            try{
                if(fileOutputStream != null)
                    fileOutputStream.close();
                if(objectOutputStream != null)
                    objectOutputStream.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object readObject(String filePath){
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try{
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            try{
                fileInputStream.close();
                objectInputStream.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
            try{
                if(fileInputStream != null)
                    fileInputStream.close();
                if(objectInputStream != null)
                    objectInputStream.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }
}
