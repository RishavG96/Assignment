import java.util.*;
import java.lang.*;
public class Assignment3{
	public static ArrayList<Node> nodes=new ArrayList<Node>();
	public static Node getID(String s){
		Node n;
		Iterator<Node> iterator=nodes.iterator();
		while(iterator.hasNext())
		{
			Node x=iterator.next();
			if(x.nodeName.equalsIgnoreCase(s))
				return x;
		}
		return null;
	}
	public static void main(String args[])
	{
		Tree ob=new Tree();
		Node a=Tree.createNode(0,"A");
		Node b=Tree.createNode(1,"B");
		Node c=Tree.createNode(2,"C");
		Node d=Tree.createNode(3,"D");
		
		nodes.add(a);
		nodes.add(b);
		nodes.add(c);
		nodes.add(d);
		
		a.addChildNode(b);
        a.addChildNode(c);
		b.addParentNode(a);
		b.addChildNode(d);
		c.addParentNode(a);
		c.addChildNode(d);
		d.addParentNode(c);
		d.addParentNode(b);
		System.out.println("Initial connection:\nA-->B;A-->C;B-->D;C-->D");
		while(true)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("1. Get immediate parent node\n2. Get immediate children nodes\n3. Get ancestors of a node\n4. Get descendents of a node\n5. Delete dependency from a tree\n6. Delete a node from the tree\n7. Add new dependency\n8. Add new Node\n9. Exit");
			int choice=sc.nextInt();
			sc.nextLine();
			switch(choice){
				case 1: 
					System.out.println("Enter node name: ");
					String s=sc.nextLine();
					ob.showParents(getID(s));
					break;
				case 2: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					ob.showChildren(getID(s));
					break;
				case 3: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					ob.showAncestors(getID(s));
					break;
				case 4: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					ob.showDescendants(getID(s));
					break;
				case 5: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					System.out.println("Enter child node name: ");
					String s1=sc.nextLine();
					ob.deleteDependency(getID(s), getID(s1));
					break;
				case 6: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					ob.deleteNode(getID(s));
					break;
				case 7: 
					System.out.println("Enter node name: ");
					s=sc.nextLine();
					System.out.println("Enter child node name: ");
					s1=sc.nextLine();
					ob.addDependency(getID(s), getID(s1));
					break;
				case 8:
					System.out.println("Enter Node ID: ");
					int id=sc.nextInt();
					System.out.println("Enter Node Name: ");
					sc.nextLine();
					s=sc.nextLine();
					Node n=Tree.createNode(id,s);
					nodes.add(n);
					break;
				case 9:
					System.exit(0); 
			}
		}
	}
}