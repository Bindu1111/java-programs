import java.io.*;
import java.util.*;
public final class puzzled_grid
{
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static FastScanner sc=new FastScanner(br);
    static PrintWriter out=new PrintWriter(System.out);
	static Random rnd=new Random();
	static ArrayList<Integer> al[];
	static Query[] qr;
	static int n,q;
	static boolean[][] v;
	static int[][] a,cnt;
	static int[] parent;
	
	static int getParent(int u)
	{
		if(u==parent[u])
		{
			return u;
		}
		else
		{
			parent[u]=getParent(parent[u]);return parent[u];
		}
	}
	
	static void merge(int u,int v)
	{
		parent[getParent(v)]=getParent(u);
	}
	
	static void add(Pair curr)
	{
		int i=curr.i,j=curr.j;
		if(i-1>=0 && v[i-1][j])
		{
			int val1=cnt[i-1][j],val2=cnt[i][j];
			merge(val1,val2);
		}
		if(i+1<n && v[i+1][j])
		{
			int val1=cnt[i+1][j],val2=cnt[i][j];
			merge(val1,val2);
		}
		if(j+1<n && v[i][j+1])
		{
			int val1=cnt[i][j+1],val2=cnt[i][j];
			merge(val1,val2);
		}
		if(j-1>=0 && v[i][j-1])
		{
			int val1=cnt[i][j-1],val2=cnt[i][j];
			merge(val1,val2);
		}
		v[i][j]=true;
	}
	
	@SuppressWarnings("unchecked")
    public static void main(String args[]) throws Exception
    {
		n=sc.nextInt();q=sc.nextInt();a=new int[n][n];cnt=new int[n][n];List<Pair> list=new ArrayList<Pair>();int ctr=0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				a[i][j]=sc.nextInt();list.add(new Pair(i,j,a[i][j]));
			}
		}
		al=new ArrayList[n*n];Collections.sort(list);int[] low=new int[q],high=new int[q];qr=new Query[q];
		for(int i=0;i<q;i++)
		{
			int x1=sc.nextInt(),y1=sc.nextInt(),x2=sc.nextInt(),y2=sc.nextInt();
			qr[i]=new Query(x1,y1,x2,y2);
			low[i]=0;high[i]=n*n;
		}
		boolean change=true;v=new boolean[n][n];parent=new int[n*n];
		for(int i=0;i<list.size();i++)
		{
			cnt[list.get(i).i][list.get(i).j]=i;
		}
		while(change)
		{
			change=false;int k=0;
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					al[k]=new ArrayList<Integer>();v[i][j]=false;parent[k]=k++;
				}
			}
			for(int i=0;i<q;i++)
			{
				if(low[i]!=high[i])
				{
					change=true;al[(low[i]+high[i])>>1].add(i);
				}
			}
			for(int i=0;i<list.size();i++)
			{
				add(list.get(i));
				for(int x:al[i])
				{
					int val1=cnt[qr[x].x1][qr[x].y1],val2=cnt[qr[x].x2][qr[x].y2];
					if(getParent(val1)==getParent(val2))
					{
						high[x]=i;
					}
					else
					{
						low[x]=i+1;
					}
				}
			}
		}
		for(int i=0;i<q;i++)
		{
			out.println(list.get(low[i]).val);
		}
		out.println("");out.close();
    }
}
class Pair implements Comparable<Pair>
{
	int i,j,val;
	public Pair(int i,int j,int val)
	{
		this.i=i;this.j=j;this.val=val;
	}
	public int compareTo(Pair x)
	{
		return Integer.compare(this.val,x.val);
	}
}
class Query
{
	int x1,y1,x2,y2;
	public Query(int x1,int y1,int x2,int y2)
	{
		this.x1=x1;this.y1=y1;this.x2=x2;this.y2=y2;
	}
}
class FastScanner
{
    BufferedReader in;
    StringTokenizer st;
 
    public FastScanner(BufferedReader in) {
        this.in = in;
    }
	
    public String nextToken() throws Exception {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }
	
	public String next() throws Exception {
		return nextToken().toString();
	}
	
    public int nextInt() throws Exception {
        return Integer.parseInt(nextToken());
    }
 
    public long nextLong() throws Exception {
        return Long.parseLong(nextToken());
    }
 
    public double nextDouble() throws Exception {
        return Double.parseDouble(nextToken());
    }
}