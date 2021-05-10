package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = this.index.search(queryTerm);
        if (postingList == null)
            return null;
        AbstractHit[] hits = new AbstractHit[postingList.size()];
        AbstractHit hit;
        AbstractPosting posting;
        for (int i = 0; i < hits.length; i++) {
            posting = postingList.get(i);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
            termPostingMapping.put(queryTerm, posting);
            hit = new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping);
            sorter.score(hit);
            hits[i] = hit;
        }
        sorter.sort(Arrays.asList(hits));
        return hits;
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1 = this.index.search(queryTerm1);
        AbstractPostingList postingList2 = this.index.search(queryTerm2);
        List<AbstractHit> hitList = new ArrayList<>();
        AbstractPosting posting1, posting2;
        int i, j, docId;

        if (combine == LogicalCombination.AND) {
            if (postingList1 == null || postingList2 == null) {
                return null;
            }
            i = 0;
            j = 0;
            while (i < postingList1.size() && j < postingList2.size()) {
                posting1 = postingList1.get(i);
                posting2 = postingList2.get(j);
                if (posting1.getDocId() == posting2.getDocId()) {
                    docId = posting1.getDocId();
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    termPostingMapping.put(queryTerm2, posting2);
                    AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                    sorter.score(hit);
                    hitList.add(hit);
                    i++;
                    j++;
                } else if (posting1.getDocId() < posting2.getDocId()) {
                    i++;
                } else {
                    j++;
                }
            }
            if (hitList.isEmpty())
                return null;
            sorter.sort(hitList);
            AbstractHit[] Hits = new AbstractHit[hitList.size()];
            hitList.toArray(Hits);
            return Hits;
        } else if (combine == LogicalCombination.OR) {
            if (postingList1 == null) {
                return search(queryTerm2, sorter);
            } else if (postingList2 == null) {
                return search(queryTerm1, sorter);
            } else {
                i = 0;
                j = 0;
                while (i < postingList1.size() && j < postingList2.size()) {
                    posting1 = postingList1.get(i);
                    posting2 = postingList2.get(j);
                    //对于两个关键词都同时命中的情况单独处理
                    if (posting1.getDocId() == posting2.getDocId()) {
                        docId = posting1.getDocId();
                        Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                        termPostingMapping.put(queryTerm1, posting1);
                        termPostingMapping.put(queryTerm2, posting2);
                        AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                        sorter.score(hit);
                        hitList.add(hit);
                        i++;
                        j++;
                    } else if (posting1.getDocId() < posting2.getDocId()) {
                        docId = posting1.getDocId();
                        Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                        termPostingMapping.put(queryTerm1, posting1);
                        AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                        sorter.score(hit);
                        hitList.add(hit);
                        i++;
                    } else {
                        docId = posting2.getDocId();
                        Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                        termPostingMapping.put(queryTerm2, posting2);
                        AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                        sorter.score(hit);
                        hitList.add(hit);
                        j++;
                    }
                }
                while (i < postingList1.size()) {
                    posting1 = postingList1.get(i);
                    docId = posting1.getDocId();
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                    sorter.score(hit);
                    hitList.add(hit);
                    i++;
                }
                while (j < postingList2.size()) {
                    posting2 = postingList2.get(j);
                    docId = posting2.getDocId();
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                    termPostingMapping.put(queryTerm2, posting2);
                    AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                    sorter.score(hit);
                    hitList.add(hit);
                    j++;
                }
                if (hitList.isEmpty())
                    return null;
                sorter.sort(hitList);
                AbstractHit[] Hits = new AbstractHit[hitList.size()];
                hitList.toArray(Hits);
                return Hits;
            }
        }
        return null;
    }
}
