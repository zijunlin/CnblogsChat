package com.cnblogs.keyindex.kernel;

import java.util.ArrayList;
import java.util.List;


import com.cnblogs.keyindex.model.Section;


 class SectionTable {

	 private List<Section> sections;
	 private List<String> keys;

	public SectionTable() {
//		map = new HashMap<String, Section>();
		sections=new ArrayList<Section>();
		keys=new ArrayList<String>();
	}

	public void regSection(Section section) {
		
		sections.add(sections.size(), section);
		keys.add(keys.size(),section.getAction());
	}

	public void clearSection() {
		sections.clear();
		keys.clear();
	}
	
	public Section getSection(String key)
	{
		int index=keys.indexOf(key);
		if(index!=-1)
			return sections.get(index);
		return null;
	}

	public List<Section> getAllSection() {
		
		return sections;
	}

}
