package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Posting extends AbstractPosting {
    /**
     * 缺省构造函数
     */
    public Posting(){}

    /**
     * 构造函数
     * @param docId ：包含单词的文档id
     * @param freq  ：单词在文档里出现的次数
     * @param positions   ：单词在文档里出现的位置
     */
    public Posting(int docId, int freq, List<Integer> positions){
        this.docId = docId;
        this.freq = freq;
        this.positions = positions;
    }

    /**
     * 构造函数
     * @param docId ：包含单词的文档id
     * @param freq  ：单词在文档里出现的次数
     */
    public Posting(int docId, int freq){
        this.docId = docId;
        this.freq = freq;
    }

    /**
     * 判断二个Posting内容是否相同
     * @param obj ：要比较的另外一个Posting
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Posting)){
            return false;
        }
        Posting p = (Posting) obj;
        return positions.containsAll(p.getPositions()) && p.getPositions().containsAll(positions) && docId == p.docId && freq == p.freq;
    }

    /**
     * 返回Posting的字符串表示
     * @return 字符串
     */
    @Override
    public String toString() {
        return "docId: " + getDocId() + ", freq: " + getFreq() + ", positions: " + positions.toString();
    }

    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Override
    public int getFreq() {
        return this.freq;
    }

    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public List<Integer> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    /**
     * 比较二个Posting对象的大小（根据docId）
     * @param o： 另一个Posting对象
     * @return ：二个Posting对象的docId的差值
     */
    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId - o.getDocId();
    }

    /**
     * 对内部positions从小到大排序
     */
    @Override
    public void sort() {
        Collections.sort(positions);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(this.docId);
            out.writeObject(this.freq);
            out.writeObject(this.positions);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.setDocId((int) in.readObject());
            this.setFreq((int) in.readObject());
            this.setPositions((List<Integer>) in.readObject());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
