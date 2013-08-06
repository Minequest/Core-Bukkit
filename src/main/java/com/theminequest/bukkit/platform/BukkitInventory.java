package com.theminequest.bukkit.platform;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQInventory;
import com.theminequest.api.platform.MQItemStack;

public class BukkitInventory implements MQInventory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 486298531552899195L;
	private Inventory inventory;
	
	public BukkitInventory(Inventory platformInventory) {
		this.inventory = platformInventory;
	}
	
	@Override
	public boolean add(MQItemStack arg0) {
		HashMap<Integer, ItemStack> unstored = inventory.addItem((ItemStack) Managers.getPlatform().fromItemStack(arg0));
		return unstored.isEmpty();
	}
	
	@Override
	public void add(int arg0, MQItemStack arg1) {
		if (arg0 >= inventory.getSize() || arg0 < 0)
			throw new IllegalArgumentException("Invalid location!");
		inventory.setItem(arg0, (ItemStack) Managers.getPlatform().fromItemStack(arg1));
	}
	
	@Override
	public boolean addAll(Collection<? extends MQItemStack> arg0) {
		for (MQItemStack stack : arg0)
			add(stack);
		return true;
	}
	
	@Override
	public boolean addAll(int arg0, Collection<? extends MQItemStack> arg1) {
		return addAll(arg1);
	}
	
	@Override
	public void clear() {
		inventory.clear();
	}
	
	@Override
	public boolean contains(Object arg0) {
		if (!(arg0 instanceof MQItemStack))
			return false;
		MQItemStack check = (MQItemStack) arg0;
		return inventory.contains((ItemStack) Managers.getPlatform().fromItemStack(check));
	}
	
	@Override
	public boolean containsAll(Collection<?> arg0) {
		for (Object obj : arg0)
			if (!contains(obj))
				return false;
		return true;
	}
	
	@Override
	public MQItemStack get(int arg0) {
		if (arg0 >= inventory.getSize() || arg0 < 0)
			throw new IllegalArgumentException("Invalid location!");

		return Managers.getPlatform().toItemStack(inventory.getItem(arg0));
	}
	
	@Override
	public int indexOf(Object arg0) {
		if (!(arg0 instanceof MQItemStack))
			return -1;
		MQItemStack stack = (MQItemStack) arg0;
		return inventory.first((ItemStack) Managers.getPlatform().fromItemStack(stack));
	}
	
	@Override
	public boolean isEmpty() {
		ItemStack[] stack = inventory.getContents();
		for (int i = 0; i < stack.length; i++)
			if (stack[i] != null)
				return false;
		return true;
	}
	
	@Override
	public Iterator<MQItemStack> iterator() {
		return new Iterator<MQItemStack>() {
			
			ItemStack[] stack = inventory.getContents();
			int i = 0;
			boolean removed = true;

			@Override
			public boolean hasNext() {
				return i >= stack.length;
			}

			@Override
			public MQItemStack next() {
				if (!hasNext())
					throw new NoSuchElementException();
				ItemStack current = stack[i];
				i++;
				removed = false;
				return Managers.getPlatform().toItemStack(current);
			}

			@Override
			public void remove() {
				if (removed)
					throw new IllegalStateException();
				removed = true;
				inventory.setItem(i - 1, null);
			}
			
		};
	}
	
	@Override
	public int lastIndexOf(Object arg0) {
		if (!(arg0 instanceof MQItemStack))
			return -1;
		MQItemStack stack = (MQItemStack) arg0;
		
		ItemStack ourStack = Managers.getPlatform().fromItemStack(stack);
		
		ItemStack[] contents = inventory.getContents();
		for (int i = contents.length - 1; i >= 0; i--) {
			if (ourStack.equals(contents[i]))
				return i;
		}
		return -1;
	}
	
	@Override
	public ListIterator<MQItemStack> listIterator() {
		return new ListIterator<MQItemStack>() {
			
			ItemStack[] stack = inventory.getContents();
			int i = 0;

			@Override
			public void add(MQItemStack arg0) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasNext() {
				return i < stack.length;
			}

			@Override
			public boolean hasPrevious() {
				return i - 1 >= 0;
			}

			@Override
			public MQItemStack next() {
				if (!hasNext())
					throw new NoSuchElementException();
				ItemStack item = stack[i];
				i++;
				return Managers.getPlatform().toItemStack(item);
			}

			@Override
			public int nextIndex() {
				return i;
			}

			@Override
			public MQItemStack previous() {
				if (!hasPrevious())
					throw new NoSuchElementException();
				i--;
				ItemStack item = stack[i];
				return Managers.getPlatform().toItemStack(item);
			}

			@Override
			public int previousIndex() {
				return i - 1;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void set(MQItemStack arg0) {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	@Override
	public ListIterator<MQItemStack> listIterator(final int arg0) {
		return new ListIterator<MQItemStack>() {
			
			ItemStack[] stack = inventory.getContents();
			int i = arg0;

			@Override
			public void add(MQItemStack arg0) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasNext() {
				return i < stack.length;
			}

			@Override
			public boolean hasPrevious() {
				return i - 1 >= 0;
			}

			@Override
			public MQItemStack next() {
				if (!hasNext())
					throw new NoSuchElementException();
				ItemStack item = stack[i];
				i++;
				return Managers.getPlatform().toItemStack(item);
			}

			@Override
			public int nextIndex() {
				return i;
			}

			@Override
			public MQItemStack previous() {
				if (!hasPrevious())
					throw new NoSuchElementException();
				i--;
				ItemStack item = stack[i];
				return Managers.getPlatform().toItemStack(item);
			}

			@Override
			public int previousIndex() {
				return i - 1;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void set(MQItemStack arg0) {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	@Override
	public boolean remove(Object arg0) {
		if (!(arg0 instanceof MQItemStack))
			return false;
		
		MQItemStack stack = (MQItemStack) arg0;
		ItemStack bStack = Managers.getPlatform().fromItemStack(stack);
		
		if (!inventory.contains(bStack))
			return false;
		
		inventory.remove(bStack);
		
		return true;
	}
	
	@Override
	public MQItemStack remove(int arg0) {
		if (arg0 >= inventory.getSize() || arg0 < 0)
			throw new IllegalArgumentException("Invalid location!");

		ItemStack stack = inventory.getItem(arg0);
		if (stack == null)
			return null;
		inventory.remove(stack);
		
		return Managers.getPlatform().toItemStack(stack);
	}
	
	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean result = false;
		
		for (Object o : arg0) {
			if (remove(o))
				result = true;
		}
		
		return result;
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MQItemStack set(int arg0, MQItemStack arg1) {
		if (arg0 >= inventory.getSize() || arg0 < 0)
			throw new IllegalArgumentException("Invalid location!");
		
		ItemStack oldStack = inventory.getItem(arg0);
		inventory.setItem(arg0, (ItemStack) Managers.getPlatform().fromItemStack(arg1));
		
		if (oldStack == null)
			return null;
		return Managers.getPlatform().toItemStack(oldStack);
	}
	
	@Override
	public int size() {
		return inventory.getSize();
	}
	
	@Override
	public List<MQItemStack> subList(int arg0, int arg1) {
		throw new UnsupportedOperationException("no.");
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[inventory.getSize()];
		
		ItemStack[] copy = inventory.getContents();
		for (int i = 0; i < copy.length; i++) {
			if (copy[i] != null)
				array[i] = Managers.getPlatform().toItemStack(copy[i]);
		}
		return array;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] arg0) {
		if (!(arg0 instanceof MQItemStack[]))
			throw new ArrayStoreException("only MQItemStack[] accepted");
		
		ItemStack[] copy = inventory.getContents();
		
		for (int i = 0; i < arg0.length; i++) {
			if (i >= copy.length)
				break;
			if (copy[i] != null)
				arg0[i] = (T) Managers.getPlatform().toItemStack(copy[i]);
		}
		return arg0;
	}

	@Override
	public boolean equals(Object obj) {
		BukkitInventory other = (BukkitInventory) obj;
		return inventory.equals(other.inventory);
	}	
}
