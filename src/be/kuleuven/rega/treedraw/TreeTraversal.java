package be.kuleuven.rega.treedraw;

import java.util.List;

public class TreeTraversal {
	
	public static int y = 0;

	/**
	 * 1.  Print out the root's value, regardless of whether you are at the actual root or 
	 * 	   just the subtree's root.
	 * 2.  Go to the left child node, and then perform a pre-order 
	 * 	   traversal on that left child node's subtree.
	 * 3.  Go to the right child node, and then perform a 
	 * 	   pre-order traversal on that right child node's subtree.
	 * 4.  Do this recursively.
	 * @param simpleRootedTree
	 * @param root
	 */
	public static void preOrder (Node root, List<Edge> edges, Shape shape) {
		if(root == null) return;
		
		// Calculate x || Calculate r
		if(shape == Shape.RECTANGULAR_PHYLOGRAM || shape == Shape.CIRCULAR_PHYLOGRAM) {
			if(root.getParent() != null) {
				for(Edge edge:edges) {
					if(edge.getNode2() == root) {
						root.setX(root.getParent().getX() + edge.getDistance());
						break;
					}
				}
			} else {
				root.setX(0.0);
			}
		} else if(shape == Shape.RECTANGULAR_CLADOGRAM) {
			if(root.getParent() != null) {
				root.setX(nodeLevel(root, 0));
			} else {
				root.setX(0.0);
			}
		} else if(shape == Shape.RADIAL) {
			if(root.getParent() != null) {
				
				for(Edge edge:edges) {
					if(edge.getNode2() == root) {
						root.setX(root.getParent().getX() + edge.getDistance() * Math.cos(root.getTheta()));
						root.setY(root.getParent().getY() + edge.getDistance() * Math.sin(root.getTheta()));
						break;
					}
				}
			} else {
				root.setX(0.0);
				root.setY(0.0);
			}
		}
		
		if(root.getChildren().size() > 0) {
			preOrder(root.getChildren().get(0), edges, shape);
			preOrder(root.getChildren().get(1), edges, shape);
		} else {
			preOrder(null, edges, shape);
		}
	}

	/**
	 * 1.  Traverse the left subtree
	 * 2.  Traverse the right subtree
	 * 3.  Visit the root
	 * @param simpleRootedTree
	 * @param root
	 */
	public static void postOrder (Node root, Shape shape, int nrLeaves) {
		if(root == null) return;
		// Inner node
		if(root.getChildren().size() > 0) {
			postOrder(root.getChildren().get(0), shape, nrLeaves);
			postOrder(root.getChildren().get(1), shape, nrLeaves);
			if(shape == Shape.RECTANGULAR_PHYLOGRAM || shape == Shape.RECTANGULAR_CLADOGRAM) {
				root.setY((root.getChildren().get(0).getY() + root.getChildren().get(1).getY())/2);
			} else if(shape == Shape.CIRCULAR_PHYLOGRAM) {
				root.setTheta((root.getChildren().get(0).getTheta() + root.getChildren().get(1).getTheta())/2);
			} else if(shape == Shape.CIRCULAR_CLADOGRAM || shape == Shape.RADIAL) {
				if(root.getParent() != null) {
					root.setX(nodeLevel(root, 0));
					root.setTheta((root.getChildren().get(0).getTheta() + root.getChildren().get(1).getTheta())/2);
				} else {
					root.setX(0.0);
					root.setY(0.0);
				}
			}
		// Leaf
		} else {
			postOrder(null, shape, nrLeaves);
			if(shape == Shape.RECTANGULAR_PHYLOGRAM || shape == Shape.RECTANGULAR_CLADOGRAM) {
				root.setY(++y);
			} else if(shape == Shape.CIRCULAR_PHYLOGRAM || shape == Shape.RADIAL) {
				root.setTheta(2*Math.PI*((double)++y/nrLeaves));
			} else if(shape == Shape.CIRCULAR_CLADOGRAM) {
				root.setX(5.0);
				root.setTheta(2*Math.PI*((double)++y/nrLeaves));
			}
		}
	}
	
	public static int nodeLevel(Node node, int nodeLevel) {
		if(node.getParent() == null) {
			return nodeLevel;
		} else {
			return nodeLevel(node.getParent(), ++nodeLevel);
		}
	}
}