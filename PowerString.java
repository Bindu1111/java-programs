import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
 
public class PowerString
{
	public static class SuffixTree
	{
		private class Edge
		{
			/*
			 * s[first : last] = label (inclusive)
			 * 
			 * if an edge is a leaf we will set immediately last as text.length() - 1, instead of 
			 * using a global variable. This because at any step the suffix to insert is less long than 
			 * any other path already in the tree.
			 * 
			 * PAY ATTENTION: THIS version IS NOT online. To make it online we should introduce the global variable 
			 * 'end'.
			 */
			
			
			int first, last;
			
			public Edge(int f, int l)
			{
				first = f;
				last = l;
			}
		}
		
		private class Node
		{
			/*
			 * Assuming that the alphabet is just {'a', ..., 'z'}.
			 * For case with bigger alphabet we could pass to an implementation with AVL Tree or better.
			 */
			
			Node[] nodes = new Node['z' - 'a' + 1];
			Edge[] edges = new Edge['z' - 'a' + 1];
			
			int pathLabelLength;
			
			int additionalEdge = -1;
			
			Node father = null;
			Node suffixLink = null;
			
			int position;
			int depth;
			int numberOfLeaves;
			
			public Node(int pll, int p)
			{
				pathLabelLength = pll;
				position = p;
			}
		}
		
		Node root;
		String text;
		
		Node[] treeNodes;
	
		
		private int[][] ancestors;
		
		public SuffixTree(String t)
		{
			/*
			 * A constructor always exists.
			 */
			
			root = new Node(0, 0);
			text = t + "$";
			
			treeNodes = new Node[text.length()];
			ancestors = new int[text.length()][(int)(Math.log(text.length()) / Math.log(2)) + 1];
			
			this.buildTree();
		}
		
		private void buildTree()
		{
			root.suffixLink = root;
			
			int firstSuffixToCompute = 0;
			
			Node currentNode = root;
			
			int nodesCounter = 0;
			treeNodes[nodesCounter] = root;
			nodesCounter ++;
			
			for(int i = 0; i < text.length(); i ++)
			{
				boolean rule3 = false;
				Node lastInternalNode = null;
				
				for(int j = firstSuffixToCompute; j <= i; j ++)
				{
					Node newInternalNode = null;
					int numberOfTraversedCharacters = currentNode.pathLabelLength;
					
					while(true)
					{
						char nextCharacter = text.charAt(j + numberOfTraversedCharacters);
						
						/*
						 * If the current node has not an edge which starts with 'nextCharacter'
						 * we just have to add it.
						 * 
						 * PAY ATTENTION: 'j + numberOfTraversedCharacters == j + currentSuffixLength - 1' 
						 * is ALWAYS true IN FIRST 2 METHODS (in the rest of the loop it is not).
						 */
						
						if(nextCharacter == '$')
						{
							currentNode.additionalEdge = j + numberOfTraversedCharacters;
							firstSuffixToCompute ++;
							
							break;
						}
						
						if(currentNode.edges[nextCharacter - 'a'] == null)
						{
							currentNode.edges[nextCharacter - 'a'] = new Edge(j + numberOfTraversedCharacters, text.length() - 1); // because it is a new leaf
							
							break;
						}
						
						int currentSuffixLength = i - j + 1;
						int edgeLength = currentNode.edges[nextCharacter - 'a'].last - currentNode.edges[nextCharacter - 'a'].first + 1;
						
						if(edgeLength < currentSuffixLength - numberOfTraversedCharacters)
						{
							numberOfTraversedCharacters += edgeLength;
							currentNode = currentNode.nodes[nextCharacter - 'a'];
							
							continue;
						}
						
						/*
						 * Checking for rule3.
						 */
						
						char edgeCharacter = text.charAt(currentNode.edges[nextCharacter - 'a'].first + (currentSuffixLength - numberOfTraversedCharacters) - 1);
						char pathCharacter = text.charAt(j + currentSuffixLength - 1);
						
						rule3 = edgeCharacter == pathCharacter;
						
						if(rule3)
							break;
						
						/*
						 * New internal node.
						 */
						
						newInternalNode = new Node(currentSuffixLength - 1, nodesCounter);
						
						treeNodes[nodesCounter] = newInternalNode;
						nodesCounter ++;
						
						newInternalNode.edges[edgeCharacter - 'a'] = new Edge(currentNode.edges[nextCharacter - 'a'].first + (currentSuffixLength - numberOfTraversedCharacters) - 1, currentNode.edges[nextCharacter - 'a'].last);
						newInternalNode.nodes[edgeCharacter - 'a'] = currentNode.nodes[nextCharacter - 'a'];
						
						if(currentNode.nodes[nextCharacter - 'a'] != null)
							currentNode.nodes[nextCharacter - 'a'].father = newInternalNode;
						
						currentNode.edges[nextCharacter - 'a'].last = currentNode.edges[nextCharacter - 'a'].first + (currentSuffixLength - numberOfTraversedCharacters) - 2; // because this is not a leaf anymore
						currentNode.nodes[nextCharacter - 'a'] = newInternalNode;
						
						newInternalNode.father = currentNode;
						
						if(pathCharacter == '$')
							newInternalNode.additionalEdge = j + currentSuffixLength - 1;
						else
							newInternalNode.edges[pathCharacter - 'a'] = new Edge(j + currentSuffixLength - 1, text.length() - 1); // because this is a new leaf
					
						break;
					}
					
					/*
					 * Suffix link assignment.
				     */
							
					if(lastInternalNode != null)
					{
						lastInternalNode.suffixLink = newInternalNode == null ? currentNode : newInternalNode;
						lastInternalNode = null;
					}
							
					/*
					 * If the path label of the new internal node is long just 1 character 
					 * we already know its suffix link and it is the root.
					 */
					
					if(newInternalNode != null)
					{
						if(newInternalNode.pathLabelLength == 1)
							newInternalNode.suffixLink = root;
						else
							lastInternalNode = newInternalNode;
					}
					
					if(rule3)
						break;
					
					/*
					 * If not it means that has been added a leaf, so we have to decrease the depth (of AT MOST 2).
					 */
 
					if(currentNode.suffixLink != null)
						currentNode = currentNode.suffixLink;
					else
						currentNode = currentNode.father.suffixLink;
					
					firstSuffixToCompute ++;
				}
			}
		}
		
