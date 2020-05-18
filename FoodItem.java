
/**
 * This class represents a FoodItem object 
 *
 * @author (Andrey Isakov)
 * @version (1)
 */
public class FoodItem
{
    // instance variables - replace the example below with your own
    private String _name ;
    private long _catalogueNumber;
    private int _quantity;
    private Date _productionDate;
    private Date _expiryDate;
    private int _minTemperature;
    private int _maxTemperature;
    private int _price;
    private final String DEF_ITEM_NAME = "item";
    private final int DEF_CATALOG_NUMBER = 9999;
    private final int MIN_CATALOG_NUMBER = 1000;
    private final int DEF_QUANTITY_NUMBER = 0;
    private final int DEF_PRICE_NUMBER = 1;
    private final int MIN_MONEY_NUMBER = 1;
    /**
     * creates a new FoodItem object
     * @param name - name of food item
     * @param catalogueNumber - catalogue number of food item
     * @param quantity - quantity of food item
     * @param productionDate - production date
     * @param expiryDate - expiry date
     * @param minTemperature - minimum storage temperature
     * @param maxTemperature - maximum storage temperature price - unit price
     * @param price - unit price
     */
    public FoodItem(String name, long catalogNumber,int quantity, Date proDate, Date expDate, int minTemperature,int maxTemperature, int price)
    {
        // initialise instance variables
        _name = name;
        if(name == "")
            _name = DEF_ITEM_NAME;

        if(catalogNumber>=MIN_CATALOG_NUMBER && catalogNumber<=DEF_CATALOG_NUMBER)
            _catalogueNumber = catalogNumber;
        else
            _catalogueNumber = DEF_CATALOG_NUMBER;

        if(validQuantity(quantity))
            _quantity = quantity;

        else
            _quantity = DEF_QUANTITY_NUMBER;  

        if(validPrice(price))
            _price = price;

        else
            _price = DEF_PRICE_NUMBER;

        if( productionCompareExpiry(expDate,proDate)){
            _expiryDate = proDate.tomorrow();
            _productionDate = new Date(proDate);

        }else{
            _productionDate = new Date(proDate);
            _expiryDate = new Date(expDate);
        }

        _maxTemperature = maxTemperature;
        _minTemperature = minTemperature;

        if(minTemperature>maxTemperature){
            _maxTemperature = minTemperature;
            _minTemperature = maxTemperature;

        }

    }
    /**
     * copy constructor
     * @param other - the food item to be copied
     */
    public FoodItem (FoodItem other)
    {
        _name=other._name;
        _catalogueNumber=other._catalogueNumber;
        _quantity = other._quantity;
        _productionDate=new Date(other._productionDate);
        _expiryDate=new Date(other._expiryDate);
        _minTemperature = other._minTemperature;
        _maxTemperature = other._maxTemperature;
        _price=other._price;
    }   

    
    // cheking if expiry date given before production date given
    private boolean productionCompareExpiry(Date expiry,Date production){
        if(expiry.before(production)){
            return true;

        }return false;
    }
    
    //Calculate the maximum temperature
    private boolean tempMax(int minTemperature,int maxTemperature){
        if(minTemperature>maxTemperature)
            return true;
        else
            return false;

    }
    
    //Checking if the quantity given to the methon valid less than 0 <0
    private boolean validQuantity(int quantity){
        if(quantity<0)
            return false;
        return true;    
    }
    
    //Checking if the quantity given to the methon valid more than 0 <1
    private boolean validPrice(int price){
        if(price<1)
            return false;
        return true;    
    }

    /** gets the name 
     * @return the name 
     */
    public String getName(){
        return _name;
    }

    /** gets the catalog number
     * @return the catalogue numbe
     */
    public long getCatalogueNumber(){
        return _catalogueNumber;
    }

    /** gets the quantity 
     * @return the quantity
     */
    public int getQuantity(){
        return _quantity;
    }

    /** gets the production date
     * @return the production date
     */
    public Date getProductionDate(){
        return new Date (_productionDate);
    }

    /** gets the expiry date
     * @return the expiry date
     */
    public Date getExpiryDate(){
        return new Date (_expiryDate);
    }

