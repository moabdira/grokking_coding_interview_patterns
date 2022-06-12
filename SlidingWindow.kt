/**
 * Sliding Window Problems
 */

// Given an array, find the average of all subarrays of ‘K’ contiguous elements in it.
// Array: [1, 3, 2, 6, -1, 4, 1, 8, 2], K=5
// 
fun avgAllSubArrays(arr: IntArray, k: Int): List<Double> {
    if(arr.size <= k)
    	return emptyList()
    
    val result = mutableListOf<Double>()
    var sum = 0.0
    var pt1 = 0
    var pt2 = k - 1
    
    var i = 0
    while(i <= pt2) {
        sum += arr[i]
        ++i
    }
    result.add(sum / k) // add the first average
    
    while(pt2 < arr.size) {
        sum = sum - arr[pt1]
        // slide the window to the right
        ++pt1; ++pt2
        if(pt2 < arr.size) {
            // calculate the new sum and add it to the average
            sum = sum + arr[pt2]
            result.add(sum / k)
        }
    }
    
    return result
    
}

/**
 * Given an array of positive numbers and a positive number ‘k,’ find the maximum sum of any contiguous subarray of size ‘k’.
 * Input: [2, 1, 5, 1, 3, 2], k=3  
 * Output: 9   Explanation: Subarray with maximum sum is [5, 1, 3].
 * 
*/
fun maxSumSubArray(arr: IntArray, k: Int): List<Int> {
    if(arr.size < k)
    	return emptyList()
       
    val result = mutableListOf<Int>(k)
    var sum = 0
    var maxSum = 0
    var strIndex = 0
    for((endIndex, v) in arr.withIndex()) {  // O(N) time complexity if we are just returning max_sum_value only, where 'N' is the total number of elements in the given array. 
        sum += v
   		if(endIndex == strIndex + (k - 1)) {
            if(sum > maxSum) {
           		maxSum = sum     
           		// Save the indexes	
           		result.clear()
                for(i in strIndex..endIndex) {
                    result.add(arr[i])
                }
            }
            sum = sum - arr[strIndex]
            ++strIndex
        }
    }
    
    return result
}

/**
 * Given an array of positive numbers and a positive number ‘S,’ find the length of the smallest 
 * contiguous subarray whose sum is greater than or equal to ‘S’. Return 0 if no such subarray exists.
 * Input: [2, 1, 5, 2, 3, 2], S=7 Output: 2
 * Input: [2, 1, 5, 2, 8], S=7 Output: 1
 * Input: [3, 4, 1, 1, 6], S=8 Output: 3
 */
 fun findLengthSmallestSubArray(arr: IntArray, K: Int): Int {
     
     if(arr.isEmpty()) return 0
     if(arr.size == 1) return if(arr[0] >= K) 1 else 0
     
  	 var minLength = Int.MAX_VALUE
     var windowSum = 0
     var startWindow = 0
     var endWindow = 0
     
     while(endWindow < arr.size) {
         windowSum += arr[endWindow]
         
         // Shrink the window as small as possible until the 'windowSum' is smaller than 'K' 
         while(windowSum >= K) { 
             minLength = Math.min(minLength, (endWindow - startWindow) + 1)
             windowSum -= arr[startWindow]
             ++startWindow
         }
        
         ++endWindow
     }
     
     return if(minLength == Int.MAX_VALUE) 0 else minLength
 }
 
 /**
  * Longest Substring with maximum K Distinct Characters (medium)
  * Given a string, find the length of the longest substring in it with no more than K distinct characters.
  * Input: String="araaci", K=2 Output: 4 
  * 	Explanation: The longest substring with no more than '2' distinct characters is "araa".
  * Input: String="araaci", K=1 Output: 2
  * 	Explanation: The longest substring with no more than '1' distinct characters is "aa".
  * Input: String="cbbebi", K=3 Output: 5
  * 	Explanation: The longest substrings with no more than '3' distinct characters are "cbbeb" & "bbebi".
  * Input: String="cbbebi", K=10 Output: 6
  * 	Explanation: The longest substring with no more than '10' distinct characters is "cbbebi".
  */ 
 fun maxSubStringDistinctChars(arr: CharArray, K: Int): Int {
     val trackChars = mutableMapOf<Char, Int>()
     var longestSubString = 0
     var startWindow = 0
     var endWindow = 0
     
     while(endWindow < arr.size) {
         if(trackChars.contains(arr[endWindow]))
         	trackChars.put(arr[endWindow], trackChars.get(arr[endWindow])!!.plus(1))
         else
         	trackChars.put(arr[endWindow], 1)
         
         
         while(trackChars.size > K) {
             val sum = trackChars.values.sum()
             if(longestSubString < sum - 1)
             	longestSubString = sum - 1
             val char = arr[startWindow]
             if(trackChars.contains(char)) {
                 if(trackChars[char] == 1)
                 	trackChars.remove(char)
                 else
                 	trackChars.put(char, trackChars.get(char)!!.minus(1))
             }
             ++startWindow
         }
         
         ++endWindow
     }
     
     return if(longestSubString < (endWindow - startWindow) + 1) trackChars.values.sum() else longestSubString
 }
 
 /**
  * Longest Substring with Distinct Characters (hard)
  * Input: String="aabccbb"  Output: 3
  * 	Explanation: The longest substring with distinct characters is "abc".
  * Input: String="abbbb"  Output: 2 
  * 	Explanation: The longest substring with distinct characters is "ab".
  * Input: String="abccde" Output: 3
  * 	Explanation: Longest substrings with distinct characters are "abc" & "cde".
  */ 
  fun longestSubStringDistinctChars(arr: CharArray): Int {
      val trackSubString = mutableSetOf<Char>()
      var longestSS = 0
      var startWindow = 0
      var endWindow = 0
      while(endWindow < arr.size) {
          if(!trackSubString.contains(arr[endWindow])) {
          	trackSubString.add(arr[endWindow])
            ++endWindow
            continue
          }
          
          if(trackSubString.size > longestSS)
          	longestSS = trackSubString.size
          trackSubString.remove(arr[startWindow])	
          ++startWindow
      }
      
      
      return longestSS
  }

