/**
 * Represents stock of FoodItem objects
 * @author (Andrey Isakov)
 * @version (30/12/19)
 */
public class Stock
{

    private FoodItem[]_stock;
    private int _noOfItems;
    private final int MAX_STOCK=100;

    /**
     * Default constructor for objects of class Stock
     */
    public Stock() {

        _stock=new FoodItem[MAX_STOCK];
        _noOfItems=0;
    }

    /**
     * return the number of the items (max 100 items)
     * @return number of items.
     */

    public int getNumOfItems()
    {
        return  _noOfItems;
    }

    /**
     *  addItem new item to the stock in case there is no same item (means same name,cataluge number,production/expiry date) and if there is enough place
     *  if same item exist the quantity of ths item would be changed
     *  @param food the item to add. 
     * @return true if the add Succeeded
     */
    public boolean addItem (FoodItem food){
        if (_noOfItems==_stock.length)
            return false;
        if(!existsInTheStock(food)){ 
            _noOfItems++;
            addNewToStock(food);
            return true;
        }
        return true;
    }
    //cheks if the item  exists in the stock with the same name and catalog number and if the production date and the expiry date are the same as well 
    //if its true its add the quantity of the new seting of quantity to the exists one. 
    private boolean existsInTheStock(FoodItem food){
        int i;
        for(i=0;i<_noOfItems;i++){
            if  (_stock[i].getName().equals(food.getName()) && _stock[i].getCatalogueNumber()==food.getCatalogueNumber()){
                if(_stock[i].getProductionDate().equals(food.getProductionDate())&& _stock[i].getExpiryDate().equals(food.getExpiryDate())){
                    _stock[i].setQuantity(_stock[i].getQuantity()+food.getQuantity());
                    return true;
                }

            }
        }
        return false;
    }
    //put the stock in order by the catalog number from small to big
    private void addNewToStock (FoodItem food)
    {

        if (!similarNameAndCatalogNumber(food)){//if there is no similar name and catalog number add new item to the stock
            _stock[_noOfItems-1]=new FoodItem (food);
            FoodItem a =new FoodItem (food);
            if(_noOfItems>1)

                for(int i =0 ; i<_noOfItems-1;i++) //put the stock in order by the catalog number from small to big
                    for(int j=0;j<_noOfItems-1;j++)
                        if (_stock[j].getCatalogueNumber()>_stock[j+1].getCatalogueNumber()){
                            a=new FoodItem (_stock[j]);
                            _stock[j]=_stock[j+1];
                            _stock[j+1]=a;

                        }
        }
    }
    //cheks if the name and the catalog number are the same but differns in the production or the expiry date
    private boolean similarNameAndCatalogNumber( FoodItem food){

        for(int i=0;i<_noOfItems-1;i++)  
            if (_stock[i].getName().equals(food.getName()) && _stock[i].getCatalogueNumber()==food.getCatalogueNumber())
                if  (!_stock[i].getProductionDate().equals(food.getProductionDate())||(! _stock[i].getExpiryDate().equals(food.getExpiryDate()))){
                    for(int j=_noOfItems;j>=i;j--){
                        _stock[j+1]=_stock[j];

                    }
                    _stock[i]=new FoodItem (food);//puts the new item befor the item with same name and catalogue number
                    return true;
                }
        return false; 
    } 

    /**
     * return list of all item names that should be purchased 
     * @param amount the amount to comper the quantity to 
     * @return list of all item that should be purchased 
     */

    public String order(int amount){

        String s = "";
        int a=0,i,c=0;//a-counter that sum all the quantity of certain item ,c-counts how many times the counter a < amount
        
        for(i=0;i<=_noOfItems-1;i++){
            a = _stock[i]. getQuantity();
            while(_noOfItems>1&&(i<_noOfItems-1)&&(_stock[i].getName().equals(_stock[i+1].getName()))&&_stock[i].getCatalogueNumber() == _stock[i+1].getCatalogueNumber()){
                a += _stock[i+1]. getQuantity();//this while checks if there is another item with the same name and catalogue number and if yes add the quntity to the counter a 
                i++;

            } 

            if(a<amount){
                c++;
                if (c==1)
                    s+=_stock[i].getName();
                else    
                    s+=", "+_stock[i].getName();

            }

        }
        return s;
    }

    /**
     * return how many pisces of items can move to fridge with particular temp
     * @param temp a temperature in a particular refrigerator
     * @return number of pisces that can be move to fridge
     */

    public int howMany(int temp){
        int c = 0;//counts the pisces that can move to  to a particular fridge
        for(int i=0;i<_noOfItems;i++){
            if(_stock[i].getMaxTemperature()>=temp&&_stock[i].getMinTemperature()<=temp)
                c+=_stock[i]. getQuantity();
        } 
        return c;
    }

    /**
     * Deletes from stock all food Items that expire before the date d
     * @param d the date to comper the expiry date to.
     */

