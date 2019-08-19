import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
 
class Magic{
    static int[] X;
    static int[] Y;
    static String[] D;
    static int[] V;
 
    class PersonDistance implements Comparable<PersonDistance>{
        double distance;
        int p1;
        int p2;
        PersonDistance(double distance,int p1,int p2){
            this.distance=distance;
            this.p1=p1;
            this.p2=p2;
        }
        @Override
        public int compareTo(PersonDistance o) {
            if(distance==((PersonDistance)o).distance)
                return 0;
            else if(distance>((PersonDistance)o).distance)
                return 1;
            else
                return -1;
        }
    }
 
    public static void main(String args[] ) throws Exception {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        for (int testCase = 0; testCase < N; testCase++) {
            int numOfPer=s.nextInt();
            X=new int[numOfPer];
            Y=new int[numOfPer];
            D=new String[numOfPer];
            V=new int[numOfPer];
            //get input
            for(int i=0;i<numOfPer;i++){
                X[i]=s.nextInt();
                Y[i]=s.nextInt();
                D[i]=s.next();
            }
 
         
 
            List<PersonDistance> personDistanceList=new ArrayList<PersonDistance>();
            for(int i=0;i<numOfPer-1;i++){
                for(int j=i+1;j<numOfPer;j++){
                    double d=new Magic().getDistance(X[i],Y[i],X[j],Y[j]);
                    personDistanceList.add(new Magic().new PersonDistance(d, i, j));
                }
            }
            Collections.sort(personDistanceList);
 
            for(PersonDistance personDistance:personDistanceList){
                int i=personDistance.p1;
                int j=personDistance.p2;
                int f=0;
                //System.out.println("distance="+distance);
                if(V[i]==0  && V[j]==0){
                    if(new Magic().setVanish(i,j))
                    {
                        //System.out.println("p1="+i);
                        //System.out.println("p1="+j);
                        V[i]=1;
                        V[j]=1;
                        f=1;
                    }
 
                }
                
                if( f==1){
                   
                    for(int k=0;k<numOfPer;k++){
                        if(k!=i && k!=j && ((new Magic().setVanish(i, k)==true) && (new Magic().setVanish(k, j)==true))){
                            V[k]=1;
                        }
                    }
                }
            }
            int countOfNotVanished=0;
            for(int j=0;j<numOfPer;j++){
                if(V[j]==0)
                    countOfNotVanished++;
            }
            System.out.println(countOfNotVanished);
        }
    }
 
    private double getDistance(int x1, int y1, int x2, int y2) {
        if(x1==x2 || y1==y2)
            return ((Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)))/2);
        return (Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
    }
 
    private boolean setVanish(int i,int j){
        if(D[i].equals("N")){
            if(D[j].equals("E")){
                if(X[j]<X[i] && Y[j]>Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("W")){
                if(X[j]>X[i] && Y[j]>Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("S")){
                if(X[j]==X[i] && Y[j]>Y[i]){
                    return true;
                }
            }
        }
        else if(D[i].equals("S")){
            if(D[j].equals("E")){
                if(X[j]<X[i] && Y[j]<Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("W")){
                if(X[j]>X[i] && Y[j]<Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("N")){
                if(X[j]==X[i] && Y[j]<Y[i]){
                    return true;
                }
            }
        }
        else if(D[i].equals("E")){
            if(D[j].equals("N")){
                if(X[j]>X[i] && Y[j]<Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("S")){
                if(X[j]>X[i] && Y[j]>Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("W")){
                if(X[j]>X[i] && Y[j]==Y[i]){
                    return true;
                }
            }
 
 
        }
        else if(D[i].equals("W")){
            if(D[j].equals("N")){
                if(X[j]<X[i] && Y[j]<Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("S")){
                if(X[j]<X[i] && Y[j]>Y[i]){
                    if(Math.abs(X[j]-X[i])==Math.abs(Y[j]-Y[i])){
                        return true;
                    }
                }
            }
            else if(D[j].equals("E")){
                if(X[j]<X[i] && Y[j]==Y[i]){
                    return true;
                }
            }
        }
        return false;
 
    }
 
}