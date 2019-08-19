import java.util.*;
 
import java.io.*;

class TrieNode {

	int count=0;
	
	HashMap<Character,TrieNode> hm=null;
	TrieNode(){
		
		hm=new HashMap<Character,TrieNode>();
		
	    }	
}
class TriePrefix{
	
	
	static int count=0;
	
	static void Dfs(TrieNode root){
		
		if(root==null)
			return ;
		
		
		if(root.count!=0)
		{
			count+=root.count;
		
		}
		for(TrieNode temp: root.hm.values())
			Dfs(temp);
		
	}
	
	
	
	static void searchQuery(TrieNode root,String s){
      if(s==null)
		  return ;
	  
	  int length=s.length();
	  
	  for(int i=0;i<length;i++){
		  
		  char c=s.charAt(i);
 
         if(root.hm.containsKey(c))
         root=root.hm.get(c);
 
          else
           return ;
        	   
		  
		  
	  }
		  
	  Dfs(root);
		
		
	}

static void insertIntoTrie(TrieNode root,String s){
	
	
	if(s==null)
		return ;
	
	int n=s.length();

	TrieNode temp=null;
	
	for(int i=0;i<n-1;i++){
		char c=s.charAt(i);
		
		if(root.hm.containsKey(c))
			root=root.hm.get(c);
          else
		  {
             temp=new TrieNode();
			 
			 root.hm.put(c,temp);
			 root=temp;
		 
 
		  }			  
		
     }
		
	if(root.hm.containsKey(s.charAt(n-1)))
	{
		
		
		root=root.hm.get(s.charAt(n-1));

		root.count++;
		
	}
	
	
	else{
		temp=new TrieNode();
		root.hm.put(s.charAt(n-1),temp);
		temp.count++;	
	}
}

public static void main(String args[]){

	Scanner sc=new Scanner(System.in);
	
	int n=sc.nextInt();
	
	int t=sc.nextInt();

	TrieNode root=new TrieNode();
	
	for(int i=0;i<n;i++)
	{
		insertIntoTrie(root,sc.next());
	}
 
    for(int i=0;i<t;i++){
		count=0;
		
   searchQuery(root,sc.next());
   
     System.out.println(count);
	}
}
}