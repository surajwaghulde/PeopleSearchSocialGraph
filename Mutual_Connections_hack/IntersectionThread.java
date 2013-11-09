import java.util.ArrayList;

import java.util.Iterator;


public class IntersectionThread implements Runnable {
	
	ArrayList<Integer> set1;
	ArrayList<Integer> set2;
	ArrayList<Integer> intersectionSet;
	int firstElementList1;
	int firstElementList2;
	int list1Index;
	int list2Index;
	
	public IntersectionThread(ArrayList<Integer> set1, ArrayList<Integer> set2, ArrayList<Integer> intersectionSet, int firstElementList1, int firstElementList2, int list1Index, int list2Index) {		
		this.set1 = set1;
		this.set2 = set2;
		this.intersectionSet = intersectionSet;
		this.firstElementList1 = firstElementList1;
		this.firstElementList2 = firstElementList2;
		this.list1Index	= list1Index;
		this.list2Index = list2Index;
	}
	
	public void run () {	
    	int node1 = 0;
    	int node2 = 0;
    	node1 = set1.get(firstElementList1++);
    	node2 = set2.get(firstElementList2++);
    	
    	System.out.println("inside intersection thread ");
     	while(firstElementList1 < list1Index) { 
    		if (node1 < node2) {
    	    	node1 = set1.get(firstElementList1++);
    		}
    		else if (node1 > node2) {
    			if(firstElementList2 < list2Index)
    		    	node2 = set2.get(firstElementList2++);
    			else 
    				break;
    		}
    		else if (node1 == node2) {
        		synchronized (intersectionSet) {
        			System.out.println("Adding " + node1);
            		intersectionSet.add(node1);					
				}
    	    	node1 = set1.get(firstElementList1++);
    			if(firstElementList2 < list2Index)
    		    	node2 = set2.get(firstElementList2++);
    			else 
    				break;
        	}
    	}

    	while(firstElementList2 < list2Index) {
    		if (node1 == node2) {
    			synchronized (intersectionSet) {
    				System.out.println("Adding " + node1);
            		intersectionSet.add(node1);
				}
		    	node2 = set2.get(firstElementList2++);
        	}
    		else if (node1 > node2) {
		    	node2 = set2.get(firstElementList2++);
    		}
    		else if (node1 < node2) {
    			break;
    		}
    	}
    	if (node1 == node2) {
    		synchronized (intersectionSet) {
    			System.out.println("Adding " + node1);
        		intersectionSet.add(node1);				
			}
    	}

		
	}
	
}
