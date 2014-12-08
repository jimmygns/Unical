import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * CSE 12 HW8
 * jinye xu
 * A99076165
 * B00
 * May 27
 * 
 * @author jimmy
 *
 */
public class Quantity {
	private double num;
	private Map<String,Integer> map;
	
	/**
	 * 1-argument constructor
	 */
	public Quantity(){
		num=1;
		map=new HashMap<String,Integer>();
	}
	/**
	 * make a deep copy of the quantity copy
	 * @param copy, a quantity object
	 */
	public Quantity(Quantity copy){
		this.num=copy.num;
		this.map=new HashMap<String,Integer>();
		this.map.putAll(copy.map);
	}
	
	/**
	 * initialize a new quantity object
	 * @param number, the numeric value
	 * @param unitInN, the list that stores positive unit
	 * @param unitInD, the list that stores negative unit
	 */
	public Quantity(double number,List<String> unitInN, List<String> unitInD){
		if(unitInN==null||unitInD==null)
			throw new IllegalArgumentException();
		num=number;
		map=new HashMap<String,Integer>();
		if(unitInN.size()!=0)
			map.put(unitInN.get(0), unitInN.size());
		if(unitInD.size()!=0)
			map.put(unitInD.get(0),unitInD.size()*-1);
	}
	
	
	/**
	 * perform multiplication between two quantities
	 * @param q, the multiplier
	 * @return product
	 */
	public Quantity mul(Quantity q){
		//checking the if the input if valid
		if(q==null)
			throw new IllegalArgumentException();
		
		double newNum=q.num*this.num;
		//initialize the quantity which we will return in later
		Quantity newQ=new Quantity();
		newQ.num=newNum;
		//making arrays of units in both quantities
		Object[] arrayKeys=this.map.keySet().toArray();
		Object[] arrayKeysInQ=q.map.keySet().toArray();
		//perform the multiplication of units and values
		for(int i=0;i<arrayKeys.length;i++){
			if(q.map.containsKey(arrayKeys[i])){
				if((this.map.get(arrayKeys[i])+q.map.get(arrayKeys[i]))!=0)
					newQ.map.put((String) arrayKeys[i], this.map.get(arrayKeys[i])+q.map.get(arrayKeys[i]));
			}
			else{
				newQ.map.put((String) arrayKeys[i], this.map.get(arrayKeys[i]));	
			}
		}
		//adding the missing units into the final quantity
		for(int i=0;i<arrayKeysInQ.length;i++){
			if(!newQ.map.containsKey(arrayKeysInQ[i])&&!this.map.containsKey(arrayKeysInQ[i]))
				newQ.map.put((String) arrayKeysInQ[i], q.map.get(arrayKeysInQ[i]));
		}
		
		
		return newQ;
		
	}
	
	/**
	 * perform division between two quantities
	 * @param q, the dividend 
	 * @return 
	 */
	public Quantity div(Quantity q){
		if(q==null||q.num==0)
			throw new IllegalArgumentException();
		Quantity newQ=new Quantity();
		newQ.num=this.num/q.num;
		//making arrays of units
		Object[] arrayKeys=this.map.keySet().toArray();
		Object[] arrayKeysInQ=q.map.keySet().toArray();
		
		//handling the units and values
		for(int i=0;i<arrayKeys.length;i++){
			if(q.map.containsKey(arrayKeys[i])){
				if((this.map.get(arrayKeys[i])-q.map.get(arrayKeys[i]))!=0)
					newQ.map.put((String) arrayKeys[i], this.map.get(arrayKeys[i])-q.map.get(arrayKeys[i]));
			}
			else{
				newQ.map.put((String) arrayKeys[i], this.map.get(arrayKeys[i]));	
			}
		}
		//adding the missing units
		for(int i=0;i<arrayKeysInQ.length;i++){
			if(!newQ.map.containsKey(arrayKeysInQ[i])&&!this.map.containsKey(arrayKeysInQ[i]))
				newQ.map.put((String) arrayKeysInQ[i], -1*q.map.get(arrayKeysInQ[i]));
		}
		
		return newQ;
	}
	/**
	 * perform exponential on a quantity
	 * @param power, the integer power
	 * @return
	 */
	public Quantity pow(int power){
		Quantity newQ=new Quantity();
		newQ.num=Math.pow(this.num,power);
		if(power==0){
			return newQ;
		}
		Object[] arrayKeys=this.map.keySet().toArray();
		for(int i=0;i<arrayKeys.length;i++){
			newQ.map.put((String) arrayKeys[i],this.map.get(arrayKeys[i])*power);
		}
		
		return newQ;
	}
	/**
	 * perform addition between two quantities
	 * @param q
	 * @return the sum
	 */
	public Quantity add(Quantity q){
		if(q==null||!q.map.equals(this.map))
			throw new IllegalArgumentException();
		Quantity newQ=new Quantity(this);
		newQ.num=this.num+q.num;
		return newQ;
	}
	
