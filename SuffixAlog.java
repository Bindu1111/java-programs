import java.io.*;
import java.util.*;
class SuffixAlog {
    static int[] occ;
    static String s;
    static String[] strs = new String[2];
    static ArrayList<Integer> soccs;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        try{
            while (true) go();
        } catch (Exception e) {
        }
    }
    static void go() throws IOException {
    	
        s = br.readLine();
        strs[0] = br.readLine();
        strs[1] = br.readLine();
        //System.out.println(s);
        int[] sa = suffixArray(s);
        int[] lcp = lcp(sa, s);
        soccs = new ArrayList<Integer>();
        occ = new int[s.length()];
        markOccurrences();
        long ans = 0;
        /*System.out.println(soccs);
        System.out.println(Arrays.toString(sa));
        System.out.println(Arrays.toString(lcp));*/
        int m = Math.max(strs[1].length() - 1, strs[0].length() - 1);
        for (int i = 0; i < sa.length; i++) {
            int least = sa[i] + m;
            if(i > 0) least = Math.max(sa[i] + lcp[i - 1], least);
            if(occ[sa[i]] == 1) {
                int ind = Collections.binarySearch(soccs, least);
                if(ind < 0) ind = -ind - 1;
                ans += soccs.size() - ind;
            }
        }
        System.out.println(ans);
    }
    public static int[] suffixArray(final CharSequence str) {
        int n = str.length();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;
        Arrays.sort(order, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return str.charAt(o1) - str.charAt(o2);
            }
        });
        // sa[i] - suffix on i'th position after sorting by first len characters
        // rank[i] - position of the i'th suffix after sorting by first len characters
        int[] sa = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = order[i];
            rank[i] = str.charAt(i);
        }
        for (int len = 1; len < n; len *= 2) {
            int[] r = rank.clone();
            for (int i = 0; i < n; i++) {
                // condition s1 + len < n simulates 0-symbol at the end of the string
                // a separate class is created for each suffix followed by 0-symbol
                rank[sa[i]] = i > 0 && r[sa[i - 1]] == r[sa[i]] && sa[i - 1] + len < n && r[sa[i - 1] + len / 2] == r[sa[i] + len / 2] ? rank[sa[i - 1]] : i;
            }
            // Suffixes are already sorted by first len characters
            // Now sort suffixes by first len * 2 characters
            int[] cnt = new int[n];
            for (int i = 0; i < n; i++)
                cnt[i] = i;
            int[] s = sa.clone();
            for (int i = 0; i < n; i++) {
                // s[i] - order of suffixes sorted by first len characters
                // (s[i] - len) - order of suffixes sorted only by second len characters
                int s1 = s[i] - len;
                // sort only suffixes of length > len, others are already sorted
                if (s1 >= 0)
                    sa[cnt[rank[s1]]++] = s1;
            }
        }
        return sa;
    }
    // longest common prefixes array in O(n)
    public static int[] lcp(int[] sa, CharSequence s) {
        int n = sa.length;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++)
            rank[sa[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++) {
            if (rank[i] < n - 1) {
                int j = sa[rank[i] + 1];
                while (Math.max(i, j) + h < s.length() && s.charAt(i + h) == s.charAt(j + h)) {
                    ++h;
                }
                lcp[rank[i]] = h;
                if (h > 0)
                    --h;
            }
        }
        return lcp;
    }
    static void markOccurrences() {
        StringBuilder builder = new StringBuilder();
        builder.append(strs[0]).append('#').append(s);
        int[] z1 = getZfunc(builder.toString());
        builder = new StringBuilder();
        builder.append(strs[1]).append('#').append(s);
        int[] z2 = getZfunc(builder.toString());
        int l1 = strs[0].length(), l2 = strs[1].length();
        for (int i = l1 + 1; i < z1.length; i++) {
            if(z1[i] == l1) occ[i - l1 - 1] = 1;
        }
        for (int i = l2 + 1; i < z2.length; i++) {
            if(z2[i] == l2) soccs.add(i - l2 - 1 + l2 - 1);
        }
    }
    public static int[] getZfunc(String str) {
        int L = 0, R = 0;
        char[] s = str.toCharArray();
        int n = s.length;
        int[] z = new int[n];
        z[0] = n;
        for (int i = 1; i < n; i++) {
            if (i > R) {
                L = R = i;
                while (R < n && s[R - L] == s[R]) R++;
                z[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if (z[k] < R - i + 1) z[i] = z[k];
                else {
                    L = i;
                    while (R < n && s[R - L] == s[R]) R++;
                    z[i] = R - L;
                    R--;
                }
            }
        }
        return z;
    }
}