		public void fixDepth()
		{
			Stack <Node> nodes = new Stack <Node> ();
			Stack <Integer> nextCharacter = new Stack <Integer> ();
			
			root.depth = 0;
			
			/*
			 * We will assume that the father of the root is itself.
			 */
			
			ancestors[root.position][0] = 0;
			
			nodes.push(root);
			nextCharacter.push(0);
			
			while(!nodes.isEmpty())
			{
				Node currentNode = nodes.pop();
				int next = nextCharacter.pop();
				
				int i = next;
				for(; i <= 'z' - 'a'; i ++)
					if(currentNode.nodes[i] != null)
					{
						currentNode.nodes[i].depth = currentNode.depth + 1;
						ancestors[currentNode.nodes[i].position][0] = currentNode.position;
						
						nodes.push(currentNode);
						nextCharacter.push(i + 1);
						nodes.push(currentNode.nodes[i]);
						nextCharacter.push(0);
						
						break;
					}
			}
		}
		
		public void fixLeaves()
		{
			Stack <Node> nodes = new Stack <Node> ();
			Stack <Integer> nextCharacter = new Stack <Integer> ();
			
			nodes.push(root);
			nextCharacter.push(0);
			
			while(!nodes.isEmpty())
			{
				Node currentNode = nodes.pop();
				int next = nextCharacter.pop();
				
				int i = next;
				for(; i <= 'z' - 'a'; i ++)
					if(currentNode.nodes[i] != null)
					{
						nodes.push(currentNode);
						nextCharacter.push(i + 1);
						nodes.push(currentNode.nodes[i]);
						nextCharacter.push(0);
						
						break;
					}
				
				if(i > 'z' - 'a')
				{
					/*
					 * This means that all the subtree has been computed
					 */
					
					currentNode.numberOfLeaves = 0;
					
					for(int j = 0; j <= 'z' - 'a'; j ++)
						if(currentNode.nodes[j] != null)
							currentNode.numberOfLeaves += currentNode.nodes[j].numberOfLeaves;
						else if(currentNode.edges[j] != null)
							currentNode.numberOfLeaves ++;
					if(currentNode.additionalEdge != -1)
						currentNode.numberOfLeaves ++;
				}
			}
		}
		
		public int getNumberOfOccurrences(String s)
		{
			Node currentNode = root;
			int i = 0;
			while(currentNode != null)
			{
				int index = s.charAt(i) - 'a';
				if(currentNode.edges[index] == null)
					return 0;
				Node nextNode = currentNode.nodes[index];
				int j = 0;
				for(; j < currentNode.edges[index].last - currentNode.edges[index].first + 1 && i + j < s.length(); j ++)
					if(text.charAt(currentNode.edges[index].first + j) != s.charAt(i + j))
						return 0;
				i += j;
				if(i == s.length())
					return nextNode == null ? 1 : nextNode.numberOfLeaves;
				currentNode = nextNode;
			}
			return 0;
		}
		
