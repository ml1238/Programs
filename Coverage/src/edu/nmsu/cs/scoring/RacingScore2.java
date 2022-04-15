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
 ***/
 
 // Updated by: Mike Lopez III
 // CS 371 - Software Development
 // P3 (Coverage)
 // 04/13/2022
 // Last Edited: 04/14/2022

import java.util.*;

public class RacingScore2
{
   
   // declare global variables
	int	score1;
	int	score2;
	int	score3;

	public RacingScore2()
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
   
   // returns the sum of the two highest scores
	public int overallScore()
	{
		int s = 0, s1 = 0, s2 = 0;
		if (score1 < score2 && score1 < score3)
		{
			s1 = score2;
			s2 = score3;
		}
		else if (score2 < score1 && score2 < score3)
		{
			s1 = score1;
			s2 = score2;
		}
		else if (score3 < score1 ) 
      {  
         // ( score3 < score2 ) is always true, so coverage is lost
			s1 = score1;
			s2 = score2;
		}
		else
		{
			s1 = 99;
			s2 = 99;
		}
		s = s1 + s2;
		return s;
	}

	public static void main(String args[])
	{
		int s1 = 0, s2 = 0, s3 = 0;
      if ( args.length != 3 )
      {
         System.err.println("Error: must supply three arguments!\n");
         return;
      } // end if
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
		if (s1 < 0 || Math.abs(s1) > 50 )
		{
			System.err.println("Error1: scores must be between 0 and 50!");
			return;
		}
      if ( s2 < 0 || Math.abs(s2) > 50 )
		{
			System.err.println("Error2: scores must be between 0 and 50!");
			return;
		}
      if ( s3 < 0 )
		{
			System.err.println("Error3: scores must be between 0 and 50!");
			return;
		}
      if ( Math.abs(s3) > 50 )
		{
			System.err.println("Error4: scores must be between 0 and 50!");
			return;
		}
		RacingScore2 score = new RacingScore2();
		score.recordScores(s1, s2, s3);
		System.out.println("Overall score: " + score.overallScore());
		return;
	}

} // end class
