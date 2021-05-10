package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostingList extends AbstractPostingList {

    public PostingList(){}

    @Override
    public void add(AbstractPosting posting) {
        if(!list.contains(posting)){
            list.add(posting);
        }
    }

    @Override
    public String toString() {
        String details = new String();
        for(AbstractPosting p: list){
            details += ((Posting) p).toString();
        }
        return details;
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        for(AbstractPosting p: postings){
            if(!list.contains(p)){
                list.add(p);
            }
        }
    }

    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        // TODO 二分查找
        return list.indexOf(posting);
    }

    @Override
    public int indexOf(int docId) {
        // TODO 二分查找
        for(int i=0;i<list.size();i++){
            if(list.get(i).getDocId() == docId){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    @Override
    public void remove(int index) {
        if(index >= list.size()){
            return;
        }
        list.remove(index);
    }

    @Override
    public void remove(AbstractPosting posting) {
        if(!list.contains(posting)){
            return;
        }
        list.remove(posting);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        if(list.isEmpty()){
            return;
        }
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void sort() {
        // TODO 改成快排
        list.sort(Comparator.comparingInt(AbstractPosting::getDocId));
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(this.list);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.list = (List<AbstractPosting>) in.readObject();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
