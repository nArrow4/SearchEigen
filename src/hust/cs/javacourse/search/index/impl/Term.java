package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.*;

public class Term extends AbstractTerm {

    public Term(){}

    public Term(String content){
        this.content = content;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof AbstractTerm)){
            return false;
        }
        Term t = (Term) obj;
        return this.content.equals(t.getContent());
    }

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
