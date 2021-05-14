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
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);   // 设置正则表达式，划分文档
    }

    /**
     * 获得下一个三元组
     * @return : 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple att = new TermTuple();
        String next = null;
        try{
            if(input == null) return null;
            // parts为空说明当前行的单词已经切分、处理完了，所以需要继续从流中读取
            while(parts.isEmpty()){
                line = input.readLine();            // 每次读取文章的一行
                if(line == null) return null;
                parts = splitter.splitByRegex(line);// 正则表达式切分
            }
            // 每次处理列表中第一个单词
            next = parts.get(0);
            parts.remove(0);
            if(Config.IGNORE_CASE) next = next.toLowerCase();
            att.term = new Term(next);
            att.curPos = pos++;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return att;
    }
}
