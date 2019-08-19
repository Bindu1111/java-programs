import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
 

public class SegmentTree {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        ScanReader in = new ScanReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        The_Market solver = new The_Market();
        solver.solve(1, in, out);
        out.close();
    }
 
    static class The_Market {
        int[] count;
        int[][] dp;
        int[] individual;
 
        public void solve(int testNumber, ScanReader in, PrintWriter out) {
            pre();
            int N = in.scanInt();
            int array[] = new int[N];
            int distinct = 1;
            for (int i = 0; i < N; i++) {
                array[i] = in.scanInt();
                array[i] = count[array[i]];
                if (individual[array[i]] == 0) {
                    individual[array[i]] = distinct++;
                }
                array[i] = individual[array[i]];
            }
            dp = new int[N + 1][105];
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= distinct; j++) dp[i][j] = dp[i - 1][j];
                dp[i][array[i - 1]]++;
            }
            int Q = in.scanInt(), l, r;
            long ans;
            while (Q-- > 0) {
                l = in.scanInt();
                r = in.scanInt();
                ans = 0;
                for (int i = 1; i < distinct; i++) {
                    ans += ((dp[r][i] - dp[l - 1][i]) * ((dp[r][i] - dp[l - 1][i]) - 1)) / 2;
                }
                out.println(ans);
            }
        }
 
        private void pre() {
            count = new int[1000005];
            for (int i = 1; i <= 1000000; i++)
                for (int j = i; j <= 1000000; j += i)
                    count[j]++;
            individual = new int[1005];
        }
 
    }
 
    static class ScanReader {
        private byte[] buf = new byte[4 * 1024];
        private int index;
        private BufferedInputStream in;
        private int total;
 
        public ScanReader(InputStream inputStream) {
            in = new BufferedInputStream(inputStream);
        }
 
        private int scan() {
            if (index >= total) {
                index = 0;
                try {
                    total = in.read(buf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (total <= 0) return -1;
            }
            return buf[index++];
        }
 
        public int scanInt() {
            int integer = 0;
            int n = scan();
            while (isWhiteSpace(n)) n = scan();
            int neg = 1;
            if (n == '-') {
                neg = -1;
                n = scan();
            }
            while (!isWhiteSpace(n)) {
                if (n >= '0' && n <= '9') {
                    integer *= 10;
                    integer += n - '0';
                    n = scan();
                }
            }
            return neg * integer;
        }
 
        private boolean isWhiteSpace(int n) {
            if (n == ' ' || n == '\n' || n == '\r' || n == '\t' || n == -1) return true;
            else return false;
        }
 
    }
}