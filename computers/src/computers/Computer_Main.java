
package computers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Computer_Main {

	public static Scanner scanner = new Scanner(System.in);
	public static int maxComputers = 0;
	public static Computer[] inventory;
	public static final int PASSWORD = 123;
	public static int currentNumOfComps = 0;

	//method to display the welcome message
	public static void showWelcomeMessage() {
		System.out.println("Welcome!");
	}

	//method to get max number of computers that store can fit
	public static void getInventoryLimit() {
	    while(maxComputers == 0){
	        try {
	        	System.out.println("What's your inventory limit?");
				maxComputers = scanner.nextInt();
				scanner.nextLine();
				break;
			} catch (InputMismatchException ex) {
				System.out.println("Please enter an integer");
				scanner.nextLine();
			}
	    }
	}
	
	// method to get user input from the keyboard ( for main menu)
	public static void getUserChoice() {
		try {
	        int input = Integer.parseInt(scanner.next());  
	        switch (input) {
	        case 1:
	        	if (getUserPassword() == true) {
	        		addComputers();//add new computer(s)
	        	} else {
	        		displayMenu(); //display main menu
	        	}
	        	break;
	        case 2:  // change info of a computer
	        	if (getUserPassword() == true) {
	        		modifyComputerInfo();//change info of a computer
	        	} else {
	        		displayMenu(); //display main menu
	        	}
	        	break;
	        case 3:  // display all computers by specific brand
	        	System.out.println("Please enter computer brand name: ");
	        	String searchedBrand = scanner.next();
	        	scanner.nextLine();
	        	ArrayList<Computer> foundComputers = findComputersByBrand(searchedBrand);
	        	if(foundComputers == null || foundComputers.size() < 1) {
	        		System.out.println("No computers with brand " + searchedBrand + " were found");
	        	}
	        	else {
	        		for(Computer c : foundComputers) {
	        			System.out.println("---\n" + c + "\n---");
	        		}
	        	}
	        	break;
	        case 4:  // display all computers cheaper than certain price
	        	double maxPrice = getMaxPriceFromUser();
	        	ArrayList<Computer> foundComps = findCheaperThan(maxPrice);
	        	if(foundComps == null || foundComps.size() < 1) {
	        		System.out.println("No computers with price lower than " + maxPrice + " were found");
	        	}
	        	else {
	        		for(Computer c : foundComps) {
	        			System.out.println("---\n" + c + "\n---");
	        		}
	        	}
	        	break;
	        case 5:
	        	System.out.println("Thank you for using this program. Bye!");
	        	System.exit(0);
	       }
	      } catch (NumberFormatException e) {
	    	  System.out.println("\nEnter an integer between 1 and 5");
	      }
	}

	//method to display the main menu
	public static void displayMenu() {
		do {
			System.out.println("\nWhat do you want to do?");
			System.out.println("To enter new computers (password required) - press 1");
		    System.out.println("To change information of a computer (password required) - press 2");
		    System.out.println("To display all computers by a specific brand - press 3");
		    System.out.println("To display all computers under a certain a price - press 4");
		    System.out.println("To quit - press 5");
		    System.out.print("\nPlease, enter your option (number from 1 to 5): ");
		    getUserChoice();
	    }
	    while(true); // Display the menu until the program is closed by the user
	}
	
	public static boolean getUserPassword() {
		int numOfTries = 3;
		int userInput = 0;
		do {
			System.out.println("Please enter password (number of tries left: " + numOfTries + ") :  ");
		    try {
		        userInput = Integer.parseInt(scanner.next());
		        numOfTries--;
		    } catch (NumberFormatException e) {
		        userInput = 0;
		        numOfTries--;
		        System.out.println("Wrong password format, must containt digits only");
		    }

		} while (userInput != PASSWORD && numOfTries > 0);
		if(numOfTries == 0 && userInput != PASSWORD) {
			System.out.println("You entered incorrect password 3 times");
			return false;
		}
		else {
			System.out.println("Your password is correct");
			return true;
		}
	}
	
	//add computers
	public static void addComputers() {
		int compQuantity = 0;
		while(true){
	        try {
	        	System.out.println("How many computers do you want to enter? ");
	        	compQuantity = scanner.nextInt();
	    		scanner.nextLine();
				break;
			} catch (InputMismatchException ex) {
				System.out.println("Please enter an integer");
				scanner.nextLine();
			}
	    }

		if (compQuantity <= (maxComputers - currentNumOfComps)) { // to add the current num of comps
			for (int i = 0; i < compQuantity; i++) {
				String brand =  getComputerBrand();
				String model = getComputerModel();
				long SN = getComputerSN();
				double price = getComputerPrice();
				scanner.nextLine();
				inventory[currentNumOfComps] = new Computer(brand, model, SN, price);
				System.out.println("New computed was added successfully");
				currentNumOfComps++;
			}
			System.out.println("Do you want to add more computers? Enter 'y' or 'n'");
			String userChoice = scanner.nextLine();
			//scanner.nextLine();
			if(userChoice.equals("y")) {
				addComputers();
			}
		}
		else if(compQuantity > (maxComputers - currentNumOfComps) && (maxComputers != currentNumOfComps)) {
			System.out.println("You can add maximum " + (maxComputers -currentNumOfComps) + " more computers");
		}
		else {
			System.out.println("You can't add more computers, your storage is full");
		}
	}
	
	//get computer SN from user
	public static long getComputerSN() {
		System.out.print("Please, enter the SN: ");
		long SN = 0;
		try {
			SN = scanner.nextLong();
		}
		catch (InputMismatchException ex) {
			System.out.println("Please enter an integer value of 6 digits");
			scanner.nextLine();
			getComputerSN();
		}
		if(String.valueOf(SN).length() != 6) {
			System.out.println("SN must contain exactly 6 digits");
			getComputerSN();
		}
		return SN;
	}
	
	//get computer price from user
		public static double getComputerPrice() {
			System.out.print("Please, enter the price: ");
			double price = 0;
			try {
				price = scanner.nextDouble();
			}
			catch (InputMismatchException ex) {
				System.out.println("Please enter decimal value greater or equal to 500");
				scanner.nextLine();
				getComputerPrice();
			}
			if(price < 500) {
				System.out.println("Price must be greater or equal to 500");
				getComputerPrice();
			}
			return price;
		}
	//get computer brand from user input
	public static String getComputerBrand() {
		System.out.print("Please, enter the brand: ");
		String brand = scanner.nextLine();
		if(brand.equals(null) || brand.length() < 1) {
			System.out.println("Brand cannot be empty");
			getComputerBrand();
		}
		return brand;
	}
	//get computer model from user input
	public static String getComputerModel() {
		System.out.print("Please, enter the model: ");
		String model = scanner.nextLine();
		if(model.equals(null) || model.length() < 1) {
			System.out.println("Model cannot be empty");
			getComputerModel();
		}
		return model;
	}
	
	//method to modify computer info
	public static void modifyComputerInfo() { 
		int compNumber = -1;
		 while(true){
		        try {
		        	System.out.println("Enter the number of computer that you wish to modify");
					compNumber = scanner.nextInt();
					scanner.nextLine();
					if(compNumber >= currentNumOfComps || compNumber < 0) {
						System.out.println("Computer with this number does not exist,\n would you like to enter another"
								+ " number? Enter 'y' or 'n'");
						String userChoice = scanner.nextLine();
						//scanner.nextLine();
						if(userChoice.equals("y")) {
							modifyComputerInfo();
						}
						else {
							break;
						}
						
					}
					else {
						//display info about found computer
						System.out.println("Computer # " + compNumber + "\n" + inventory[compNumber]);
						//display computer menu
						displayComputerMenu(compNumber);
					}
					break;
				} catch (InputMismatchException ex) {
					System.out.println("Please enter an integer");
					scanner.nextLine();
				}
		    }
	}
	
	//method to display computer menu (to modify computer)
	public static void displayComputerMenu(int selectedIndex) {
			do {
				System.out.println("\nWhat information would you like to change?");
				System.out.println("1. brand");
			    System.out.println("2. model");
			    System.out.println("3. SN");
			    System.out.println("4. price");
			    System.out.println("5. quit");
			    System.out.print("\nEnter your choice > ");
			    getUserSelection(selectedIndex);
		    }
		    while(true); // Display the computer menu until user enters 5
	}
	
	// method to get user input from the keyboard (for computer menu)
		public static void getUserSelection(int selectedIndex) {
			try {
		        int input = Integer.parseInt(scanner.next());  
		        switch (input) {
		        case 1:
		        	//modify brand
		        	System.out.print("--- Brand modification ---\nPlease enter new brand: ");
		        	String newBrand = scanner.next();
		        	scanner.nextLine();
		        	inventory[selectedIndex].setBrand(newBrand);
		        	System.out.println("Computer brand was sucessfully modified\n");
		        	//display modified info about computer
					System.out.println("Computer # " + selectedIndex + "\n" + inventory[selectedIndex]);
		        	displayComputerMenu(selectedIndex); //display computer menu again
		        	break;
		        case 2:  
		        	//modify model
		        	System.out.print("--- Model modification ---\nPlease enter new model: ");
		        	String newModel = scanner.next();
		        	scanner.nextLine();
		        	inventory[selectedIndex].setModel(newModel);
		        	System.out.println("Computer model was sucessfully modified\n");
		        	//display modified info about computer
					System.out.println("Computer # " + selectedIndex + "\n" + inventory[selectedIndex]);
		        	displayComputerMenu(selectedIndex); //display computer menu again
		        	
		        	break;
		        case 3:  
		        	//modify SN
		        	System.out.print("--- SN modification ---\nPlease enter new SN: ");
		        	int newSN = scanner.nextInt();
		        	scanner.nextLine();
		        	inventory[selectedIndex].setSN(newSN);
		        	if(String.valueOf(newSN).length() == 6) {
		        		System.out.println("Computer SN was sucessfully modified\n");
		        		//display modified info about computer
		        		System.out.println("Computer # " + selectedIndex + "\n" + inventory[selectedIndex]);
		        	}
		        	displayComputerMenu(selectedIndex); //display computer menu again
		        	
		        	break;
		        case 4:  
		        	//modify price
		        	System.out.print("--- Price modification ---\nPlease enter new price: ");
		        	int newPrice = scanner.nextInt();
		        	scanner.nextLine();
		        	inventory[selectedIndex].setPrice(newPrice);
		        	if(newPrice >= 500) {
		        		System.out.println("Computer price was sucessfully modified\n");
		        		//display modified info about computer
		        		System.out.println("Computer # " + selectedIndex + "\n" + inventory[selectedIndex]);
		        	}
		        	displayComputerMenu(selectedIndex); //display computer menu again
		        	break;
		        case 5:  
		        	//Quit ( go back to main menu)
		        	displayMenu();
		        	break;
		       }
		      } catch (NumberFormatException e) {
		    	  System.out.println("\nEnter an integer between 1 and 5");
		      }
		}
		
		//method to return all computers by specific brand
		public static ArrayList<Computer> findComputersByBrand(String searchedBrand) {
			ArrayList<Computer> computers = new ArrayList<Computer>();
			for (int i = 0; i < inventory.length; i++) {
		        if (inventory[i] != null && inventory[i].getBrand().equals(searchedBrand)) {
		        	computers.add(inventory[i]);
		        }
			}
		    return computers;  
		}
		
		//method to return all computers that have price lower than certain amount
		public static ArrayList<Computer> findCheaperThan(double limitPrice) {
			ArrayList<Computer> computers = new ArrayList<Computer>();
			for (int i = 0; i < inventory.length; i++) {
		        if (inventory[i] != null && inventory[i].getPrice() < limitPrice) {
		        	computers.add(inventory[i]);
		        }
			}
		    return computers;  
		}
		
		//method to get max price from user to find computers lower than this price
		public static double getMaxPriceFromUser() {
			System.out.println("Please enter computer maximum price: ");
        	double maxPrice = scanner.nextDouble();
        	scanner.nextLine();
        	if(maxPrice < 0) {
        		System.out.println("Maximum price must not be a negative number");
        		getMaxPriceFromUser();
        	}
        	else {
        		return maxPrice;
        	}
			return maxPrice;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		showWelcomeMessage(); // welcome message

		getInventoryLimit(); // get max number of computers

		inventory = new Computer[maxComputers]; // create array for the max number of computers
		
		displayMenu(); //display main menu
		//getUserChoice();

	/*	int counter = 0;

		// System.out.println(maxComputers);

		// int option;

		System.out.println("\nWhat do you want to do?");
		System.out.println("To enter new computers (password required) - press 1");
		System.out.println("To change information of a computer (password required) - press 2");
		System.out.println("To display all computers by a specific brand - press 3");
		System.out.println("To display all computers under a certain a price - press 4");
		System.out.println("To quit - press 5");
		System.out.print("\nPlease, enter your option (number from 1 to 5): ");

		int option = scanner.nextInt();
		int password = 123;
		// String str = scanner.next();
		// char ans = str.charAt('y');

		if (option == 1) {
			System.out.print("Please, enter your password: ");
			int pass = scanner.nextInt();
			scanner.nextLine();
			if (pass == password) {
				System.out.print("How many computers do you want to enter? ");
				int compQuantity = scanner.nextInt();
				scanner.nextLine();

				if (compQuantity <= maxComputers) { // to add the current num of comps
					// do {
					for (int i = 0; i < compQuantity; i++) {
						System.out.print("Please, enter the brand: ");
						String brand = scanner.nextLine();
						System.out.print("Please, enter the model: ");
						String model = scanner.nextLine();
						System.out.print("Please, enter the SN: ");
						long SN = scanner.nextLong();
						System.out.print("Please, enter the price: ");
						double price = scanner.nextDouble();
						inventory[counter + i] = new Computer(brand, model, SN, price);
						System.out.println("New computed was added successfully");
					}
				}

				else {
					System.out.println("You can't add more computers");
				}
			}
			// System.out.println("Do you want to add one more? Enter 'y' or 'n'");

			// } while (ans == 'y');

			// System.out.println("Do you want to add one more? Enter 'y' or 'n'");
			// String str = scanner.next();
			// char ans = str.charAt(0);

//	                    if (ans == 'n') {
//	                    	break;
//	                    }

		}

		else {
			System.out.println("Entrered password is wrong, please try again.");
		}

		Computer c1 = new Computer("Dell", "XPS", 111111, 1400.70); // using parameterized constructor
		Computer c2 = new Computer("HP", "ENVY", 222222, 1700.90);
		Computer c3 = new Computer("Microsoft", "Surface", 333333, 1300.40);
		Computer c4 = new Computer(); // using default constructor

		// System.out.println("\n\n===== Information about c1 =====");
		System.out.println(c1);
		// System.out.println("\n===== Information about c2 =====");
		System.out.println(c2);
		// System.out.println("\n===== Information about c3 =====");
		System.out.println(c3);
		// System.out.println("\n===== Information about c4 =====");
		System.out.println(c4);
		// System.out.println("\n===== Information about c5 =====");
		System.out.println(inventory.toString());

		Computer.findNumberOfCreatedComputers();

		// System.out.println(equalComps());

		scanner.close();   */

	}

}
