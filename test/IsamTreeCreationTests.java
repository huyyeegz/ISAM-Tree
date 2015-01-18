

import static org.junit.Assert.assertEquals;
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
public class IsamTreeCreationTests {
	@Test
	public void createHugeTree() {
		int pageSize = 10;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		
		for(int i = 0; i < 1000; i++) {
			data.add(new SimpleEntry<Integer,String>(i, "xyz"));
		}
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		for(int i = 0; i < 1000; i++) {
			assertTrue(tree.search(i) != null);
		}
		
		for(int i = 1000; i < 2000; i++) {
			assertTrue(tree.search(i) == null);
		}
	}
	
	@Test
	public void createTinyTreeWithTinyDataset() {
		int pageSize = 1;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertEquals("one", tree.search(1));
		assertEquals("two", tree.search(2));
		assertEquals("three", tree.search(3));
		
		assertEquals("(([ 1 ] 2 [ 2 ]) 3 ([ 3 ] E ()))", tree.toString());
	}
	
	@Test
	public void createHugeTreeWithTinyPages() {
		int pageSize = 1;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		
		for(int i = 0; i < 1000; i++) {
			data.add(new SimpleEntry<Integer,String>(i, "xyz"));
		}
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		for(int i = 0; i < 1000; i++) {
			assertTrue(tree.search(i) != null);
		}
		
		for(int i = 1000; i < 2000; i++) {
			assertTrue(tree.search(i) == null);
		}
	}
	
	@Test
	public void createTreeWithNegativeIndexes() {
		int pageSize = 5;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		
		for(int i = -1000; i < 1000; i++) {
			data.add(new SimpleEntry<Integer,String>(i, "xyz"));
		}
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		for(int i = -1000; i < 1000; i++) {
			assertTrue(tree.search(i) != null);
		}
		
		for(int i = 1000; i < 2000; i++) {
			assertTrue(tree.search(i) == null);
		}
		
		for(int i = -2000; i < -1000; i++) {
			assertTrue(tree.search(i) == null);
		}
	}
	
	@Test
	public void createTreeWithGapsInIndexes() {
		int pageSize = 5;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		
		for(int i = 0; i < 100; i++) {
			int index = (int) Math.pow(2,  i);
			data.add(new SimpleEntry<Integer,String>(index, "xyz"));
		}
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		for(int i = 0; i < 100; i++) {
			int index = (int) Math.pow(2,  i);
			assertTrue(tree.search(index) != null);
		}
	}
}
