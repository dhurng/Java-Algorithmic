/**
 * @author David Hurng 
 * CS146 Program Assignment #2
 */

public class TwoThreeTree {

	private Node root;
	public TwoThreeTree() {
		root = new Node();
	}

	/*
	 * Inserts x into the tree
	 * @param the key to be inserted
	 * @return true if the key is inserted and false if duplicate
	 */
	public boolean insert(int x) {
		Node currentNode = root;
		boolean done = true;
		while(!currentNode.isLeaf() && done){
			if(x == currentNode.getValue(0) || x == currentNode.getValue(1)) {
				return false;
			}
			currentNode = getNextChild(currentNode, x); //move down because just 2 keys
		}
		if(currentNode.isLeaf()){
			if(currentNode.isFull()){
				if(currentNode.insertKey(x) == false) {
					return false;
				}
				else{
					split(currentNode);
					return true;
				}
			}
			//return currentNode.inputKey(x);
		}
		return currentNode.insertKey(x);
	}
	/*
	 * Search for key in the tree
	 * @param key to search for
	 * @Return the the keys in String format
	 */
	public String search(int key) {
		Node currentNode = root;
		while (!currentNode.isLeaf()) {
			if (key < currentNode.getValue(0))
				currentNode = currentNode.getChild(0);
			else if (key == currentNode.getValue(0))
				return printFormat(currentNode);
			else if (currentNode.getNumKeys() == 1) {
				currentNode = currentNode.getChild(1);
			}
			else if(key < currentNode.getValue(1)) {
				currentNode = currentNode.getChild(1);
			}
			else if (key == currentNode.getValue(1))
				return printFormat(currentNode);
			else if (key > currentNode.getValue(1))
				currentNode = currentNode.getChild(2);
		}
		return printFormat(currentNode);
	}

