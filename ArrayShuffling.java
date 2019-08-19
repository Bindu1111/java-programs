import java.util.*;
import java.io.*;
class Node {
    int val;
    int cnt;
    public Node(int val,int cnt){
        this.val=val;
        this.cnt=cnt;
    }
}
public class ArrayShuffling {
 
    void solve(){
     
            int n = ni();
 
            dp = new int[n + 1][101][101];
            arr = new int[3][n + 1];
            for (int i = 0; i <= n; i++) for (int j = 0; j <= 100; j++) Arrays.fill(dp[i][j], -1);
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j <= n; j++) {
                    arr[i][j] =ni();
 
                }
 
            }
            int ans = go(1, 0, 0, n);
            pw.println(ans);
        
    }
    int dp[][][];
    int arr[][];
    int go(int i,int a,int b,int n){
 
        if(i>n) return 0;
        if(dp[i][a][b]!=-1) return dp[i][a][b];
        int cc=0;
 
        int f[]={a,b};
        for(int ii=0;ii<6;ii++) {
            for (int mask = 0; mask < 7; mask++) {
                int cnt=0,c1=0;
                int f2[]={a,b};
                for (int j = 0; j < 2; j++) {
                    if((mask&(1<<j))>0){
                        if(arr[pr[ii][j]][i]>=f[j]){
                            f2[j]=arr[pr[ii][j]][i];
                            cnt++;
                        }
 
                    }
                }
                cc=Math.max(cc,cnt+go(i+1,f2[0],f2[1],n));
            }
        }
 
        dp[i][a][b]=cc;
        return cc;
    }
    int pr[][]={{0,1,2} ,{0,2,1} ,{1,0,2} ,{1,2,0} ,{2,0,1} ,{2,1,0} };
 
 
 
    long M=(long)1e9+7;
    InputStream is;
    PrintWriter pw;
    String INPUT = "";
    void run() throws Exception {
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        pw = new PrintWriter(System.out);
        long s = System.currentTimeMillis();
        solve();
        pw.flush();
        if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");
    }
 
    public static void main(String[] args) throws Exception { new ArrayShuffling().run(); }
 
    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;
 
    private int readByte() {
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }
 
    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
 
    private double nd() { return Double.parseDouble(ns()); }
    private char nc() { return (char)skip(); }
 
    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
 
    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
 
    private char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }
 
    private int[] na(int n) {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }
 
    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
 
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
 
    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
 
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
 
    private void tr(Object... o) { if(INPUT.length() > 0)System.out.println(Arrays.deepToString(o)); }
}