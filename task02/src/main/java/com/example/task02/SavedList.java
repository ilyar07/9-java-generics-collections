package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final List<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        load();
    }

    private void load(){
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            List<E> loaded = (List<E>) ois.readObject();
            list.clear();
            list.addAll(loaded);
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("Ошибка загрузки файла: " + file, e);
        }
    }

    private void save(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(list));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла: " + file, e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        save();
        return old;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        save();
        return removed;
    }
}
