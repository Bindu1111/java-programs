import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.*; 
import java.util.StringTokenizer; 
 
public class Heap 
{ 
	FastReader in;
	
	public void solve(){
	    TreeMap<Integer,Integer> map=new TreeMap<>();
	    
	    int q=in.ni();
	    while(q-->0){
	        int i=in.ni();
	        if(i==1){
	            int n=in.ni();
	            if(map.containsKey(n))
	                map.put(n,map.get(n)+1);
	           else     
	                map.put(n,1);
	        }else if(i==2){
	            int n=in.ni();
	            if(!map.containsKey(n)){
	                System.out.println("-1");
	            }
	            else{
	                if(map.get(n)==1)
	                    map.remove(n);
	                else
	                    map.put(n,map.get(n)-1);
	            }
	        }else if(i==4){
	            if(!map.isEmpty())
	                System.out.println(map.firstKey());
	            else
	                System.out.println("-1");
	        }else if(i==3){
	            if(!map.isEmpty())
	                System.out.println(map.lastKey());
	            else
	                System.out.println("-1");
	        }
	    }
	}
	
	public static void main(String[] args) 
	{ 
		new Heap().main1();
		
	}
	void main1(){
	    in=new FastReader();
	    solve();
	}
	public class FastReader 
	{ 
		BufferedReader br; 
		StringTokenizer st; 
 
		public FastReader() 
		{ 
			br = new BufferedReader(new
					InputStreamReader(System.in)); 
		} 
 
		String next() 
		{ 
			while (st == null || !st.hasMoreElements()) 
			{ 
				try
				{ 
					st = new StringTokenizer(br.readLine()); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				} 
			} 
			return st.nextToken(); 
		} 
 
		int ni() 
		{ 
			return Integer.parseInt(next()); 
		} 
 
		long nl() 
		{ 
			return Long.parseLong(next()); 
		} 
 
		double nd() 
		{ 
			return Double.parseDouble(next()); 
		} 
 
		String nextLine() 
		{ 
			String str = ""; 
			try
			{ 
				str = br.readLine(); 
			} 
			catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
			return str; 
		} 
	}
} 