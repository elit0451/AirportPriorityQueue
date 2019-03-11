package dk.cphbusiness.algorithm.examples.queues;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import dk.cphbusiness.airport.template.Passenger;
import java.util.NoSuchElementException;

public class HeapQueue implements PriorityQueue<Passenger>{

    private final Passenger[] items;
    private int index = 0;
    private int _capacity;
    
    public HeapQueue(int capacity) {
        items = new Passenger[capacity];
        _capacity = capacity;
    }
    
    @Override
    public void enqueue(Passenger item) {
        items[index] = item;
        int currentPos = index;

        while (items[currentPos].compareTo(items[(currentPos - 1) / 2]) > 0)
        {
            Passenger tmpData = items[currentPos];
            items[currentPos] = items[(currentPos - 1) / 2];
            items[(currentPos - 1) / 2] = tmpData;
            currentPos = (currentPos - 1) / 2;
        }
        index++;
    }
    
    @Override
    public Passenger dequeue() {
        if(index == 0)
            throw new NoSuchElementException("Cannot dequeue from an empty queue");
        
        Passenger passangerToReturn = items[0];
        
        items[0] = items[index-1];
        items[index-1] = null;
        int currentPos = 0;
        boolean foundRightPosition = false;
        
        while (!foundRightPosition && items[currentPos] != null)
        {
            int leftPos = (currentPos * 2) + 1;
            int rightPos = (currentPos+1) * 2;
            //Get left value
            Passenger left;
            if(leftPos < _capacity)
                left = items[leftPos];
            else
                left = null;
            //Get right value
            Passenger right;
            if(rightPos < _capacity)
                right= items[rightPos];
            else
                right = null;
            
            if(left != null && left.compareTo(right)>=0 && items[currentPos].compareTo(left) < 0)
            {
                Passenger tmp = left;
                items[leftPos] = items[currentPos];
                items[currentPos] = tmp;
                currentPos = leftPos;
            }
            else if(right!=null && right.compareTo(left)>=0 && items[currentPos].compareTo(right)<0)
            {
                Passenger tmp = right;
                items[rightPos] = items[currentPos];
                items[currentPos] = tmp;
                currentPos = rightPos;
            }
            else
            {
                foundRightPosition = true;
            }
        }
        
        index--;
        return passangerToReturn;
    }

    @Override
    public Passenger peek() {
        if (index == 0)
          throw new NoSuchElementException("Cannot peek into empty queue");
        return items[0];
    }

    @Override
    public int size() {
        return index;
    }

    public void printQueue()
    {
        for(Passenger p : items)
            if(p!=null)
                System.out.println(p.getCategory());
        System.out.println("\n");
    }
}
