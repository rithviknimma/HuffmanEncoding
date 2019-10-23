import java.util.ArrayList;

public class HuffmanTree {
	TreeNode root;
	
	// array list for storing chars and binary code of chars
	ArrayList<String> chars = new ArrayList<String>();
	ArrayList<String> charsBinary = new ArrayList<String>();
	
	HuffmanTree() {
		root = null;
	}
	
	public void constructHuffmanTree(ArrayList<String> characters, ArrayList<Integer> freq) {
		ArrayList<TreeNode> trees = new ArrayList<TreeNode>();
		
		// make k new treenodes and set value and freq --> O(k)
		for(int i = 0; i < characters.size(); i++) {
			trees.add(new TreeNode());
			trees.get(i).setValue(characters.get(i));
			trees.get(i).setFrequency(freq.get(i));
		}
		
		// indexes and nodes with lowest frequency
		int node1Index;
		int node2Index;
		TreeNode node1;
		TreeNode node2;
		
		while(trees.size() > 1) {
			node1Index = findMin(trees);
			node1 = trees.get(node1Index);
			trees.remove(node1Index);
			
			node2Index = findMin(trees);
			node2 = trees.get(node2Index);
			trees.remove(node2Index);
			
			// combine minimum nodes and set frequency and children
			trees.add(new TreeNode());
			trees.get(trees.size()-1).setFrequency(node1.getFrequency() + node2.getFrequency());
			trees.get(trees.size()-1).setLeftChild(node1);
			trees.get(trees.size()-1).setRightChild(node2);
		}
		
		root = trees.get(0); // set root of tree
	}
	
	// finds the index of the node with the least frequency
	public int findMin(ArrayList<TreeNode> trees) {
		int min = 0;
		for(int i = 0; i < trees.size(); i++) {
			if(trees.get(i).getFrequency() < trees.get(min).getFrequency()) {
				min = i;
			}
		}
		return min;
	}
	
	// encodes the humanMessage and returns the encoded message
	public String encode(String humanMessage) {
		makeTable(root, ""); // make the char-binary table
		String encodedMessage = "";
		
		String [] message = humanMessage.split("");
		for(String character : message) {
			if(chars.contains(character))
				encodedMessage += charsBinary.get(chars.indexOf(character));
		}
		
		return encodedMessage;
	}
	
	// decodes the encodedMessage and returns the human message
	public String decode(String encodedMessage) {
		makeTable(root, ""); // make the char-binary table
		String decodedMessage = "";
		int binaryIndexStart = 0;
		int binaryIndexEnd = 1;
		
		while(binaryIndexEnd <= encodedMessage.length()) { // runs until the index reaches the end of encoded message
			if(charsBinary.contains(encodedMessage.substring(binaryIndexStart, binaryIndexEnd))) {
				decodedMessage += chars.get(charsBinary.indexOf(encodedMessage.substring(binaryIndexStart, binaryIndexEnd)));
				binaryIndexStart = binaryIndexEnd;
				binaryIndexEnd = binaryIndexStart + 1;
			}
			else if(charsBinary.contains(encodedMessage.substring(binaryIndexStart))){ // if the last 
				decodedMessage += chars.get(charsBinary.indexOf(encodedMessage.substring(binaryIndexStart)));
				binaryIndexEnd = encodedMessage.length() + 1; // finish running as the message has been decoded
			}
			else {
				binaryIndexEnd++;
			}
		}
			
        return decodedMessage;
	}
	
	// creates the char-binary conversion table based on the tree created
	public void makeTable(TreeNode node, String s) {
		if(node == null) 
			return;
		
		// found a leaf, add node value to chars list and chars binary list at corresponding spots
		if(node.getLeftChild() == null && node.getRightChild() == null) {
			chars.add(node.getValue());
			charsBinary.add(s);
			System.out.println(node.getValue()+":"+s);
			return;
		}
		
		if(node.getLeftChild() != null) {
			// add the '0' to the string because it is going left
			s += "0";
			makeTable(node.getLeftChild(), s);
		}
		
		if(node.getRightChild() != null) {
			// remove the '0' before adding the '1' for the right path
			if (s != null && s.length() > 0) {
		        s = s.substring(0, s.length() - 1);
		    }
			s += "1";
			makeTable(node.getRightChild(), s);
		}
	}
}
