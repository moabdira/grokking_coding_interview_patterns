/* SubSets: A huge number of coding interview problems involve dealing with Permutations and 
 * Combinations of a given set of elements. This pattern describes an efficient 
 * Breadth First Search (BFS) approach to handle all these problems.
 * 
 * To generate all subsets of the given set, we can use the Breadth First Search (BFS) approach. 
 * We can start with an empty set, iterate through all numbers one-by-one, and add them to existing 
 * sets to create new subsets.
 * 
 * Let’s take the example-2 mentioned above to go through each step of our algorithm:
 * Given set: [1, 5, 3]
 * 	1. Start with an empty set: [[]]
 *  2. Add the first number (1) to all the existing subsets to create new subsets: [[], [1]];
 * 	3. Add the second number (5) to all the existing subsets: [[], [1], [5], [1,5]];
 * 	4. Add the third number (3) to all the existing subsets: [[], [1], [5], [1,5], [3], [1,3], [5,3], [1,5,3]].
 */

/* Subsets (easy)
 * Given a set with distinct elements, find all of its distinct subsets.
 * 
 * Input: [1, 3] Output: [], [1], [3], [1,3]
 * Input: [1, 5, 3] Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3]
 */
fun subSets(elements: List<Int>): List<List<Int>> {
    val subsets = mutableListOf<List<Int>>()
    subsets.add(emptyList())
   
    for(item in elements){
        
        val newList = mutableListOf<List<Int>>()
        
        for(subset in subsets){
            val newSS = mutableListOf<Int>()
            subset.forEach{ it -> newSS.add(it) }
            newSS.add(item)
            newList.add(newSS)
            //println("subset=$subset newSS=$newSS newList=$newList")
        }
        
        newList.forEach{ it -> subsets.add(it) }
    }
    
    return subsets
}

/* Subsets With Duplicates (easy)
 * Given a set of numbers that might contain duplicates, find all of its distinct subsets.
 * 
 * Input: [1, 3, 3] 
 * Output: [], [1], [3], [1,3], [3,3], [1,3,3]
 * 
 * Input: [1, 5, 3, 3]
 * Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3], [3,3], [1,3,3], [3,3,5], [1,5,3,3] 
 */
fun duplicateSubSets(list: List<Int>): List<List<Int>> {
    // sort the list to avoid duplicates later
    Collections.sort(list)
    
    val subsets = mutableListOf<List<Int>>()
    subsets.add(emptyList()) // start with empty list
    
    var prevItem: Int = Int.MAX_VALUE
    var prevSubsets: List<List<Int>>? = null
    for(item in list){
        // copy and add item
        val newSubsets = mutableListOf<List<Int>>() // for temporary holding new level items
        
        if(prevItem == item){
            prevSubsets?.forEach{ subset -> 
                val newItem = mutableListOf<Int>()
            	subset.forEach{ newItem.add(it) }
      			newItem.add(item)
                newSubsets.add(newItem) // add the updated subset
            }
            println("=========")
        } else {
            subsets.forEach{ subset ->
                val newItem = mutableListOf<Int>()
                subset.forEach{ newItem.add(it) } // copy
            	newItem.add(item)
            	newSubsets.add(newItem) // add the updated subset
            }
        }
        
        //println("newSubsets=$newSubsets")
        
        subsets.addAll(newSubsets)
        //println("subsets=$subsets")
        prevSubsets = newSubsets // keep track incase we need it for duplicates
        prevItem = item
    }
    
    
    return subsets
}

/* Permutations (medium)
 * Given a set of distinct numbers, find all of its permutations.
 * 
 * Permutation is defined as the re-arranging of the elements of the set. For example, {1, 2, 3} 
 * has the following six permutations:
 * 	1. {1, 2, 3}
 * 	2. {1, 3, 2}
 *  3. {2, 1, 3}
 * 	4. {2, 3, 1}
 * 	5. {3, 1, 2}
 * 	6. {3, 2, 1}
 * 
 * If a set has ‘n’ distinct elements it will have n! permutations.
 * 
 * Input: [1,3,5] Output: [1,3,5], [1,5,3], [3,1,5], [3,5,1], [5,1,3], [5,3,1]
 */
 fun generatePermutations(perm: List<List<Int>>, num: Int): List<List<Int>>{
     for(itemList in perm){
         
     }
 }
 fun permutations(list: List<Int>): List<List<Int>>{
     if(list.isEmpty()) return listOf(emptyList())
     
     val permutation = mutableListOf<List<Int>>()
     for(item in list){
         
     }
 }

/* String Permutations by changing case (medium)
 * 
 * Given a string, find all of its permutations preserving the character sequence but changing case.
 */  

fun main() {
	val result_1 = subSets(listOf(1, 5, 3))
    val result_2 = duplicateSubSets(listOf(1, 3, 3, 5))
    val result_3 = permutations(listOf(1, 2, 3))
    
    println("Result for subsets: $result_1")
    println("Result for subsets: $result_2")
    println("Result for permutations: $result_3")
}
