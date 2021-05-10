package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

public class Hit extends AbstractHit {

    public Hit(){}

    public Hit(int docId, String docPath){
        super(docId, docPath);
    }

    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping){
        super(docId, docPath, termPostingMapping);
    }

    @Override
    public int getDocId() {
        return docId;
    }

    @Override
    public String getDocPath() {
        return docPath;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return termPostingMapping;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Hit:\n").append(this.docId).append("\t").append(this.docPath).append("\t").append(this.content).append("\t").append("-").append(this.score).append("\t");
        for (Map.Entry<AbstractTerm, AbstractPosting> entry : termPostingMapping.entrySet()) {
            str.append(entry.getKey()).append("\t").append(entry.getValue()).append("\t");
        }
        return str.toString();
    }

    @Override
    public int compareTo(AbstractHit o) {
        return (int) (this.score - o.getScore());
    }
}