/**
 * Longest Substring with Same Letters after Replacement (hard)
 * Given a string with lowercase letters only, if you are allowed to replace no more than k letters 
 * with any letter, find the length of the longest substring having the same letters after replacement.
 * Input: String="aabccbb", k=2  Output: 5
 * 	Explanation: Replace the two 'c' with 'b' to have the longest repeating substring "bbbbb".
 * Input: String="abbcb", k=1 Output: 4
 * 	Explanation: Replace the 'c' with 'b' to have the longest repeating substring "bbbb".
 * Input: String="abccde", k=1  Output: 3
 * 	Explanation: Replace the 'b' or 'd' with 'c' to have the longest repeating substring "ccc".
 */ 
fun longestSubStringReplacement(arr: CharArray, K: Int): Int {
    var allowed = true
    var longestSS = 0
    var curSS = 0
    var pt1 = 0
    var pt2 = 0
    
    while (pt2 < arr.size) {
        //println("pt1=$pt1 pt2=$pt2 curSS=$curSS  longestSS=$longestSS")
        if(arr[pt2] != arr[pt1] && pt2 + K < arr.size) {
            if(arr[pt2 + K] != arr[pt1]) {
                if(curSS + 1 > longestSS)
                    longestSS = curSS + 1
                curSS = 0
                pt1 = pt2
                continue
            } else if (arr[pt2 + K] == arr[pt1] && allowed) {
                curSS += K
                pt2 = pt2 + K
                allowed = false
                continue
            }
        }
            
        ++curSS
        ++pt2
    }
    
    return if(curSS > longestSS) curSS else longestSS 
}
 
