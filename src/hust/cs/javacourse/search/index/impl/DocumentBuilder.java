package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
//        AbstractDocument doc = new Document(docId, docPath);
//        AbstractTermTupleFilter ltt = new LengthTermTupleFilter(termTupleStream);
//        AbstractTermTupleFilter ptt = new PatternTermTupleFilter(ltt);
//        AbstractTermTupleFilter swtt = new StopWordTermTupleFilter(ptt);
//        AbstractTermTuple att = swtt.next();
//        while(att != null){
//            doc.addTuple(att);
//            att = swtt.next();
//        }
//        return doc;
        List<AbstractTermTuple> list = new ArrayList<>();
        AbstractTermTuple termTuple = termTupleStream.next();
        while (termTuple != null) {
            list.add(termTuple);
            termTuple = termTupleStream.next();
        }
        return new Document(docId, docPath, list);
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
//        AbstractDocument doc = null;
//        BufferedReader reader = null;
//        AbstractTermTupleScanner scanner = null;
//        try{
//            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            scanner = new TermTupleScanner(reader);
//            doc = build(docId, docPath, scanner);
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//        finally {
//            if(scanner != null){
//                scanner.close();
//            }
//        }
//        return doc;
        AbstractDocument document = null;
        AbstractTermTupleStream termTupleStream = null;
        try {
            termTupleStream = new TermTupleScanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
            termTupleStream = new StopWordTermTupleFilter(termTupleStream); //停用词过滤
            termTupleStream = new PatternTermTupleFilter(termTupleStream);  //正则表达式过滤
            termTupleStream = new LengthTermTupleFilter(termTupleStream);   //单词长度过滤
            document = build(docId, docPath, termTupleStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert termTupleStream != null;
            termTupleStream.close();
        }
        return document;
    }
}
