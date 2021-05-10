package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermTupleScanner extends AbstractTermTupleScanner {

    private int pos = 0;
    private List<String> parts = new ArrayList<>();
    private String line = null;
    private StringSplitter splitter = new StringSplitter();

    public TermTupleScanner(){}

    public TermTupleScanner(BufferedReader reader){
        super(reader);
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = new TermTuple();
        while (this.parts.isEmpty()) {
            String str = null;
            try {
                str = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (str == null)
                return null;
            if (Config.IGNORE_CASE)
                this.parts = splitter.splitByRegex(str.toLowerCase());
            else
                this.parts = splitter.splitByRegex(str);
        }
        termTuple.term = new Term(this.parts.get(0));
        this.parts.remove(0);
        termTuple.curPos = pos;
        pos++;
        return termTuple;
//        AbstractTermTuple att = new TermTuple();
//        String next = null;
//        try{
//            if(input == null) return null;
//            while(parts.isEmpty()){
//                line = input.readLine();
//                if(line == null){
//                    return null;
//                }
//                parts = splitter.splitByRegex(line);
//            }
//            next = parts.get(0);
//            parts.remove(0);
////            if(Config.IGNORE_CASE) next = next.toLowerCase();
//            att.term = new Term(next.toLowerCase());
//            att.curPos = pos++;
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        return att;
    }
}
