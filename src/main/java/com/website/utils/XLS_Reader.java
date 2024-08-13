package com.website.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLS_Reader {
	public String path;
	public FileInputStream fis=null;
	public FileOutputStream fos=null;
	private XSSFWorkbook workbook= null;
	private XSSFSheet sheet= null;
	private XSSFRow row= null;
	private XSSFCell cell= null;
	
	
	
	

	public XLS_Reader(String path) {
		// TODO Auto-generated constructor stub
	this.path=path;
	try {
		fis= new FileInputStream(path);
		System.out.println("path is "+fis);
		workbook= new XSSFWorkbook(fis);
		sheet= workbook.getSheetAt(0);
		fis.close();
	}catch(Exception e)
	{
		e.printStackTrace();
	}

	}
	public int getRowCount(String sheetName) {
		int index=workbook.getSheetIndex(sheetName);
		if (index==-1)
		{
			return 0;
		}else
		{
			sheet=workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}
	}
	public String getCellData(String sheetName,String colName,int rowNum)
	{
		try {
			if(rowNum<=0)
			{
				return "";
			}
			int index=workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";
			sheet=workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for( int i=0;i<row.getLastCellNum();i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if (col_Num==-1)
				return "";
			sheet=workbook.getSheetAt(index);
			row=sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);
			if(cell==null)
				return "";
			if(cell.getCellType() == CellType.STRING)
			{
				return cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC|| cell.getCellType()==CellType.FORMULA)
			{
				String cellText=String.valueOf(cell.getNumericCellValue());
				if(DateUtil.isCellDateFormatted(cell)) {
					double d=cell.getNumericCellValue();
					Calendar cal=Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText=(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText=cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+1+"/"+cellText;
				}
				return cellText;
			}
			else if(cell.getCellType()==CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "row"+rowNum+"or column does not exist"+colName;
		}
		
	}
	

}
