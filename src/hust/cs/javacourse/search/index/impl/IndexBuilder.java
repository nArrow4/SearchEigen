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

    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        File f = new File(rootDirectory);
        Index index = new Index();
        if(f.isDirectory()){
            File[] files = f.listFiles();
            if(files != null){
                for(File file: files){
                    if(file.isFile()){
                        AbstractDocument doc = docBuilder.build(docId, file.getPath(), file);
                        index.addDocument(doc);
                        docId++;
                    }
                }
                return index;
            }
        }
        return null;
    }
}
