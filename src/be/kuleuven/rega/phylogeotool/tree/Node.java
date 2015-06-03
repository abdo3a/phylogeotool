package be.kuleuven.rega.phylogeotool.tree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private String label;
	private Node parent;
	private List<Node> children;
	private List<Node> leafs;
	private List<String> leafsAsString;
	private double x = 0.0;
	private double y = 0.0;
	private double theta = 0.0;
	private int size = 0;
	private Color nodeColor;

	public Node() {
		this.children = new ArrayList<Node>();
		this.label = "";
	}

	public Node(String label) {
		this.children = new ArrayList<Node>();
		this.label = label;
	}

	public Node(String label, int id) {
		this.children = new ArrayList<Node>();
		this.label = label;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	// Note that the biggest element has to be in the beginning and the lowest
	// one in the end
	// public List<Node> getChildrenBiggestFirst() {
	// Collections.sort(this.getChildren(), new PhyloClusterSizeComparator());
	// return this.getChildren();
	// }
	public void addChild(Node node) {
		this.children.add(node);
	}
	
	public void removeChild(Node node) {
		this.children.remove(node);
	}

	public void removeChildren() {
		this.children = new ArrayList<Node>();
	}

	public boolean hasChildren() {
		return children.size() != 0;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public List<Node> getLeaves() {
		if (leafs == null) {
			this.leafs = new ArrayList<Node>();
		} else if (leafs.size() > 0) {
			return leafs;
		}
		visitNode(this);
		return leafs;
	}

	public List<String> getLeavesAsString() {
		if(leafsAsString != null) {
			return leafsAsString;
		} else {
			leafsAsString = new ArrayList<String>();
			if (leafs == null) {
				this.getLeaves();
			}
			for(Node node:leafs) {
				leafsAsString.add(node.getLabel());
			}
		}
		return leafsAsString;
	}

	public void visitNode(Node node) {
		if (node.hasChildren() && node.getChildren().get(0) != null) {
			visitNode(node.getChildren().get(0));
		}
		if (node.hasChildren() && node.getChildren().get(1) != null) {
			visitNode(node.getChildren().get(1));
		}
		if (!node.hasChildren()) {
			leafs.add(node);
		}
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setColor(Color color) {
		this.nodeColor = color;
	}

	public Color getColor() {
		return nodeColor;
	}

	public static double polarToEucledianX(Node node) {
		return node.getX() * Math.cos(node.getTheta());
	}

	public static double polarToEucledianY(Node node) {
		return node.getX() * Math.sin(node.getTheta());
	}

	public boolean hasParent() {
		return getParent() != null;
	}
	
	// TODO: Check if it's better to clone the leaves as well.
	public Node clone() {
		Node node = new Node(this.getLabel(), this.getId());
		node.setColor(this.getColor());
		node.setParent(this.getParent());
		for(Node tempNode:this.getChildren()) {
			node.addChild(tempNode);
		}
		node.setSize(this.getSize());
		node.setTheta(this.getTheta());
		node.setX(this.getX());
		node.setY(this.getY());
		return node;
	}
}