    /** gets the minimum storage temperature
     * @return the minimum storage temperature
     */
    public int getMinTemperature(){
        return _minTemperature;
    }

    /** gets the maximum storage temperature
     * @return the maximum storage temperature
     */
    public int getMaxTemperature(){
        return _maxTemperature;
    }

    /** gets the unit price 
     * @return the unit price
     */
    public int getPrice(){
        return _price;
    }
    
    
    /** set the quantity (only if not negative)  
     * @param quantityToSet - the quantity value to be set
     */
    public void setQuantity(int quantityToSet){
        if(validQuantity(quantityToSet))

            _quantity = quantityToSet;
    }
    
    
    /** set the expiry date (only if not before production date ) 
     * @param expiryDateToSet - expiry date value to be set
     */
    public void setExpiryDate(Date expiryDateToSet){

        if(productionCompareExpiry(expiryDateToSet,_productionDate))
            _expiryDate = _expiryDate;

        else
            _expiryDate = expiryDateToSet;
    }
    
    
    /** set the production date (only if not after expiry date ) 
     * @param productionDateToSet - production date value to be set
     */
    public void setProductionDate(Date productionDateToSet){
        if(productionCompareExpiry(_expiryDate,productionDateToSet))
            _productionDate = _productionDate;

        else
            _productionDate = productionDateToSet;
    }
    

    /** set the price (only if positive) 
     * @param priceToSet - price value to be set
     */
    public void setPrice(int priceToSet){
        if(validPrice(priceToSet))
            _price = priceToSet;
        else
            _price = _price;

    }

    /**
     * check if 2 food items are the same (excluding the quantity values)
     * @param other - the food item to compare this food item to 
     * @return true if the food items are the same
     */
    public boolean equals(FoodItem other){
        return((this._name.equals(other._name))&&(this._catalogueNumber == other._catalogueNumber)&&(this._productionDate.equals(other._productionDate))&&
            (this._expiryDate.equals(other._expiryDate))&&(this._minTemperature == other._minTemperature)&&(this._maxTemperature == other._maxTemperature)&&
            (this._price == other._price));

    }
    /**
     * check if this food item is fresh on the date d 
     * @param d - date to check   
     * @return true if this food item is fresh on the date d
     */
    public boolean isFresh(Date d){
        if((d.after(_productionDate)&&(d.before(_expiryDate))) || d.equals(_productionDate)|| d.equals(_expiryDate)){
            return true;

        }
        return false;
    }
    /**
     * returns a String that represents this food item
     * @return tring that represents this food item in the following format:
     * FoodItem: milk CatalogueNumber: 1234 ProductionDate: 14/12/2019 ExpiryDate: 21/12/2019 Quantity: 3 
     */
    public String toString(){
        return("FoodItem: "+_name+"\t"+"CatalogueNumber: "+_catalogueNumber+"\t"+"ProductionDate: "+_productionDate.toString()+"\t"+"ExpiryDate: "+_expiryDate.toString()+"\t"+"Quantity: "+_quantity);
    }
    /**
     * check if this food item is older than other food item 
     * @param other - food item to compare this food item to  
     * @return true if this food item is older than other date
     */
    public boolean olderFoodItem(FoodItem other){
        if(this._productionDate.before(other._productionDate))  
            return true;

        return false;    
    }
    /**
     * returns the number of units of products that can be purchased for a given amount 
     * @param amount  - amount  to purchase  
     * @return the number of units can be purchased
     */
    public int howManyItems(int amount ){
        if(amount <MIN_MONEY_NUMBER){

            return 0;
        }
        if(amount >=MIN_MONEY_NUMBER&&this._quantity>0){
            if(this._quantity*this._price>=amount ){    
                return (amount /this._price);
            }else{
                return this._quantity;
            }

        }

        return 0;
    }
    /**
     * check if this food item is cheaper than other food item 
     * @param other - food item to compare this food item to  
     * @return true if this food item is cheaper than other date
     */
    public boolean isCheaper(FoodItem other){
        if(this._price < other._price)  
            return true;
        return false;    
    }
}