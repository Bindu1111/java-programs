import java.util.ArrayList;
import java.util.Scanner;
 
public class Fractions{
    public static void main(String[] args) {
        rs x=new rs();
        Scanner s=new Scanner(System.in);
        int n,q;
        n=s.nextInt();
        q=s.nextInt();
        x.add(n);
        x.out(q);
        // x.add(8);
       // x.printAll();
    }
    public static class rs {
        ArrayList<fraction> fr=new ArrayList<>();
        public rs(){
            fr.add(new fraction(0,1));
            fr.add(new fraction(1,2));
        }
        public void add(int n)
        {
            for(int k=2;k<=n;k++){
 
                for(int i=0;i<fr.size()-1;i++)
                {
                    int j=i+1;
                    if(fr.get(i).denom+fr.get(j).denom<=k)
                    {
                        fr.add(i+1,new fraction(fr.get(i).num+fr.get(j).num,fr.get(i).denom+fr.get(j).denom));
                    }
                }
            }}
        public void out(int n)
        {
            System.out.println(fr.get(n-1).num+"/"+fr.get(n-1).denom);
        }
        public class fraction {
            public int num;
            public int denom;
            public fraction(int n,int d)
            {
                num=n;
                denom=d;
            }
 
 
        }
    }
 
}