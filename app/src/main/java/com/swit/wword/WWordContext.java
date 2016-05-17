package com.swit.wword;

import java.util.ArrayList;

public class WWordContext {
    public static class ScoredWordMatch implements Comparable<ScoredWordMatch> {
        public final WWord word;
        public final int score;

        public ScoredWordMatch(WWord word, int score) {
            this.word = word;
            this.score = score;
        }

        @Override
        public int compareTo(ScoredWordMatch scoredWordMatch) {
            return Integer.compare(scoredWordMatch.score, this.score);
        }
    }

    public final WWord word;
    public int index;
    public ArrayList<ScoredWordMatch> matches = new ArrayList<>();

    public WWordContext(WWord word) {
        this.word = word;
        this.index = 0;
    }

    public boolean hasValues() { return index < word.values.size(); }

    public int[] getCurrentValues()
    {
        return word.values.get(index);
    }
}

