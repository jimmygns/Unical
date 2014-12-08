import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
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

public class UnicalcTester extends TestCase {

	private Unicalc cal;
	private Unicalc cal1;
	private Unicalc calKg;
	private Unicalc sum;
	private Unicalc neg;
	private Unicalc value;
	private Unicalc pow;
	private List<String> emp;
	
	protected void setUp() throws Exception {
		super.setUp();
		cal=new Unicalc();
		cal1=new Unicalc();
		calKg=new Unicalc();
		sum=new Unicalc();
		neg=new Unicalc();
		value=new Unicalc();
		pow=new Unicalc();
		cal.tokenize("21*2 meter");
		cal1.tokenize("def x 10 meter meter");
		calKg.tokenize("# 5 kg");
		sum.tokenize("3 meter + 5 meter");
		neg.tokenize("-5");
		value.tokenize("10 second");
		pow.tokenize("5^2 kg");
		emp=new ArrayList<String>();
	}
	
	public void testS() {
		Product newProduct=new Product(new Value(new Quantity(21,emp,emp)),new Product(new Value(new Quantity(2,emp,emp)),new Value(new Quantity(1,Arrays.asList("meter"),emp))));
		assertEquals(newProduct,cal.S());
		Define newDefine=new Define("x",new Product(new Value(new Quantity(10.0,emp,emp)),new Product(new Value(new Quantity(1,Arrays.asList("meter"),emp)),new Value(new Quantity(1,Arrays.asList("meter"),emp)))));
		assertEquals(newDefine,cal1.S());
		
	}

	public void testL() {
		Normalize newN=new Normalize(new Product(new Value(new Quantity(5,emp,emp)),new Value(new Quantity(1,Arrays.asList("kg"),emp))));
		assertEquals(newN,calKg.L());
		
	}

	public void testE() {
		Sum newS=new Sum(new Product(new Value(new Quantity(3,emp,emp)),new Value(new Quantity(1,Arrays.asList("meter"),emp))),new Product(new Value(new Quantity(5,emp,emp)),new Value(new Quantity(1,Arrays.asList("meter"),emp))));
		assertEquals(newS,sum.E());
	}

	public void testP() {
		Product newProduct=new Product(new Value(new Quantity(21,emp,emp)),new Product(new Value(new Quantity(2,emp,emp)),new Value(new Quantity(1,Arrays.asList("meter"),emp))));
		assertEquals(newProduct,cal.P());
		
	}

	public void testK() {
		Negation newN=new Negation(new Value(new Quantity(5,emp,emp)));
		assertEquals(newN,neg.K());
	}

	public void testQ() {
		Product newP=new Product(new Value(new Quantity(10,emp,emp)),new Value(new Quantity(1,Arrays.asList("second"),emp)));
		assertEquals(newP,value.Q());
		
	}

	public void testR() {
		Power newP=new Power(new Value(new Quantity(1,Arrays.asList("meter"),emp)),2);
		Unicalc newU=new Unicalc();
		newU.tokenize("meter^2");
		assertEquals(newP,newU.R());
		cal.tokenize("(3)^-1");
		assertEquals(new Power(new Value(new Quantity(3,emp,emp)),-1),cal.R());
	}

}
