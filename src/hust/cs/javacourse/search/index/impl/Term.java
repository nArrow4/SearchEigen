package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.*;

public class Term extends AbstractTerm {

    /**
     * 缺省构造函数
     */
    public Term(){}


    public Term(String content){
        super(content);
    }

    /**
     * 判断二个Term内容是否相同
     * @param obj ：要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof AbstractTerm)){
            return false;
        }
        Term t = (Term) obj;
        return content.equals(t.getContent());
    }

    /**
     * 返回Term的字符串表示
     * @return 字符串
     */
    @Override
    public String toString(){
        return this.getContent();
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = new String(content);
    }

    /**
     * 比较二个Term大小（按字典序）
     * @param o： 要比较的Term对象
     * @return ： 返回二个Term对象的字典序差值
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return this.getContent().compareTo(o.getContent());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(this.content);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.content = (String) in.readObject();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
