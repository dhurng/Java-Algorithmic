/**
 * 
 * @author David Hurng
 * Max Heap class
 */
import java.util.ArrayList;
import java.util.Collection;

public class MaxHeap {
	private ArrayList<Student> students;

	public MaxHeap(int capacity) {
		students = new ArrayList<Student>(capacity);
	}

	public MaxHeap(Collection<Student> collection) {
		students = new ArrayList<Student>(collection);
		for (int a = 0; a < size() - 1; a++) {// input into index
			students.get(a).setPosition(a);
		}
		for (int i = size() / 2; i >= 0; i--) {
			maxHeapify(i);
		}
	}

	public Student getMax() {
		if (size() < 1) {
			throw new IndexOutOfBoundsException(
					"No maximum value:  the heap is empty.");
		}
		return students.get(0);
	}

	public Student extractMax() {
		Student value = getMax();
		students.set(0, students.get(size() - 1));
		students.remove(size() - 1);
		maxHeapify(0);
		return value;
	}

	public void insert(Student elt) {
		// Please write me.
		int pos;
		students.add(elt); // add last spot
		elt.setPosition(size() - 1);// initializes at the end
		pos = size() - 1;
		// elt.indexOfStudent(pos);
		while (pos > 0 && elt.compareTo(students.get(parent(pos))) > 0) {
			swap(pos, parent(pos));
			pos = parent(pos);
			elt.setPosition(pos);
		}
	}

	public void changeGPA(Student s, double newGPA) {
		// Please write me.
		// int pos = students.indexOf(s);// find the index of the student
		s.setGPA(newGPA);
		int pos = s.initialize(); // initialize

		/*
		 * if (newGPA < s.gpa()) {// if new GPA is decreasing
		 * s.setGPA(newGPA);// change to new GPA maxHeapify(pos); } else if
		 * (newGPA > s.gpa()) {// key is increasing s.setGPA(newGPA);
		 */
		while (pos > 0 && s.compareTo(students.get(parent(pos))) > 0) {
			swap(pos, parent(pos));
			pos = parent(pos);
		}
		maxHeapify(pos);
		// }
	}

	private int parent(int index) {
		return (index - 1) / 2;
	}

	private int left(int index) {
		return 2 * index + 1;
	}

	private int right(int index) {
		return 2 * index + 2;
	}

	private int size() {
		return students.size();
	}

	private void swap(int from, int to) {
		Student val = students.get(from);
		Student temp = students.get(to);

		students.set(from, temp);
		students.set(to, val);

		temp.setPosition(from);
		val.setPosition(to);
	}

	private void maxHeapify(int index) {
		int left = left(index);
		int right = right(index);
		int largest = index;
		if (left < size()
				&& students.get(left).compareTo(students.get(largest)) > 0) {
			largest = left;
		}
		if (right < size()
				&& students.get(right).compareTo(students.get(largest)) > 0) {
			largest = right;
		}
		if (largest != index) {
			swap(index, largest);
			maxHeapify(largest);
		}
	}
}
