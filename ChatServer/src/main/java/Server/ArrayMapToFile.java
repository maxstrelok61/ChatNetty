package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//Класс для загрузки и сохранения в файле параметров в виде ((String)key - (String)value)
public class ArrayMapToFile {
    //Имя рабочего файла файла
    String nameFile;
    //Список значений
    HashMap<String, String> arrayMap = new HashMap<>();

    public ArrayMapToFile(){
    }
    public ArrayMapToFile(String nameFile){
        this.nameFile = nameFile;
        loadFromFile();
    }
    //Добавления параметра в массив
    public void add(String key, String value){
        if(!arrayMap.containsKey(key)) arrayMap.put(key,value);
    }
    //Изменение значения параметра
    public void correct(String key, String value){
        if(arrayMap.containsKey(key)) arrayMap.put(key,value);
    }
    //Удаление параметра
    public void del(String key){
        arrayMap.remove(key);
    }
    //Проверка существования параметра в списке
    public boolean checkKey (String key){
        return arrayMap.containsKey(key);
    }
    //Берет значение по ключу
    public String get(String key){
        return arrayMap.get(key);
    }
    //Поиск ключа по значению
    public String getKey(String val){
        String str = "";
        for (Map.Entry entry: arrayMap.entrySet()) {
            if (entry.getValue().equals(val)) str = (String) entry.getKey();
        }
        return str;
    }
    //**********************************************************************
    //Работа с файлом
    //**********************************************************************
    //Сохранение в файл всего списка
    public void saveToFile(){
        try(FileWriter writer = new FileWriter(nameFile, false))
        {
            String k = "";
            String v = "";
            for(Map.Entry entry: arrayMap.entrySet()) {
                k = (String) entry.getKey();
                v = (String) entry.getValue();

                writer.write(k + " = " + v +  System.getProperty("line.separator"));
            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    //Загрузка списка из файла
    public void loadFromFile(){
        if(checkFile()) {

            try (BufferedReader br = new BufferedReader(new FileReader(nameFile))) {
                String line;
                String[] arr;
                while ((line = br.readLine()) != null) {
                    arr = line.split(" = ");
                    add(arr[0], arr[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("(loadFromFile) Файл не найден - " + nameFile);
        }
    }
    //Проверка существования файла
    private boolean checkFile() {
        File file = new File(nameFile);
        return file.exists() && !file.isDirectory();
    }

    //***********************************************************************

    public void print(){
        System.out.println("**************************");
        for (Map.Entry etry: arrayMap.entrySet()) {
            System.out.println(etry.getKey() + " = " + etry.getValue());
        }
        System.out.println("**************************");
    }

}