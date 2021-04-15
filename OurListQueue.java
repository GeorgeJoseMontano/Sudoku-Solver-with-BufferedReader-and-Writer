/**
  * CC 12 - Activity 5
  * Name: Montano, George Jose P.
  * Date: March 09, 2021
  *
  * Linked list implementation of a queue of integers
  *	Create a queue of integers (enqueue, dequeue, print, and other supporting methods)
  *	To check if your queue is correct, use the following sequence of statements in your main method
  */
class OurListQueue
{
	Node head, tail;	//two nodes as different entry and exit point
	int size; 
	
	class Node
	{ 
		int data;
		Node next;
	}
	 
	//constructor
	public OurListQueue()	
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	//check if list is empty
	public boolean isEmpty()
	{
		if (size == 0) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	//dequeue	
	public int dequeue()
	{	
		if (isEmpty()) 
		{
			System.out.println("The Queue List Is Empty");		
			return -1;
		}
		else
		{
			int data = head.data; //store data of head node to variable data
			head = head.next;	//replace beginning with next node
			size--;	//reduce the size
			//System.out.println("Removed " + data);
			return data; //return the data of head node
		}
		
	}
	 
	//enqueue
	public void enqueue(int data)
	{
		Node previousTail = tail; //the current tail will now be the previous tail
		tail = new Node(); //create a new node for the new tail
		tail.data = data;  //the data input will be the new value for tail
		tail.next = null;	//as tail is last there will be nothing next to it
		if (isEmpty()) 
		{
			head = tail; //if the list is empty the new added data is now also the head's value
		}
		else 
		{
			previousTail.next = tail; //if the list is not empty new tail will connect to previous tail
		}
		size++; 
	}
	 
	// print
	public void print()
	{
		System.out.print("The Queue List ");
		if (isEmpty()) 
		{
			System.out.print("Is Empty");
			return;
		}
		else
		{
			Node temp = head; //create a temporary node and assign value of head to it
			while (temp != null)
			{
				System.out.print(" - " + temp.data + " "); //print out the given data
				temp = temp.next; //assign next node to temp until it becomes null
			}
			System.out.println();
		}
	}
}