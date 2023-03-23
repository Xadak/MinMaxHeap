class CoolHeap {

	private static class Node {
		private final int key;
        public Node mirror; 
        public int location; 

		public Node(int key) {this.key = key;}  

		public int getKey() {return key;}
	}

	private final Node[] maxHeap;	
	private final Node[] minHeap;	
	private int currentSize;	
	private final int mx;	

    private int maxCurrentSize; 
    private int minCurrentSize; 

    public CoolHeap(int mx) {
		maxHeap = new Node[mx];
		minHeap = new Node[mx];
		currentSize = 0;
		maxCurrentSize = 0;
		minCurrentSize = 0;
		this.mx = mx;
	}
	
	public Integer insertMax(int key) {
	    if (isFull()) {
	    	if (minHeap[0].getKey() < key) {
	    	    int min = removeMin();

	    	    Node nodeInMaxHeap = insertInMaxHeap(key);
				Node nodeInMinHeap = insertInMinHeap(key);

				nodeInMaxHeap.mirror = nodeInMinHeap;
				nodeInMinHeap.mirror = nodeInMaxHeap;

				currentSize++;  
				return min; 
            }
	        else return key;    
        }

	    Node nodeInMaxHeap = insertInMaxHeap(key);
        Node nodeInMinHeap = insertInMinHeap(key);

        nodeInMaxHeap.mirror = nodeInMinHeap;
        nodeInMinHeap.mirror = nodeInMaxHeap;

        currentSize++; 
	    return null;
	}

	public Integer insertMin(int key) {
	    if (isFull()) {
			if (maxHeap[0].getKey() > key) {
				int max = removeMax();

				Node nodeInMaxHeap = insertInMaxHeap(key);
                Node nodeInMinHeap = insertInMinHeap(key);

                nodeInMaxHeap.mirror = nodeInMinHeap;
                nodeInMinHeap.mirror = nodeInMaxHeap;

                currentSize++;  
				return max;     
			}
			else return key;
		}

	    Node nodeInMaxHeap = insertInMaxHeap(key);
        Node nodeInMinHeap = insertInMinHeap(key);

        nodeInMaxHeap.mirror = nodeInMinHeap;
        nodeInMinHeap.mirror = nodeInMaxHeap;
		currentSize++;  
		return null;
	}

	public Integer removeMax() {
        if (isEmpty()) return null;
		currentSize--;  

        Node maxInMinHeap = removeMaxFromMaxHeap().mirror;
		removeMaxFromMinHeap(maxInMinHeap.location);

		return maxInMinHeap.getKey();   
	}  

	public Integer removeMin() {
		if (isEmpty()) return null;
		currentSize--;  

        Node minInMaxHeap = removeMinFromMinHeap().mirror;
		removeMinFromMaxHeap(minInMaxHeap.location);

		return minInMaxHeap.getKey();
	}

	private boolean isEmpty() {return maxHeap[0] == null;}

	private boolean isFull() {return currentSize == mx;}

	private Node insertInMaxHeap(int key) {
	    Node newNode = new Node(key);
	    maxHeap[maxCurrentSize] = newNode;  
	    return trickleUpMax(maxCurrentSize++); 
    }

    private Node insertInMinHeap(int key) {
        Node newNode = new Node(key);
	    minHeap[minCurrentSize] = newNode; 
        return trickleUpMin(minCurrentSize++);
    }

    private Node removeMaxFromMaxHeap() {
	    Node root = maxHeap[0];
	    maxHeap[0] = maxHeap[--maxCurrentSize]; 
	    maxHeap[0].location = 0;
	    trickleDownMax(0);  
	    return root;
    }

    private Node removeMinFromMinHeap() {
        Node root = minHeap[0];
        minHeap[0] = minHeap[--minCurrentSize];
        minHeap[0].location = 0;
        trickleDownMin(0);
        return root;
    }

    private void removeMinFromMaxHeap(int location) {
		maxHeap[location] = maxHeap[--maxCurrentSize]; 
		trickleUpMax(location);	
	}

	private void removeMaxFromMinHeap(int location) {
		minHeap[location] = minHeap[--minCurrentSize];
		trickleUpMin(location);
	}

	private Node trickleUpMax(int index) {
		int parent = (index - 1) / 2;
		Node bottom = maxHeap[index];

		while (index > 0 && maxHeap[parent].getKey() < bottom.getKey()) {
			maxHeap[index] = maxHeap[parent];
            maxHeap[index].location = index;
			index = parent;
			parent = (parent -1) / 2;
		}
		maxHeap[index] = bottom;
        maxHeap[index].location = index;    
        return maxHeap[index];
	}

	private Node trickleUpMin(int index) {
		int parent = (index - 1) / 2;
		Node bottom = minHeap[index];

		while (index > 0 && minHeap[parent].getKey() > bottom.getKey()) {
			minHeap[index] = minHeap[parent];
            minHeap[index].location = index;
			index = parent;
			parent = (parent -1) / 2;
		}
		minHeap[index] = bottom;
        minHeap[index].location = index;    
        return minHeap[index];
	}

	private void trickleDownMax(int index) {
		int largerChild;
		Node top = maxHeap[index];
		while (index < maxCurrentSize / 2) {
			int leftChild = 2*index + 1;
			int rightChild = leftChild + 1;
			if (rightChild < maxCurrentSize &&
					maxHeap[leftChild].getKey() < maxHeap[rightChild].getKey()) largerChild = rightChild;
			else largerChild = leftChild;
			if (top.getKey() > maxHeap[largerChild].getKey()) break;
			maxHeap[index] = maxHeap[largerChild]; 
			maxHeap[index].location = index;    
			index = largerChild;
		}
		maxHeap[index] = top;
        maxHeap[index].location = index;
	}

	private void trickleDownMin(int index) {
		int largerChild;
		Node top = minHeap[index];
		while (index < minCurrentSize / 2) {
			int leftChild = 2*index + 1;
			int rightChild = leftChild + 1;
			if (rightChild < minCurrentSize &&
					minHeap[leftChild].getKey() > minHeap[rightChild].getKey()) largerChild = rightChild;
			else largerChild = leftChild;
			if (top.getKey() < minHeap[largerChild].getKey()) break;
			minHeap[index] = minHeap[largerChild]; 
            minHeap[index].location = index;
			index = largerChild;
		}
		minHeap[index] = top;
        minHeap[index].location = index;
	}
}  

