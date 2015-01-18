

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.junit.Test;

import edu.cornell.cs4320.hw2.IsamTree;

/**
 * These tests should give the 10 points for delete
 */
public class IsamTreeDeletionTests {
	@Test
	public void mergeOverflow() {
		int pageSize = 2;
		Set<Entry<Integer, String>> data = new HashSet<Entry<Integer, String>>();

		data.add(new SimpleEntry<Integer, String>(10, "the"));
		data.add(new SimpleEntry<Integer, String>(15, "cake"));
		data.add(new SimpleEntry<Integer, String>(20, "is"));
		data.add(new SimpleEntry<Integer, String>(27, "a"));
		data.add(new SimpleEntry<Integer, String>(35, "lie"));

		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		tree.insert(36, "1");
		tree.insert(37, "2");
		tree.insert(38, "3");

		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 ] ])",
				tree.toString());

		tree.delete(36);
		tree.delete(38);

		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 37 ])", tree.toString());
	}

	@Test
	public void mergeOverflowTwoOverlows() {
		int pageSize = 2;
		Set<Entry<Integer, String>> data = new HashSet<Entry<Integer, String>>();

		data.add(new SimpleEntry<Integer, String>(10, "the"));
		data.add(new SimpleEntry<Integer, String>(15, "cake"));
		data.add(new SimpleEntry<Integer, String>(20, "is"));
		data.add(new SimpleEntry<Integer, String>(27, "a"));
		data.add(new SimpleEntry<Integer, String>(35, "lie"));

		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		tree.insert(36, "1");
		tree.insert(37, "2");
		tree.insert(38, "3");
		tree.insert(39, "4");
		tree.insert(40, "5");

		assertEquals(
				"([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 [ 39 40 ] ] ])",
				tree.toString());

		tree.delete(36);
		tree.delete(38);
		tree.delete(40);

		// Order doesn't really matter... check 'manually' if students are
		// correct
		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 39 [ 37 E ] ])",
				tree.toString());
	}

	@Test
	public void createHugeTreeAndDeleteEverything() {
		int pageSize = 10;

		Set<Entry<Integer, String>> data = new HashSet<Entry<Integer, String>>();

		for (int i = 0; i < 1000; i++) {
			data.add(new SimpleEntry<Integer, String>(i, "xyz"));
		}

		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		for (int i = 0; i < 1000; i++) {
			assertTrue(tree.delete(i));
		}

		for (int i = 0; i < 5000; i++) {
			assertFalse(tree.delete(i));
		}
	}
	
	@Test
	public void createTinyAndDeleteEverything() {
		int pageSize = 1;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertTrue(tree.delete(1));
		assertTrue(tree.delete(2));
		assertTrue(tree.delete(3));
		
		assertEquals("(([ E ] 2 [ E ]) 3 ([ E ] E ()))", tree.toString());
	}
	
	@Test
	public void mulitDelete() {
		int pageSize = 1;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertTrue(tree.delete(1));
		assertFalse(tree.delete(1));
		assertFalse(tree.delete(1));
	}
}
