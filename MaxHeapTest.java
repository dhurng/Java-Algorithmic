/* 
 * @author David Hurng
 * Max Heap JUnit Test
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MaxHeapTest {

	MaxHeap heap = new MaxHeap(10);
	MaxHeap heap2;
	MaxHeap empty = new MaxHeap(2);
	Student a = new Student("test", 0, 1.0);

	@Before
	public void setUp() throws Exception {
		heap.insert(a);
		heap.insert(new Student("Alvis", 12, 4.0));
		heap.insert(new Student("Clara", 10, 3.5));
		heap.insert(new Student("Calvin", 15, 2.1));
		heap.insert(new Student("David", 12, 3.2));
		heap.insert(new Student("Esther", 15, 3.7));
		
		ArrayList<Student> test = new ArrayList<>();
		test.add(a);
		heap2 = new MaxHeap(test);
	}
	
	@Test
	public void randomStuff() {
		Student test = new Student("lame");
		test.getName();
		test.units();
		test.setUnits(5);
	}

	@Test(expected=IndexOutOfBoundsException.class) public void outOfBounds() {
		empty.insert(a);
		empty.extractMax();
		empty.getMax();
    }
	
	@Test
	public void testGetMax() {
		heap.getMax();
		assertEquals(4.0, heap.getMax().gpa(), .000001);
	}

	@Test
	public void testExtractMax() {
		assertEquals(4.0, heap.extractMax().gpa(), .000001);
		assertEquals(3.7, heap.extractMax().gpa(), .000001);
		assertEquals(3.5, heap.extractMax().gpa(), .000001);
		assertEquals(3.2, heap.extractMax().gpa(), .000001);
		assertEquals(2.1, heap.extractMax().gpa(), .000001);
	}

	@Test
	public void changeGPA() {
		heap.changeGPA(a, 4.2); //now is max 
		assertEquals(4.2, heap.extractMax().gpa(), .000001); 
		//heap.changeGPA(a,)
	}
}
