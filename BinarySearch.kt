/* Modified Binary Search: This pattern describes an efficient way to handle all problems 
 * involving Binary Search. We will go through a set of problems that will help us build 
 * an understanding of this pattern so that we can apply this technique to other problems 
 * we might come across in the interviews.
 * 
 */ 

/* Order-agnostic Binary Search (easy)
 * Given a sorted array of numbers, find if a given number ‘key’ is present in the array. 
 * Though we know that the array is sorted, we don’t know if it’s sorted in ascending or 
 * descending order. You should assume that the array can have duplicates.
 * 
 * Write a function to return the index of the ‘key’ if it is present in the array, 
 * otherwise return -1.
 * 
 * Input: [4, 6, 10], key = 10 Output: 2
 * Input: [1, 2, 3, 4, 5, 6, 7], key = 5 Output: 4
 * Input: [10, 6, 4], key = 10 Output: 0
 * Input: [10, 6, 4], key = 4 Output: 2
 */ 
fun orderAgnosticBS(list: List<Int>, k:Int, start: Int, end: Int): Int {
    if(list.isEmpty() || start > end) return -1
    
    // Safest way to find the middle of two numbers without getting overflow is: start + (end-start)/2
    var mid = Math.round(start + (end-start) / 2f)
    
    if(list[mid] == k) return mid
    
    // check if ascending vs. descening order
    if(list[start] < list[end]){
        if(list[mid] < k)
        	return orderAgnosticBS(list, k, start = mid + 1, end = end)
        else
        	return orderAgnosticBS(list, k, start, end = mid - 1)
    } else {
        if(list[mid] < k)
        	return orderAgnosticBS(list, k, start = start, end = mid - 1)
        else
        	return orderAgnosticBS(list, k, start = mid + 1, end = end)
    }
    	
}

/* Ceiling of a Number (medium)
 * Given an array of numbers sorted in an ascending order, find the ceiling of a given 
 * number ‘key’. The ceiling of the ‘key’ will be the smallest element in the given array 
 * greater than or equal to the ‘key’.
 * 
 * Write a function to return the index of the ceiling of the ‘key’. If there isn’t 
 * any ceiling return -1.
 * 
 * Input: [4, 6, 10], key = 6 Output: 1
 * 	Explanation: The smallest number greater than or equal to '6' is '6' having index '1'.
 * Input: [4, 6, 10], key = 17 Output: -1
 * 	Explanation: There is no number greater than or equal to '17' in the given array.
 * Input: [4, 6, 10], key = -1 Output: 0
 * 	Explanation: The smallest number greater than or equal to '-1' is '4' having index '0'.
 */ 
fun searchCeiling(list: List<Int>, k: Int, start: Int, end: Int, ceiling: Int): Int {
    if(start > end) return ceiling
    
    val mid = Math.round(start + (end - start) / 2f)
  
    if(list[mid] == k) return mid
    val currCeiling = if(mid + 1 < list.size) mid + 1 else -1
    
    if(k > list[mid]) return searchCeiling(list, k, start = mid + 1, end = end, ceiling = currCeiling)
    else return searchCeiling(list, k, start, end = mid - 1, ceiling = currCeiling)
}
fun ceilingOfNumber(list: List<Int>, k: Int): Int {
    if(list.isEmpty()) return -1
    if(k > list[list.size - 1]) return -1 // since there is no number than k
    if(k < list[0]) return 0 // since smallest number greater than or equal to
    
    return searchCeiling(list, k, 0, list.size - 1, -1)
} 

/* Next Letter (medium)
 * Given an array of lowercase letters sorted in ascending order, find the smallest letter 
 * in the given array greater than a given ‘key’.
 * 
 * Assume the given array is a circular list, which means that the last letter is assumed 
 * to be connected with the first letter. This also means that the smallest letter in 
 * the given array is greater than the last letter of the array and is also the first 
 * letter of the array.
 * 
 * Write a function to return the next letter of the given ‘key’.
 * 
 * Input: ['a', 'c', 'f', 'h'], key = 'f' Output: 'h'
 * 	Explanation: The smallest letter greater than 'f' is 'h' in the given array.
 * Input: ['a', 'c', 'f', 'h'], key = 'b' Output: 'c'
 * 	Explanation: The smallest letter greater than 'b' is 'c'.
 * Input: ['a', 'c', 'f', 'h'], key = 'm' Output: 'a'
 * 	Explanation: As the array is assumed to be circular, the smallest letter greater than 'm' is 'a'.
 * Input: ['a', 'c', 'f', 'h'], key = 'h' Output: 'a'
 * 	Explanation: As the array is assumed to be circular, the smallest letter greater than 'h' is 'a'.
 */ 
