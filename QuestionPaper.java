import java.util.Scanner;
 
public class QuestionPaper {
	public static void input() {
		Scanner in= new Scanner(System.in);
		int t= in.nextInt();
		while(t-->0) {
			long n= in.nextLong();
			long a = in.nextLong();
			long b = in.nextLong();
			if(a==b) {
				System.out.println((n*2)+1);
			}
			else {
				long ca = 0;
				long cb = 0;
				for(long x=1;;x++) {
					if((a*x)%b==0) {
						ca = x;
						cb = (a*x)/b;
						break;
					}
				}
				long y=0;
				for(long x = cb;;x++) {
					if(x+ca<=n) {
						y++;
					}
					else break;
				}
				System.out.println(((n+1)*(n+2)/2)-((y)*(y+1)/2));
			}
		}
	}
    public static void main(String[] args) {
    	input();
    }
}