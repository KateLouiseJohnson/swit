package com.swit.wword;

import java.util.ArrayList;
import java.util.Collections;

public class WTree {
    public WTreeNode root = new WTreeNode();

    public void match(WWordContext context) {
        ArrayList<WTreeNode.ScoredTreeNode> searchList = new ArrayList<>();
        root.search(context, null, searchList);
        context.index++;

        while (!searchList.isEmpty() && context.hasValues()) {
            ArrayList<WTreeNode.ScoredTreeNode> newList = new ArrayList<>();

            for (WTreeNode.ScoredTreeNode node : searchList) {
                node.node.search(context, node, newList);
            }
            context.index++;

            searchList = newList;
        }

        Collections.sort(context.matches);
    }

    public void addWord(WWord word) {
        root.addWord(word, 0);
    }
}

