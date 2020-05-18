
/**
 * Represent Date day/month/year.
 * day value must be between than 1 and 31
 * month value must be between than 1 and 12
 * year value must be between than 1000 and 9999
 * @author (Andrey Isakov)
 * @version (1)
 */
public class Date
{
    // instance variables 
    private int _day;
    private int _month;
    private int _year;
    private final int MAX_YEAR = 9999;// the maximum number allowed 4 digits
    private final int MIN_YEAR = 1000;// the minimmum number allowed 4 digits
    private final int MAX_DAY = 31;// maximum of days possible in a month
    private final int MIN_DAY = 1;
    
    private final int MAX_MONTH = 12;
    private final int MIN_MONTH = 0;
    private final int FEB_DAY_LEAP_YEAR = 29;

    /**
     * Creating new Date object if the date is valid, otherwise creates the date 1/1/2000 
     * @param day - the day in the month (1-31) 
     * @param month - the month in the year (1-12) 
     * @param year - the year (4 digits)
     */
    public Date(int day, int month, int year)
    {
        // initialise instance variables
        if(validDate(day,month,year)){
            _day = day;
            _month = month;
            _year = year;
        } else{
            _day = 1;
            _month = 1;
            _year = 2000;
        }   
    }
    //Checks if the day between 1 and 31
    private boolean validDay(int day){
        if (day>=MIN_DAY && day<=MAX_DAY)
            return true;
        return false;
    }
    //Checks if the motnh between 1 and 12
    private boolean validMonth(int month){
        if (month>=MIN_MONTH && month<=MAX_MONTH)
            return true;
        return false;
    }
    //Checks if the year between 1000 and 9999 (4 digits limit)
    private boolean validYear(int year){
        if (year>=MIN_YEAR && year<=MAX_YEAR)
            return true;
        return false;
    }
    // Checking the day month and year combined and if the combination is leag by Georgian calendar
    private boolean validDate(int day, int month, int year){
        if (validDay(day) && validMonth(month) && validYear(year)){
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                if(day<=31){
                    return true;

                }else{
                    return false;
                }
            }

            if(month == 4 || month == 6 || month == 9 || month == 11){
                if(day<=30){
                    return true;

                }else{
                    return false;
                }
            }
            if(month == 2){
                if((year%4==0 && year%100!=0) || (year%400 == 0)){
                    if(day<=29)
                        return true;
                    else
                        return false;

                }else{
                    if(day<=28)
                        return true;
                    else
                        return false;

                }
            }
        }
        return false;
    }

    /**
     * Copy Constructor
     * Construct a date with the same variables as  another date.
     * @param other - the date to be copied
     */
    public Date(Date date){
        _day = date._day;
        _month = date._month;
        _year = date._year;
    }

    /** gets the year
     * @return The Year of the date
     */
    public int getYear(){
        return _year;
    }

    /** gets the month 
     * @return The month of the date
     */
    public int getMonth(){
        return _month;
    }

    /** gets the day
     * @return The day of the date
     */
    public int getDay(){
        return _day;
    }

    /** sets the year
     * If illegal number is received date will be unchanged.
     * @param yearToSet - the year value to be set
     */
    public void setYear(int yearToSet){
        if(validDate(_day, _month, yearToSet))

            _year = yearToSet;
    }

    /** set the month
     * If illegal number is received date will be unchanged.
     * @param monthToSet - the month value to be set
     */
    public void setMonth(int monthToSet){
        if(validDate(_day, monthToSet, _year))

            _month = monthToSet;
    }

    /** sets the day 
     * If illegal number is received date will be unchanged.
     *  @param dayToSet - the day value to be set
     */
    public void  setDay(int dayToSet){
        if(validDate(dayToSet,_month , _year))

            _day = dayToSet;
    }

    /**
     * check if 2 dates are the same 
     * @param other - the date to compare this date to
     * @return true if the dates are the same
     */
    public boolean equals(Date other)
    {
        if((_day==other._day)&&(_month==other._month)&&(_year==other._year))
            return true;
        return false;
    }

    /**
     * check if this date is before other date
     * @param other - date to compare this date to 
     * @return true true if this date is before other date
     */
    public boolean before(Date other){
        if(_year<other._year){
            return true;
        }

        if(_year==other._year){
            if(_month<other._month)
                return true;

        }
        if((_year==other._year) && (_month==other._month))
            if(_day<other._day)
                return true;

        return false;

    }

    /**
     * check if this date is after other date
     * @param other - date to compare this date to 
     * @return true if this date is after other date
     */
    public boolean after(Date other){
        if(other.before(this))
            return true;

        return false;    
    }
    // computes the day number since the beginning of the Christian counting of years
    private int calculateDate(int day, int month, int year){
        if(month<3){
            year--;
            month=month+12;
        }
        return 365*year+year/4-year/100+year/400+((month+1)*306)/10+(day-62);
    }

    /**
     * calculates the difference in days between two dates
     * @param other - the date to calculate the difference between
     * @return the number of days between the dates
     */
    public int difference(Date other){
        return Math.abs(calculateDate(this._day,this._month,this._year)-calculateDate(other._day,other._month,other._year));
    }
    /**
     * returns a String that represents this date 
     * @return String representation of this date (day/month/year).
     */
    public String toString()
    {
        String s="";
        if (_day<10)
            s+="0"+_day+"/";
        else
            s+=_day+"/";
        if (_month<10)
            s+="0"+_month+"/";
        else
            s+=_month+"/";    
        s+=_year;  
        return s;

    }

    /**
     * calculate the date of tomorrow 
     * @return the date of tomorrow
     */
    public Date tomorrow (){
        Date tomorrowDay = new Date (1, 1,2000);
        if (validDate (_day +1,_month,_year)){
            tomorrowDay._day= _day +1;
            tomorrowDay._month=_month;
            tomorrowDay._year=_year;
            return tomorrowDay;
        }
        if ( validDate (1,_month+1,_year)){
            tomorrowDay._day=1;
            tomorrowDay._month=_month+1;
            tomorrowDay._year=_year;
            return tomorrowDay;
        }
        if ( validDate (1,1,_year+1)) {
            tomorrowDay._day=1;
            tomorrowDay._month=1;
            tomorrowDay._year=_year+1;
            return tomorrowDay;
        }

        return  tomorrowDay;
    }
    /**
     * calculate the day of the week that this date occurs on 0-Saturday 1-Sunday 2-Monday etc
     * @return the day of the week that this date occurs on.
     */
    public int dayInWeek(){
        int Day ;
        int M =_month;
        int Y,C,MD,YD,CD;
        int trueYear=_year;
        if ((_month==1)){
            M=13;
            trueYear--;
        }
        if ((_month==2)){
            M=14;
            trueYear--;
        }

        Y=trueYear%100;
        C=trueYear/100;
        CD=C/4;
        YD=Y/4;
        MD=26*(M +1)/10;   

        Day=(_day+MD+Y+YD+CD-2*C)% 7;
        if(Day<0)
            Day=Math.floorMod(Day,7);
        return Day;
    }

}
 