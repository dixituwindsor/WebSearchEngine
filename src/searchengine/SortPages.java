package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;


public class SortPages {
	
	private static final int CUTOFF = 3;
	
	//ranking pages with Collections sort
	public static void rankPages(Hashtable<?, Integer> files, int numberOfFiles) {

		ArrayList<Map.Entry<?, Integer>> listOfFile = new ArrayList<Map.Entry<?, Integer>>(files.entrySet());
		
		Comparator<Map.Entry<?, Integer>> compareByFreq=
				(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2)->o1.getValue().compareTo(o2.getValue());
				
		//List in reverse order of rank
		Collections.sort(listOfFile, compareByFreq.reversed());		
				
	    System.out.println("\nRanked URLs are:");
		int no=1;
		for(int i=0;i<listOfFile.size();i++)
		{
			if(listOfFile.get(i).getKey()!=null) {
				System.out.println("(" + no + ") " + listOfFile.get(i).getKey() + " - frequency of Word: "+ listOfFile.get(i).getValue());
				no++;
			}
		}
	}
	
	/**
     * Quicksort algorithm.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void quicksort( AnyType [ ] a )
    {
    	if(a.length>1)
    	{
        quicksort( a, 0, a.length - 1 );
    	}
        System.out.println("\nSorted URLs are:");
        int srno=1;
		for( int i = 0; i < a.length; i++ )
		{
			System.out.println("(" + srno + ") " + a[i]);
			srno++;
		}
    }
    
    /**
     * Internal quicksort method that makes recursive calls.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void quicksort( AnyType [ ] a, int l, int r )
    {
        if( l + CUTOFF <= r )
        {
            AnyType pivot = median3( a, l, r );

                // Begin partitioning
            int i = l, j = r - 1;
            for( ; ; )
            {
                while( a[ ++i ].compareTo( pivot ) < 0 ) { }
                while( a[ --j ].compareTo( pivot ) > 0 ) { }
                if( i < j )
                	changeReferences( a, i, j );
                else
                    break;
            }

            changeReferences( a, i, r - 1 );   // Restore pivot

            quicksort( a, l, i - 1 );    // Sort small elements
            quicksort( a, i + 1, r );   // Sort large elements
        }
        else  // Do an insertion sort on the subarray
            insertionSort( a, l, r );
    }
    
    /**
     * Return median of left, center, and right.
     * Order these and hide the pivot.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    AnyType median3( AnyType [ ] a, int l, int r)
    {
        int m = ( l + r ) / 2;
        if( a[ m ].compareTo( a[ l ] ) < 0 )
        	changeReferences( a, l, m );
        if( a[ r ].compareTo( a[ l ] ) < 0 )
        	changeReferences( a, l, r );
        if( a[ r ].compareTo( a[ m ] ) < 0 )
        	changeReferences( a, m, r );

            // Place pivot at position right - 1
        changeReferences( a, m, r - 1 );
        return a[ r - 1 ];
    }
    
    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static <AnyType> void changeReferences( AnyType [ ] a, int index1, int index2 )
    {
        AnyType tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }

    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void insertionSort( AnyType [ ] a, int l, int r )
    {
        for( int p = l + 1; p <= r; p++ )
        {
            AnyType tmp = a[ p ];
            int j;

            for( j = p; j > l && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

   
}
