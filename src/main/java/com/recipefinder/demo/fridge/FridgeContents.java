package com.recipefinder.demo.fridge;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.recipefinder.demo.support.Unit;

public class FridgeContents {
	String item;
	int amount;
	Unit unit;
	Date useBy;
	
	public FridgeContents (String item, String amount, String unit, String useBy) {
		this.item = item;
		this.amount = Integer.parseInt(amount);
		this.unit = Unit.valueOf(unit);
		
		DateFormat format = new SimpleDateFormat("dd/M/yyyy");
		try {
			this.useBy = format.parse(useBy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getUseBy() {
		return useBy;
	}
	public void setUseBy(Date useBy) {
		this.useBy = useBy;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	//@Override
	  public int compare(FridgeContents o) {
	    return getUseBy().compareTo(o.getUseBy());
	  }
	
}
