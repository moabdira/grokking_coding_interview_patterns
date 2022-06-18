/**
 * TWO POINTERS PATTERN: Problems where we deal with sorted arrays (or LinkedList) and 
 * need to find a set of elements that fulfill certain contraints. The set of elements could be a pair, 
 * a triplet or even a subarray.
 * 
 * Ex: Given an array of sorted numbers and a target sum, find a pair in the array whose sum is equal 
 * to the given target.
 * 
 * Solution Pattern 1: Start with one pointer in the beginning and another pointer at the end. At, every
 * step we will see if the numbers pointed by the two pointers add up to the target sum. If they don't, 
 * we will do one of the two things.
 * 	1. If the sum of the two numbers pointed by the two pointers is greater than the target sum? 
 * 	   decrement the end-pointer.
 *  2. If the sum of the two numbers pointed by the two pointers is smaller than tha target sum?
 * 	   increment the start-pointer.
 * 
 * Solution Pattern 2: If the list is not sorted, Instead of using a two-pointer or a binary search approach, 
 * we can utilize a HashTable to search for the required pair. We can iterate through the array one number 
 * at a time. Let’s say during our iteration we are at number ‘X’, 
 * so we need to find ‘Y’ such that “X + Y == Target”. We will do two things here:
 *  1. Search for ‘Y’ (which is equivalent to “Target - X”) in the HashTable. If it is there, 
 * 	   we have found the required pair.
 *  2. Otherwise, insert “X” in the HashTable, so that we can search it for the later numbers.
 */ 

import java.util.Arrays

/**
 * Given an array of sorted numbers and a target sum, find a pair in the array whose sum is equal 
 * to the given target.
 * Input: [1, 2, 3, 4, 6], target=6 Output: [1, 3]
 * 	Explanation: The numbers at index 1 and 3 add up to 6: 2+4=6
 * Input: [2, 5, 9, 11], target=11 Output: [0, 2]
 * 	Explanation: The numbers at index 0 and 2 add up to 11: 2+9=11
 */ 
fun findTargetPairSorted(arr: IntArray, target: Int): List<Int> {
    if(arr.size < 2)
    	return emptyList()
   
    var start_pt = 0
    var end_pt = arr.size - 1
    
    while(start_pt < end_pt) {
        val sum = arr[start_pt] + arr[end_pt]
        
        if(sum == target)
            return listOf<Int>(start_pt, end_pt)
        
        if(sum < target)
        	++start_pt
        else
        	--end_pt
    }
    
    return emptyList()
}

/**
 * Squaring a Sorted Array (easy)
 * Given a sorted array, create a new array containing squares of all the numbers of the input array 
 * in the sorted order.
 * Input: [-2, -1, 0, 2, 3] Output: [0, 1, 4, 4, 9]
 * Input: [-3, -1, 0, 1, 2] Output: [0, 1, 1, 4, 9]
 */ 
fun squareSortedArray(arr: IntArray): IntArray {
    var result = IntArray(arr.size)
    var index = result.size - 1
    var start = 0
    var end = arr.size - 1
    
    while(start <= end){
        //println("start=$start end=$end index=$index result=${Arrays.toString(result)}")
        var startVal = arr[start] * arr[start]
        var endVal = arr[end] * arr[end]
        if(startVal > endVal){
            result[index] = startVal
            --index
            if(endVal == 0) 
            	result[0] = 0
            else {
                result[index] = endVal
                --index
            }
            ++start
            --end
        } else {
            result[index] = endVal
            --index
            --end
        }
    }
    
    return result
} 
 
/**
 * Triplet Sum to Zero (medium)
 * Given an array of unsorted numbers, find all unique triplets in it that add up to zero.
 * 
 * Input: [-3, 0, 1, 2, -1, 1, -2] Output: [-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]
 * Input: [-5, 2, -1, -2, 3] Output: [[-5, 2, 3], [-2, -1, 3]]
 */ 
