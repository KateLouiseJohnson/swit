package com.swit.wword;

import java.util.ArrayList;

public class WTreeNode {
    public int[] values;
    public ArrayList<WTreeNode> children = new ArrayList<WTreeNode>();
    public ArrayList<WWord> words = new ArrayList<WWord>();

    public class ScoredTreeNode implements Comparable<ScoredTreeNode> {
        public final int score;
        public final int totalScore;
        public final WTreeNode node;
        public final ScoredTreeNode parent;

        public ScoredTreeNode(int score, WTreeNode node, ScoredTreeNode parent) {
            this.score = score;
            this.totalScore = parent != null ? parent.totalScore + score : score;
            this.node = node;
            this.parent = parent;
        }

        @Override
        public int compareTo(ScoredTreeNode node) {
            return Integer.compare(this.score, node.score);
        }

        @Override
        public String toString() {
            String wordStr = "";
            if (node.words.size() > 0) {
                wordStr = ": ";
                boolean first = true;
                for (WWord word : node.words) {
                    if (!first) {
                        wordStr += ", ";
                    }
                    first = false;
                    wordStr += word.word;
                }
            }
            return score + "/" + totalScore + wordStr;
        }
    }


    public int search(WWordContext context, ScoredTreeNode parent, ArrayList<ScoredTreeNode> results) {
        if (this.children.size() == 0) {
            return 0;
        }

        int[] contextValues = context.getCurrentValues();
        ArrayList<ScoredTreeNode> scores = new ArrayList<ScoredTreeNode>();
        int maxScore = 0;
        for (int i = 0; i < children.size(); i++) {
            WTreeNode node = children.get(i);
            int score = compare(node.values, contextValues);
            if (score > 0) {
                maxScore = score > maxScore ? score : maxScore;
                scores.add(new ScoredTreeNode(score, node, parent));
            }
        }
        int thresholdScore = maxScore / 2;

        for (int i = 0; i < scores.size(); i++) {
            ScoredTreeNode scored = scores.get(i);
            if (scored.score >= thresholdScore) {
                results.add(scored);

                for (WWord scoredWord : scored.node.words) {
                    context.matches.add(new WWordContext.ScoredWordMatch(scoredWord, scored.totalScore));
                }
            }
        }

        return maxScore;
    }

    public void addWord(WWord word, int wordIndex) {
        if (wordIndex >= word.values.size()) {
            words.add(word);
            return;
        }

        int[] wordValues = word.values.get(wordIndex);
        for (int i = 0; i < children.size(); i++) {
            WTreeNode child = children.get(i);
            if (equals(wordValues, child.values)) {
                child.addWord(word, wordIndex + 1);
                return;
            }
        }

        WTreeNode newNode = new WTreeNode();
        newNode.values = wordValues;
        children.add(newNode);
        newNode.addWord(word, wordIndex + 1);
    }

    private static boolean equals(int[] v1, int[] v2) {
        if (v1.length != v2.length) {
            return false;
        }

        for (int i = 0; i < v1.length; i++) {
            if (v1[i] != v2[i]) {
                return false;
            }
        }
        return true;
    }

    private static int distanceCompare = 16;
    private static int compare(int[] v1, int[] v2) {
        int endIndex = v1.length > v2.length ? v2.length : v1.length;
        int score = 0;
        for (int i = 0; i < endIndex; i++) {
            int delta = v1[i] - v2[i];
            if (delta >= distanceCompare || delta <= -distanceCompare) {
                continue;
            }

            score += distanceCompare + (delta >= 0 ? -delta : delta);
        }

        return score;
    }
}
