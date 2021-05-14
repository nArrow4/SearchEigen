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

    /**
     * 用停用词对单词进行过滤
     *
     * @return : 停用词过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
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
