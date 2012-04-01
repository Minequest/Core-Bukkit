package com.theminequest.MineQuest.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtils {
	
	@SuppressWarnings({ "unchecked" })
	public static <E> List<E> convert(Set<E> s){
		List<E> toreturn = new ArrayList<E>();
		Iterator<?> i = s.iterator();
		while (i.hasNext()){
			toreturn.add((E) i.next());
		}
		return toreturn;
	}
	
	public static List<Comparable> sort(Set<? extends Comparable> s){
		List<Comparable> l = new ArrayList<Comparable>();
		for (Comparable c : s){
			l.add(c);
		}
		MergeSort m = new MergeSort(l);
		m.sort();
		return m.getSorted();
	}
	
	@SuppressWarnings("rawtypes")
	private static class MergeSort {
		
		private Comparable[] temp;
		private List<Comparable> tosort;
		
		public MergeSort(List<Comparable> array){
			tosort = array;
			temp = new Comparable[array.size()];
		}
		
		public void sort(){
			sort(tosort,0,tosort.size()-1);
		}
		
		
		private void sort(List<Comparable> array, int from, int to){
			
			if (to == from)
				return;
			
			int middle = (from+to)/2;
			
			sort(array, middle+1,to);
			sort(array, from, middle);
			
			int i = from, j = middle+1, k = from;
			
			while (i<=middle && j<=to){
				if (array.get(i).compareTo(array.get(j))<0)
					temp[k++] = array.get(i++);
				else
					temp[k++] = array.get(j++);
			}
			
			while (i<=middle)
				temp[k++] = array.get(i++);
			while (j<=to)
				temp[k++] = array.get(j++);
			
			for (k = from; k<= to; k++)
				array.set(k, temp[k]);
		}
		
		public List<Comparable> getSorted(){
			return tosort;
		}
	}

}
