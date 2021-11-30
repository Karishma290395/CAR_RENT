package car.rentingapplication;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Car {
	static Car carObj = new Car();
	static CarUser objCarUser = new CarUser();

	public static void main(String[] args) throws ParseException {

		boolean isUserLoggedIn = false;
		boolean isUserRegistered = false;
		int optionSelected = 0;
		Scanner userSc = new Scanner(System.in);
		Scanner doubleSc = new Scanner(System.in);

		System.out.println("Explore your WORLD with JUST CAB !!! \n1.Are you a New User\n2.Are you an Existing User");
		optionSelected = carObj.inputInt(userSc, optionSelected);

		while (optionSelected > 2 || optionSelected < 1) {
			System.out.println("you enterd incorrect option. Please enter the valid option!!");
			optionSelected = carObj.inputInt(userSc, optionSelected);
		}
		if (optionSelected == 1) {
			isUserRegistered = carObj.newUserRegistration(userSc);
		} else {
			isUserLoggedIn = carObj.Login(userSc);
		}

		System.out.println("Please select an option : \n1. Issue Car\n2.Return Car");
		optionSelected = carObj.inputInt(userSc, optionSelected);
		while (optionSelected > 2 || optionSelected < 1) {
			System.out.println("you enterd incorrect option. Please enter the valid option!!");
			optionSelected = carObj.inputInt(userSc, optionSelected);
		}
		
		if (optionSelected == 1) {
			if (isUserRegistered || isUserLoggedIn) {

				carObj.issueCars(userSc, doubleSc);
			}
		}else {
			CarIssueDao objCarIssueDao = new CarIssueDao();
			CarUserData objCarUserData = new CarUserData();
			objCarIssueDao.IsCarOnUserName(objCarUser,objCarUserData, doubleSc);
		}

	}

	public boolean newUserRegistration(Scanner userSc) throws ParseException {

		boolean isSuccesfullyRegister = false;
		System.out.println("Please enter the Name : ");
		String userName = userSc.nextLine();
		objCarUser.setUserName(userName);
		System.out.println("Please enter address : ");
		String userAddress = userSc.nextLine();
		objCarUser.setUserAddress(userAddress);
		System.out.println("Please enter contact details : ");
		String userContact = userSc.nextLine();
		objCarUser.setUserContact(userContact);
		System.out.println("Please enter the password : ");
		String userPassword = userSc.nextLine();
		objCarUser.setUserPassword(userPassword);

		CarUserDao objCarUserDao = new CarUserDao();
		isSuccesfullyRegister = objCarUserDao.registerUser(objCarUser);

		return isSuccesfullyRegister;

	}

	public boolean Login(Scanner userSc) {

		CarUsersLoginDao objCarUsersLoginDao = new CarUsersLoginDao();
		boolean isUserLoggedIn = false;
		int noOfAttempts = 1;
		while (!isUserLoggedIn && noOfAttempts < 4) {

			System.out.println("Please enter the Name : ");
			String extUserName = userSc.nextLine();
			System.out.println("Please enter the password : ");
			String extUserPassword = userSc.nextLine();
			isUserLoggedIn = objCarUsersLoginDao.loginExtistingUser(extUserName, extUserPassword, objCarUser );
			noOfAttempts++;
		}
		if (noOfAttempts > 3) {
			System.out.println("SORRY!! You have exausted maximum attempt limit. Please try after sometime");
		}
		return isUserLoggedIn;
	}

	public CarUserData issueCars(Scanner userSc, Scanner doubleSc) throws ParseException {

		CarUserData objCarUserData = new CarUserData();
		CarIssueDao objCarIssueDao = new CarIssueDao();
		HashMap<String, Integer> carsMap = new HashMap<>();
		String carName = null;
		boolean isValidCar = false;
		while (!isValidCar) {
			System.out.println("Please enter the car From below list : ");
			carsMap = objCarIssueDao.RentalCars();
			carName = userSc.nextLine();
			isValidCar = carsMap.containsKey(carName);
		}
		objCarUserData.setCarName(carName);
		
		objCarIssueDao.insertNumberOfCars(objCarUserData);

		System.out.println("Please enter the date from which you want the car : ");
		String carFromDate = userSc.nextLine();
		Date fromDate = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(carFromDate);
		objCarUserData.setFromDate(fromDate);

		System.out.println("Please enter the end date for car : ");
		String carToDate = userSc.nextLine();
		Date toDate = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(carToDate);
		objCarUserData.setToDate(toDate);

		long differenceInMilliseconds = toDate.getTime() - fromDate.getTime();
		long differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24) % 365;
		objCarUserData.setNoOfDayTaken((int) differenceInDays);

		System.out.println("Please enter amount you want to pay as advance payment");
		double advancePayment = doubleSc.nextDouble();
		objCarUserData.setAdvanceAmount(advancePayment);
		objCarUserData.setTotalAmount(carsMap.get(carName) * (int) differenceInDays);
		objCarUserData.setBalanceAmount(objCarUserData.getTotalAmount() - advancePayment);
		objCarUserData.setCarRent(carsMap.get(carName));
		objCarUserData.setUserId(objCarUser.getUserpid());

		objCarIssueDao.insertCarTransaction(objCarUserData);
		System.out.println("Car : " + carName + " has been successfully booked on your Name for the date : " + fromDate);
		return objCarUserData;
	}



	public int inputInt(Scanner userSc, int loginChoice) {
		try {
			String login = userSc.nextLine();
			loginChoice = Integer.parseInt(login);
		} catch (NumberFormatException e) {
			System.out.println("you enterd incorrect option. Please enter the valid option!!");
			loginChoice = inputInt(userSc, loginChoice);
		}
		return loginChoice;
	}

}
