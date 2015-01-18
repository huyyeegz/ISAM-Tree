package edu.cornell.cs4320.hw2.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import org.junit.Test;

import edu.cornell.cs4320.hw2.IsamTree;

public class IsamTreeTest {
	@Test
	public void simpleTree() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(15, "cake"));
		data.add(new SimpleEntry<Integer,String>(20, "is"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(35, "lie"));
		
		IsamTree tree = new IsamTree(pageSize);
		
		
		tree.create(data);
		
		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 E ])", tree.toString());
		assertEquals("the", tree.search(10));
	}
	
	@Test
	public void textBookTree() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "this"));
		data.add(new SimpleEntry<Integer,String>(15, "is"));
		data.add(new SimpleEntry<Integer,String>(20, "just"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(33, "simple"));
		data.add(new SimpleEntry<Integer,String>(37, "test"));
		data.add(new SimpleEntry<Integer,String>(40, "of"));
		data.add(new SimpleEntry<Integer,String>(46, "the"));
		data.add(new SimpleEntry<Integer,String>(51, "data"));
		data.add(new SimpleEntry<Integer,String>(55, "bases"));
		data.add(new SimpleEntry<Integer,String>(63, "text"));
		data.add(new SimpleEntry<Integer,String>(97, "book"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 ]) E ())", tree.toString());
		assertEquals("this", tree.search(10));
	}
	
	@Test
	public void initialTree5() {
		int pageSize = 5;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(1, "this"));
		data.add(new SimpleEntry<Integer,String>(2, "is"));
		data.add(new SimpleEntry<Integer,String>(3, "just"));
		data.add(new SimpleEntry<Integer,String>(4, "a"));
		data.add(new SimpleEntry<Integer,String>(5, "simple"));
		data.add(new SimpleEntry<Integer,String>(6, "test"));
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(8, "db"));
		data.add(new SimpleEntry<Integer,String>(9, "class"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);

		assertEquals("([ 1 2 3 4 5 ] 6 [ 6 8 9 10 E ] E () E () E () E ())", tree.toString());
		assertEquals("db", tree.search(8));
	}
	
	@Test
	public void insertDataIntoTree() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(1, "this"));
		data.add(new SimpleEntry<Integer,String>(2, "is"));
		data.add(new SimpleEntry<Integer,String>(3, "just"));
		//data.add(new SimpleEntry<Integer,String>(4, "a"));
		data.add(new SimpleEntry<Integer,String>(5, "simple"));
		data.add(new SimpleEntry<Integer,String>(6, "test"));
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(8, "db"));
		data.add(new SimpleEntry<Integer,String>(9, "class"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertEquals(true, tree.insert(4, "a"));
		assertEquals(false, tree.insert(5, "simple"));
	}

	@Test
	public void overflow() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(15, "cake"));
		data.add(new SimpleEntry<Integer,String>(20, "is"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(35, "lie"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		tree.insert(36, "1");
		tree.insert(37, "2");
		tree.insert(38, "3");

		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 ] ])", tree.toString());
	}
	
	
	/*
	@Test
	public void delete_tests() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(1, "the"));
		data.add(new SimpleEntry<Integer,String>(2, "cake"));
		data.add(new SimpleEntry<Integer,String>(3, "is"));
		data.add(new SimpleEntry<Integer,String>(4, "a"));
		data.add(new SimpleEntry<Integer,String>(5, "lie"));
		data.add(new SimpleEntry<Integer,String>(6, "lie"));
		data.add(new SimpleEntry<Integer,String>(7, "lie"));
		data.add(new SimpleEntry<Integer,String>(9, "lie"));
		data.add(new SimpleEntry<Integer,String>(10, "lie"));
		data.add(new SimpleEntry<Integer,String>(14, "lie"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		assertEquals("(([ 1 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 ] E ()) E ())", tree.toString());
		
		// Delete one key with other non empty in a data node
		tree.delete(1);
		System.out.printf("%s\n", tree);
		assertEquals("(([ 2 E ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 ] E ()) E ())", tree.toString());
		
		tree.insert(8, "truth");
		assertEquals("(([ 2 E ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 [ 8 E ] ] 10 [ 10 14 ] E ()) E ())", tree.toString());
		
		tree.insert(11, "truth");
		tree.insert(12, "truth");
		tree.insert(13, "truth");
		assertEquals("(([ 2 E ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 [ 8 E ] ] 10 [ 10 14 [ 11 12 [ 13 E ] ] ] E ()) E ())", tree.toString());
		
		// deleting the node in overflow page with only one Entry here [ 8  E ]
		tree.delete(8);
		assertEquals("(([ 2 E ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 [ 11 12 [ 13 E ] ] ] E ()) E ())", tree.toString());
		
		// testing deletion of an entry that does not exist
		assertEquals(false,tree.delete(8));
		
		tree.insert(11, "truth");
		tree.insert(12, "truth");
		tree.insert(13, "truth");
		tree.delete(13);
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 [ 11 12 ] ] E ()) E ())", tree.toString());
		tree.insert(13, "truth");
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 [ 11 12 [ 13 E ] ] ] E ()) E ())", tree.toString());
		tree.delete(11);
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 [ E 12 [ 13 E ] ] ] E ()) E ())", tree.toString());
		tree.delete(12);
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 10 14 [ 13 E ] ] E ()) E ())", tree.toString());
		tree.delete(10);
		tree.delete(13);
		tree.delete(14);
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ E E ] E ()) E ())", tree.toString());
		tree.insert(12, "truth");
		tree.insert(13, "truth");
		tree.insert(14, "truth");
		tree.insert(15, "truth");
		tree.insert(16, "truth");
		tree.insert(17, "truth");
		tree.insert(18, "truth");
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 12 13 [ 14 15 [ 16 17 [ 18 E ] ] ] ] E ()) E ())",tree.toString());
		tree.delete(16);
		tree.delete(17);
		assertEquals("(([ E 2 ] 3 [ 3 4 ] 5 [ 5 6 ]) 7 ([ 7 9 ] 10 [ 12 13 [ 14 15 [ 18 E ] ] ] E ()) E ())",tree.toString());
	} 
	*/
	
	@Test
	public void Insert() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "this"));
		data.add(new SimpleEntry<Integer,String>(15, "is"));
		data.add(new SimpleEntry<Integer,String>(20, "just"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(33, "simple"));
		data.add(new SimpleEntry<Integer,String>(37, "test"));
		data.add(new SimpleEntry<Integer,String>(40, "of"));
		data.add(new SimpleEntry<Integer,String>(46, "the"));
		data.add(new SimpleEntry<Integer,String>(51, "data"));
		data.add(new SimpleEntry<Integer,String>(55, "bases"));
		data.add(new SimpleEntry<Integer,String>(63, "text"));
		data.add(new SimpleEntry<Integer,String>(97, "book"));
	
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
	
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 ]) E ())", tree.toString());
		assertEquals("this", tree.search(10));
		
		assertEquals(true,tree.insert(100, "val"));
		
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 [ 100 E ] ]) E ())", tree.toString());
		assertEquals(false,tree.insert(100, "val"));
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 [ 100 E ] ]) E ())", tree.toString());
		assertEquals(true,tree.insert(34, "hi"));
		//System.out.println("Tree is "+tree.toString());
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 [ 34 E ] ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 [ 100 E ] ]) E ())", tree.toString());
		assertEquals(true,tree.insert(35, "hi"));
		//System.out.println("Tree is "+tree.toString());
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 [ 34 35 ] ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 [ 100 E ] ]) E ())", tree.toString());
		assertEquals(true,tree.insert(36, "hi"));
		//System.out.println("Tree is "+tree.toString());
		assertEquals("(([ 10 15 ] 20 [ 20 27 ] 33 [ 33 37 [ 34 35 [ 36 E ] ] ]) 40 ([ 40 46 ] 51 [ 51 55 ] 63 [ 63 97 [ 100 E ] ]) E ())", tree.toString());
		assertEquals(false,tree.insert(36, "hello"));
		//System.out.println("Tree is "+tree.toString());
		assertEquals("hi", tree.search(35));
		assertEquals(true,tree.delete(33));
	}
	
	@Test
	public void Search() {
	{
	int pageSize = 2;
	Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();

	data.add(new SimpleEntry<Integer,String>(10, "this"));
	data.add(new SimpleEntry<Integer,String>(15, "is"));
	data.add(new SimpleEntry<Integer,String>(20, "just"));
	data.add(new SimpleEntry<Integer,String>(27, "a"));
	data.add(new SimpleEntry<Integer,String>(33, "simple"));
	data.add(new SimpleEntry<Integer,String>(37, "test"));
	data.add(new SimpleEntry<Integer,String>(40, "of"));
	data.add(new SimpleEntry<Integer,String>(46, "the"));
	data.add(new SimpleEntry<Integer,String>(51, "data"));
	data.add(new SimpleEntry<Integer,String>(55, "bases"));
	data.add(new SimpleEntry<Integer,String>(63, "text"));
	data.add(new SimpleEntry<Integer,String>(97, "book"));
	
	IsamTree tree = new IsamTree(pageSize);
	tree.create(data);
	assertEquals("the",tree.search(46)); //Key present in index nodes
	assertEquals(true, tree.insert(36,"binary"));
	assertEquals(false, tree.insert(20, "just"));
	assertEquals(true, tree.insert(34, "ternary"));
	assertEquals(true, tree.insert(38, "tuple"));
	
	//System.out.printf("Akhil %s", tree);
	assertEquals("binary",tree.search(36)); //1st level Overflow Search
	
	assertEquals("tuple",tree.search(38));
	assertEquals(null,tree.search(39)); //Data not present
	assertEquals("tuple",tree.search(38)); //2nd level Overflow Search
	assertEquals("a",tree.search(27));//Key not present in index nodes
	//System.out.println("End of Test..");
	}
	}

