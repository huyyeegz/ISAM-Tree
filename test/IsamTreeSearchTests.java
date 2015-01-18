

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.junit.Test;

import edu.cornell.cs4320.hw2.IsamTree;

public class IsamTreeSearchTests {
	@Test
	public void testNormalSearch() {
		int pageSize = 5;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		data.add(new SimpleEntry<Integer,String>(4, "four"));
		data.add(new SimpleEntry<Integer,String>(5, "five"));
		data.add(new SimpleEntry<Integer,String>(6, "six"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		assertEquals(null, tree.search(-1));	
		assertEquals(null, tree.search(0));	
		assertEquals("one", tree.search(1));
		assertEquals("two", tree.search(2));
		assertEquals("three", tree.search(3));	
		assertEquals("four", tree.search(4));	
		assertEquals("five", tree.search(5));	
		assertEquals("six", tree.search(6));	
		assertEquals(null, tree.search(7));	
		assertEquals(null, tree.search(8));		
	}


	@Test
	public void testTinySearch() {
		int pageSize = 1;
		
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		assertEquals(null, tree.search(-1));	
		assertEquals(null, tree.search(0));	
		assertEquals("one", tree.search(1));
		assertEquals("two", tree.search(2));
		assertEquals("three", tree.search(3));	
		assertEquals(null, tree.search(4));	
		assertEquals(null, tree.search(5));	
		assertEquals(null, tree.search(6));
	}

}
