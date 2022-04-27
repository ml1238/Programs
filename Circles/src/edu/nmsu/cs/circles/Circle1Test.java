package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle1 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle1Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle1Test
{
	// Data you need for each test case
	private Circle1 circle1;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle1 = new Circle1(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

   @Test
   //
   // Tests intersect for the case that two circles are not touching.
   //
   public void intersectNoIntersection() {
      
      System.out.println("Running test: intersectNoIntersection.");
      
      // next to each other, same x
      System.out.println("intersectNoIntersection - next to each other");
      Circle1 baseCircle = new Circle1(0, 50, 10);
      Circle1 nextCircle = new Circle1(0, 0, 5);
      Assert.assertFalse(baseCircle.intersects(nextCircle));
      Assert.assertFalse(nextCircle.intersects(baseCircle));
      
      // one above the other, almost touching
      System.out.println("intersectNoIntersection - almost touching on vertical");
      Circle1 baseCircle1 = new Circle1(1, 10, 2.99);
      Circle1 nextCircle1 = new Circle1(1, 5, 2);
      Assert.assertFalse(baseCircle1.intersects(nextCircle1));
      Assert.assertFalse(nextCircle1.intersects(baseCircle1));
      
      // circle above to the right
      System.out.println("intersectNoIntersection - above to right very close");
      Circle1 baseCircle2= new Circle1(16, 128, 4);
      Circle1 nextCircle2 = new Circle1(23.45, 121.6, 5);
      Assert.assertFalse(baseCircle2.intersects(nextCircle2));
      Assert.assertFalse(nextCircle2.intersects(baseCircle2));
      
      
   } // end intersectNoIntersection
   
   @Test
   //
   // Tests intersect for the case that two circles are perfectly on top of each other
   //
   public void intersectCompleteOverlap() {
   
      System.out.println("Running test: intersectCompleteOverlap.");

      System.out.println("intersectCompleteOverlap - at origin");
      Circle1 baseCircle = new Circle1(0, 0, 5);
      Circle1 nextCircle = new Circle1(0, 0, 5);
      Assert.assertTrue(baseCircle.intersects(nextCircle));
      Assert.assertTrue(nextCircle.intersects(baseCircle));
   
   } // end intersectCompleteOverlap
   
   @Test
   //
   // Tests intersect for the case that two circles interesect at exactly one point
   //
   public void intersectAtOnePoint() {
   
      System.out.println("Running test: intersectAtOnePoint.");

      System.out.println("intersectAtOnePoint - on x axis");
      Circle1 baseCircle = new Circle1(0, 0, 3);
      Circle1 nextCircle = new Circle1(7, 0, 4);
      Assert.assertFalse(baseCircle.intersects(nextCircle));
      Assert.assertFalse(nextCircle.intersects(baseCircle));
   
   } // end intersectAtOnePoint
   
	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle1.moveBy(1, 1);
		Assert.assertTrue(p.x == 2 && p.y == 3);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg()
	{
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle1.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

}
