import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class ASTTester extends TestCase {
	private AST product;
	private AST quotient;
	private AST sum;
	private AST difference;
	private AST power;
	private AST negation;
	private AST normalize;
	private AST value;
	private AST value1;
	private AST value2;
	private AST define;
	private Map<String,Quantity> database;
	List<String> emp;
	
	protected void setUp() throws Exception {
		super.setUp();
		emp=new ArrayList<String>();
		database=QuantityDB.getDB();
		value=new Value(new Quantity(15,Arrays.asList("second"),emp));
		value1=new Value(new Quantity(10,Arrays.asList("second"),emp));
		value2=new Value(new Quantity(5,Arrays.asList("meter"),Arrays.asList("second")));
		power=new Power(value,2);
		sum=new Sum(value,value1);
		difference=new Difference(sum,value);
		product=new Product(value2,sum);
		quotient=new Quotient(power,product);
		negation=new Negation(quotient);
		normalize=new Normalize(new Value(new Quantity(10,Arrays.asList("hour"),emp)));
		define=new Define("x",new Value(new Quantity(10,Arrays.asList("hour"),emp)));
	}

	public void testEvalProduct() {
		assertEquals(new Quantity(125,Arrays.asList("meter"),emp),product.eval(database));
	}
	
	
	public void testEvalQuotient() {
		assertEquals(new Quantity(1.8,Arrays.asList("second","second"),Arrays.asList("meter")),quotient.eval(database));
		
	}
	
	
	public void testEvalSum() {
		assertEquals(new Quantity(25,Arrays.asList("second"),emp),sum.eval(database));
	}
	
	public void testEvalDifference() {
		assertEquals(new Quantity(10,Arrays.asList("second"),emp),difference.eval(database));
	}
	
	public void testEvalPower() {
		assertEquals(new Quantity(225,Arrays.asList("second","second"),emp),power.eval(database));
	}
	
	public void testEvalNegation() {
		assertEquals(new Quantity(-1.8,Arrays.asList("second","second"),Arrays.asList("meter")),negation.eval(database));
	}
	
	public void testEvalNormalize() {
		assertEquals(new Quantity(36000,Arrays.asList("second"),emp),normalize.eval(database));
	}
	
	public void testEvalDefine() {
		assertEquals(new Quantity(10,Arrays.asList("hour"),emp),define.eval(database));
		assertTrue(database.containsKey("x"));
		assertEquals(new Quantity(10,Arrays.asList("hour"),emp),database.get("x"));
	}
	
	public void testEvalValue() {
		assertEquals(new Quantity(5,Arrays.asList("meter"),Arrays.asList("second")),value2.eval(database));
	}
}