    public void removeAfterDate(Date d){
        for(int i=0;i<=_noOfItems-1;i++){
            if(_stock[i].getExpiryDate().before(d)){
                i=+removeItem(i);

            }
        }
    }   

    /**
     * Returns the most expensive product in stock
     * @return the first item with the most expensive price  
     */

    public FoodItem mostExpensive(){
        if(_noOfItems>1){
            int mostExp = _stock[0].getPrice();// find the biggest price in the stock
            for(int i =1;i<=_noOfItems-1;i++){
                if(mostExp<_stock[i].getPrice()){
                    mostExp = _stock[i].getPrice();
                }
            }
            for(int j = 0;j<=_noOfItems-1;j++){//find the first item that has the biggest price
                if(_stock[j].getPrice()==mostExp)
                    return _stock[j];

            }
        }
        return null;
    }

    /**
     * Return how many pieces in the stock.
     * @return how many pieces in the stock. 
     */

    public int howManyPieces(){
        int quantityCounter = 0;
        if(_noOfItems>1){
            for(int i = 0;i<=_noOfItems-1;i++){
                quantityCounter+=_stock[i].getQuantity();

            }
            return quantityCounter;

        }
        return 0;
    }

    /**
     * rturns a String that represents this stock
     * @return  String that represents this stock follows- FoodItem:Milk CatalogueNumber:1111    ProductionDate:8/9/2019   ExpiryDate:18/9/2019   Quantity:4
     */

    public String toString(){
        String s = "";

        for(int i = 0;i<=_noOfItems-1;i++){
            s+="FoodItem:"+_stock[i].getName()+" "+"CatalogueNumber:"+_stock[i].getCatalogueNumber()+"\t"+"ProductionDate:"+_stock[i].getProductionDate()+" "+"ExpiryDate:"
            +_stock[i].getExpiryDate()+"\t"+"Quantity:"+_stock[i].getQuantity()+"\n";
        }
         
        return s;
    }
    //removes item from the stock
    private int removeItem(int j ){
        if(_noOfItems>1){
            for(int i=j;i< _noOfItems-1;i++){

                _stock[i] = _stock[i+1];
            }
            _stock[_noOfItems-1]=null;
            _noOfItems--;
            return -1;//if from point "i" the stock moved one place left now there is new item in "i" place so we need to come back to this plase in the stock to prevent skiping Items 
            //so we add -1 to "i" and after "i++" its come back to check the new item that took this plase in the stock 
        }

        if(j==_noOfItems-1){

            _stock[j] = null;
            _noOfItems--;

        }

        return 0;//if there is no need to come bake and check the "i" place again
    }

    /**
     * Receives a list of items that sold + updates the stock
     * @param list of Item names to update
     */

    public void updateStock (String arr[]){
        int itemsToUpdate=arr.length;
        for(int i=0;i<=_noOfItems-1&&itemsToUpdate>0;i++){

            for(int j=0;itemsToUpdate>0 && j<=itemsToUpdate-1 && i<=_noOfItems-1;j++){
                if(arr[j].equals(_stock[i].getName())){
                    if(_stock[i].getQuantity()>0){
                        _stock[i].setQuantity(_stock[i].getQuantity()-1);
                        for(int q=j;q<itemsToUpdate-1&&itemsToUpdate>1;q++){//remove the apdateded name from the list
                            arr[q]=arr[q+1];

                        }
                        arr[itemsToUpdate-1]=null;
                        j--;//  come back to this point in the arr to prevent skiping names in the list
                        itemsToUpdate--;

                        if (_stock[i].getQuantity()==0 ){      //if the quantity is 0 remove the item

                            i+= removeItem(i);//remove the item that in point i
                            i++;
                        }
                    }
                }

            }
        }  
    }

    /**
     * Rturns  the minimum temperature that should be the refrigerator containing all food products
     * If it is not possible to find a suitable temp, the value will be returned The maximum integer number
     * @return minimum temperature that should be the refrigerator containing all food products
     * 
     */

    public int getTempOfStock (){
        int minForAll ,maxForAll,i=0;
        if(_noOfItems==0)
            return   Integer.MAX_VALUE;   
        minForAll =_stock[i].getMinTemperature();
        maxForAll=_stock[i].getMaxTemperature();
        for( i=1;i<=_noOfItems-1;i++){
            if(_stock[i].getMinTemperature()> maxForAll|| _stock[i].getMaxTemperature()<minForAll)
                return Integer.MAX_VALUE;
            else
            if(minForAll<_stock[i].getMinTemperature()&&_stock[i].getMinTemperature()<= maxForAll)
                minForAll=_stock[i].getMinTemperature();
            if(maxForAll>_stock[i].getMaxTemperature()&&_stock[i].getMaxTemperature()>=minForAll)
                maxForAll=_stock[i].getMaxTemperature();

        }
        return minForAll ;   
    }
}