// AKHILAKHILAKHIL
	

	@Test
	public void delete1() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(15, "cake"));
		data.add(new SimpleEntry<Integer,String>(20, "is"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(35, "lie"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		tree.insert(36, "1");
		tree.insert(37, "2");
		tree.insert(38, "3");
		tree.insert(39, "4");
		tree.insert(40, "5");

		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 [ 39 40 ] ] ])", tree.toString());
		assertEquals(true,tree.delete(20));
		assertEquals("([ 10 15 ] 20 [ 27 E ] 35 [ 35 36 [ 37 38 [ 39 40 ] ] ])", tree.toString());
		assertEquals(true,tree.delete(40));
		assertEquals("([ 10 15 ] 20 [ 27 E ] 35 [ 35 36 [ 37 38 [ 39 E ] ] ])", tree.toString());
		assertEquals(true,tree.delete(37));
		assertEquals("([ 10 15 ] 20 [ 27 E ] 35 [ 35 36 [ 39 38 ] ])", tree.toString());
		tree.delete(27);
		assertEquals("([ 10 15 ] 20 [ E E ] 35 [ 35 36 [ 39 38 ] ])", tree.toString());
		assertEquals(false,tree.delete(27));
		tree.delete(35);
		assertEquals(false,tree.delete(35));
		assertEquals("([ 10 15 ] 20 [ E E ] 35 [ 38 36 [ 39 E ] ])", tree.toString());
		assertEquals(true,tree.delete(36));
		assertEquals("([ 10 15 ] 20 [ E E ] 35 [ 38 39 ])", tree.toString());
		
	}
	
	@Test
	public void delete2() {
		int pageSize = 5;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(1, "this"));
		data.add(new SimpleEntry<Integer,String>(2, "is"));
		data.add(new SimpleEntry<Integer,String>(3, "just"));
		data.add(new SimpleEntry<Integer,String>(4, "a"));
		data.add(new SimpleEntry<Integer,String>(5, "simple"));
		data.add(new SimpleEntry<Integer,String>(6, "test"));
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(8, "db"));
		data.add(new SimpleEntry<Integer,String>(9, "class"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		assertEquals("([ 1 2 3 4 5 ] 6 [ 6 8 9 10 E ] E () E () E () E ())", tree.toString());
		tree.insert(11,"abc");
		assertEquals("([ 1 2 3 4 5 ] 6 [ 6 8 9 10 11 ] E () E () E () E ())", tree.toString());
		assertEquals("db", tree.search(8));
		assertEquals(null,tree.search(500));
		assertEquals(true,tree.delete(4));
		assertEquals("([ 1 2 3 5 E ] 6 [ 6 8 9 10 11 ] E () E () E () E ())", tree.toString());

	}
	
	@Test
	public void delete3() {
		int pageSize = 2;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(10, "the"));
		data.add(new SimpleEntry<Integer,String>(15, "cake"));
		data.add(new SimpleEntry<Integer,String>(20, "is"));
		data.add(new SimpleEntry<Integer,String>(27, "a"));
		data.add(new SimpleEntry<Integer,String>(35, "lie"));
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		
		tree.insert(36, "1");
		tree.insert(37, "2");
		tree.insert(38, "3");
		tree.insert(39, "4");
		tree.insert(40, "5");

		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 [ 39 40 ] ] ])", tree.toString());
		assertEquals(true,tree.delete(40));
		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 [ 39 E ] ] ])", tree.toString());
		assertEquals(true,tree.insert(40,"aa"));
		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 35 36 [ 37 38 [ 39 40 ] ] ])", tree.toString());
		assertEquals(true,tree.delete(40));
		assertEquals(true,tree.delete(35));
		assertEquals("([ 10 15 ] 20 [ 20 27 ] 35 [ 39 36 [ 37 38 ] ])", tree.toString());

	}


	@Test
	public void bulkLoad()
	{
		int pageSize = 10;

        Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();

        for(int i=1;i<=100000;i++)
        	data.add(new SimpleEntry<Integer,String>(i, String.valueOf(i)));

        IsamTree tree = new IsamTree(pageSize);
        
        System.out.println(data.size());

        tree.create(data);
        
        System.out.println(tree.toString());

	}









}