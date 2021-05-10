package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    public LengthTermTupleFilter(AbstractTermTupleStream stream){
        super(stream);
    }


    @Override
    public AbstractTermTuple next() {
//        AbstractTermTuple tt = input.next();
//        if(tt == null) return null;
//        String content = tt.term.getContent();
//        while(content == null || content.length() > Config.TERM_FILTER_MAXLENGTH || content.length() < Config.TERM_FILTER_MINLENGTH){
//            tt = input.next();
//            content = tt.term.getContent();
//        }
//        return tt;
        AbstractTermTuple termTuple = input.next();
        int length;
        while (termTuple != null) {
            length = termTuple.term.getContent().length();
            if (length <= Config.TERM_FILTER_MAXLENGTH && length >= Config.TERM_FILTER_MINLENGTH) {
                return termTuple;
            } else {
                termTuple = input.next();
            }
        }
        return null;
    }
}
