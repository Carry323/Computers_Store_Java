package computers;


public class Computer {

	String brand = "";
	String model = ""; 
	long SN;
	double price;
	static int numOfComps = 0;
	
	//Default constructor
	public Computer() {
		brand = "HP";
		model = "ProBook";
		SN = 100000;
		price = 1000;
		numOfComps++;
	}
	
	//Fully Parameterized Constructor
	public Computer (String br, String md, long sn, double pr) {
		
		this.brand = br;
		this.model = md;
        this.SN = sn;  
        this.price = pr;
        numOfComps++;
    }

	//Getters and setters
	
	//BRAND
	public String getBrand() {
		return brand;
	}

	public void setBrand(String br) {
		this.brand = br;
	}

	
	//MODEL
	public String getModel() {
		return model;
	}

	public void setModel(String md) {
		this.model = md;
	}

	
	//SN
	public long getSN() {
		return SN;
	}

	public void setSN(long sn) {
		if (String.valueOf(sn).length() == 6) {
			this.SN = sn;
		}
		else {
			System.out.println("SN must contain 6 digits");
		}
	}

	
	//PRICE
	public double getPrice() {
		return price;
	}

	public void setPrice(double pr) {
		if (pr >= 500) {
			this.price = pr;
		}
		else {
			System.out.println("Price can't be less than 500");
		}
	}
	
	//toString() method, description of computer object
	public String toString(){ 
		  return "Brand: " + brand + "\nModel: " + model + "\nSN: " + SN +
					"\nPrice: " + price;
		 }  
	
	/*  !! Replaced by toString() method above !!
	public void showInfo() {
		System.out.println("The brand of the computer is " + brand + " it's model is " + model + " it's serial number is " + SN +
				" and the price is " + price );

	} */
	
	//return current number of computer objects
	public static int findNumberOfCreatedComputers() { 
       return numOfComps;
    }
	
	// for comparison of two computer objects
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;  //of objects are equal returns true
	    if (obj == null || getClass() != obj.getClass()) return false;  //if provided object is null or an object of different class, return false
	    Computer comp = (Computer) obj;
	    return brand.equals(comp.brand) &&
	      model.equals(comp.model) && (price == comp.price); //if brand, model and price are equal, return true/otherwise false
	}
	
	
	
//	public boolean equalComps() {
//		//boolean isEqual;
//		
//		if (this.brand.equals(getBrand()) && this.model.equals(getModel()) && this.price == getPrice()) {
//            return true;
//        }
//		else {
//            return false;
//        }
		
	}
	