import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;
 
class Node
{
    int end;
    Node in[] = new Node[26];
    public Node()
    {
        end=0;
        for (int x=0;x<26;x++)
        in[x]=null;
    }
}
class Trie
{
    Node root;
    static int ans=0;
    Trie()
    {
        root = new Node();
        ans=0;
    }
    void insert(String a)
    {
        Node t = root;
        for (int x=0;x<a.length();x++)
        {
            if (t.in[a.charAt(x)-'a']==null)
            t.in[a.charAt(x)-'a'] = new Node();
            t=t.in[a.charAt(x)-'a'];
        }
        t.end++;
    }
    void dfs(Node root)
    {
        for (int x=0;x<26;x++)
        {
            if (root.in[x]==null)
            continue;
            else{
                //System.out.println ((char)(x+97));
            ans++;
            dfs(root.in[x]);}
        }
    }
    public static void main(String args[] ) throws Exception 
    {
        Scanner in = new Scanner(System.in);
        int N=in.nextInt();
        String a[] = new String[N];
        Trie t = new Trie();
        for (int x=0;x<N;x++)
        {
            a[x]=in.next();
            t.insert(a[x]);
        }
        t.dfs(t.root);
        System.out.println (ans+1);
    }
}