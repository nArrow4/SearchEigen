package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;
import hust.cs.javacourse.search.util.StringSplitter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    public StopWordTermTupleFilter(AbstractTermTupleStream stream){
        super(stream);
    }

    @Override
    public AbstractTermTuple next() {
//        AbstractTermTuple att = null;
//        String content = null;
//        do{
//            att = input.next();
//            if(att == null) return null;
//            content = att.term.getContent();
//        } while(content == null || Arrays.binarySearch(StopWords.STOP_WORDS, content) < 0);
//        return att;
        AbstractTermTuple termTuple = input.next();
        while (termTuple != null) {
            if (!Arrays.asList(StopWords.STOP_WORDS).contains(termTuple.term.getContent())) {
                return termTuple;
            } else {
                termTuple = input.next();
            }
        }
        return null;
    }
}
