package edu.cornell.cs4320.hw2;

public class IsamIndexNode extends IsamNode {
	protected final Integer[] keys = new Integer[getSize()];
	protected final IsamNode[] children = new IsamNode[getSize() + 1];
		
	IsamIndexNode(int size) {
		super(size);
	}
		
	public Integer getIndex(int pos) {
		return keys[pos];
	}
	
	@Override
	public String search(Integer key) {
		Integer i = 0;
		for(i = 0; i < getSize(); ++i)
		{
			if(keys[i] == null)
			{
				if(children[i] == null)
				{
					return null;
				}
				else
				{
					return children[i].search(key);
				}
			}
			if(key < keys[i])
			{
				if(children[i] == null)
				{
					return null;
				}
				return children[i].search(key);
			}
		}
		return children[i].search(key);
	}

	@Override
	public boolean insert(Integer key, String value) {
		Integer i = 0;
		while(i < getSize())
		{
			if(keys[i] == null)
			{
				if(children[i] == null)
				{
					return false;
				}
				else
				{
					return children[i].insert(key, value);
				}
			}
			if(key < keys[i])
			{
				return children[i].insert(key, value);
			}
			++i;
		}
		if(children[i] != null)
		{
			return children[i].insert(key, value);
		}
		return false;
	}

	/**
	 * Create a child at a specific position and give the height of the subtree
	 * Only for internal use
	 */
	protected void createChild(int i, int height) {
		/*
		 * Not implementing this as I am doing bottom up construction of tree
		*/ 
	}
	
	/**
	 * Get child at a specific position
	 */
	public IsamNode getChild(int pos) {
		return children[pos];
	}

	/**
	 * Get the to string for a specific child
	 * (this is a helper function for toString)
	 */
	private String getChildInOrderString(int pos) {
		IsamNode child = getChild(pos);
		
		if(child == null) {
			return "()";
		} else {
			return child.toString();
		}
	}

	@Override
	public String toString() {
		// Dont change this. This already does everything it is supposed to do
		String output = "";
		
		output += "(";
		
		int i = 0; 
		
		for(; i < getSize(); ++i) {
			output += getChildInOrderString(i);
				
			Integer index = getIndex(i);						
			if(index != null) {
				output += " " + index + " ";
			} else {
				output += " E ";
			}
		}
		
		output += getChildInOrderString(i);
		output += ")";
		
		return output;
	}

	@Override
	public boolean delete(Integer key) {
		Integer i = 0;
		for(i = 0; i < keys.length; ++i)
		{
			if(keys[i] == null)
			{
				if(children[i] == null)
					return false;
				else
					return children[i].delete(key);
			}
			if(key < keys[i])
			{
				if(children[i] == null)
					return false;
				return children[i].delete(key);
			}
		}
		if(children[i] != null)
			return children[i].delete(key);
		else
			return false;
	}

	public Integer get_child_key_for_parent() {
		return children[0].get_child_key_for_parent();
	}
}
