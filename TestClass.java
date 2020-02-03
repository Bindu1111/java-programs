import java.util.*;
import java.lang.*;
class TestClass {
    public static void main(String args[] ) throws Exception {
        Scanner in = new Scanner(System.in);
        int flag=0,flag1=0;
        int n=in.nextInt();
        int m=in.nextInt();
        int a[]=new int[n];
        int b[]=new int[m];
        int c[][]=new int[n][m];
        int x,y,n1,m1;
        for(int i=0;i<n-1;i++){
            x=in.nextInt();
            y=in.nextInt();
            a[x-1]++;
            a[y-1]++;
        }
        
        for(int i=0;i<m-1;i++){
            x=in.nextInt();
            y=in.nextInt();
            b[x-1]++;
            b[y-1]++;
        }
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                flag=a[i]-b[j];
                if(flag<0){
                    flag=flag*(-1);
                    n1=n+flag;
                    flag1=Math.abs(n1-m);
                    flag=flag+flag1;
                }
                else if(flag>=0){
                    m1=m+flag;
                    flag1=Math.abs(m1-n);
                    flag=flag+flag1;
                }
                System.out.print(flag+" ");
            }
            System.out.println();
        }
 
    }
}