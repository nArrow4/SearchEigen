package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TermTuple)){
            return false;
        }
        TermTuple tt = (TermTuple) obj;
        return term.equals(tt.term) && curPos == tt.curPos;
    }

    @Override
    public String toString() {
        return term.toString() + ", freq: " + freq + ", curPos: " + curPos;
    }
}
