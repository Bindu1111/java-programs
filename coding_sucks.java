import java.io.*;
import java.util.*;
class coding_sucks{
	public static void main(String[] args) throws IOException {
		BufferedReader S=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out=new PrintWriter(System.out);
		int t=Integer.parseInt(S.readLine());
		for(int zzz=0;zzz<t;zzz++) {
			StringTokenizer st=new StringTokenizer(S.readLine());
			char c[]=st.nextToken().toCharArray();
			int n=c.length;
			int K=Integer.parseInt(st.nextToken());
			long count=0;
			int z[]=new int[n];
			construct(c,n,z);
			count+=calc(n,K);
			for(int i=1;i<n;i++) {
				int l=z[i];
				int total=l+z[l];
				if(total==n) {
					count+=((long)K)*((long)l);
					count+=calc(n,K-1);
				}
				else {
					if(i+l==n)
					    count+=((long)total)*((long)(K-1))+(long)l;
					else
						count+=((long)K)*((long)l);
				}
			}//for
			out.println(count);
		}//for
		out.close();
	}//main
	static long calc(int n,int k) {
		long N=n;
		long K=k;
		return (N*K*(K+1))/2;
	}
	static void construct(char c[],int n,int z[]) {
		int l=0,r=0;
		for(int i=1;i<n;i++) {
			if(i>r) {
				l=i;
				r=i;
				while(r<n && c[r-l]==c[r])
					r++;
				z[i]=r-l;
				r--;
			}//if
			else {
				int k=i-l;
				if(z[k]<r-i+1)
					z[i]=z[k];
				else {
					l=i;
					while(r<n && c[r-l]==c[r])
						r++;
					z[i]=r-l;
					r--;
				}
			}//else
		}//for
	}//construct
}//class