	public void split(Node currentNode) {
		int a;
		int b;
		int c;
		Node parent;
		Node leftChild;
		Node rightChild;
		
		a = currentNode.removeKey();
		b = currentNode.removeKey();
		c = currentNode.removeKey();
		boolean done = true;
		if(currentNode.getNumChildren() < 4 && currentNode == root && done){
			root = new Node();
			parent = root;
			parent.cutChild(0);
			root.insertKey(b);
			
			leftChild = new Node();
			rightChild = new Node();
			
			leftChild.insertKey(c);
			rightChild.insertKey(a);
			root.connectChild(0, leftChild);
			root.connectChild(1, rightChild);
			done = false;
		}
		else if(currentNode.getNumChildren() == 4 && currentNode == root){
			root = new Node();
			parent = root;
			
			rightChild = new Node();
			root.insertKey(b);
			currentNode.insertKey(c);
			rightChild.insertKey(a);
			root.connectChild(0, currentNode);
			root.connectChild(1, rightChild);
			rightChild.connectChild(0, currentNode.cutChild(2));
			rightChild.connectChild(1, currentNode.cutChild(3));
		}
		else{
			parent = currentNode.getParent();
			parent.insertKey(b);
			currentNode.insertKey(c);
			
			rightChild = new Node();
			rightChild.insertKey(a);
			
			if(currentNode == parent.getChild(0) && parent.getNumChildren() == 1){
				parent.connectChild(1, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
			else if(currentNode == parent.getChild(0) && parent.getNumChildren() == 2){
				parent.connectChild(2, parent.cutChild(1));
				parent.connectChild(1, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
			
			else if(currentNode == parent.getChild(0) && parent.getNumChildren() == 3){
				parent.connectChild(3, parent.cutChild(2));
				parent.connectChild(2, parent.cutChild(1));
				parent.connectChild(1, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
			
			else if(currentNode == parent.getChild(1) && parent.getNumChildren() == 2){
				parent.connectChild(2, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
			else if(currentNode == parent.getChild(1) && parent.getNumChildren() == 3){
				parent.connectChild(3, parent.cutChild(2));
				parent.connectChild(2, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
			else if(currentNode == parent.getChild(2) && parent.getNumChildren() == 3){
				parent.connectChild(3, rightChild);
				if(currentNode.getNumChildren() == 4){
					rightChild.connectChild(0, currentNode.cutChild(2));
					rightChild.connectChild(1, currentNode.cutChild(3));
				}
			}
		}
		if(parent.getNumChildren() == 4){
			split(parent);
		}		
	}

	/*
	 * Retrieves the child node that is after the current
	 */
	public Node getNextChild(Node curr, int key) {
		int i;
		for (i = 0; i < curr.getNumKeys(); i++) {
			if (key < curr.getValue(i)) {
				return curr.getChild(i);
			}
		}
		return curr.getChild(i);
	}

	/*
	 * Prints the keys inside the node in format
	 */
	public String printFormat(Node a) {
		String results = "";
		for (int i = 0; i < a.getNumKeys(); i++) {
			if (i == 0) {
				results = results + a.getValue(i);
			}
			else {
				results = results + " " + a.getValue(i);
			}
		}
		return results;
	}
}

class Node {
	private static final int MaxNumValues = 4;
	private int numKeys;
	private Node parent;
	private Node children[] = new Node[MaxNumValues];
	private int values[] = new int[MaxNumValues - 1];
	
	
	/*
	 * Inserts the key into Node
	 * 
	 * @return true if the key is inserted. False if duplicate
	 */	
	public boolean insertKey(int x) {
		if(numKeys == 0){
			values[0] = x;
			numKeys++;
			return true;
		}
		else if(numKeys == 1){
			if(x > values[0]){
				values[1] = x;
				numKeys++;
				return true;
			}
			else if(x < values[0]){
				values[1] = values[0];
				values[0]  = x;
				numKeys++;
				return true;
			}
			else if(x == values[0]) {
				return false;
			}
		}
		else if(numKeys == 2){
			if(x > values[1]){
				values[2] = x;
				numKeys++;
				return true;
			}
			else if(x > values[0] && x < values[1]){
				values[2] = values[1];
				values[1] = x;
				numKeys++;
				return true;
			}
			else if(x < values[0]){
				values[2] = values[1];
				values[1] = values[0];
				values[0] = x;
				numKeys++;
				return true;
			}
			if(x == values[0] || x == values[1]) {
				return false;
			}
		}
		return false;
	}
	
	/*
	 * Gets the parent of the node
	 * 
	 * @Returns the node's parent
	 */
	public Node getParent() {
		return parent;
	}

	/*
	 * Determines whether the node is a leaf or not
	 * 
	 * @Return true if it is a leaf
	 */
	public boolean isLeaf() {
		return (children[0] == null) ? true : false;
	}

	/*
	 * Gets the number of keys inside the node
	 * 
	 * @Return the key
	 */
	public int getNumKeys() {
		return numKeys;
	}

	/*
	 * the value from the index spot
	 * 
	 * @Return the value
	 */
	public int getValue(int index) {
		return values[index];
	}

	/*
	 * cuts the child from a parent
	 * @return the node that was cut
	 */
	public Node cutChild(int x) {
		Node temp = children[x];
		children[x] = null;
		return temp;
	}

	/*
	 * connects a child to a parent
	 */
	public void connectChild(int x, Node a) {
		children[x] = a;
		if (a != null)
			a.parent = this;
	}

	/*
	 * Checks if the specific node is full or not
	 * 
	 * @Return true if it is full
	 */
	public boolean isFull() {
		if(numKeys == MaxNumValues - 2) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * Gets the child from the array of children
	 * 
	 * @Return the node child
	 */
	public Node getChild(int child) {
		return children[child];
	}

	/*
	 * removes a key from a selected Node
	 * 
	 * @return the key that is removed
	 */
	public int removeKey() {
		int temp = values[numKeys - 1];
		values[numKeys - 1] = 0;
		numKeys--;
		return temp;
	}
	
	/*
	 * Gets the number of children 
	 * @return the number of children the node has
	 */
	public int getNumChildren(){
		int temp = 0;
		for(int i = 0; i < MaxNumValues; i++){
			if(children[i] != null && children[i].getValue(0) != 0) temp++;
		}
		return temp;
	}
}


