package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.util.List;

public class PatternTermTupleFilter extends AbstractTermTupleFilter{

    public PatternTermTupleFilter(AbstractTermTupleStream stream){
        super(stream);
    }

    /**
     * 用大小写字母对单词进行过滤
     *
     * @return : 大小写过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        while (termTuple != null) {
            if (termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
                return termTuple;
            } else {
                termTuple = input.next();
            }
        }
        return null;
    }
}