		/*
		 * TO IMPLEMENT:
		 * 
		 * getNumberOfOccurrences of a substring of the text (text[i : j])
		 */
		
		public void buildAncestors()
		{
			for(int j = 1; j < ancestors[0].length; j ++)
				for(int i = 0; i < ancestors.length; i ++)
					ancestors[i][j] = ancestors[ancestors[i][j - 1]][j - 1];
		}
		
		public int[] getSuffixArray()
		{
			/*
			 * Because there was not the terminal character '$' in the original text.
			 */
			
			int[] suffixArray = new int[text.length() - 1];
			int c = 0;
 
			Stack <Node> nodes = new Stack <Node> ();
			Stack <Integer> nextCharacter = new Stack <Integer> ();
			
			nodes.push(root);
			nextCharacter.push(0);
			
			while(!nodes.isEmpty())
			{
				Node currentNode = nodes.pop();
				int next = nextCharacter.pop();
				
				/*
				 * We will not include the empty suffix
				 */
				
				if(currentNode.additionalEdge != -1 && next == 0 && currentNode != root)
					suffixArray[c ++] = currentNode.additionalEdge - currentNode.pathLabelLength;
				
				for(int i = next; i <= 'z' - 'a'; i ++)
					if(currentNode.edges[i] != null)
					{
						if(currentNode.edges[i].last == text.length() - 1)
						{
							suffixArray[c ++] = currentNode.edges[i].first - currentNode.pathLabelLength;
						}else
						{
							nodes.push(currentNode);
							nextCharacter.push(i + 1);
							nodes.push(currentNode.nodes[i]);
							nextCharacter.push(0);
							
							break;
						}
					}
			}
			
			return suffixArray;
		}
		/*
		 * The second method is decisively the best. We can't know the complexity but after some test 
		 * i can state that the second runtime is better.
		 */
		
		/*
		private int getCentralNode(int up, int down)
		{
			int l = 0, r = ancestors[0].length - 1, c = r >> 1;
			int desiredDepth = (treeNodes[down].depth + treeNodes[up].depth) >> 1;
			
			while(l < r)
			{
				if(treeNodes[ancestors[down][c]].depth > desiredDepth)
					l = c + 1;
				else if(treeNodes[ancestors[down][c]].depth < desiredDepth)
					r = c - 1;
				else 
					break;
				c = (l + r) >> 1;
			}
			
			return ancestors[down][c];
		}
		*/
		
		private int getCentralNode(int up, int down)
		{
			int difference = (treeNodes[down].depth - treeNodes[up].depth) >> 1;
			if(difference == 0)
				return down;
			return ancestors[down][31 - Integer.numberOfLeadingZeros(difference)];
		}
		
		public Node getLca(Node n1, Node n2)
		{
			if(n1.depth < n2.depth)
			{
				Node support = n1;
				n1 = n2;
				n2 = support;
			}
			
			/*
			 * Pick up n1 to the level of n2
			 */
			
			int up = 0, down = n1.position, centralNode = getCentralNode(up, down);
			
			while(treeNodes[down].depth - treeNodes[up].depth > 1)
			{
				if(treeNodes[centralNode].depth < n2.depth)
					up = centralNode;
				else if(treeNodes[centralNode].depth > n2.depth)
					down = centralNode;
				else
					break;
				centralNode = getCentralNode(up, down);
			}
			
			if(treeNodes[down].depth == n2.depth)
				centralNode = down;
			
			/*
			 * Find lowest common ancestor with binary search
			 */
			
			up = 0;
			int down1 = centralNode, centralNode1 = getCentralNode(up, down1);
			int down2 = n2.position, centralNode2 = getCentralNode(up, down2);
			
			Node ans = treeNodes[up];
			while(treeNodes[down1].depth - treeNodes[up].depth > 1)
			{
				if(centralNode1 == centralNode2)
				{
					up = centralNode1;
					ans = treeNodes[centralNode1];
				}else
				{
					down1 = centralNode1;
					down2 = centralNode2;
				}
				centralNode1 = getCentralNode(up, down1);
				centralNode2 = getCentralNode(up, down2);
			}
			
			if(down1 == down2)
				ans = treeNodes[down1];
			
			return ans;
		}
		
