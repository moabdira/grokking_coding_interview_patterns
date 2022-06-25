/* Two Heaps Pattern: In many problems, where we are given a set of elements such that 
 * we can divide them into two parts. We are interested in knowing the smallest element 
 * in one part and the biggest element in the other part. The Two Heaps pattern is an efficient 
 * approach to solve such problems.
 * 
 * As the name suggests, this pattern uses two Heaps; A Min Heap to find the smallest element and 
 * a Max Heap to find the biggest element.
 * 
 * Two Heaps Solution: 
 * A brute force solution could be maintaining a sorted list of all numbers and returning the median 
 * whenever required. Inserting a number in a sorted list will take O(N) time if there are N numbers 
 * in the list. A better approach is using the Two Heaps pattern with a max_heap and min_heap.
 * 
 * For example: Let’s assume that x is the median of the list. This means that, half of the items 
 * in the list are smaller than (or equal to) x and other half is greater than (or equal to) x.
 */ 

/* Find the Median of a Number Stream (medium)
 * Design a class to calculate the median of a number stream. 
 * 
 * The class should have the following two methods:
 * 	1. insertNum(int num): stores the number in the class
 * 	2. findMedian(): returns the median of all numbers inserted in the class
 * 
 * If the count of numbers inserted in the class is even, the median will be the average of 
 * the middle two numbers.
 * 
 * 1. insertNum(3)
 * 2. insertNum(1)
 * 3. findMedian() -> output: 2
 * 4. insertNum(5)
 * 5. findMedian() -> output: 3
 * 6. insertNum(4)
 * 7. findMedian() -> output: 3.5
 */ 
import java.util.PriorityQueue
class MedianOfAStream {
    
    val minHeap = PriorityQueue<Int>({ a, b -> a - b})
    val maxHeap = PriorityQueue<Int>({ a, b -> b - a })
    
    fun insertNum(num: Int){
        //println("insert:$num")
        if(maxHeap.isEmpty() || num <= maxHeap.peek())
        	maxHeap.add(num)
        else
        	minHeap.add(num)
        
        reBalanceHeaps()
    }
    
    private fun reBalanceHeaps(){
        // now check and balance it
        if(maxHeap.size - minHeap.size > 1){
            // move the maxHeap to the minHeap
            minHeap.add(maxHeap.poll())
        } else if(maxHeap.size - minHeap.size < 0) {
            maxHeap.add(minHeap.poll())
        }
    }
    
    fun removeNum(num: Int){
        if(maxHeap.contains(num))
        	maxHeap.remove(num)
        else if(minHeap.contains(num))
        	minHeap.remove(num)
        
        reBalanceHeaps()
    }
    
    fun findMedian(): Float {
        val min = minHeap.peek()
        val max = maxHeap.peek()
        //println("min=$min max=$max maxHeap=$maxHeap minHeap=$minHeap")
        if(maxHeap.size > minHeap.size) return maxHeap.peek().toFloat()
        else return (maxHeap.peek() + minHeap.peek()) / 2.toFloat()
        	
    }
}

/* Sliding Window Median (hard)
 * Given an array of numbers and a number ‘k’, find the median of all the ‘k’ sized 
 * sub-arrays (or windows) of the array.
 * 
 * Input: nums=[1, 2, -1, 3, 5], k = 2 Output: [1.5, 0.5, 1.0, 4.0]
 * 	Explanation: Lets consider all windows of size ‘2’:
 * 		[ 1, 2, -1, 3, 5] -> median is 1.5
 * 		[1, 2, -1, 3, 5] -> median is 0.5
 * 		[1, 2, -1, 3, 5] -> median is 1.0
 * 		[1, 2, -1, 3, 5] -> median is 4.0
 * 
 * Input: nums=[1, 2, -1, 3, 5], k = 3 Output: [1.0, 2.0, 3.0]
 * 	Explanation: Lets consider all windows of size ‘3’:
 * 		[1, 2, -1, 3, 5] -> median is 1.0
 * 		[1, 2, -1, 3, 5] -> median is 2.0
 * 		[1, 2, -1, 3, 5] -> median is 3.0
 */ 
fun findMedianOfKSize(list: List<Int>, k: Int): List<Float>{
    if(list.size < 2) return emptyList<Float>()
    
    val result = mutableListOf<Float>()
    val stream = MedianOfAStream()
    
    var start = 0; var end = 0;
    while(end < list.size){
        while(end < k - 1){
            stream.insertNum(list[end])
            ++end
            continue
        }
        
        // find the median between
        stream.insertNum(list[end])
        result.add(stream.findMedian())
        stream.removeNum(list[start])
        
        ++start
        ++end
    }
    
    return result
}

