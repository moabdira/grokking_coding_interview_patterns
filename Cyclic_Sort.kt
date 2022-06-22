/* Cyclic Sort: This pattern describes an interesting approach to deal with problems 
 * involving arrays containing numbers in a given range. For example, take the following 
 * problem:
 * 	You are given an unsorted array containing n numbers taken from the range 1 to n. 
 * 	The array can have duplicates, which means that some numbers will be missing. 
 * 	Find all the missing numbers.
 * 
 * To efficiently solve this problem, we can use the fact that the input array contains 
 * numbers in the range of 1 to n. For example, to efficiently sort the array, we can 
 * try placing each number at its correct place, i.e., placing 1 at index '0', placing 2 
 * at index ‘1’, and so on. Once we are done with the sorting, we can iterate the array 
 * to find all indices missing the correct numbers. These will be our required numbers.
 */ 
 
/* Cyclic Sort (easy)
 * Write a function to sort the objects in-place on their creation sequence number in O(n)
 * and without using any extra space. For simplicity, let’s assume we are passed an integer 
 * array containing only the sequence numbers, though each number is actually an object.
 * 
 * Input: [3, 1, 5, 4, 2] Output: [1, 2, 3, 4, 5]
 * Input: [2, 6, 4, 3, 1, 5] Output: [1, 2, 3, 4, 5, 6]
 * Input: [1, 5, 6, 4, 3, 2] Output: [1, 2, 3, 4, 5, 6]
 */ 
fun cyclicSort(list: MutableList<Int>): List<Int> {
    var i = 0
    while(i < list.size - 1) {
        val item = list.get(i)
        if(item != i + 1){
            // put in its correct place
            val temp = list[item - 1]
            list[i] = temp
            list[item - 1] = item
            
            if(list.get(i) != i + 1)
            	continue // don't increment since the new item is not in place.
        }
        
        ++i
    }
    
    return list
}

/* Find the Missing Number (easy)
 * We are given an array containing n distinct numbers taken from the range 0 to n. 
 * Since the array has only n numbers out of the total n+1 numbers, find the missing number.
 * 
 * Input: [4, 0, 3, 1] Output: 2
 * Input: [8, 3, 5, 2, 4, 6, 0, 1] Output: 7
 */
 fun findMissingNum(list: MutableList<Int>): Int {
     // Calculate the total for 1..n+1
     var total = 0
     for(item in 0..(list.size))
     	total += item
     
     var total_input = 0
     for(item in list)
     	total_input += item
     
     return total - total_input
 }
 
/* Find all Missing Numbers (easy)
 * We are given an unsorted array containing numbers taken from the range 1 to ‘n’. 
 * The array can have duplicates, which means some numbers will be missing. 
 * Find all those missing numbers.
 * 
 * Input: [2, 3, 1, 8, 2, 3, 5, 1] Output: 4, 6, 7
 * 	Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, 
 * 	and 7 are missing.
 * Input: [2, 4, 1, 2] Output: 3
 * Input: [2, 3, 2, 1] Output: 4
 */  
 fun swap(list: MutableList<Int>, i: Int, j: Int){
     val j = list[i] - 1
     var temp = list[i]
     list[i] = list[j]
     list[j] = temp
 }
 fun findAllMissingNum(list: MutableList<Int>): List<Int> {
     val result = mutableListOf<Int>()
     // Sort it
     var i = 0
     while(i < list.size){
         if(list[i] != i + 1 && list[i] != list[list[i] - 1]){
             // swap it
             val j = list[i] - 1
             var temp = list[i]
     		 list[i] = list[j]
     		 list[j] = temp
             continue
         }
         
         ++i	 
     }
     
     //println("Result: $list")
     
     // find all the missing numbers (list[i] != i + 1)
     for((i, item) in list.withIndex()){
         if(item != i + 1)
         	result.add(i + 1)
     }
     
     return result
 }
 
