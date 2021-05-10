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

    @Override
    public AbstractTermTuple next() {
//        AbstractTermTuple att = null;
//        String content = null;
//        StringSplitter splitter = new StringSplitter();
//        splitter.setSplitRegex(Config.TERM_FILTER_PATTERN);
//        List<String> parts = null;
//        do{
//            att = input.next();
//            if(att == null) return null;
//            content = att.term.getContent();
//            parts = splitter.splitByRegex(content);
//        } while(parts == null);
//        return att;
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
