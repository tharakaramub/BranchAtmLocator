package com.atmloc.jpmcatmlocator.data;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationListData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> root;
	private List<Location> locations;
	
	public List<String> getRoot() {
		return root;
	}
	public void setRoot(List<String> root) {
		this.root = root;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