		public int[] getLcpArray()
		{
			int[] lcpArray = new int[text.length() - 1];
			int c = 0;
			
			Node lastNode = null;
			
			Stack <Node> nodes = new Stack <Node> ();
			Stack <Integer> nextCharacter = new Stack <Integer> ();
			
			nodes.push(root);
			nextCharacter.push(0);
			
			while(!nodes.isEmpty())
			{
				Node currentNode = nodes.pop();
				int next = nextCharacter.pop();
				
				if(currentNode.additionalEdge != -1 && next == 0)
				{
					if(lastNode != null)
						lcpArray[c ++] = getLca(lastNode, currentNode).pathLabelLength;
					lastNode = currentNode;
				}
				
				for(int i = next; i <= 'z' - 'a'; i ++)
					if(currentNode.edges[i] != null)
					{
						if(currentNode.edges[i].last == text.length() - 1)
						{
							if(lastNode != null)
								lcpArray[c ++] = getLca(lastNode, currentNode).pathLabelLength;
							lastNode = currentNode;
						}
						else
						{
							nodes.push(currentNode);
							nextCharacter.push(i + 1);
							nodes.push(currentNode.nodes[i]);
							nextCharacter.push(0);
							
							break;
						}
					}
			}
			
			return lcpArray;
		}
		
		public String getLongestCommonSubstring(String s)
		{
			int bestFirst = 0, bestLength = 0;
			int currentFirst = 0, currentLength = 0;
			
			Node currentNode = root;
			
			int firstToCheck = 0;
			
			char firstCharacterOfTheEdge = s.charAt(0);
			
			while(currentFirst + currentLength < s.length())
			{
				boolean ok = true;
				
				if(firstToCheck == 0)
				{
					ok = currentNode.edges[firstCharacterOfTheEdge - 'a'] != null;
				}else
				{
					if(currentNode.edges[firstCharacterOfTheEdge - 'a'].first + firstToCheck > currentNode.edges[firstCharacterOfTheEdge - 'a'].last)
					{
						currentNode = currentNode.nodes[firstCharacterOfTheEdge - 'a'];
						firstCharacterOfTheEdge = s.charAt(currentFirst + currentLength);
						firstToCheck = 0;
						continue;
					}
					
					ok = text.charAt(currentNode.edges[firstCharacterOfTheEdge - 'a'].first + firstToCheck) == s.charAt(currentFirst + currentLength);
				}
				
				if(!ok)
				{
					/*
					 * If s[first : first + length) belongs to test of course also 
					 * s[first + 1 : first + length) do the same.
					 * We can use once again suffix link and skip count trick.
					 */
					
					currentFirst ++;
					if(currentLength > 0)
						currentLength --;
					
					if(currentFirst + currentLength >= s.length())
						break;
					
					currentNode = currentNode.suffixLink;
					
					firstCharacterOfTheEdge = s.charAt(currentFirst + currentNode.pathLabelLength);
					while(currentLength - currentNode.pathLabelLength > 0)
					{
						int edgeLength = currentNode.edges[firstCharacterOfTheEdge - 'a'].last - currentNode.edges[firstCharacterOfTheEdge - 'a'].first + 1;
						if(edgeLength > currentLength - currentNode.pathLabelLength)
							break;
						currentNode = currentNode.nodes[firstCharacterOfTheEdge - 'a'];
						firstCharacterOfTheEdge = s.charAt(currentFirst + currentNode.pathLabelLength);
					}
					
					firstToCheck = currentLength - currentNode.pathLabelLength;
					continue;
				}
				
				firstToCheck ++;
				currentLength ++;
				
				if(currentLength > bestLength)
				{
					bestLength = currentLength;
					bestFirst = currentFirst;
				}
			}
			
			return s.substring(bestFirst, bestFirst + bestLength);
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		int k = Integer.parseInt(input[0]);
		int n = Integer.parseInt(input[1]);
		SuffixTree st = new SuffixTree(br.readLine());
		if(k == 1)
		{
			sop(n);
			return;
		}
		st.fixLeaves();
		int ans = 0;
		for(int i = 0; i < st.treeNodes.length; i ++)
		{
			if(st.treeNodes[i] == null)
				break;
			if(st.treeNodes[i].numberOfLeaves < k)
				continue;
			else if(st.treeNodes[i].pathLabelLength > st.treeNodes[ans].pathLabelLength)
				ans = i;
		}
		sop(st.treeNodes[ans].pathLabelLength);
	}
    
    public static void sop(Object o)
    {
    	System.out.print(o);
    }
}