fun searchNextLetter(list: List<Char>, key: Char, start: Int, end: Int, nextLetter: Int): Char {
    if(start > end) return list[nextLetter]
    
    val mid = Math.round(start + (end - start) / 2f)
    
    if(list[mid] == key) 
    	return if(mid + 1 < list.size) list[mid + 1] else list[0]
    
    if(key < list[mid])
    	return searchNextLetter(list, key, start = start, end = mid - 1, nextLetter = mid - 1 )
    else
    	return searchNextLetter(list, key, start = mid + 1, end = end, nextLetter = mid + 1)
}
fun nextLetter(list: List<Char>, key: Char): Char {
    if(list[list.size - 1] < key) return list[0]
  	
    return searchNextLetter(list, key, 0, list.size - 1, 0)
} 

/* Number Range (medium)
 * Given an array of numbers sorted in ascending order, find the range of a given number 
 * ‘key’. The range of the ‘key’ will be the first and last position of the ‘key’ 
 * in the array.
 * 
 * Write a function to return the range of the ‘key’. If the ‘key’ is not present 
 * return [-1, -1].
 * 
 * Input: [4, 6, 6, 6, 9], key = 6 Output: [1, 3]
 * Input: [1, 3, 8, 10, 15], key = 10 Output: [3, 3]
 * Input: [1, 3, 8, 10, 15], key = 12 Output: [-1, -1]
 */ 
fun searchNumRange(list: List<Int>, key: Int, start: Int, end: Int): List<Int> {
    if(start > end) return listOf(-1, -1)
    
    val mid = Math.round(start + (end - start) / 2f)
    
    if(list[mid] == key){
        // return the range
        val result = mutableListOf<Int>()
        var pt1 = mid; var pt2 = mid
        while(pt1 - 1 >= 0 && list[pt1 - 1] == key) --pt1
        while(pt2 + 1 < list.size && list[pt2 + 1] == key) ++pt2
        
        result.add(pt1); result.add(pt2)
        return result
    }
    
    if(key < list[mid])
    	return searchNumRange(list, key, start, end = mid - 1)
    else
    	return searchNumRange(list, key, start = mid + 1, end = end)
} 
fun numberRange(list: List<Int>, key: Int): List<Int> {
    return searchNumRange(list, key, 0, list.size - 1)
}

/* Search in a Sorted Infinite Array (medium)
 * Given an infinite sorted array (or an array with unknown size), find if a given number 
 * ‘key’ is present in the array. Write a function to return the index of the ‘key’ if it 
 * is present in the array, otherwise return -1.
 * 
 * Since it is not possible to define an array with infinite (unknown) size, you will be 
 * provided with an interface ArrayReader to read elements of the array. 
 * ArrayReader.get(index) will return the number at index; if the array’s size is smaller 
 * than the index, it will return Integer.MAX_VALUE.
 * 
 * Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 16 Output: 6
 * 	Explanation: The key is present at index '6' in the array.
 * Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 11 Output: -1
 * 	Explanation: The key is not present in the array.
 * Input: [1, 3, 8, 10, 15], key = 15 Output: 4
 * 	Explanation: The key is present at index '4' in the array.
 * Input: [1, 3, 8, 10, 15], key = 200 Output: -1
 * 	Explanation: The key is not present in the array.
 */ 
fun searchInfiniteArray(list: List<Int>, key: Int): Int {
    /* An efficient way to find the proper bounds is to start at the beginning of the array
     * with the bound’s size as ‘1’ and exponentially increase the bound’s size 
     * (i.e., double it) until we find the bounds that can have the key.
     */ 
    var windowSize = 2
    var start = 0; var end = windowSize - 1
    while(start < list.size && end < list.size){
        //println("start=${list[start]} end=${list[end]} windowSize=$windowSize")
        val index = orderAgnosticBS(list, key, start, end)
        if(index != -1)
        	return index
        
        // increase the bound size of the array
        windowSize *= 2
        start = end + 1
        if(list.size <= windowSize + end)
        	end = list.size - 1
        else
        	end = end + windowSize
        
        //println("start=$start end=$end windowSize=$windowSize")
    }
    
    return -1
} 

/* Minimum Difference Element (medium)
 * Given an array of numbers sorted in ascending order, find the element in the array 
 * that has the minimum difference with the given ‘key’.
 * 
 * Input: [4, 6, 10], key = 7 Output: 6
 * 	Explanation: The difference between the key '7' and '6' is minimum than any other 
 * 	number in the array
 * Input: [4, 6, 10], key = 4 Output: 4
 * Input: [1, 3, 8, 10, 15], key = 12 Output: 10
 * Input: [4, 6, 10], key = 17 Output: 10 
 */ 
 

fun main() {
   val list = listOf(10, 6, 4)
   val result_1 = orderAgnosticBS(list, 4, 0, list.size - 1)
   val result_2 = ceilingOfNumber(listOf(4, 6, 10), -1)
   val result_3 = nextLetter(listOf('a', 'c', 'f', 'h'), 'h')
   val result_4 = numberRange(listOf(1, 3, 8, 10, 15), 12)
   val result_5 = searchInfiniteArray(
       listOf(1, 3, 8, 10, 15), 200)
   
   println("Result for orderAgnosticBS: $result_1")
   println("Result for ceilingOfNumber: $result_2")
   println("Result for nextLetter: $result_3")
   println("Result for numberRange: $result_4")
   println("Result for searchInfiniteArray: $result_5")
}
