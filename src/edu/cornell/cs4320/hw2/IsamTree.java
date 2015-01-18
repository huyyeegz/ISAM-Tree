package edu.cornell.cs4320.hw2;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * A very simple ISAM tree that makes a few assumptions
 * 
 * - Key is always an Integer
 * - Value is always a String
 */
public class IsamTree {	
	
	public IsamTree(int pageSize) {
		assert(pageSize > 0);
		
		this.pageSize = pageSize;
	}
	
	/**
	 * Create initial tree from a data set
	 */
	public void create(Set<Entry<Integer,String>> entries) {
		//System.out.printf("entriesSize %d pageSize %d and entries %s\n",entries.size(), pageSize, entries);
		assert(entries.size() > this.pageSize);
		
		Map<Integer,String> entries_map = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> my_itr = entries.iterator();
		Entry<Integer, String> entry;
		while(my_itr.hasNext())
		{
			entry = my_itr.next();
			entries_map.put(entry.getKey(), entry.getValue());
		}
		
		entries.clear();
		entries = entries_map.entrySet();
		//System.out.printf("Entries after sorting %s\n", entries);
		// Create an new root (old data will be discarded)
		this.root = new IsamIndexNode(pageSize);
		
        // Calculating height of the tree
		Integer height_counter = 0;
		Integer num_pages_needed = (entries.size() - 1)/ this.pageSize / 3;
		
		while(num_pages_needed != 0)
		{
			height_counter++;
        	num_pages_needed = num_pages_needed / 3; /* 3 is the fan out */
		}
        // At this moment we have calculated the height as per the defination provided in section 10.3 of Textbook
		// Creating the indexes.

		height = height_counter + 1;
        // Create the data nodes
		List<IsamDataNode> dataNodes = new ArrayList<IsamDataNode>();
		
		//Iterator<Entry<Integer, String>> 
		my_itr = entries.iterator();
		//Entry<Integer, String> entry;
		IsamDataNode new_node = new IsamDataNode(pageSize);
		
		Integer i = 0;
		while(my_itr.hasNext())
		{
			entry = my_itr.next();
			new_node.insert(entry.getKey(), entry.getValue());
			++i;
			if(i%pageSize == 0 && i != 0)
			{
				dataNodes.add(new_node);
				new_node = new IsamDataNode(pageSize);
			}
		}
		if(i%pageSize != 0 || i == 0)
		{
			dataNodes.add(new_node);
		}
		
		// At this point dataNodes contains the list of DataNodes
		
		/*
         * Helper function to print all dataNodes we have created
		i = 0;
		for(IsamDataNode data_node : dataNodes)
		{
			System.out.printf("<Node id=%d num_keys=%d>",i, data_node.num_records);
			for(Integer key : data_node.keys)
			{
				System.out.printf(" %d = %s ",key, data_node.search(key));
			}
			System.out.printf("</Node>");
			++i;
		}
		System.out.printf("\n");
		*/
		
        /* Make the first level index nodes of type IsamIndexNode which point to IsamDataNode */
        Iterator<IsamDataNode> data_nodes_itr = dataNodes.iterator();
		List<IsamIndexNode> upper_level_nodes = new ArrayList<IsamIndexNode>();
        IsamDataNode dataElement;
        IsamIndexNode index_node;
        while(data_nodes_itr.hasNext())
        {
            // Take pageSize + 1 data nodes and index them using one IndexNode 
            i = 0;
            index_node = new IsamIndexNode(pageSize);
            while(data_nodes_itr.hasNext() && i != pageSize+1)
            {
                dataElement = data_nodes_itr.next();
                index_node.children[i] = dataElement;
                if(i != 0)
                {
                    index_node.keys[i-1] = dataElement.keys[0];
                }
                ++i;
            }
            upper_level_nodes.add(index_node);
        }
        /* Make the rest of the Index tree */
        List<IsamIndexNode> lower_level_nodes = upper_level_nodes;
		IsamIndexNode element;
		Integer current_height = 0;
		
        while(height != current_height)//change
		{// At each height level
			current_height++;
			upper_level_nodes = new ArrayList<IsamIndexNode>();
			Iterator<IsamIndexNode> lower_itr = lower_level_nodes.iterator();
			while(lower_itr.hasNext()) // get all Index Nodes
			{
                i = 0;
				index_node = new IsamIndexNode(pageSize);
				while(lower_itr.hasNext() && i != pageSize+1) // Get Elements inside an Index Node
				{
					element = lower_itr.next();
					index_node.children[i] = element;
					if(i != 0)
					{
						index_node.keys[i-1] = element.get_child_key_for_parent();
					}
					++i;
				}
				upper_level_nodes.add(index_node);
			}
			lower_level_nodes.clear();
			lower_level_nodes = upper_level_nodes;
		}
		
		// At this point we should have only one root node.
		//System.out.printf("Root node size %d \n",upper_level_nodes.size());
		if(upper_level_nodes.size() != 1)
		{
			System.out.printf("I have erred!!\n");
		}
		root = (IsamIndexNode) upper_level_nodes.get(0);
	}
	
	/**
	 * Get the height of this tree
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the root of this tree
	 * Note: Should only be used internally and/or by helper classes
	 */
	protected IsamIndexNode getRoot() {
		return root;
	}
	
	/**
	 * Get a in-order string representation of the tree
	 * 
	 * Note:
	 *  - this only prints the indexes
	 *  - index nodes should be shown by curly braces
	 *  - data nodes by square brackets
	 *  - empty indexes by the letter 'E'
	 *  - and empty nodes/subtrees by ()
	 *  
	 *  See the unit tests for examples
	 */
	public String toString() {
		return root.toString();
	}
	
	/**
	 * Search for a specific entry
	 * Returns the entry if found and null otherwise
	 */
	public String search(Integer key) {
		return root.search(key);
	}
	
	/**
	 * Insert a new value
	 * This will return false if the value already exists
	 */
	public boolean insert(Integer key, String value) {
		return root.insert(key, value);
	}
	
	/**
	 * Remove the entry with the specified key
	 * This will return false if the value wasn't found
	 */
	public boolean delete(Integer key) {
		return root.delete(key);
	}
	
	/**
	 * Get the size of one page/node
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Root of the tree
	 * It is assumed that this is never a data node
	 */
	private IsamIndexNode root;

	/**
	 * Size of each node/page 
	 * This is set via the constructor
	 */
	private final int pageSize;
	
	/**
	 * The height of the tree
	 * Should be calculated by create()
	 */
	private int height;
}
