import java.util.*;
public class Node{
	int nodeId;
	String nodeName;
	ArrayList<Node> parentNode;
	ArrayList<Node> childNode;
	Node()
	{
		nodeId=0;
		nodeName=null;
		parentNode=new ArrayList<Node>();
		childNode=new ArrayList<Node>();
	}
	public int getNodeId()
	{
		return nodeId;
	}
	public String getNodeName()
	{
		return nodeName;
	}
	public void setNodeId(int id)
	{
		nodeId=id;
	}
	public void setNodeName(String name)
	{
		nodeName=name;
	}
	void addParentNode(Node parent)
	{
		parentNode.add(parent);
	}
	void addChildNode(Node children)
	{
		childNode.add(children);
	}
}