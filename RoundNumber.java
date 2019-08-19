import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
 
public class RoundNumber {
	InputStream is;
	PrintWriter out;
	String INPUT = "";
 
	void solve() 
	{
		int n = ni(), Q = ni();
		long[] a = new long[n];
		int[] ft = new int[n+5];
		for(int i = 0; i < n; i++) {
			a[i] = nl();
			addFenwick(ft, i, -1, a[i]);
		}
		for(int q = 0; q < Q; q++) {
			int t = ni();
			if(t == 1) {
				int l = ni()-1, r = ni()-1;
				int ll = l == 0 ? 0 : sumFenwick(ft, l-1);
				int rr = sumFenwick(ft, r);
				out.println(rr-ll);
			}
			else {
				int ind = ni() - 1;
				long K = nl();
				addFenwick(ft, ind, a[ind], K);
				a[ind] = K;
			}
		}
	}
	
	public int sumFenwick(int[] ft, int i) {
		int sum = 0;
		for(i++; i > 0; i -= i&-i) sum += ft[i];
		return sum;
	}
	
	public void addFenwick(int[] ft, int i, long prev, long cur) {
		int v = 0;
		if(!isRound(prev) && isRound(cur)) v++;
		else if(isRound(prev) && !isRound(cur)) v--;
		if(v == 0) return;
		int n = ft.length;
		for(i++; i < n; i += i&-i) ft[i] += v;
	}
	
	public boolean isRound(long n) {
		if(n < 0) return false;
		long b = n % 10;
		while(n != 0) {
			if(n / 10 == 0) return n == b;
			n /= 10;
		}
		return false;
	}
			
	void run() throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
 
		long s = System.currentTimeMillis();
		solve();
		out.flush();
		if (!INPUT.isEmpty())
			tr(System.currentTimeMillis() - s + "ms");
	}
 
	public static void main(String[] args) throws Exception {
		new RoundNumber().run();
	}
 
	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;
 
	private int readByte() {
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}
 
	private boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}
 
	private int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}
 
	private double nd() {
		return Double.parseDouble(ns());
	}
 
	private float nf() {
		return Float.parseFloat(ns());
	}
 
	private char nc() {
		return (char) skip();
	}
 
	private String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
 
	private char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n && !(isSpaceChar(b))) {
			buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
 
	private char[][] nm(int n, int m) {
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}
 
	private int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}
 
	private int ni() {
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}
 
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
 
	private long nl() {
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}
 
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
 
	private static void tr(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}
}