private fun searchTargetPair(arr: IntArray, start: Int, x: Int, target: Int, result: MutableList<List<Int>>){
    var startI = start; var endI = arr.size - 1
    while(startI < endI){
        val sum = x + arr[startI] + arr[endI]
        
        if(arr[startI] == arr[startI - 1]){ ++startI; continue } // skip duplicates
        if(endI < arr.size - 1 && arr[endI] == arr[endI + 1]){ --endI; continue } // skip duplicates
        
        if(sum < target)
        	++startI
        else if(sum > target)
        	--endI
        else{
         	result.add(listOf<Int>(x, arr[startI], arr[endI]))
            ++startI
        }
            
    }
}
fun findAllUniqueTriplets(arr: IntArray, target: Int): List<List<Int>> {
    if(arr.size < target) return emptyList()
    
    Arrays.sort(arr) // Time Complexity = O(NLogN)
    val result = mutableListOf<List<Int>>()
    
    for((i, x) in arr.withIndex()){ // Time Complexity = O(N^2)
        // Skip duplicates
        if(i > 0 && arr[i-1] == x)
        	continue // skip duplicates
        if(i < arr.size - 2)
        	searchTargetPair(arr, i+1, x, target, result) // Time Complexity = O(N)
    }
   	
    return result
} 

/**
 * Triplet Sum Close to Target (medium)
 * Given an array of unsorted numbers and a target number, find a triplet in the array whose sum 
 * is as close to the target number as possible, return the sum of the triplet. If there are more 
 * than one such triplet, return the sum of the triplet with the smallest sum.
 * 
 * Input: [-2, 0, 1, 2], target=2 Output: 1 
 * 	Explanation: The triplet [-2, 1, 2] has the closest sum to the target.
 * Input: [-3, -1, 1, 2], target=1 Output: 0
 * 	Explanation: The triplet [-3, 1, 2] has the closest sum to the target.
 * Input: [1, 0, 1, 1], target=100 Output: 3
 * 	Explanation: The triplet [1, 1, 1] has the closest sum to the target.
 */ 
private fun searchTargetPairClose(arr: IntArray, start: Int, x: Int, target: Int): Int {
    var startIdx = start; var endIdx = arr.size - 1
    var smallestDifference = Int.MAX_VALUE
    while(startIdx < endIdx){
        // Avoid duplicates
        //if(arr[startIdx] == arr[startIdx - 1]) { ++startIdx; continue }
        //if(endIdx < arr.size - 1 && arr[endIdx] == arr[endIdx + 1]) { --endIdx; continue }
        
        val targetDiff = target - x - arr[startIdx] - arr[endIdx]
                 
        if(targetDiff == 0) return target // we have found a triplet with exact sum
        // the second part of the above 'if' is to handle the smallest sum when we have more than one solution
        if (Math.abs(targetDiff) < Math.abs(smallestDifference)
            || (Math.abs(targetDiff) == Math.abs(smallestDifference) && targetDiff > smallestDifference))
        	smallestDifference = targetDiff; // save the closest and the biggest difference  
        
        //println("($x, ${arr[startIdx]}, ${arr[endIdx]}) == $targetDiff | smallestDifference=$smallestDifference")
        
        if(targetDiff > 0) ++startIdx // we need a triplet with a bigger sum
        else --endIdx // we need a triplet with a smaller number
    }
    //println("($target - $smallestDifference) = ${target - smallestDifference}")
    return target - smallestDifference
} 
fun findTripletCloseToTarget(arr: IntArray, target: Int): Int {
    var smallestTripletSum = Int.MAX_VALUE
    Arrays.sort(arr) // time complexity = O(NLogN)
    for((i, x) in arr.withIndex()){
        if(i < arr.size - 2){
            val currentSum = searchTargetPairClose(arr, i + 1, x, target)
            if(currentSum < smallestTripletSum)
            	smallestTripletSum = currentSum
        }
    }
    
    return smallestTripletSum
}

