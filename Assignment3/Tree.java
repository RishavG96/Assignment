import java.util.*;
public class Tree{
	ArrayList<Node> nodes=new ArrayList<Node>();

	public static Node createNode(int id, String name){
		Node n = new Node();
        n.setNodeId(id);
        n.setNodeName(name);
        return n;
	}
	public void showParents(Node n){
		ArrayList<Node> parents=n.parentNode;
		Iterator<Node> iterator=parents.iterator();
		System.out.println("The parents are");
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().nodeName);
		}
	}
	public void showChildren(Node n){
		ArrayList<Node> children=n.childNode;
		Iterator<Node> iterator=children.iterator();
		System.out.println("The children are");
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().nodeName);
		}
	}
	public void showAncestors(Node n){
		Stack<Node> stack=new Stack<>();
        ArrayList<Node> parents=n.parentNode;
        ArrayList<String> ancestors=new ArrayList<String>();
        Iterator<Node> iterator=parents.iterator();
        while(iterator.hasNext())  
        {
            Node flag=iterator.next();
            stack.add(flag);
        }
        while(!stack.isEmpty())
        {
            Node temp=stack.pop();
            iterator=temp.parentNode.iterator();
            while(iterator.hasNext())  
            {
                Node x=iterator.next();
                try{
                    if(!stack.contains(x))
                    {
                        stack.add(x);
                    }
                }
                catch(Exception e){}
            }
			if(!ancestors.contains(temp.nodeName))
				ancestors.add(temp.nodeName);
        }
		System.out.println("The ancestors are:");
        System.out.println(ancestors);
	}
	public void showDescendants(Node n)
	{
		Stack<Node> stack=new Stack<>();
        ArrayList<Node> children=n.childNode;
        ArrayList<String> descendants=new ArrayList<String>();
        Iterator<Node> iterator=children.iterator();
        while(iterator.hasNext())  
        {
            Node flag=iterator.next();
            stack.add(flag);
        }
        while(!stack.isEmpty())
        {
            Node temp=stack.pop();
            iterator=temp.childNode.iterator();
            while(iterator.hasNext())  
            {
                Node x=iterator.next();
                try{
                    if(!stack.contains(x))
                    {
                        stack.add(x);
                    }
                }
                catch(Exception e){}
            }
			if(!descendants.contains(temp.nodeName))
				descendants.add(temp.nodeName);
        }
		System.out.println("The descendants are:");
        System.out.println(descendants);
	}
	public void deleteDependency(Node n,Node cn){
		if(n.childNode.contains(cn))
		{
			int index=n.childNode.indexOf(cn);
			n.childNode.remove(index);
		}
		else{
			System.out.println("No relation between them");
		}
		if(cn.parentNode.contains(n))
		{
			int index=cn.parentNode.indexOf(n);
			cn.parentNode.remove(index);
		}
		else{
			System.out.println("No relation between them");
		}
	}
	public void deleteNode(Node n)
	{
		System.out.println("Deleting node:");
		Iterator<Node> iterator=n.childNode.iterator();
		while(iterator.hasNext())
		{
			ArrayList temp=iterator.next().parentNode;
			if(temp.contains(n)){
				int index=temp.indexOf(n);
				temp.remove(index);
			}
		}
		iterator=n.parentNode.iterator();
		while(iterator.hasNext())
		{
			ArrayList temp=iterator.next().childNode;
			if(temp.contains(n)){
				int index=temp.indexOf(n);
				temp.remove(n);
			}
		}
		n.childNode=null;
		n.parentNode=null;
	}
	public void addDependency(Node n, Node cn)
	{
		Stack<Node> stack=new Stack<>();
        ArrayList<Node> parents=n.parentNode;
        ArrayList<String> ancestors=new ArrayList<String>();
        Iterator<Node> iterator=parents.iterator();
        while(iterator.hasNext())  
        {
            Node flag=iterator.next();
            stack.add(flag);
        }
        while(!stack.isEmpty())
        {
            Node temp=stack.pop();
            iterator=temp.parentNode.iterator();
            while(iterator.hasNext())  
            {
                Node x=iterator.next();
                try{
                    if(!stack.contains(x))
                    {
                        stack.add(x);
                    }
                }
                catch(Exception e){}
            }
			if(!ancestors.contains(temp.nodeName))
				ancestors.add(temp.nodeName);
        }
		System.out.println(ancestors);
		if(!ancestors.contains(cn.nodeName))
		{
			n.childNode.add(cn);
			cn.parentNode.add(n);
			System.out.println("Dependency added!");
		}
		else{
			System.out.println("Cyclic Dependency not allowed!");
		}
	}
}