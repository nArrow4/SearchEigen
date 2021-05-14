package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 */
public class Document extends AbstractDocument {
    /**
     * 构造函数
     */
    public Document(){}

    /**
     * 构造函数
     * @param docId：文档id
     * @param docPath：文档绝对路径
     */
    public Document(int docId, String docPath){
        super(docId, docPath);
    }

    /**
     * 构造函数
     * @param docId： 文档id
     * @param docPath： 文档绝对路径
     * @param tuples： 文档包含的三元组列表
     */
    public Document(int docId, String docPath, List<AbstractTermTuple> tuples){
        super(docId, docPath, tuples);
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
    public String getDocPath() {
        return this.docPath;
    }

    @Override
    public void setDocPath(String docPath) {
        this.docPath = new String(docPath);
    }

    @Override
    public List<AbstractTermTuple> getTuples() {
        return this.tuples;
    }

    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if(!tuples.contains(tuple)){        // 重复检查
            tuples.add(tuple);
        }
    }

    /**
     * 判断是否包含指定的三元组
     * @param tuple ： 指定的三元组
     * @return ： 如果包含指定的三元组，返回true;否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return tuples.contains(tuple);
    }

    /**
     * 获得指定下标位置的三元组
     * @param index：指定下标位置
     * @return ：三元组
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return tuples.get(index);
    }

    /**
     * 返回文档对象包含的三元组的个数
     * @return ：文档对象包含的三元组的个数
     */
    @Override
    public int getTupleSize() {
        return tuples.size();
    }

    /**
     * 获得Document的字符串表示
     * @return ： Document的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < getTupleSize(); i++) {
            str.append(tuples.get(i).toString());       // 取出tuple，调用其toString方法
        }
        return "docId: " + docId + "\tdocPath:" + docPath + "tuples: " + str;
    }
}
