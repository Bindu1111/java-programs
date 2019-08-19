import java.util.*;
import java.io.*;
class MaximiseSum{
	public static void main(String[] args) throws IOException{
		BufferedReader S=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out=new PrintWriter(System.out);
		int n=Integer.parseInt(S.readLine());
		a=new long[n];
		StringTokenizer st=new StringTokenizer(S.readLine());
		for(int i=0;i<n;i++)
			a[i]=Integer.parseInt(st.nextToken());
		dp=new long[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++)
				dp[i][j]=-1;
		}
		out.println(solve(0,n-1));
		out.close();
	}
	static long a[];
	static int n;
	static long dp[][];
	static long solve(int l,int r) {
		if(dp[l][r]!=-1)
			return dp[l][r];
		if(l==r)
			return a[l];
		if(r-l==1)
			return Math.max(a[l]+a[r],a[l]*a[r]);
		long max=a[l]+solve(l+1,r);
		long a1=a[r]+solve(l,r-1);
		if(max<a1)
			max=a1;
		a1=a[l]*a[l+1]+solve(l+2,r);
		if(max<a1)
			max=a1;
		a1=a[r]*a[r-1]+solve(l,r-2);
		if(max<a1)
			max=a1;
		dp[l][r]=max;
		return max;
	}//solve
}