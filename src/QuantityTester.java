import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class QuantityTester extends TestCase {
 private Quantity q1;
 private Quantity q2;
 private Quantity q3;
 private Quantity speed;
 private Quantity time;
 private Quantity time1;
 private Quantity time2;
 private List<String> emp;
 private Map<String,Quantity> db;

 protected void setUp() throws Exception {
  //setting up the database
	 emp=new ArrayList<String>();
	 db = new HashMap<String,Quantity>(); 
	 db.put("km", new Quantity(1000, Arrays.asList("meter"), emp)); 
	 db.put("day", new Quantity(24, Arrays.asList("hour"), emp)); 
	 db.put("hour", new Quantity(60, Arrays.asList("minute"), emp)); 
	 db.put("minute", new Quantity(60, Arrays.asList("second"), emp)); 
	 db.put("hertz", new Quantity(1, emp, Arrays.asList("second"))); 
	 db.put("kph", new Quantity(1, Arrays.asList("km"),Arrays.asList("hour")));
	 //finishing the database
	 super.setUp();
	 q1=new Quantity();
	 q2=new Quantity(2.2,Arrays.asList("kg"),Arrays.asList("m","m","m"));
	 q3=new Quantity(q2);
	 speed=new Quantity(5,Arrays.asList("m"),Arrays.asList("s"));
	 time=new Quantity(10,Arrays.asList("s"),emp);
	 time1=new Quantity(5,Arrays.asList("s"),emp);
	 time2=new Quantity(10,Arrays.asList("hour"),emp);
 }

 public void testQuantity() {
  assertEquals(q1,new Quantity(1,emp,emp));
 }

 public void test1ArgQuantity() {
  assertEquals(q2,q3);
 }

 public void test3ArgsQuantity() {
  try{
   Quantity illegal=new Quantity(2,null,null);
   fail("should generate an exception!");
  }
  catch(IllegalArgumentException e){
   //expected
  }
 }

 public void testMul() {
  Quantity distance;
  distance=speed.mul(time);
  assertEquals(new Quantity(50,Arrays.asList("m"),emp),distance);
  assertEquals(new Quantity(60, Arrays.asList("minute"), emp),q1.mul(new Quantity(60, Arrays.asList("minute"), emp)));
  try{
   distance=speed.mul(null);
   fail("should generate an exception!");
  }
  catch(IllegalArgumentException e){
   //expected
  }
 }
 
 public void testDiv(){
  Quantity timeDiv=time.div(time1);
  assertEquals(timeDiv,new Quantity(2,emp,emp));
  Quantity timex=new Quantity(1,emp,emp);
  
  try{
   timeDiv=speed.div(new Quantity(0,emp,emp));
   fail("should generate an exception!");
  }
  catch(IllegalArgumentException e){
   //expected
  }
 }
 
 public void testPow(){
  Quantity powPositive=speed.pow(2);
  Quantity powNegative=speed.pow(-2);
  Quantity powZero=speed.pow(0);
  assertEquals(new Quantity(25,Arrays.asList("m","m"),Arrays.asList("s","s")),powPositive);
  assertEquals(new Quantity((double)1/25,Arrays.asList("s","s"),Arrays.asList("m","m")),powNegative);
  assertEquals(new Quantity(1,emp,emp),powZero);
 }
 
 public void testAdd(){
  Quantity sum=time.add(time1);
  assertEquals(sum,new Quantity(15,Arrays.asList("s"),emp));
  try{
   sum=time.add(speed);
   fail("should generate an exception!");
  }
  catch(IllegalArgumentException e){
   //expected
  }
 }
 
 public void testSub(){
  Quantity dif=time.sub(time1);
  assertEquals(dif,new Quantity(5,Arrays.asList("s"),emp));
 }
 
 public void testNegate(){
  assertEquals(time.negate(),new Quantity(-10,Arrays.asList("s"),emp));
  
 }
 
 public void testNormalizedUnit(){
  assertEquals(new Quantity(3600,Arrays.asList("second"),emp),Quantity.normalizedUnit("hour", db));
  double temp=1/3.6;
  assertEquals(new Quantity(temp,Arrays.asList("meter"),Arrays.asList("second")),Quantity.normalizedUnit("kph", db));
  
 }
 
 public void testNomalize(){
  assertEquals(new Quantity(36000,Arrays.asList("second"),emp),time2.normalize(db));
 }
 
 public void testEquals(){
  assertEquals(q2,q3);
 }
 
 public void testHashCode(){
  assertEquals(q2.hashCode(),q3.hashCode());
 }

}