/**
 * Longest Subarray with Ones after Replacement (hard)
 * Given an array containing 0s and 1s, if you are allowed to replace no more than ‘k’ 0s with 1s, 
 * find the length of the longest contiguous subarray having all 1s.
 * Input: Array=[0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1], k=2 Output: 6
 * 	Explanation: Replace the '0' at index 5 and 8 to have the longest contiguous subarray of 1s having length 6.
 * Input: Array=[0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1], k=3 Output: 9
 * 	Explanation: Replace the '0' at index 6, 9, and 10 to have the longest contiguous subarray of 1s having length 9.
 */ 
 fun longestSubArrayOnesAfterReplacement(arr: IntArray, k: Int): Int {
     var output = 0
   	 var count = 0
     var replace = k
     var start = 0; var end = 0
     
     while(end < arr.size) {
         println("start=$start end=$end count=$count replace=$replace output=$output")
         if (arr[end] == 1) count++;

          // current window size is from windowStart to windowEnd, overall we have a maximum of 1s
          // repeating a maximum of 'maxOnesCount' times, this means that we can have a window with
          // 'maxOnesCount' 1s and the remaining are 0s which should replace with 1s.
          // now, if the remaining 0s are more than 'k', it is the time to shrink the window as we
          // are not allowed to replace more than 'k' Os
          if (end - start + 1 - count > k) {
            if (arr[start] == 1)
              count--;
            start++;
          }

          output = Math.max(output, end - start + 1);
          
          ++end
     }
     
     return output
 }
 
 /** Problem Challenge 1
  * Given a string and a pattern, find out if the string contains any permutation of the pattern.
  * Permutation in a String (hard)
  * Input: String="oidbcaf", Pattern="abc" Output: true
  * 	Explanation: The string contains "bca" which is a permutation of the given pattern.
  * Input: String="odicf", Pattern="dc" Output: false
  * 	Explanation: No permutation of the pattern is present in the given string as a substring.
  * Input: String="bcdxabcdy", Pattern="bcdyabcdx"	Output: true
  * 	Explanation: Both the string and the pattern are a permutation of each other.
  * Input: String="aaacb", Pattern="abc" Output: true
  * 	Explanation: The string contains "acb" which is a permutation of the given pattern.
  */
  fun permutationInString(arr: CharArray, pattern: CharArray): Boolean {
      val listWin = mutableListOf<Char>() // Space = O(N)
      var startWin = 0
      
      // A map to track pattern occurences
      val pMap = initialize(pattern) // Space = O(N)
      //println(pMap)
      //println(comparePattern(charArrayOf('b', 'c', 'f'), pMap.toMutableMap()))
      
      for((endWin, endWinValue) in arr.withIndex()) {
          //println("listWin=$listWin")
          if(endWin >= pattern.size) {
              // comparePattern O(N)
              if(comparePattern(listWin.toCharArray(), pMap.toMutableMap())) return true
              // Shrink the window to the right
              ++startWin
              listWin.removeAt(0)
              //listWin.add(0, arr[startWin]) 
          } 
          
          listWin.add(endWinValue)
      }
      
      //println("listWin=$listWin")
      return if(comparePattern(listWin.toCharArray(), pMap.toMutableMap())) true else false
  }
  private fun initialize(pattern: CharArray): MutableMap<Char, Int> {
      val result = mutableMapOf<Char, Int>()
      for(value in pattern) {
          if(result.contains(value)) {
              result[value] = result.get(value)?.plus(1) ?: 0 // increment it
          } else {
              result[value] = 1
          }
      }
      
      return result
  }
  private fun comparePattern(arr: CharArray, pattern: MutableMap<Char, Int>): Boolean {
      for(value in arr) {
          if(pattern.contains(value)) {
              if(pattern.get(value) == 0)
              	return false
              else
              	pattern[value] = pattern.get(value)?.minus(1) ?: 0
          } else 
          	return false
      }
      
      return true 
  }
  
/** Problem Challenge 2
  * String Anagrams (hard)
  * Write a function to return a list of starting indices of the anagrams of the pattern in the given string.
  * Input: String="ppqp", Pattern="pq" Output: [1, 2]
  * 	Explanation: The two anagrams of the pattern in the given string are "pq" and "qp".
  * Input: String="abbcabc", Pattern="abc" Output: [2, 3, 4]
  * 	Explanation: The three anagrams of the pattern in the given string are "bca", "cab", and "abc".
  */
fun findAllAnagramsPattern(arr: CharArray, pattern: CharArray): List<Int> {
    val result = mutableListOf<Int>()
    val pMap = initialize(pattern) 
    val winList = mutableListOf<Char>()
    
    var startWin = 0
    for((endWin, endWinValue) in arr.withIndex()) {
        if(winList.size == pattern.size) {
            if(comparePattern(winList.toCharArray(), pMap.toMutableMap())) {
                result.add(startWin)
            }
                
            // shift the window
            winList.removeAt(0)
            ++startWin
        }
        
        winList.add(endWinValue)
    }
    
    if(comparePattern(winList.toCharArray(), pMap.toMutableMap())) 
    	result.add(startWin)
    
    return result
}  

