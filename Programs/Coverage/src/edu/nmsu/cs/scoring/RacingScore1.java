package edu.nmsu.cs.scoring;

/***
 * Olympic Dragon Racing Scoring Class
 *
 * For the Summer Olympics dragon racing event there are three judges, each of which gives a score
 * from 0 to 50 (inclusive), but the lowest score is thrown out and the competitor's overall score
 * is just the sum of the two highest scores. This class supports the recording of the three judge's
 * scores, and the computing of the competitor's overall score.
 * 
 * @author Jon Cook, Ph.D.
 * 
 ***/
 
 // Updated by: Mike Lopez III
 // CS 371 - Software Development
 // P3 (Coverage)
 // 04/13/2022

public class RacingScore1
{
   
   // declare global variables
	int	score1;
	int	score2;
	int	score3;
   
	public RacingScore1()
	{
		score1 = 0;
		score2 = 0;
		score3 = 0;
	}

	public void recordScores(int s1, int s2, int s3)
	{
		score1 = s1;
		score2 = s2;
		score3 = s3;
	}

	public int overallScore()
	{
		int s;
		if (score1 < score2)
			s = score2;
		else
			s = score1;
		if (s > score3)
			s = score3;
		s = (score1 + score2 + score3) - s;
		return s;
	}
   
   // driver main
	public static void main(String args[])
	{
      // declare variables
		int s1, s2, s3;
      
      // ensure user provides exactly three inputs
		if (args.length != 3)
		{
			System.err.println("Error: must supply three arguments!");
			return;
		}
      
      // assign three inputs to s1, s2, and s3
		try
		{
			s1 = Integer.parseInt(args[0]);
			s2 = Integer.parseInt(args[1]);
			s3 = Integer.parseInt(args[2]);
		}
		catch (Exception e)
		{
			System.err.println("Error: arguments must be integers!");
			return;
		}
      
      // create new RacingScore1 object score
		RacingScore1 score = new RacingScore1();
      
      // assign object scores to s1, s2, and s3
		score.recordScores(s1, s2, s3);
      // output the two highest scores
		System.out.println("Overall score: " + score.overallScore());
		return;
	}

} // end class
