import java.io.*;
import java.util.*;
public class Tree{
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		
		
		for(int i = 0; i < n; i++) {
			int col = 100001;
			Stack<Integer> stack = new Stack<Integer>();
			List<ArrayList<Character>> cols = new ArrayList<ArrayList<Character>>();
			
			for(int j = 0; j < 200005; j++) {
				cols.add(new ArrayList<Character>());
			}
 
			String[] line = br.readLine().split(" ");
			int c = Integer.parseInt(line[0]);
			String s = line[1];
			for(int j = 0; j < s.length(); j++) {
				if(s.charAt(j) == '(') {
					col = stack.peek() - 1;
					continue;
				}
				if(s.charAt(j) == ')') {
					stack.pop();
					if(!stack.isEmpty()) col = stack.peek() + 1;
					continue;
				}
				if(s.charAt(j) == '.') {
					col = stack.peek() + 1;
					continue;
				}
				cols.get(col).add(s.charAt(j));
				stack.push(col);
			}
			
			Object[] chars = cols.get(100001+c).toArray();
			if(chars.length != 0) {
				Arrays.sort(chars);
				for(int j = 0; j < chars.length; j++) {
					bw.write((Character)chars[j]);
				}
				bw.write("\n");
			}else {
				bw.write("Common!\n");
			}			
		}
		
		bw.flush();
		
		
	}
}