package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;

import javax.print.Doc;
import java.io.File;

public class IndexBuilder extends AbstractIndexBuilder {

    public IndexBuilder(AbstractDocumentBuilder builder){
        super(builder);
    }

    /**
     * <pre>
     * 构建指定目录下的所有文本文件的倒排索引.
     *      需要遍历和解析目录下的每个文本文件, 得到对应的Document对象，再依次加入到索引，并将索引保存到文件.
     * @param rootDirectory ：指定目录
     * @return ：构建好的索引
     * </pre>
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        File f = new File(rootDirectory);
        Index index = new Index();
        if(f.isDirectory()){
            File[] files = f.listFiles();
            if(files != null){
                for(File file: files){      // 遍历所有文件
                    if(file.isFile()){
                        AbstractDocument doc = docBuilder.build(docId, file.getPath(), file);   // 构建单个文档
                        index.addDocument(doc);                                                 // 将文档加入倒排索引中
                        docId++;
                    }
                }
                return index;
            }
        }
        return null;
    }
}
