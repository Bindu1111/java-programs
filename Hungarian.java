import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
 
public class Hungarian {
 
	// a[1..n][1..m] >= 0, n <= m
	public static int solveAssignmentProblem(int[][] a) {
		int n = a.length - 1;
		int m = a[0].length - 1;
		int[] u = new int[n + 1];
		int[] v = new int[m + 1];
		int[] p = new int[m + 1];
		int[] way = new int[m + 1];
		for (int i = 1; i <= n; ++i) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[m + 1];
			Arrays.fill(minv, Integer.MAX_VALUE);
			boolean[] used = new boolean[m + 1];
			do {
				used[j0] = true;
				int i0 = p[j0];
				int delta = Integer.MAX_VALUE;
				int j1 = 0;
				for (int j = 1; j <= m; ++j)
					if (!used[j]) {
						int cur = a[i0][j] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				for (int j = 0; j <= m; ++j)
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else
						minv[j] -= delta;
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 != 0);
		}
		return -v[0];
	}
 
	public static void main(String[] args) {
		InputReader s=new InputReader(System.in); 
    	PrintWriter out=new PrintWriter(new OutputStreamWriter(System.out));
    	int t=s.nextInt();
    	while(t-->0)
    	{
    		int n=s.nextInt();
    		int m=s.nextInt();
    		int kk=s.nextInt();
    			
    		int[][] a = new int[kk+1][kk+1];
			int ds[][]=new int[n+1][n+1];
 
			for (int i = 0; i <= n; i++) {
				for (int j = 0; j <= n; j++) {
					ds[i][j] = 10000;
 
				}
			}
			for(int i=0;i<m;i++)
    		{
    		int u=s.nextInt();
    		int v=s.nextInt();
    		int c=s.nextInt();
    		ds[u][v]=Math.min(ds[u][v], c);
    		ds[v][u]=Math.min(ds[v][u], c);
      		}			 	
			for (int k = 1; k <= n; k++) {
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						if (ds[i][j] > ds[i][k] + ds[k][j]) {
							int c = ds[i][k] + ds[k][j];
							ds[i][j] = ds[i][k] + ds[k][j];
						}
					}
				}
			}
    		
			for (int i = 1; i <= kk; i++) {
				for (int j = 1; j <=kk; j++) {
						a[i][j]=ds[i][j+n-kk]; 
				}
			}
 
    		
			int res1 = solveAssignmentProblem(a);
			out.println(res1);
		}
    	out.close();
	}
static int gcd(int num1, int num2){
    if(num1 > num2){
        int temp = num1;
        num1 = num2;
        num2 = temp;
    }
    while(num1 != 0){
        int temp = num1;
        num1 = num2 % num1;
        num2 = temp;
    }
    return num2;
}
	static int solveAssignmentProblemSlow(int[][] a) {
		int n = a.length - 1;
		int m = a[0].length - 1;
		int res = Integer.MAX_VALUE;
		int[] p = new int[n];
		for (int i = 0; i < n; i++)
			p[i] = i;
		do {
			int cur = 0;
			for (int i = 0; i < p.length; i++)
				cur += a[i + 1][p[i] + 1];
			res = Math.min(res, cur);
		} while (nextArrangement(p, m));
		return res;
	}
 
	static boolean nextArrangement(int[] p, int n) {
		boolean[] used = new boolean[n];
		for (int x : p) {
			used[x] = true;
		}
		int m = p.length;
		for (int i = m - 1; i >= 0; i--) {
			used[p[i]] = false;
			for (int j = p[i] + 1; j < n; j++) {
				if (!used[j]) {
					p[i++] = j;
					used[j] = true;
					for (int k = 0; k < n && i < m; k++) {
						if (!used[k]) {
							p[i++] = k;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}
class InputReader {
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;
 
    public InputReader(InputStream stream) {
        this.stream = stream;
    }
 
    public static boolean isWhitespace(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }
 
    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }
 
    public int nextInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
    public long nextLong() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
    public String nextToken() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }
 
    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return isWhitespace(c);
    }
 
    public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
 
 
	
 
}