	/**
	 * perform subtraction between two quantities
	 * @param q
	 * @return the difference
	 */
	public Quantity sub(Quantity q){
		if(q==null||!q.map.equals(this.map))
			throw new IllegalArgumentException();
		Quantity newQ=new Quantity(this);
		newQ.num=this.num-q.num;
		return newQ;
	}
	/**
	 * perform negation 
	 * @return
	 */
	public Quantity negate(){
		List<String> emp=new ArrayList<String>();
		return this.mul(new Quantity(-1,emp,emp));
	}
	/**
	 * toString method convert a quantity to a string
	 */
	public String toString(){
		double value=this.num;
		Map<String,Integer> mapOfTheQuantity=this.map;
		TreeSet<String> orderedUnits =
				new TreeSet<String>(mapOfTheQuantity.keySet());
		StringBuffer unitsString = new StringBuffer();
		for (String key : orderedUnits) {
			int expt = mapOfTheQuantity.get(key); unitsString.append(" " + key);
			if (expt != 1)
				unitsString.append("^" + expt);
		}
		DecimalFormat df = new DecimalFormat("0.0####");
		// Put it all together and return.
		return df.format(value)+ unitsString.toString();
	}
	/**
	 * check if two quantities are identical
	 */
	public boolean equals(Object q){

		if(q instanceof Quantity){
			if(((Quantity)q).num!=this.num||q.hashCode()!=this.hashCode())
				return false;
			return true;
		}
		return false;
	}
	/**
	 * normalize a unit to its simplest form
	 * @param unit
	 * @param database, the map that stores all the units and their corresponding quantities
	 * @return the quantity with the simplest form of unit 
	 */
	public static Quantity normalizedUnit(String unit,Map<String,Quantity> database){
		List<String> emp=new ArrayList<String>();
		if(!database.containsKey(unit))
			return new Quantity(1,Arrays.asList(unit),emp);
		
		Quantity newQ=database.get(unit);
		//call normalize
		newQ=newQ.normalize(database);
		return newQ;

	}

	/**
	 * normalize a quantity in respect to a map
	 * @param database, the reference map
	 * @return the normalized quantity
	 */
	public Quantity normalize(Map<String,Quantity> database){
		Quantity newQ=new Quantity();
		Object[] arrayKeys=this.map.keySet().toArray();
		newQ.num=this.num;
		//recursive call
		for(int i=0;i<arrayKeys.length;i++){
			if(this.map.get(arrayKeys[i])<0)
				newQ=newQ.div(Quantity.normalizedUnit((String)arrayKeys[i], database));
			else
				newQ=newQ.mul(Quantity.normalizedUnit((String)arrayKeys[i], database));
			
		}

		return newQ;

	}
	/**
	 * @return the hashCode of a quantity object
	 */
	public int hashCode(){
		return this.toString().hashCode();
		
	}
	
}

