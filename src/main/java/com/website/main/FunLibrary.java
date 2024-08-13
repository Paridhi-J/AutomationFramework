package com.website.main;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.website.pages.GetPages;

public class FunLibrary extends SelLibrary{
	public Logger testLog= LogManager.getLogger("debugLogger:"+ Thread.currentThread().getId());
	public int random_bag_flag=-1;
	public String method_name="";
	public String first_name="";
	public String last_name="";
	public String username="";
	public String password="";
	public GetPages getPages;
	public FunLibrary()
	{
		load_Obj_Repository();
		getExcelData(sheet_name);
		getPages= new GetPages();
		
	}
	private void getExcelData(String sheet_name) {
		// TODO Auto-generated method stub
		username=datatable.getCellData(sheet_name, "username", currentRow);
		password=datatable.getCellData(sheet_name, "password", currentRow);
		method_name=datatable.getCellData(sheet_name, "method_name", currentRow);
		first_name=datatable.getCellData(sheet_name, "first_name", currentRow);
		last_name=datatable.getCellData(sheet_name, "last_name", currentRow);
	}

}
