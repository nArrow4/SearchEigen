package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Posting extends AbstractPosting {

    public Posting(){}

    public Posting(int docId, int freq, List<Integer> positions){
        this.docId = docId;
        this.freq = freq;
        this.positions = positions;
    }

    public Posting(int docId, int freq){
        this.docId = docId;
        this.freq = freq;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Posting)){
            return false;
        }
        Posting p = (Posting) obj;
        return positions.containsAll(p.getPositions()) && p.getPositions().containsAll(positions) && docId == p.docId && freq == p.freq;
    }

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

    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId - o.getDocId();
    }

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
            // TODO
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
