class GlassDropTop{


int GlassFallingRecur(int numFloors, int numGlass)
{
    // With just one Glass, we have to test each floor
    if (numFloors == 1)
        return numGlass;
 
    // For very few glasses with, every glass has to be tested
    if (numGlass <= 2)
        return numGlass;
 
    int minDroppings = -1;
    int GlassForMinDroppings = -1;
    for (int Glass=1; Glass < numGlass; Glass++)
    {
        int maxDroppingsBelow = GlassFallingRecur(numFloors-1, Glass-1); // glass broke at floorK
        int maxDroppingsAbove = GlassFallingRecur(numFloors, numGlass-Glass); // glass did not break at floorK
 
        // We choose the worst of the cases becuase we do not know
        // when or if the glass will break or not
        int maxDroppingsAtGlass = Math.max(maxDroppingsBelow, maxDroppingsAbove);
 
        // we can choose that floor for which the above worse-case number is minimum
        if (minDroppings == -1 || minDroppings > maxDroppingsAtGlass)
        {
            minDroppings = maxDroppingsAtGlass;
            GlassForMinDroppings = Glass;
        }
    }
    // Add 1 because whatever floor we choose, one glass must be dropped on that floor
    return minDroppings + 1;
}
}

class GlassDropBottom{

int GlassFallingBottom(int numFloors, int numGlass)
{
    int lookup[][] = new int[numFloors+1][numGlass+1];
 
    // For a single floor, Drops required = numGlass
    for (int glasses=0; glasses<=numGlass; glasses++)
    {
        lookup[1][glasses] = glasses;
    }
 
    // For 0, 1 and 2 glasses, num of Drops is fixed as well
    // regardless of the number of Floors
    for (int floorsk=1; floorsk<=numFloors; floorsk++)
    {
    	//we have three cases 
        lookup[floorsk][0] = 0; // For 0 Glasses, 0 Drops
        lookup[floorsk][1] = 1; // For 1 Glass, 1 Drop
        lookup[floorsk][2] = 2; // For 2 Glasses, 2 Drops
    }
 
    for (int floorsk=2; floorsk<=numFloors; floorsk++) // loop on the number of floors 
    {
        for (int glasses=3; glasses<=numGlass; glasses++) // loop on the number of glasses 
        {
            int minDrops = -1; // indicate there has been no drops 
            for (int x=1; x<=glasses; x++)
            {
                int maxDropsAtGlass = 1 + Math.max( // utilize the java math package to perform calculations 
                        lookup[floorsk-1][x-1],
                        lookup[floorsk][glasses-x]
                    );
                if (minDrops == -1 || minDrops > maxDropsAtGlass) // Three conditional boolean statement to filter drop  
                {												  // case	
                    minDrops = maxDropsAtGlass;
                    lookup[floorsk][glasses] = minDrops;
                }
            }
        }
        printLookup (lookup);
    }
    return lookup[numFloors][numGlass];
}
 
void printLookup(int[][] lookup)
{
    System.out.println ();
    for (int i=0; i<lookup.length; i++)
    {
        for (int j=0; j<lookup[i].length; j++)
        {
            System.out.print (String.format("%3d", lookup[i][j]));// this solution will provide a view of 
        }														  // the array in regards to glass drops and floors 
        System.out.println();
    }
}
}

class MemorizeCuttingRod{   



public int MemorizeCutRod(int[] CostPerCut, int C) {
        int[] Proceeds = new int[C + 1];//Proceeds[i] corresponds to the maximum proceeds of length i. We define Proceeds[0] = 0.
        for (int i = 0; i < Proceeds.length; i++) {
        	Proceeds[i] = -1;// -1 is used to express that the Proceeds are not set at the moment instead of
            			 // because proceeds will always be positive.
        }
        return memoizedCutRod(CostPerCut, C, Proceeds);
    }

    public int memoizedCutRod(int[] CostPerCut, int C, int[] Proceeds) {
        if (Proceeds[C] >= 0) {// enforce the integrity of Proceeds to be above 0
            return Proceeds[C];
        }
        int Max = Integer.MIN_VALUE; 
        if (C == 0) {
            Max = 0;
          
          
        } else {
            for (int i = 0; i < C; i++) {
                Max = Math.max(Max, CostPerCut[i] + memoizedCutRod(CostPerCut, C - i - 1, Proceeds));// we use the Math function call 
                																				   // in java to perform calculations	
            }
        }
        Proceeds[C] = Max;
        return Max;
    }
}


public class RodCuttingBottomUp {

    public int CutRodBottomUp(int[] CostPerCut, int C) {
        int[] Proceeds = new int[C + 1];
        Proceeds[0] = 0;//Proceeds of a certain rod of length 0 = 0.
        int max = Integer.MIN_VALUE;
        for (int j = 1; j <= C; j++) {//Pieces[j] reveal maximum proceeds of a rod of length j.
            max = Integer.MIN_VALUE;
            for (int i = 0; i < j; i++) {
                max = Math.max(max, CostPerCut[i] + Proceeds[j - i - 1]);
            }
            Proceeds[j] = max;
        }
        return Proceeds[C];
    }

}



public class MainGlass {

	public static void main(String[] args) {
		
		
		GlassDropTop A = new GlassDropTop();
		System.out.println("this is the (Glass drop Top) total" + " "
				+ ""+ A.GlassFallingRecur(2,27));//numbers are backwards but it works 
		
		
		GlassDropBottom B = new GlassDropBottom();
		// So we have Floors and Glasses  
		System.out.println("The minimum number of trials in the worst case is "
				+ "total" +" " +B.GlassFallingBottom(3,100));//numbers are backwards but it works 
		
		System.out.println();
		System.out.println();
		
		MemorizeCuttingRod C = new MemorizeCuttingRod();
        int[] CostPerCut = { 1, 5, 8, 9, 10, 17, 19, 20, 24, 30 };
        System.out.println("These are the Memorized Rod maximum cost");
        for (int i = 0; i < CostPerCut.length + 1; i++) {
        	   int FirstMax = C.MemorizeCutRod(CostPerCut, i);
               System.out.printf("max: %d%n", FirstMax);
        }      
        System.out.println();
        System.out.println();      
               
        RodCuttingBottomUp E = new RodCuttingBottomUp();
        int[] Cost = { 1, 5, 8, 9, 10, 17, 19, 20, 24, 30 };
        System.out.println("These are the ButtomUp Rod maximum cost");
        for (int j = 0; j < Cost.length + 1; j++) {
        	int SecondMax  = E.CutRodBottomUp(Cost, j);
        	 System.out.printf("Rod max: %d%n", SecondMax );
        
        }
        	 
        }
		
	}


