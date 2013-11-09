import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public final class GraphIntersection {

        private static ArrayList<Integer> vertices;
        private static ArrayList<TreeSet <Integer>> edges = new ArrayList<TreeSet <Integer>>();
        private static int numVertices = 0;
        private static ArrayList<String> Connections = new ArrayList<String>();
        private static int node1;
        private static int node2;
        private static int numberOfConnections;
        private static TreeSet<Integer> nodes = new TreeSet<Integer>();

        public GraphIntersection(int numVertices) {
                GraphIntersection.numVertices = numVertices;
                vertices = new ArrayList<Integer>();
                for (int i = 0; i < numVertices; i++) {
                        vertices.add(i);
                        TreeSet<Integer> list = new TreeSet<Integer>();
                        edges.add(list);
                }
        }
        
        public static final void addRandomConncetions(int numberOfConnections) {
    		int destination = 0;
        	for (int i = 0; i < numVertices; i++) {
        		TreeSet<Integer> tempEdges = new TreeSet<Integer>();
        		tempEdges = edges.get(i);
        		int size = tempEdges.size();
        		for (int j = 0; j < numberOfConnections-size; j++) {
            		destination = (int)Math.round(Math.random()* (numVertices-1)) ;
        			while(destination == i) {
                		destination = (int)Math.round(Math.random()* (numVertices-1)) ;
        			}
            		tempEdges.add(destination);
            		TreeSet<Integer> destinationEdges = edges.get(destination);
            		destinationEdges.add(i);       	
        		}
        	}        	
        }
        
        public static void totalConnectionsInGraph() {
        	int totalConnections = 0;
        	Iterator<TreeSet<Integer>> edgeIterator = edges.iterator();
        	while (edgeIterator.hasNext()) {
        		TreeSet<Integer> edgeConnections = edgeIterator.next();
        		totalConnections += edgeConnections.size();
        	}
        	System.out.println("Total connections in the graph " + totalConnections);
        	System.out.println("Average outdegree of the unstructured graph " + Math.round(totalConnections/GraphIntersection.numVertices));
        }
        
        public static ArrayList<Integer> getConnections(int node1) {
        	Iterator<Integer> TreeSetIterator = edges.get(node1).iterator();
        	ArrayList<Integer> list = new ArrayList<Integer>();
        	while(TreeSetIterator.hasNext()) {
        		list.add(TreeSetIterator.next());
        	}
        	
        	return list;
        }
        
        public static ArrayList<Integer> getConnectionsN2(int node1) {
        	TreeSet<Integer> connectionN2Set = new TreeSet<Integer>();
        	TreeSet<Integer> intermediateSet = edges.get(node1);
        	ArrayList<Integer> connectionN2List = new ArrayList<Integer>();
        	Iterator<Integer> intermediateSetIterator = intermediateSet.iterator();
        	while (intermediateSetIterator.hasNext()) {
        		int intermediateNode = intermediateSetIterator.next();
        		TreeSet<Integer> n2set = edges.get(intermediateNode);
        		Iterator<Integer> n2SetIterator = n2set.iterator();
        		while (n2SetIterator.hasNext()) {
        			connectionN2List.add(n2SetIterator.next());
        		}
        	}
        	Iterator<Integer> N2SetIterator = connectionN2Set.iterator();
        	while(N2SetIterator.hasNext()) {
        		connectionN2List.add(N2SetIterator.next());
        	}
        	return connectionN2List;
        }
        
        public static TreeSet<Integer> mergeSet(TreeSet<Integer> set1, TreeSet<Integer> set2) {
        	Iterator<Integer> set2Iterator = set2.iterator();
        	while (set2Iterator.hasNext()) {
        		set1.add(set2Iterator.next());
        	}        	
        	return null; 
        }
        
        public static void addLevel2Connections(ArrayList<Integer> mutualConnectionSet) {
        	Iterator<Integer> setIterator = mutualConnectionSet.iterator();
        	while(setIterator.hasNext()) {
            	StringBuilder connection = new StringBuilder();
            	connection.append(node1);
            	connection.append(" -> ");
            	connection.append(setIterator.next());
            	connection.append(" -> ");
            	connection.append(node2);
            	Connections.add(connection.toString());        		
        	}
        }
        
        public static void addLevel3Connections(ArrayList<Integer> level3ConnectionList, int node) {
        	Iterator<Integer> level3Iterator = level3ConnectionList.iterator();
        	while(level3Iterator.hasNext()) {
        		int nodeIntermediate = level3Iterator.next();
        		ArrayList<Integer> level1Set = GraphIntersection.intersection(GraphIntersection.getConnections(node), GraphIntersection.getConnections(nodeIntermediate));
        		if (level1Set.size() > 0) {
        			Iterator<Integer> level1SetIterator = level1Set.iterator();
        			while (level1SetIterator.hasNext()) {
        				int nodeLevel1 = level1SetIterator.next();
        				nodes.add(nodeLevel1);
            			StringBuilder connection = new StringBuilder();
            			if (node == node1) {
                			connection.append(node1);            				
            			}
            			else {
            				connection.append(node2);
            			}
                    	connection.append(" -> ");
                    	connection.append(nodeLevel1);
                    	connection.append(" -> ");
                    	connection.append(nodeIntermediate);
                    	connection.append(" -> ");
            			if (node == node1) {
                			connection.append(node2);            				
            			}
            			else {
            				connection.append(node1);
            			} 
                    	Connections.add(connection.toString());
        			}
        		}
        	}
        }
        
        public static void addLevel4Connections(ArrayList<Integer> level4ConnectionList) {
        	Iterator<Integer> level4Iterator = level4ConnectionList.iterator();
        	while (level4Iterator.hasNext()) {
        		int nodeIntermediate = level4Iterator.next();
        		ArrayList<Integer> level1Set = GraphIntersection.intersection(GraphIntersection.getConnections(node1), GraphIntersection.getConnections(nodeIntermediate));
        		if (level1Set.size() > 0) {
        			ArrayList<Integer> level3Set = GraphIntersection.intersection(GraphIntersection.getConnections(node2), GraphIntersection.getConnections(nodeIntermediate));
        			if (level3Set.size() > 0) {
            			Iterator<Integer> level1SetIterator = level1Set.iterator();        	
            			Iterator<Integer> level3SetIterator = level3Set.iterator();
            			while (level1SetIterator.hasNext()) {
            				int nodeLevel1 = level1SetIterator.next();
            				while (level3SetIterator.hasNext()) {
            					int nodeLevel3 = level3SetIterator.next();
            					nodes.add(nodeLevel1);
            					nodes.add(nodeLevel3);
                    			StringBuilder connection = new StringBuilder();
                    			connection.append(node1);
                            	connection.append(" -> ");
                            	connection.append(nodeLevel1);
                            	connection.append(" -> ");
                            	connection.append(nodeIntermediate);
                            	connection.append(" -> ");
                            	connection.append(nodeLevel3); 
                            	connection.append(" -> ");
                            	connection.append(node2); 
                            	Connections.add(connection.toString());            					
            				}
            			}            				
        			}        			
        		}
        	}
        }
        
        public static void printConnections() {
        	Iterator<String> connectionsIterator = Connections.iterator();
        	while (connectionsIterator.hasNext()) {
        		System.out.println(connectionsIterator.next());
        	}
        }
        
        public static ArrayList<Integer> intersection(ArrayList<Integer> connectionsN2node1, ArrayList<Integer> connectionsN2node2) {
        	ArrayList<Integer> intersectionSet = new ArrayList<Integer>();
        	int size = 0;
        	size = connectionsN2node1.size();
        	if (connectionsN2node2.size() < size) {
        		size = connectionsN2node2.size();
        	}
        	int list1Size = connectionsN2node1.size();
        	int list2Size = connectionsN2node2.size();
        	int numberOfThreads = 2;
        	int firstElementList1 = 0;
        	int firstElementList2 = 0;
        	int section = size / numberOfThreads;
        	int list1Index = 0;
        	int list2Index = 0;
        	list2Index = list1Index = firstElementList1 + section;
        	IntersectionThread [] intersection_t = new IntersectionThread [numberOfThreads];
        	Thread [] intersectionThreads = new Thread[numberOfThreads];
        	for (int i = 0; i < numberOfThreads; i++) {
        		System.out.println("list1 Size " + list1Size);
        		System.out.println("list2 Size " + list2Size);
            	list1Index = firstElementList1 + section;
            	if (list1Index >= list1Size)
            		list1Index = list1Size-1;
            	list2Index = firstElementList2 + section;
            	if (list2Index >= list2Size)
            		list2Index = list2Size-1;
            	System.out.println("list1Index" + list1Index);
            	System.out.println("list2Index" + list2Index);
        		int element1 = connectionsN2node1.get(list1Index);
        		int element2 = connectionsN2node2.get(list2Index);
        		System.out.println("element1 " + element1);
        		System.out.println("element2 " + element2);
        		if (element2>element1) {
            		while(element2>element1) {
            			element2 = connectionsN2node2.get(--list2Index);
            		}        			
        		}
        		else if (element2<element1) {
        			while(element2<element1) {
        				element2 = connectionsN2node2.get(++list2Index);
        			}
        		}        		
        	intersection_t[i] = new IntersectionThread(connectionsN2node1, connectionsN2node2, intersectionSet, firstElementList1, firstElementList2, list1Index, list2Index);
        	intersectionThreads[i] = new Thread(intersection_t[i],"intersectionThread");
        	intersectionThreads[i].start();
        	firstElementList1 = list1Index+1;
        	firstElementList2 = list2Index+1;
        	}        	        
//        	
//        	Iterator<Integer> node1Iterator = connectionsN2node1.iterator();
//        	Iterator<Integer> node2Iterator = connectionsN2node2.iterator();
//        	int node1Int = 0;
//        	int node2Int = 0;
//        	if(node1Iterator.hasNext())
//				node1Int = node1Iterator.next();
//        	if(node2Iterator.hasNext())
//				node2Int = node2Iterator.next();
//        	while(node1Iterator.hasNext()) {
//        		if (node1Int < node2Int) {
//        			node1Int = node1Iterator.next();
//        		}
//        		else if (node1Int > node2Int) {
//        			if(node2Iterator.hasNext())
//        				node2Int = node2Iterator.next();
//        			else 
//        				break;
//        		}
//        		else if (node1Int == node2Int) {
//            		intersectionSet.add(node1Int);
//            		node1Int = node1Iterator.next();
//        			if(node2Iterator.hasNext())
//        				node2Int = node2Iterator.next();
//        			else 
//        				break;
//            	}
//        	}
//
//        	while(node2Iterator.hasNext()) {
//        		if (node1Int == node2Int) {
//            		intersectionSet.add(node1Int);
//            		node2Int = node2Iterator.next();
//            	}
//        		else if (node1Int > node2Int) {
//        			node2Int = node2Iterator.next();
//        		}
//        		else if (node1Int < node2Int) {
//        			break;
//        		}
//        	}
//        	if (node1Int == node2Int) {
//        		intersectionSet.add(node1Int);
//        	}
        	return intersectionSet;        	
        }
 
        public static void printIntersectionSet(TreeSet<Integer> intersectionSet) {
        	Iterator<Integer> intersectionSetIterator = intersectionSet.iterator();
        	System.out.println(" Intersection Set = > ");
        	while(intersectionSetIterator.hasNext()) {
        		int node = intersectionSetIterator.next();
        		nodes.add(node);
        		System.out.print(" " + node);
        	}
        	System.out.println();
        	System.out.println();
        }
        
        public static void printNeededNodes() {
        	Iterator<Integer> nodesIterator = nodes.iterator();
        	while(nodesIterator.hasNext()) {
        		TreeSet<Integer> list = new TreeSet<Integer>();
        		int node = nodesIterator.next();
        		list = edges.get(node);
        		System.out.print(node + " : \t");
        		Iterator<Integer> listIterator = list.iterator();        		
        		while(listIterator.hasNext()) {
        			System.out.print(listIterator.next() + " ");
        		}
        		System.out.println();
        	}

        }
        
        public static void printGraph() {
        	for (int i = 0; i < numVertices; i++) {
        		System.out.print(i + "\t");
        		TreeSet<Integer> list = new TreeSet<Integer>();
        		list = edges.get(i);
        		Iterator<Integer> listIterator = list.iterator();        	
        		while(listIterator.hasNext()) {
        			System.out.print(listIterator.next() + " ");
        		}
        		System.out.println();
        	}        	
        }
        
        public static void writeGraphToFile(ArrayList<TreeSet<Integer>> edges, String fileName) {
        	try {
				PrintWriter writer = new PrintWriter(new FileWriter(new File("./" + fileName)));					
	        	for (int i = 0; i < numVertices; i++) {
	        		writer.print(i + ":");
	        		TreeSet<Integer> list = new TreeSet<Integer>();
	        		list = edges.get(i);
	        		Iterator<Integer> listIterator = list.iterator();        	
	        		while(listIterator.hasNext()) {
	        			writer.print(listIterator.next() + ",");
	        		}
	        		writer.println();
	        	}        	
	        	writer.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}			
        }
        
        public static void loadGraphFromFile(String fileName) {
        	try {        	
//        		for (int j = 1; j < 25; j++) {
//                	Graph graph = new Graph(Graph.numVertices);
//                	Graph.addRandomConncetions(Graph.numberOfConnections);

    				BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
	

//    				ArrayList<TreeSet<Integer>> edgesFromFile = new ArrayList<TreeSet<Integer>>();
    				String line;
//    				PrintWriter writer = new PrintWriter(new FileWriter(new File("./" + fileName)));					
    	        	for (int i = 0; i < numVertices; i++) {
//    	        		writer.print(i + ":");
    	        		TreeSet<Integer> list = new TreeSet<Integer>();
    	        		if ((line = reader.readLine())!=null) {
    	        			StringTokenizer tokenizeLine = new StringTokenizer(line,":");
    	        			tokenizeLine.nextToken();
    	        			String elements = tokenizeLine.nextToken();
    	        			StringTokenizer tokenizeNodes = new StringTokenizer(elements,",");
    	        			while(tokenizeNodes.hasMoreTokens()) {
    	        				list.add(Integer.parseInt(tokenizeNodes.nextToken()));	        				
    	        			}
    	        		}
//    	        		list = mergeTreeSet(edges.get(i), list);
//    	        		Iterator<Integer> listIterator = list.iterator();        	
//    	        		while(listIterator.hasNext()) {
//    	        			writer.print(listIterator.next() + ",");
//    	        		}
//    	        		writer.println();
//    	        		writer.flush();
    	        		edges.add(list);
    	        	}        		
//    	        	Graph.writeGraphToFile(edgesFromFile, "fixedGraph.txt");
//        		}
    		} catch (FileNotFoundException e) {
    				e.printStackTrace();
    		}
    		catch (IOException e) {
    				e.printStackTrace();
    		}			
        }
         
        public static TreeSet<Integer> mergeTreeSet(TreeSet<Integer> set1, TreeSet<Integer> set2) {
        	TreeSet<Integer> mergedSet = set1;
        	Iterator<Integer> set2Iterator = set2.iterator();
        	while(set2Iterator.hasNext()) {
        		mergedSet.add(set2Iterator.next());
        	}
        	return mergedSet;
        }
        
        public static void main(String [] args) {
        	GraphIntersection.numVertices = Integer.parseInt(args[0]);
        	GraphIntersection.numberOfConnections = Integer.parseInt(args[1]);
        	GraphIntersection.node1 = (int)Math.round(Math.random()*(GraphIntersection.numVertices-1));
        	GraphIntersection.node2 = (int)Math.round(Math.random()*(GraphIntersection.numVertices-1));
        	System.out.println("node1 " + node1);
        	System.out.println("node2 " + node2);
        	GraphIntersection graph = new GraphIntersection(GraphIntersection.numVertices);
        	GraphIntersection.addRandomConncetions(numberOfConnections);
        	GraphIntersection.printGraph();
//        	Graph.writeGraphToFile(edges, args[2]);
        	
        	
//        	GraphIntersection.loadGraphFromFile(args[2]);
        	GraphIntersection.totalConnectionsInGraph();
        	System.out.println(); 
        	System.out.println("Direct Friend N1 " + edges.get(node1).contains(node2));
        	Long level2StartTime = System.currentTimeMillis();
        	ArrayList<Integer> intersectionSetN2 = GraphIntersection.intersection(GraphIntersection.getConnections(node1), GraphIntersection.getConnections(node2));
        	Long level2EndTime = System.currentTimeMillis();
        	System.out.println("----------------------------------------------");
       	    System.out.println("Mutual Connections  = " + intersectionSetN2.size());
       	    
       	    
       	    GraphIntersection.addLevel2Connections(intersectionSetN2);
       	    Long level2Time = (level2EndTime - level2StartTime);
       	    System.out.println("Time required to find Level 2 connections " + level2Time + " ms.");
        	System.out.println("----------------------------------------------");
        	Long N2StartTime = System.currentTimeMillis();
        	ArrayList<Integer> node1N2Connections = GraphIntersection.getConnectionsN2(node1);
        	ArrayList<Integer> node2N2Connections = GraphIntersection.getConnectionsN2(node2);
        	Long N2EndTime = System.currentTimeMillis();
       	    System.out.println("Time required to find N^2 connections " + (N2EndTime - N2StartTime) + " ms.");
        	Long level3StartTime = System.currentTimeMillis();
        	ArrayList<Integer> intersectionSetN3 = GraphIntersection.intersection(node1N2Connections, GraphIntersection.getConnections(node2));
        	ArrayList<Integer> intersectionSetOtherSideN3 = GraphIntersection.intersection(GraphIntersection.getConnections(node1), node2N2Connections);
        	Long  level3EndTime = System.currentTimeMillis();
        	Long level3Time = level3EndTime - level3StartTime;
       	    System.out.println("Time required to find Level 3 connections " + level3Time + " ms.");
        	GraphIntersection.addLevel3Connections(intersectionSetN3, node1);
        	GraphIntersection.addLevel3Connections(intersectionSetOtherSideN3, node2);
        	System.out.println("Level 3 Mutual Connections = " + (intersectionSetN3.size() + intersectionSetOtherSideN3.size()));
        	System.out.println("----------------------------------------------");
        	Long level4StartTime = System.currentTimeMillis();
        	ArrayList<Integer> intersectionSetN4 = GraphIntersection.intersection(node1N2Connections, node2N2Connections);
        	Long level4EndTime = System.currentTimeMillis();
        	System.out.println("Level 4 Mutual Connections " + intersectionSetN4.size());
        	Long level4Time = level4EndTime - level4StartTime;
       	    System.out.println("Time required to find Level 4 connections " + level4Time + " ms.");
        	GraphIntersection.addLevel4Connections(intersectionSetN4);
        	System.out.println();
        	System.out.println("----------------------------------------------");
        	GraphIntersection.printConnections();
        	System.out.println("----------------------------------------------");
        	System.out.println("Total time for all connections " + (level2Time + level3Time + level4Time) + "ms.");
        	
//        	Graph.printNeededNodes();
        }        
}
