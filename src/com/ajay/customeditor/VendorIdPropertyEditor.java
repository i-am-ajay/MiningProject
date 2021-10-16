package com.ajay.customeditor;

import java.beans.PropertyEditorSupport;
// This property editor will read vendor id from the page and based on the id it will fetch the vendor from client table.

import com.mine.component.master.Client;
import com.mine.service.MiningService;

public class VendorIdPropertyEditor extends PropertyEditorSupport{
	MiningService service;
	public VendorIdPropertyEditor(MiningService service) {
		this.service = service;
	}
	@Override
	public void setAsText(String vendorId) throws IllegalArgumentException{
		System.out.println("Vendor Id"+vendorId);
		int id = Integer.parseInt(vendorId);
		Client client = service.getClient(id);
		setValue(client);
	}
}