/* Maximize Capital (hard)
 * Given a set of investment projects with their respective profits, we need to find the most 
 * profitable projects. We are given an initial capital and are allowed to invest only in a 
 * fixed number of projects. Our goal is to choose projects that give us the maximum profit. Write 
 * a function that returns the maximum total capital after selecting the most profitable projects.
 * 
 * We can start an investment project only when we have the required capital. 
 * Once a project is selected, we can assume that its profit has become our capital.
 * 
 * Input: Project Capitals=[0,1,2], Project Profits=[1,2,3], Initial Capital=1, Number of Projects=2
 * Output: 6  
 * Explanation: 
 * 	1. With initial capital of ‘1’, we will start the second project which will give us 
 *     profit of ‘2’. Once we selected our first project, our total capital will become 
 *     3 (profit + initial capital).
 * 	2. With ‘3’ capital, we will select the third project, which will give us ‘3’ profit.
 * After the completion of the two projects, our total capital will be 6 (1+2+3).
 * 
 * Input: Project Capitals=[0,1,2,3], Project Profits=[1,2,3,5], Initial Capital=0, Number of Projects=3
 * Output: 8
 * Explanation:
 * 	1. With ‘0’ capital, we can only select the first project, bringing out capital to 1.
 * 	2. Next, we will select the second project, which will bring our capital to 3.
 * 	3. Next, we will select the fourth project, giving us a profit of 5.
 * After selecting the three projects, our total capital will be 8 (1+2+5).
 */
 fun getMaxTotalCapital(project: MutableList<Int>, profit: List<Int>, capital: Int, numProj: Int): Int {
     if(!project.contains(capital)) return 0
     
     val maxHeap = PriorityQueue<Int>({ a, b -> b - a })
     var i = 0
     while(i < numProj){
         // take capital and invest into a project
         if(maxHeap.isEmpty()){
             val gain = profit[project.indexOf(capital)]
             println("gain=$gain")
             maxHeap.add(gain + capital)
             ++i
             continue
         }
         
         val currentCapital = maxHeap.peek()
         //println("currentCapital=$currentCapital")
         if(project.indexOf(currentCapital) != -1){
             // there is a project available to invest
             val gain = profit[project.indexOf(currentCapital)]
             println("gain=$gain currentCapital=$currentCapital")
             maxHeap.add(maxHeap.peek() + gain)
         }
         
         ++i
     }
     
     return maxHeap.peek()
 }
 
/* Problem 1: Next Interval (hard)
 * Given an array of intervals, find the next interval of each interval. In a list of intervals, 
 * for an interval ‘i’ its next interval ‘j’ will have the smallest ‘start’ greater than or equal 
 * to the ‘end’ of ‘i’.
 * 
 * Write a function to return an array containing indices of the next interval of each input interval. 
 * If there is no next interval of a given interval, return -1. It is given that none of the intervals 
 * have the same start point.
 * 
 * Input: Intervals [[2,3], [3,4], [5,6]] Output: [1, 2, -1]
 * 	Explanation: The next interval of [2,3] is [3,4] having index ‘1’. Similarly, 
 * 	the next interval of [3,4] is [5,6] having index ‘2’. There is no next interval for 
 * 	[5,6] hence we have ‘-1’.
 * 
 * Input: Intervals [[3,4], [1,5], [4,6]] Output: [2, -1, -1]
 * 	Explanation: The next interval of [3,4] is [4,6] which has index ‘2’. There is no next interval
 *  for [1,5] and [4,6].
 */  
fun  
 
 
fun main() {
   val stream = MedianOfAStream()
   stream.insertNum(3)
   stream.insertNum(1)
   var median = stream.findMedian()
   println("findMedian(): $median")
   stream.insertNum(5)
   median = stream.findMedian()
   println("findMedian(): $median")
   stream.insertNum(4)
   median = stream.findMedian()
   println("findMedian(): $median")
   
   val result_2 = findMedianOfKSize(listOf(1, 2, -1, 3, 5), 3)
   val result_3 = getMaxTotalCapital(mutableListOf(0,1,2, 3), listOf(1,2,3,5), 0, 3)
   
   println("Result for findMedianOfKSize: $result_2")
   println("Result for getMaxTotalCapital: $result_3")
}
