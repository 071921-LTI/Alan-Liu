package com.revature;

import com.revature.exceptions.CalculatorException;

public class Calculator {

	/*
	 * Should be able to:
	 * 		- add
	 * 		- subtract
	 * 		- multiply
	 * 		- divide
	 * 			- throw a CalculatorException when attempting to divide by 0
	 * 		- isPrime: checks if a number is a prime number
	 * 		- compareThreeDecimals: returns true if the same up to 3 decimals
	 * 				- 3.123.compare...(3.1233445) should return true
	 */
	
	public double add(double x, double y) {
		return x+y;
	}
	
	public double subtract(double x, double y) {
		return x-y;
	}
	
	public double multiply(double x, double y) {
		return x*y;
	}
	
	public double divide(double x, double y){
		if (y==0) {
			throw new CalculatorException();
		}
		return x/y;
	}
	
	public boolean isPrime(int x) {
		if (x <= 1)
            return false;
  
        for (int i = 2; i < x; i++)
            if (x % i == 0)
                return false;
  
        return true;
	}
	
	public boolean compareThreeDecimal(double x, double y) {
		int a = (int)Math.floor(x*1000);
		int b = (int)Math.floor(y*1000);
		return a == b;
	}
}
