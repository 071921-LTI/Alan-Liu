package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.exceptions.CalculatorException;

@TestMethodOrder(OrderAnnotation.class)
public class CalculatorTest {

	/*
	 * JUnit annotations
	 * 	- @BeforeEach
	 * 	- @AfterEach
	 * 	- @BeforeAll
	 * 	- @AfterAll
	 * 	- @Test
	 * 	- @Ignore
	 *  - @Order
	 */
	
	private static Calculator calc;
	
	
	@BeforeAll
	public static void setUp() {
		calc = new Calculator();
	}
	
	@AfterAll
	public static void tearDown() {
	}
	
	@Order(1)
	@Test
	public void addTwoAndTwo() {
		double expected = 4;
		double actualResult = calc.add(2, 2);
		assertEquals(expected, actualResult);
	}
	
	@Order(2)
	@Test
	public void divideBy0() {
		assertThrows(CalculatorException.class, () -> calc.divide(1,0));
	}
	
	@Order(3)
	@Test
	public void FourSubtractTwo() {
		double actualResult = calc.subtract(4, 2);
		assertEquals(2, actualResult);
	}
	
	@Order(4)
	@Test
	public void TwoMultiplyTwo() {
		double actualResult = calc.multiply(2, 2);
		assertEquals(4, actualResult);
	
	}
	@Order(5)
	@Test
	public void TwoDivideByTwo() {
		double actualResult = calc.divide(2, 2);
		assertEquals(1, actualResult);
	}
	
	@Order(6)
	@Test
	public void AddFloating() {
		double actualResult = calc.add(1.5, 7.888);
		assertEquals(9.388, actualResult);
	}
	
	@Order(7)
	@Test
	public void AddNegative() {
		double actualResult = calc.add(-2,-8);
		assertEquals(-10, actualResult);
	}
	@Order(8)
	@Test
	public void AddNegativeFloating() {
		double actualResult = calc.add(-2.45,-8.89);
		assertEquals(-11.34, actualResult);
	}
	
	@Order(9)
	@Test
	public void SubtractNegative() {
		double actualResult = calc.subtract(-10,-24);
		assertEquals(14, actualResult);
	}
	
	@Order(10)
	@Test
	public void SubtractNegativeFloating() {
		double actualResult = calc.subtract(-10.23,-24.25);
		assertEquals(14.02, actualResult);
	}
	
	@Order(11)
	@Test
	public void AddPositiveAndNegative() {
		double actualResult = calc.add(10,-4);
		assertEquals(6, actualResult);
	}
	
	@Order(12)
	@Test
	public void SubtractPositiveAndNegative() {
		double actualResult = calc.subtract(10,-4);
		assertEquals(14, actualResult);
	}
	
	@Order(13)
	@Test
	public void DividedByNegative() {
		double actualResult = calc.divide(10,-4);
		assertEquals(-2.5, actualResult);
	}
	
	@Order(14)
	@Test
	public void Primetest() {
		boolean actualResult = calc.isPrime(10);
		assertEquals(false, actualResult);
	}
	
	@Order(15)
	@Test
	public void Primetest2() {
		boolean actualResult = calc.isPrime(7);
		assertEquals(true, actualResult);
	}
	
	@Order(16)
	@Test
	public void ThreeDeci() {
		boolean actualResult = calc.compareThreeDecimal(0.1115793875,0.1113467283648);
		assertEquals(true, actualResult);
	}
	
	@Order(17)
	@Test
	public void ThreeDeci2() {
		boolean actualResult = calc.compareThreeDecimal(0.1125793875,0.1113467283648);
		assertEquals(false, actualResult);
	}
	
	@Order(18)
	@Test
	public void Primetest3() {
		boolean actualResult = calc.isPrime(0);
		assertEquals(false, actualResult);
	}
	
	@Order(19)
	@Test
	public void Primetest4() {
		boolean actualResult = calc.isPrime(1);
		assertEquals(false, actualResult);
	}
	
	@Order(20)
	@Test
	public void Primetest5() {
		boolean actualResult = calc.isPrime(-1);
		assertEquals(false, actualResult);
	}
	
	@Order(21)
	@Test
	public void Primetest6() {
		boolean actualResult = calc.isPrime(37);
		assertEquals(true, actualResult);
	}
}
