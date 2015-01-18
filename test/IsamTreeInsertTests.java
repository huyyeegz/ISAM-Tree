

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.junit.Test;

import edu.cornell.cs4320.hw2.IsamTree;

public class IsamTreeInsertTests {
	@Test
	public void manyInsertsInTinyTree()
	{
		int pageSize = 1;

		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
		data.add(new SimpleEntry<Integer,String>(1, "one"));
		data.add(new SimpleEntry<Integer,String>(2, "two"));
		data.add(new SimpleEntry<Integer,String>(3, "three"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		for(int i = 4; i < 1000; ++i) {
			assertTrue(tree.insert(i, "new val"));
		}
		
		for(int i = 1; i < 1000; ++i) {
			assertFalse(tree.insert(i, "double val"));
		}
	}
	
	@Test
	public void manyInsertsInNormalTree()
	{
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
		
		for(int i = 7; i < 1000; ++i) {
			assertTrue(tree.insert(i, "new val"));
		}
		
		for(int i = 1; i < 1000; ++i) {
			assertFalse(tree.insert(i, "double val"));
		}
	}
	
	@Test
	public void manyInsertsInterleaved()
	{
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
		
		for(int i = 4; i < 1000; ++i) {
			assertTrue(tree.insert(2*i+1, "uneven val"));
		}
		
		for(int i = 4; i < 1000; ++i) {
			assertTrue(tree.insert(2*i, "even val"));
		}
	}
	
	@Test
	public void negativeInserts()
	{
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
		
		for(int i = 0; i > -1000; --i) {
			assertTrue(tree.insert(i, "val"));
		}
		
		for(int i = 6; i > -1000; --i) {
			assertFalse(tree.insert(i, "val"));
		}
	}
	
	@Test
	public void multipleInsertsOfTheSameValue()
	{
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

		for(int i = 0; i < 1000; i++) {
			assertFalse(tree.insert(4, "val"));
		}
	}
}