/**
 * Problem Challenge 3
 * Smallest Window containing Substring (hard)
 * Given a string and a pattern, find the smallest substring in the given string which has all the 
 * character occurrences of the given pattern.
 * 
 * Input: String="aabdec", Pattern="abc" Output: "abdec"
 * 	Explanation: The smallest substring having all characters of the pattern is "abdec"
 * Input: String="aabdec", Pattern="abac" Output: "aabdec" 
 * 	Explanation: The smallest substring having all character occurrences of the pattern is "aabdec"
 * Input: String="abdbca", Pattern="abc" Output: "bca"
 * 	Explanation: The smallest substring having all characters of the pattern is "bca".
 * Input: String="adcad", Pattern="abc" Output: ""
 * 	Explanation: No substring in the given string has all characters of the pattern.
 */
 fun findSmallestSubStringPattern(arr: CharArray, pattern: CharArray): String {
     val result = mutableListOf<String>()
     var pMap = initialize(pattern)
     var winList = mutableListOf<Char>()
     var startWin = 0
     for((endWin, value) in arr.withIndex()) {
         println("result=$result  winList=$winList  pMap=$pMap")
         if(checkAllPatternIsMatch(pMap)) { 
             // We hit a substring
             result.add(winList.joinToString(""))
             pMap = initialize(pattern) // reinitialize
             // Shift startWindow
             winList.removeAt(0)
             ++startWin
         } else if(pMap.contains(value)) {
             if(pMap.get(value) == 0) {
                 //Shift startWindow to endWindow and restart
                 winList.clear()
                 pMap = initialize(pattern)
                 pMap[value] = pMap.get(value)?.minus(1) ?: 0
                 startWin = endWin
             } else {
                 pMap[value] = pMap.get(value)?.minus(1) ?: 0
             }
             winList.add(value)
         } else {
             winList.add(value)
         }
     }
     
     if(checkAllPatternIsMatch(pMap)) {
         result.add(winList.joinToString(""))
     }
     
     var smallestSubString = ""
     println("result=$result  winList=$winList  pMap=$pMap")
     for(value in result) {
         if(smallestSubString == "")
         	smallestSubString = value
         else if(value.length < smallestSubString.length)
         	smallestSubString = value
         else {
             //don't do anything
         }
     }
     
     return smallestSubString
 }
 private fun checkAllPatternIsMatch(pattern: Map<Char, Int>): Boolean {
     for(value in pattern.values) {
     	if(value > 0)
        	return false
     }
     
     return true
 }
  
fun main() {
    val result_1 = avgAllSubArrays(intArrayOf(1, 3, 2, 6, -1, 4, 1, 8, 2), 5)
    val result_2 = maxSumSubArray(intArrayOf(2, 1, 5, 1, 3, 2), 3)
    val result_3 = maxSumSubArray(intArrayOf(2, 3, 4, 1, 5), 2) 
    val result_4 = findLengthSmallestSubArray(intArrayOf(3, 4, 1, 1, 6), 8)
    val result_5 = maxSubStringDistinctChars("abcbbc".toCharArray(), 2)
    val result_6 = longestSubStringDistinctChars("abbbb".toCharArray())
    val result_7 = longestSubStringReplacement("ffffa".toCharArray(), 1)
    val result_8 = longestSubArrayOnesAfterReplacement(intArrayOf(0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1), 3)
   
    val prob_1 = permutationInString("aaacb".toCharArray(), "abc".toCharArray())
    val prob_2 = findAllAnagramsPattern("abbcabc".toCharArray(), "abc".toCharArray())
    val prob_3 = findSmallestSubStringPattern("adcad".toCharArray(), "abc".toCharArray())
    
    println("Result for avgAllSubArrays: $result_1")
    println("Result for maxSumSubArray: $result_2 = ${result_2.sum()}")
    println("Result for maxSumSubArray: $result_3 = ${result_3.sum()}")
    println("Result for findLengthSmallestSubArray: $result_4")
    println("Result for maxSubStringDistinctChars: $result_5")
    println("Result for longestSubStringDistinctChars: $result_6")
    println("Result for longestSubStringReplacementChars: $result_7")
    println("Result for longestSubArrayOnesAfterReplacement: $result_8")
    println("Result for permutationInString: $prob_1")
    println("Result for findAllAnagramsPattern: $prob_2")
    println("Result for findSmallestSubStringPattern: $prob_3")
}
