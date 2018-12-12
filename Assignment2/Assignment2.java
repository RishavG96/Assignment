import java.util.*;
import java.io.*;
public class Assignment2{
	User ob1;
	Assignment2()
	{
		ob1=new User();
		ob1.fullName=new ArrayList<String>();
		ob1.age=new ArrayList<Integer>();
		ob1.address=new ArrayList<String>();
		ob1.rollNo=new ArrayList<Integer>();
		ob1.courses=new ArrayList<ArrayList>();
		
		try
        {
            FileInputStream fis = new FileInputStream("myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ob1.rollNo = (ArrayList) ois.readObject();
			ob1.fullName = (ArrayList) ois.readObject();
			ob1.age = (ArrayList) ois.readObject();
			ob1.address = (ArrayList) ois.readObject();
			ob1.courses = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch(Exception c){
            System.out.println("No previous files created");
        }
	}
	public void addUser()
	{
		Scanner sc=new Scanner(System.in);
		String fn,addr;
		int age, roll;
		ArrayList list;
		while(true){
			System.out.println("Enter Full Name: ");
			fn=sc.nextLine();
			if(fn.equals(""))
			{
				System.out.println("Name should not be blank!");
				continue;
			}
			System.out.println("Enter Age: ");
			try{
				age=sc.nextInt();
			}catch(Exception e){
				System.out.println("Age should be in integers!");
				sc.nextLine();
				continue;
			}
			System.out.println("Enter address: ");
			sc.nextLine();
			addr=sc.nextLine();
			if(addr.equals(""))
			{
				System.out.println("Address should not be blank!");
				sc.nextLine();
				continue;
			}
			System.out.println("Enter Roll Number: ");
			try{
				roll=sc.nextInt();
			}catch(Exception e){
				System.out.println("Roll Number should be in integers!");
				sc.nextLine();
				continue;
			}
			if(ob1.rollNo.contains(roll))
			{
				System.out.println("Roll Number already present!");
				sc.nextLine();
				continue;
			}
			System.out.println("Enter courses(comma seperated)(A,B,C,D,E,F): ");
			sc.nextLine();
			String course=sc.nextLine();
			String[] values = course.split(",");
			if(values.length<4)
			{
				System.out.println("Minimum of 4 courses should be present!");
				continue;
			}
			int flag=0;
			for(int i=0;i<values.length;i++)
			{
				if(!(values[i].equals("A") || values[i].equals("a") || values[i].equals("B") || values[i].equals("b") || values[i].equals("C") || values[i].equals("c") || values[i].equals("D")|| values[i].equals("d")||values[i].equals("E")||values[i].equals("e")||values[i].equals("F")|| values[i].equals("f")))
				{
					flag=1;
					break;
				}
			}
			if(flag==1)
			{
				System.out.println("Enter valid courses!");
				continue;
			}
			list = new ArrayList(Arrays.asList(values));
			break;
		}
		ob1.fullName.add(fn);
		ob1.age.add(age);
		ob1.address.add(addr);
		ob1.rollNo.add(roll);
		ob1.courses.add(list);
		keySort(ob1.rollNo, ob1.rollNo, ob1.fullName, ob1.age, ob1.address, ob1.courses);
	}
	public static <T extends Comparable<T>> void keySort(final List<T> key, List<?>... lists){
		// Create a List of indices
		List<Integer> indices = new ArrayList<Integer>();
		for(int i = 0; i < key.size(); i++)
			indices.add(i);

		// Sort the indices list based on the key
		Collections.sort(indices, new Comparator<Integer>(){
			@Override public int compare(Integer i, Integer j) {
				return key.get(i).compareTo(key.get(j));
			}
		});

		// Create a mapping that allows sorting of the List by N swaps.
		Map<Integer,Integer> swapMap = new HashMap<Integer, Integer>(indices.size());

		// Only swaps can be used b/c we cannot create a new List of type <?>
		for(int i = 0; i < indices.size(); i++){
			int k = indices.get(i);
			while(swapMap.containsKey(k))
				k = swapMap.get(k);
			swapMap.put(i, k);
		}

		// for each list, swap elements to sort according to key list
		for(Map.Entry<Integer, Integer> e : swapMap.entrySet())
			for(List<?> list : lists)
				Collections.swap(list, e.getKey(), e.getValue());
	}
	public void showUser()
	{
		Iterator i=ob1.fullName.iterator();
		Iterator i1=ob1.age.iterator();
		Iterator i2=ob1.address.iterator();
		Iterator i3=ob1.rollNo.iterator();
		Iterator i4=ob1.courses.iterator();
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("Name	Roll Number 	Age		Address		Courses");
		System.out.println("--------------------------------------------------------------------------");
		while(i.hasNext() && i1.hasNext() && i2.hasNext() && i3.hasNext() && i4.hasNext())
		{
			System.out.println(i.next()+"	"+i3.next()+"		"+i1.next()+" 		"+i2.next()+" 		"+i4.next());
		}	
		System.out.println();
	}
	public void deleteUser()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the roll number you wish to delete: ");
		int r=sc.nextInt();
		if(ob1.rollNo.contains(r))
		{
			int index=ob1.rollNo.indexOf(r);
			ob1.rollNo.remove(index);
			ob1.fullName.remove(index);
			ob1.age.remove(index);
			ob1.courses.remove(index);
			ob1.address.remove(index);
		}
		else{
			System.out.println("The roll number does not exists!");
		}
	}
	public void saveUser()
	{
		try{
			FileOutputStream fos= new FileOutputStream("myfile");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(ob1.rollNo);
			oos.writeObject(ob1.fullName);
			oos.writeObject(ob1.age);
			oos.writeObject(ob1.address);
			oos.writeObject(ob1.courses);
			oos.close();
			fos.close();
        }catch(IOException ioe){
			ioe.printStackTrace();
        }
	}
	public void exit()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Do you want to save changes before exiting?(y/n): ");
		char ch=sc.next().charAt(0);
		if(ch=='y' || ch=='Y')
		{
			saveUser();
			System.out.println("Changes Saved!");
		}
	}
	public static void main(String args[]){
		Assignment2 ob=new Assignment2();
		Scanner sc=new Scanner(System.in);
		while(true){
			System.out.println("1. Add User Details\n2. Display User Details\n3. Delete User Details\n4. Save User Details\n5. Exit");
			int choice=sc.nextInt();
			if(choice==1)
			{
				ob.addUser();
			}
			else if(choice==2)
			{
				ob.showUser();
			}
			else if(choice==3)
			{
				ob.deleteUser();
			}
			else if(choice==4)
			{
				ob.saveUser();
			}
			else{
				ob.exit();
				break;
			}
		}
	}
}