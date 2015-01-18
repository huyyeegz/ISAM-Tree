package edu.cornell.cs4320.hw2;

public class IsamDataNode extends IsamNode {
	protected final Integer[] keys = new Integer[getSize()];
	protected final String[] values = new String[getSize()];
	protected Integer num_records = 0;
	private IsamDataNode successor = null;
	
	IsamDataNode(int size) {
		super(size);
	}
	
	public Integer getIndex(int pos) {
		return keys[pos];
	}

	@Override
	public String search(Integer key) {
		Integer num_records_to_traverse = num_records;
		for(Integer i = 0; i < num_records; ++i)
		{
			if(keys[i] == null)
				num_records_to_traverse++;
			if(keys[i] != null && keys[i].equals(key))//change
			{
				return values[i];
			}
		}
		if(null == successor)
		{
			return null;
		}
		return successor.search(key);
	}
	
	public boolean hasOverflow() {
		return successor != null;
	}
	
	public IsamDataNode getOverflowPage() {
		return successor;
	}

	@Override
	public boolean insert(Integer key, String value) {
		if(search(key) != null)
		{
			return false;
		}
		if(getSize() == num_records)
		{ // We have to insert into an overflows page
			if(hasOverflow())
			{
				return successor.insert(key, value);
			}
			else
			{
				successor = new IsamDataNode(getSize());
				return successor.insert(key, value);
			}
		}
		keys[num_records] = key;
		values[num_records] = value;
		++num_records;
		return true;
	}

	@Override
	public String toString() {
		String data = "[ ";
		
		for(int i = 0; i < getSize(); ++i) {
			Integer key = keys[i];
			
			if(key == null) {
				data += "E ";
			} else {
				data += keys[i] + " ";
			}
		}
		
		if(hasOverflow()) {
			return data + successor.toString() + " ]";
		} else {
			return data + "]";
		}
	}

	public Integer get_child_key_for_parent() {
		return keys[0];
	}
	
	public void compact()
	{
		// Find 1st Empty node
		IsamDataNode cur_pointer = this;
		IsamDataNode E_pos = null;
		Integer E_pos_i = 0;
		
		if(this.successor == null && this.num_records == 0)
			return;
		
		Integer i = 0;
		boolean found = false;
		while(cur_pointer != null)
		{
			//Integer num_records_to_iterate_over = cur_pointer.num_records;
			for(i = 0; i < cur_pointer.getSize(); ++i)
			{
				if(cur_pointer.keys[i] == null)
				{// We found the first E
					if(!found)
					{
						found = true;
						E_pos_i = i;
						E_pos = cur_pointer;
					}
				}
			}
			cur_pointer = cur_pointer.successor;
		}
		
		if(E_pos == null)
		{
			return;
		}
		
		i = 0;
		cur_pointer = E_pos;
		
		Integer num_pos_i = 0;
		IsamDataNode num_pos = null;
		IsamDataNode num_pos_prev = cur_pointer;
		IsamDataNode prev_prev_ptr = cur_pointer;
		
		while(cur_pointer != null)
		{
			Integer num_records_to_iterate_over = cur_pointer.num_records;
			for(i = 0; i < num_records_to_iterate_over; ++i)
			{
				if(cur_pointer.keys[i] == null)
				{
					num_records_to_iterate_over = num_records_to_iterate_over+1;
					if(cur_pointer == E_pos && i == E_pos_i)
						continue;
				}
				else
				{
					num_pos = cur_pointer;
					num_pos_i = i;
				}
			}
			prev_prev_ptr = num_pos_prev;
			num_pos_prev = cur_pointer;
			cur_pointer = cur_pointer.successor;
		}

		if(num_pos.num_records == 0)
		{// root data node case
			return;
		}

		if(E_pos == num_pos)
		{	
			if(E_pos_i < num_pos_i)
			{
				// same node E is before Number. replace it
				E_pos.keys[E_pos_i] = num_pos.keys[num_pos_i];
				E_pos.values[E_pos_i] = num_pos.values[num_pos_i];
				num_pos.keys[num_pos_i] = null;
				num_pos.values[num_pos_i] = null;
				E_pos.num_records++;
				num_pos.num_records--;
				return;
			}
			else
				return;
		}
		if(num_pos.num_records == 1)
		{// not the same overflow page and has only one data item
			
			E_pos.keys[E_pos_i] = num_pos.keys[num_pos_i];
			E_pos.values[E_pos_i] = num_pos.values[num_pos_i];
			num_pos.keys[num_pos_i] = null;
			num_pos.values[num_pos_i] = null;
			E_pos.num_records++;
			num_pos.num_records--;
			prev_prev_ptr.successor = null;
		}
		else
		{
			E_pos.keys[E_pos_i] = num_pos.keys[num_pos_i];
			E_pos.values[E_pos_i] = num_pos.values[num_pos_i];
			num_pos.keys[num_pos_i] = null;
			num_pos.values[num_pos_i] = null;
			E_pos.num_records++;
			num_pos.num_records--;
		}
	}
	
	@Override
	public boolean delete(Integer key) {
		boolean i_have_key = false;
		Integer key_pos = 0;
		Integer num_records_to_iterate_over = num_records;
		
		for(Integer i = 0; i < num_records_to_iterate_over; ++i)
		{
			if(keys[i] == null)
				num_records_to_iterate_over++;
			if(keys[i] == key)
			{
				key_pos = i;
				i_have_key = true;
			}
		}
		if(i_have_key == true)
		{
			keys[key_pos] = null;
			values[key_pos] = null;
			num_records--;
			compact();
			return true;
		}
		
		boolean ret_val = false;
		// handling overflow pages
		if(successor != null)
		{
			if(successor.delete(key) == true)
			{
				if(successor.num_records == 0 && successor.successor != null)
					successor = successor.successor;
				else if(successor.num_records == 0)
					successor = null;
				ret_val = true;
			}
		}
		return ret_val;
	}
}
