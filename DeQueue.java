    import java.util.*;
    public class DeQueue{
        public static void main(String[] args) {
			System.out.println("two unique nuumbers");
            Scanner in = new Scanner(System.in);
			
            Deque<Integer> deque = new LinkedList<Integer>();
            int n = in.nextInt();
            int m = in.nextInt();
            int a[]=new int[n];
    HashSet<Integer>hs=new  HashSet<Integer>();
             HashMap<Integer,Integer>hm=new  HashMap<Integer,Integer>();
			 System.out.println("enter elements:");
            for (int i = 0; i < n; i++) {
                int num = in.nextInt();
               a[i]=num;
            }
            int ans=0;

            for(int i=0;i<m;i++)
                {
                    deque.addLast(a[i]);
                if(hm.containsKey(a[i]))
                    {
                    int y=hm.get(a[i]);
                    hm.put(a[i],y+1);
                }
                else
                    hm.put(a[i],1);
                    hs.add(a[i]);
            }
            ans=hs.size();
            int k=1;
            for(int j=m+k-1;j<n;j++,k++)
                {
                Integer x=deque.removeFirst();
                  if(hm.containsKey(x))
                    {
                    int y=hm.get(x);
                      if(y>1)
                    hm.put(x,y-1);
                      else
                         {
                           hs.remove(x);
                       hm.remove(x);
                      }
                }
                 if(hm.containsKey(a[j]))
                    {
                    int y=hm.get(a[j]);
                    hm.put(a[j],y+1);
                }
                else
                    hm.put(a[j],1);
               
                               hs.add(a[j]);
                    deque.addLast(a[j]);
                if(ans<hs.size())
                    ans=hs.size();
            }
			System.out.println("output:");
            System.out.println(ans);
        }
    }