package demos;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;


import de.fhpotsdam.unfolding.marker.Marker;

public class MyOwnLifeExpectancy extends PApplet{

	UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800,600,OPENGL);
		map=new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this,map);
	
		lifeExpMap=loadLifeExpFromCSV("LifeExpectancyWorldBankModule3.csv");
		println("Loaded " + lifeExpMap.size() + " data entries");
	
		countries=GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers=MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
		shadeCountries();
	}
	
	public void draw() {
		map.draw();
	}
	
	private void shadeCountries() {
		for(Marker marker : countryMarkers) {
			String countryId=marker.getId();
			if(lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp,40,90,10,255);
				marker.setColor(color(255-colorLevel,100,colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}
	
	private HashMap<String, Float> loadLifeExpFromCSV(String filename) {
		HashMap<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows= loadStrings(filename);
		for(String row : rows) {
			String[] columns = row.split(",");
			if(columns.length == 6 && !columns[5].equals("..")) {
				lifeExpMap.put(columns[4], Float.parseFloat(columns[5]));
			}
		}
		return lifeExpMap;
	}
}


