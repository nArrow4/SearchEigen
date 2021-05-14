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
    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
    }

    /**
     * 根据单个检索词进行搜索
     * @param queryTerm ：检索词
     * @param sorter ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = index.search(queryTerm);  // 在倒排索引中查找单词对应的postingList
        if (postingList == null)
            return null;
        AbstractHit[] hits = new AbstractHit[postingList.size()];
        AbstractHit hit;
        AbstractPosting posting;
        for (int i = 0; i < hits.length; i++) {
            posting = postingList.get(i);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
            termPostingMapping.put(queryTerm, posting);
            hit = new Hit(posting.getDocId(), index.getDocName(posting.getDocId()), termPostingMapping);
            sorter.score(hit);      // 计算命中文档的得分
            hits[i] = hit;
        }
        sorter.sort(Arrays.asList(hits));
        return hits;
    }

    /**
     *
     * 根据二个检索词进行搜索
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter ：    排序器
     * @param combine ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);
        List<AbstractHit> hitList = new ArrayList<>();
        int i = 0, j = 0, docId;

        if (combine == LogicalCombination.AND) {        // 与逻辑，必须同时出现
            if (postingList1 == null || postingList2 == null) {
                return null;
            }
            while (i < postingList1.size() && j < postingList2.size()) {
                AbstractPosting posting1 = postingList1.get(i);
                AbstractPosting posting2 = postingList2.get(j);
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
                }
                else if (posting1.getDocId() < posting2.getDocId()) {
                    i++;
                }
                else {
                    j++;
                }
            }
            if (hitList.isEmpty())
                return null;
            sorter.sort(hitList);
            AbstractHit[] Hits = new AbstractHit[hitList.size()];
            hitList.toArray(Hits);
            return Hits;
        }
        else if (combine == LogicalCombination.OR) {        // 或逻辑，只需要出现一个
            if (postingList1 == null) {
                return search(queryTerm2, sorter);
            }
            else if (postingList2 == null) {
                return search(queryTerm1, sorter);
            }
            else {      // 分别把postingList1和postingList2加入到hit
                for(i=0; i<postingList1.size(); i++) {
                    AbstractPosting posting1 = postingList1.get(i);
                    docId = posting1.getDocId();
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                    sorter.score(hit);
                    hitList.add(hit);
                }
                for(j=0; j<postingList2.size(); j++) {
                    AbstractPosting posting2 = postingList2.get(j);
                    docId = posting2.getDocId();
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                    termPostingMapping.put(queryTerm2, posting2);
                    AbstractHit hit = new Hit(docId, index.getDocName(docId), termPostingMapping);
                    sorter.score(hit);
                    hitList.add(hit);
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
