package DBMS;


public class Date {
	private String date;
	public Date(String _date) {
		date = _date;
	}
	public boolean validate(){
		try{
			String[] parsedDate = this.date.split("/");
			if(parsedDate.length!=3)return false;
			int days = Integer.parseInt(parsedDate[0]);
			int months = Integer.parseInt(parsedDate[1]);
			int years = Integer.parseInt(parsedDate[2]);
			if(days>31 || days<=0)return false;
			if(months>12 || months<=0) return false;
			if(years<1)return false;
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
}

