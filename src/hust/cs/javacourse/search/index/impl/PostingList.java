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

    /**
     * 添加Posting,要求不能有内容重复的posting
     * @param posting：Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if(!list.contains(posting)){    // 重复检查
            list.add(posting);
        }
    }

    /**
     * 获得PosingList的字符串表示
     * @return ： PosingList的字符串表示
     */
    @Override
    public String toString() {
        String details = new String();
        for(AbstractPosting p: list){
            details += ((Posting) p).toString();
        }
        return details;
    }

    /**
     * 添加Posting列表,,要求不能有内容重复的posting
     * @param postings：Posting列表
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        for(AbstractPosting p: postings){
            this.add(p);                // 添加单个posting
        }
    }

    /**
     * 返回指定下标位置的Posting
     * @param index ：下标
     * @return ： 指定下标位置的Posting
     */
    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    /**
     * 返回指定Posting对象的下标
     * @param posting：指定的Posting对象
     * @return ：如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    /**
     * 返回指定文档id的Posting对象的下标
     * @param docId ：文档id
     * @return ：如果找到返回对应下标；否则返回-1
     */
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

    /**
     * 是否包含指定Posting对象
     * @param posting： 指定的Posting对象
     * @return : 如果包含返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    /**
     * 删除指定下标的Posting对象
     * @param index：指定的下标
     */
    @Override
    public void remove(int index) {
        if(index >= list.size()){       // 检查index范围
            return;
        }
        list.remove(index);
    }

    /**
     * 删除指定的Posting对象
     * @param posting ：定的Posting对象
     */
    @Override
    public void remove(AbstractPosting posting) {
        if(!list.contains(posting)){    // 检查存在
            return;
        }
        list.remove(posting);
    }

    /**
     * 返回PostingList的大小，即包含的Posting的个数
     * @return ：PostingList的大小
     */
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

    /**
     * 根据文档id的大小对PostingList进行从小到大的排序
     */
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
