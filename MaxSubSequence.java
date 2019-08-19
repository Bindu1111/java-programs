import java.util.*;
import java.io.*;
class Node{
	int length;
	int count;
	Node( int length, int count ){
		this.length = length;
		this.count = count;
	}
}
class Tree{
	public static final int N = 100001;
	public static final int MOD = 1000000007;
	Node [] pos;
	Node [] neg;
	int treeSize;
	Tree(){
		treeSize = 4 * N;
		pos = new Node [ 4 * N ];
		neg = new Node [ 4 * N ];
		for( int i = 0; i < treeSize; i += 1 ){
			pos[i] = new Node( 0, 0 );
			neg[i] = new Node( 0, 0 );
		}
	}
	Node queryUtil ( int s, int e, int l, int r, int n, boolean sign ){
		if( s > e || r < s || l > e)
			return null;
		if( l <= s && e <= r ){
			if( sign )
				return neg[n];
			return pos[n];
		}
		int m = ( s + e ) / 2;
		Node left = queryUtil( s, m, l, r, 2 * n + 1, sign );
		Node right = queryUtil( m + 1, e, l, r, 2 * n + 2, sign );
		if( left == null )
			return right;
		if( right == null )
			return left;
		if( left.length == right.length )
		    return new Node( left.length,  ( left.count % MOD + right.count % MOD ) % MOD);
		return left.length > right.length ? left : right;
	}
	Node query ( int l, int r, boolean sign ){
		int s = 0;
		int e = N - 1;
		return queryUtil( s, e, l, r, 0, sign );
	}
	private void setBest( int n, boolean sign ){
		Node [] tree = neg;
		if( sign )
			tree = pos;
		Node left = tree[ 2 * n + 1 ];
		Node right = tree[ 2 * n + 2 ];
		if( left.length == right.length ){
			tree[n].length = right.length;
			tree[n].count = ( left.count % MOD + right.count % MOD ) % MOD;
		}else if( left.length > right.length ){
			tree[n].length = left.length;
			tree[n].count = left.count;
		}else if( left.length < right.length ){
			tree[n].length = right.length;
			tree[n].count = right.count;
		}
	}	
	void updateUtil( int s, int e, int i, int n, int length, int count, boolean sign ){
		if( s > e )
			return;
		if( s == e ){
			Node [] tree = neg;
			if(sign)
			    tree = pos;
			if(tree[n].length < length){
			    tree[n].length = length;
			    tree[n].count = count;
			}else if( tree[n].length == length)
			    tree[n].count = ( count % MOD + tree[n].count % MOD ) % MOD;
			return;
		}
		int m = ( s + e ) / 2;
		if( i >= s && i <= m )
			updateUtil( s, m, i, 2 * n + 1, length, count, sign );
		else
			updateUtil( m + 1, e, i, 2 * n + 2, length, count, sign );
		setBest(n, sign);
	}
	void update( int index, int length, int count, boolean sign ){
		int s = 0;
		int e = N - 1;
		updateUtil ( s, e, index, 0, length, count, sign );
	}
}
class MaxSubSequence{
     
    static void find( int [] arr ){
	    int n = arr.length;
		Tree t = new Tree();
		for( int i = 0; i < n; i += 1 ){
			boolean sign = true;
			if( arr[i] < 0 )
				sign = false;
			int val = Math.abs( arr[i] );
			Node _n = t.query( 0, val - 1, sign );
			//System.out.println(_n.length + " " + _n.count + " " + val);
			if(_n.count == 0)
			    t.update( val, 1, 1, sign);
			else
			    t.update( val, _n.length + 1, _n.count, sign );
		}
		
		Node res = t.pos[0].length > t.neg[0].length ? t.pos[0] : t.neg[0];
		System.out.println( res.length + " " + res.count);
	}
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int [] a = new int [N];
        for(int i = 0; i < N; i += 1){
            a[i] = sc.nextInt();
        }
        find(a);
    }
}