/* Find the Duplicate Number (easy)
 * We are given an unsorted array containing ‘n+1’ numbers taken from the range 1 to ‘n’. 
 * The array has only one duplicate but it can be repeated multiple times. Find that 
 * duplicate number without using any extra space. You are, however, allowed to 
 * modify the input array.
 * 
 * Input: [1, 4, 4, 3, 2] Output: 4
 * Input: [2, 1, 3, 3, 5, 4] Output: 3
 * Input: [2, 4, 1, 4, 4] Output: 4
 */
 fun findDuplicateNum(list: MutableList<Int>): Int {
     // Sort it first
     var i = 0
     while(i < list.size){
         if(list[i] != i + 1){
             if(list[i] == list[list[i] - 1])
             	return list[i] // found the duplicate
             //swap it
             swap(list, i, list[i] - 1)
         } else 
         	 ++i
     }
     
     return -1 // No duplicate found
 }
 
 /* Find all Duplicate Numbers (easy)
  * We are given an unsorted array containing n numbers taken from the range 1 to n. 
  * The array has some numbers appearing twice, find all these duplicate numbers using 
  * constant space.
  * 
  * Input: [3, 4, 4, 5, 5] Output: [4, 5]
  * Input: [5, 4, 7, 2, 3, 5, 3] Output: [3, 5]
  */ 
 fun findAllDuplicateNum(list: MutableList<Int>): List<Int> {
     val result = mutableListOf<Int>()
     
     var i = 0
     while(i < list.size){
         if(list[i] != i + 1){
             if(list[i] == list[list[i] - 1])
             	result.add(list[i]) // found the duplicate
             else {
                 //swap it
                 swap(list, i, list[i] - 1)
                 continue
             }
         }
         
         ++i
     }
     
     return result
 }

 /* Problem 1: Find the Corrupt Pair (easy)
  * We are given an unsorted array containing ‘n’ numbers taken from the range 1 to ‘n’. 
  * The array originally contained all the numbers from 1 to ‘n’, but due to a data error, 
  * one of the numbers got duplicated which also resulted in one number going missing. 
  * Find both these numbers.
  * 
  * Input: [3, 1, 2, 5, 2] Output: [2, 4]
  * 	Explanation: '2' is duplicated and '4' is missing.
  * Input: [3, 1, 2, 3, 6, 4] Output: [3, 5]
  * 	Explanation: '3' is duplicated and '5' is missing.
  */ 
 fun findCorruptPair(list: MutableList<Int>): List<Int> {
     val result = mutableListOf<Int>()
     
     var i = 0
     while(i < list.size){
         if(list[i] != i + 1){
             if(list[i] == list[list[i] - 1]) {
             	result.add(list[i])
                result.add(i + 1)
             } else {
                 //swap it
                 swap(list, i, list[i] - 1)
                 continue
             }
         }
         
         ++i
     }
     
     return listOf(result[result.size - 2], result[result.size - 1])
 }
 
 /* Problem 2: Find the Smallest Missing Positive Number (medium)
  * Given an unsorted array containing numbers, find the smallest missing positive number 
  * in it.  Note: Positive numbers start from ‘1’.
  * 
  * Input: [-3, 1, 5, 4, 2] Output: 3
  * 	Explanation: The smallest missing positive number is '3'
  * Input: [3, -2, 0, 1, 2] Output: 4
  * Input: [3, 2, 5, 1] Output: 4 
  */
 fun findSmallestMissingPositiveNumber(list: MutableList<Int>): Int {
     // sort it first
     var i = 0
     while(i < list.size){
         if(list[i] > 0){
            if(list[i] != i + 1){
                if(list[i] - 1 < list.size){
                	swap(list, i, list[i] - 1)
                	continue
                } 
            } 
         }
         
         ++i
     }
     
     for(i in list.indices){
         if(list[i] <= 0)
         	return i + 1
         
         if(i < list.size && list[i + 1] - list[i] > 1)
         	return list[i] + 1
     }
     
     return -1
 }
 
 /* Problem 3: Find the First K Missing Positive Numbers (hard)
  * Given an unsorted array containing numbers and a number ‘k’, 
  * find the first ‘k’ missing positive numbers in the array.
  * 
  * Input: [3, -1, 4, 5, 5], k=3 Output: [1, 2, 6] 
  * 	Explanation: The smallest missing positive numbers are 1, 2 and 6.
  * Input: [2, 3, 4], k=3 Output: [1, 5, 6]
  * 	Explanation: The smallest missing positive numbers are 1, 5 and 6.
  * Input: [-2, -3, 4], k=2 Output: [1, 2]
  * 	Explanation: The smallest missing positive numbers are 1 and 2.
  */
 fun findFirstKMissingPosNum(list: MutableList<Int>, k: Int): List<Int> {
     val result = mutableListOf<Int>()
     
     // sort it first
     var max = Int.MIN_VALUE
     var i = 0
     while(i < list.size){
         // keep track of max value
         if(list[i] > max) max = list[i]
         
         if(list[i] > 0 && list[i] != i + 1){
            if(list[i] - 1 < list.size && list[i] != list[list[i] - 1]){
                // swap it
                swap(list, i, list[i] - 1)
                continue
            }
         }
         
         ++i
     }
     
     println("list=$list")
     
     for((i, item) in list.withIndex()){
         if(result.size >= k) break // we are done
         
         if(item <= 0 || item != i + 1){
             // it is missing
             result.add(i + 1)
         }
     }
     
     if(result.size < k){
         var missing = result.size
         while(missing < k){
             ++missing
             ++max
             result.add(max)   
         }
     }
     
     return result
 }
  
fun main() {
   val result_1 = cyclicSort(mutableListOf(1, 5, 6, 4, 3, 2))
   val result_2 = findMissingNum(mutableListOf(8, 3, 5, 2, 4, 6, 0, 1))
   val result_3 = findAllMissingNum(mutableListOf(2, 3, 2, 1))
   val result_4 = findDuplicateNum(mutableListOf(2, 4, 1, 4, 4))
   val result_5 = findAllDuplicateNum(mutableListOf(5, 4, 7, 2, 3, 5, 3))
   
   val prob_1 = findCorruptPair(mutableListOf(3, 1, 2, 5, 2))
   val prob_2 = findSmallestMissingPositiveNumber(mutableListOf(3, 2, 5, 1))
   val prob_3 = findFirstKMissingPosNum(mutableListOf(-2, -3, 4), 2)
   
   println("Result for cyclicSort: $result_1")
   println("Result for findMissingNum: $result_2")
   println("Result for findAllMissingNum: $result_3")
   println("Result for findDuplicateNum: $result_4")
   println("Result for findAllDuplicateNum: $result_5")
   println("Result for findCorruptPair: $prob_1")
   println("Result for findSmallestMissingPositiveNumber: $prob_2")
   println("Result for findFirstKMissingPosNum: $prob_3")
   
}