/**
 * Triplets with Smaller Sum (medium)
 * Given an array arr of unsorted numbers and a target sum, count all triplets in it such that 
 * arr[i] + arr[j] + arr[k] < target where i, j, and k are three different indices. 
 * Write a function to return the count of such triplets.
 * 
 * Input: [-1, 0, 2, 3], target=3 Output: 2
 * 	Explanation: There are two triplets whose sum is less than the target: [-1, 0, 3], [-1, 0, 2]
 * Input: [-1, 4, 2, 1, 3], target=5 Output: 4
 * 	Explanation: There are four triplets whose sum is less than the target: 
 * 	[-1, 1, 4], [-1, 1, 3], [-1, 1, 2], [-1, 2, 3]
 */ 
 fun countAllSmallerTriplets(arr: IntArray, target: Int): Int {
     var result = mutableListOf<List<Int>>()
     Arrays.sort(arr) // time complexity = O(NLogN)
     
     for(i in arr.indices){ // time complexity = O(N^2)
         var start = i + 1
         var end = arr.size - 1
         while(start < arr.size - 2){
             //println("result=$result")
             // skip duplicates
             if(arr[start] == arr[start - 1]) { ++start; continue }
             if(end < arr.size - 2 && arr[end] == arr[end + 1]) { ++end; continue }
             
             val count = arr[i] + arr[start] + arr[end]
             if(count < target)
             	result.add(listOf(arr[i], arr[start], arr[end]))
             
             --end
             if(end > start)
             	continue
             else {
                 ++start
                 end = arr.size - 1
             }
        
         }
     }
     
     //println("result=$result")
     return result.size
 }
 
/**
 * Subarrays with Product Less than a Target (medium)
 * Given an array with positive numbers and a positive target number, find all of its 
 * contiguous subarrays whose product is less than the target number.
 * 
 * Input: [2, 5, 3, 10], target=30 Output: [2], [5], [2, 5], [3], [5, 3], [10]
 * 	Explanation: There are six contiguous subarrays whose product is less than the target.
 * Input: [8, 2, 6, 5], target=50 Output: [8], [2], [8, 2], [6], [2, 6], [5], [6, 5] 
 * 	Explanation: There are seven contiguous subarrays whose product is less than the target.
 */  
fun findAllContiguousProduct(arr: IntArray, target: Int): Set<Set<Int>> {
    if(arr.size < 1) return emptySet()
    if(arr.size == 1) return setOf(arr.toSet())
    
    val result = mutableSetOf<Set<Int>>()
    var p1 = 0; var p2 = 1
    while(p1 < p2 && p2 < arr.size){
        if(arr[p1] < target) result.add(setOf(arr[p1]))
        if(arr[p2] < target) result.add(setOf(arr[p2]))
        
        var i = p1
        var product = 1
        var tempSet = mutableSetOf<Int>()
        while(i <= p2) {
            product *= arr[i]
            tempSet.add(arr[i])
            ++i
        }
        if(product < target){
            result.add(tempSet)
            ++p2
        } else {
            ++p1
        }
        
    }
    
    return result
}
 
/**
 * Dutch National Flag Problem (medium)
 * Given an array containing 0s, 1s and 2s, sort the array in-place. You should treat numbers 
 * of the array as objects, hence, we can’t count 0s, 1s, and 2s to recreate the array.
 * The flag of the Netherlands consists of three colors: red, white and blue; and since our 
 * input array also consists of three different numbers that is why it is called Dutch National Flag problem.
 * 
 * Input: [1, 0, 2, 1, 0] Output: [0, 0, 1, 1, 2]
 * Input: [2, 2, 0, 1, 2, 0] Output: [0, 0, 1, 2, 2, 2,]
 */  
fun dutchNationalFlag(arr: IntArray): IntArray{
    var start = 0; var end = arr.size - 1
    
    var i = 0
    while(i <= end){
//         print("Result = [")
//    		arr.forEach { print("$it, ") }
//    		println("]")
        
        if(arr[i] == 0){
            // swap it
            val temp = arr[i]
            arr[i] = arr[start]
            arr[start] = temp
            ++start
            ++i
        } else if(arr[i] == 2) {
            // swap it
            val temp = arr[i]
            arr[i] = arr[end]
            arr[end] = temp
            --end
        } else {
            ++i
        }
    }
    
    return arr
} 


