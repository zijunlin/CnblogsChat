package com.cnblogs.keyindex.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnblogs.keyindex.model.Section;


 class SectionTable {

	private HashMap<String, Section> map;

	public SectionTable() {
		map = new HashMap<String, Section>();
	}

	public void regSection(Section section) {
		map.put(section.getAction(), section);
	}

	public void clearSection() {
		map.clear();
	}
	
	public Section getSection(String key)
	{
		return map.get(key);
	}

	public List<Section> getAllSection() {
		List<Section> list = new ArrayList<Section>();
		for (Map.Entry<String, Section> item : map.entrySet()) {
			list.add(item.getValue());
		}
		return list;
	}

}
