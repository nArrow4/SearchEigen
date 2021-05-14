package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    public LengthTermTupleFilter(AbstractTermTupleStream stream){
        super(stream);
    }

    /**
     * 用长度对单词进行过滤
     *
     * @return : 长度过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        String content = null;
        while (termTuple != null) {
            content = termTuple.term.getContent();
            if (content.length() <= Config.TERM_FILTER_MAXLENGTH && content.length() >= Config.TERM_FILTER_MINLENGTH) {
                return termTuple;
            } else {
                termTuple = input.next();
            }
        }
        return null;
    }
}
