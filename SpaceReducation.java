import java.io.*;
 
import java.util.*;
 import java.awt.Point;
 
 class SpaceReducation {
 
static long mod ;
static int[] S;
static ArrayList<Integer>[] M;
static int[][] dp;
static int[] P;
public static void explore(int i,int pa, int len){
	   
    S[i]=len;
    P[i]=pa;
    
    for(int j:M[i]){
  	  
  	  if(j!=pa){ 
  		  explore(j,i, len+1);    
  	  }
    }
   
}
 
public static int lca(int a , int b){
	 
	   if(S[a]>S[b]){
		   int temp =a;
		   a=b;
		   b=temp;
	   }
	   
	   for(int i=19;i>=0;i--){
		   
		      if(S[b]-(1<<i)>=S[a]){
		    	     b= dp[i][b];
		      }
	   }
	   if(a==b) return a;
	   
	   for(int i=19;i>=0;i--){
		   
		     if(dp[i][a]!=dp[i][b]){
		    	  a= dp[i][a];
		    	  b=dp[i][b];
		     }
	   }
	   return  P[a];
}
 
 
public static int gcd(int a , int b){
	
	 if(b==0) return a;
	 
	 return gcd(b,a%b);
}
 
public static void main(String args[] ) throws java.lang.Exception {
 
 
 
InputStream inputStream = System.in;
InputReader in = new InputReader(inputStream);
 
int n = in.nextInt();
long[] X = new long[n];
long[] H = new long[n];
Point[] R = new Point[n];
for(int i=0;i<n;i++) R[i] = new Point(in.nextInt(), in.nextInt());
Arrays.sort(R,new Comparator<Point>() {
 
	@Override
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		return o1.x-o2.x;
	}
});
for(int i=0;i<n;i++){
	X[i]=R[i].x;
	H[i] =R[i].y;
}
int[][] dp = new int[2][n];
for(int i=0;i<2;i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
dp[0][0]=1;
int j=1;
dp[1][0]=1;
long range = X[0]+H[0];
while(j<n && range>=X[j]){
	dp[1][j]=1;
	range = Math.max(range,X[j]+H[j]);
	j++;
}
 
for(int i=1;i<n;i++){
	
	j=i-1;
	range = X[i]-H[i];
	while(j>=0 && range<=X[j]){
		range = Math.min(range,X[j]-H[j]);
		j--;
	}
	if(j<0) j=0;
	if(range<=X[j]){ 
	    dp[0][i] = dp[0][j];
	    dp[0][i] = Math.min(dp[0][i],1+dp[1][j]);
	}
	else{
		
		dp[0][i] = Math.min(dp[0][i],1+dp[0][j]);
		dp[0][i] = Math.min(dp[0][i],1+dp[1][j]);
	}
	
	dp[1][i] = Math.min(dp[1][i],dp[0][i-1]+1);
		dp[1][i] = Math.min(dp[1][i],dp[1][i-1]+1);
	if(X[i-1]+H[i-1]>=X[i]) dp[1][i] = Math.min(dp[1][i], dp[1][i-1]);
	
	j=i+1;
	range= X[i]+H[i];
	while(j<n && range>=X[j]){
		
		dp[1][j] = Math.min(dp[1][j],dp[1][i]);
		range = Math.max(range,X[j]+H[j]);
		j++;
	}
	
	 
}
System.out.println(Math.min(dp[1][n-1],dp[0][n-1]));
 
 
}public static class InputReader
{
private InputStream stream;
private byte[] buf = new byte[1024];
private int curChar;
private int numChars;
private SpaceCharFilter filter;
 
public InputReader(InputStream stream)
{
this.stream = stream;
}
 
public int read()
{
if (numChars == -1)
throw new InputMismatchException();
if (curChar >= numChars)
{
curChar = 0;
try
{
numChars = stream.read(buf);
} catch (IOException e)
{
throw new InputMismatchException();
}
if (numChars <= 0)
return -1;
}
return buf[curChar++];
}
 
public int nextInt()
{
int c = read();
while (isSpaceChar(c))
c = read();
int sgn = 1;
if (c == '-')
{
sgn = -1;
c = read();
}
int res = 0;
do
{
if (c < '0' || c > '9')
throw new InputMismatchException();
res *= 10;
res += c - '0';
c = read();
} while (!isSpaceChar(c));
return res * sgn;
}
 
public String readString()
{
int c = read();
while (isSpaceChar(c))
c = read();
StringBuilder res = new StringBuilder();
do
{
res.appendCodePoint(c);
c = read();
} while (!isSpaceChar(c));
return res.toString();
}
public double readDouble() {
int c = read();
while (isSpaceChar(c))
c = read();
int sgn = 1;
if (c == '-') {
sgn = -1;
c = read();
}
double res = 0;
while (!isSpaceChar(c) && c != '.') {
if (c == 'e' || c == 'E')
return res * Math.pow(10, nextInt());
if (c < '0' || c > '9')
throw new InputMismatchException();
res *= 10;
res += c - '0';
c = read();
}
if (c == '.') {
c = read();
double m = 1;
while (!isSpaceChar(c)) {
if (c == 'e' || c == 'E')
return res * Math.pow(10, nextInt());
if (c < '0' || c > '9')
throw new InputMismatchException();
m /= 10;
res += (c - '0') * m;
c = read();
}
}
return res * sgn;
}
public long readLong() {
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
public boolean isSpaceChar(int c)
{
if (filter != null)
return filter.isSpaceChar(c);
return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
}
 
public String next()
{
return readString();
}
 
public interface SpaceCharFilter
{
public boolean isSpaceChar(int ch);
}
}
}