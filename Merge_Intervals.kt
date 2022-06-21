/*
 * Merge Interval Pattern: This pattern describes an efficient technique to deal with overlapping 
 * intervals. In a lot of problems involving intervals, we either need to find overlapping intervals 
 * or merge intervals if they overlap.
 * 
 * Our algorithm will look like this:
 * 	1. Sort the intervals on the start time to ensure a.start <= b.start
 * 	2. If ‘a’ overlaps ‘b’ (i.e. b.start <= a.end), we need to merge them into 
 * 	   a new interval ‘c’ such that: [c.start = a.start, c.end = max(a.end, b.end)]
 * 	3. We will keep repeating the above two steps to merge ‘c’ with the next interval 
 * 	   if it overlaps with ‘c’.
 */

/* Merge Intervals (medium)
 * Given a list of intervals, merge all the overlapping intervals to produce a list that has only 
 * mutually exclusive intervals.
 * 
 * Intervals: [[1,4], [2,5], [7,9]] Output: [[1,5], [7,9]] 
 * 	Explanation: Since the first two intervals [1,4] and [2,5] overlap, we merged them into one [1,5].
 * Intervals: [[6,7], [2,4], [5,9]] Output: [[2,4], [5,9]] 
 * 	Explanation: Since the intervals [6,7] and [5,9] overlap, we merged them into one [5,9].
 * Intervals: [[1,4], [2,6], [3,5]] Output: [[1,6]]
 * 	Explanation: Since all the given intervals overlap, we merged them into one.
 */ 
fun mergeAllOverlappingIntervals(list: List<List<Int>>): List<List<Int>> {
    /* Time Complexity = O(N*logN) and Space Complexity = O(N) */ 
    if(list.size < 2) return list
    
    val result = mutableListOf<List<Int>>()
    // sort the list
    var sortedList = list.sortedBy { it[0] }
    // merge intervals
    val interval = mutableListOf<Int>()
    for(item in sortedList){
        if(interval.isEmpty()) {
            interval.add(0, item.get(0))
            interval.add(1, item.get(1))
            continue
        }
        
       	val diff = interval.get(1) - item.get(0)
        if(diff >= 0){
            // merge them and update interval
            val first = interval.get(0)
            val second = if(interval.get(1) > item.get(1)) interval.get(1) else item.get(1)
            interval.clear()
            interval.add(first)
            interval.add(second)
        } else {
            // add the interval into the result
            result.add(listOf(interval.get(0), interval.get(1)))
            // update the interval to the current item
            interval.clear()
            interval.add(0, item.get(0))
            interval.add(1, item.get(1))
        }
        
        //println("interval=$interval result=$result")
    }
    
    if(!interval.isEmpty())
    	result.add(listOf(interval.get(0), interval.get(1)))
    
    return result
} 

/*
 * Insert Interval (medium)
 * Given a list of non-overlapping intervals sorted by their start time, insert a given interval 
 * at the correct position and merge all necessary intervals to produce a list that has 
 * only mutually exclusive intervals.
 * 
 * Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6] Output: [[1,3], [4,7], [8,12]]
 * 	Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
 * Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,10] Output: [[1,3], [4,12]]
 * 	Explanation: After insertion, since [4,10] overlaps with [5,7] & [8,12], we merged them into [4,12].
 * Input: Intervals=[[2,3],[5,7]], New Interval=[1,4] Output: [[1,4], [5,7]]
 * 	Explanation: After insertion, since [1,4] overlaps with [2,3], we merged them into one [1,4].
 */ 
fun insertInterval(list: List<List<Int>>, padding: Int): List<List<Int>> {
    if(list.isEmpty()) return listOf(listOf(1, 2))
    
    val result = mutableListOf<List<Int>>()
    
    val curr = mutableListOf<Int>() // assumes intervals always start with greater than 1
    for((index, item) in list.withIndex()){
        if(index == 0 && item.get(0) > 1){
            val first = 1
            val second = item.get(1) + padding
            result.add(listOf(first, second))
            result.addAll(list)
            //println("New Interval: ($first, $second)")
            break
        }
        
        if(curr.isEmpty()){
            curr.add(item.get(0))
            curr.add(item.get(1))
            continue
        }
        
        // Check if interval can be inserted
        // c = b.0 - a.1; if c > 0 -> insert a new interval
        val diff = item.get(0) - curr.get(1)
        if(diff > 1) {
            // Insert a new interval and exit the loop
            result.addAll(list.subList(0, index))
            val first = curr.get(1) + 1
            val second = item.get(0) + padding
            result.add(listOf(first, second))
            result.addAll(list.subList(index, list.size))
     		//println("New Interval: ($first, $second)")
            break
        }
        
        curr.clear()
        curr.add(item.get(0))
        curr.add(item.get(1))
    }
    
    //println("result=$result")
    return mergeAllOverlappingIntervals(result)
} 

/* Intervals Intersection (medium)
 * Given two lists of intervals, find the intersection of these two lists. 
 * Each list consists of disjoint intervals sorted on their start time.
 * 
 * Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]] Output: [2, 3], [5, 6], [7, 7]
 * 	Explanation: The output list contains the common intervals between the two lists.
 * Input: arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]] Output: [5, 7], [9, 10]
 * 	Explanation: The output list contains the common intervals between the two lists.
 */ 
fun findIntersection(list1: List<List<Int>>, list2: List<List<Int>>): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    
    var pt1 = 0; var pt2 = 0
    while(pt1 < list1.size && pt2 < list2.size){
        var first = emptyList<Int>(); var second = emptyList<Int>()
        
        if(list1.get(pt1)[0] <= list2.get(pt2)[0]){
            if(list1.get(pt1)[0] == list2.get(pt2)[0]){
                if(list1.get(pt1)[1] < list2.get(pt2)[1]){
                    first = list1.get(pt1)
                	second = list2.get(pt2)
                } else {
                    first = list2.get(pt2)
                	second = list1.get(pt1)
                }
            } else {
                first = list1.get(pt1)
                second = list2.get(pt2)
            }
        } else {
            first = list2.get(pt2)
            second = list1.get(pt1)
        }
        	
        //println("first=$first second=$second")
       
        if(first.get(1) - second.get(0) >= 0){
            // it intersects
            result.add(listOf(second.get(0), first.get(1)))
            ++pt1
        } else {
            if(pt2 == list2.size - 1)
            	++pt1
            else
            	++pt2
        }
    }
    
    return result
} 

/* Conflicting Appointments (medium)
 * Given an array of intervals representing ‘N’ appointments, find out if a person can attend 
 * all the appointments.
 * 
 * Appointments: [[1,4], [2,5], [7,9]] Output: false
 * 	Explanation: Since [1,4] and [2,5] overlap, a person cannot attend both of these appointments.
 * Appointments: [[6,7], [2,4], [8,12]] Output: true
 * 	Explanation: None of the appointments overlap, therefore a person can attend all of them.
 * Appointments: [[4,5], [2,3], [3,6]] Output: false
 * 	Explanation: Since [4,5] and [3,6] overlap, a person cannot attend both of these appointments.
 */ 
fun isAppointmentConflict(list: List<List<Int>>): Boolean {
    if(list.size < 2) return true
    
    val sorted = list.sortedBy { it[0] }
    for((i, item) in sorted.withIndex()){
        if(i+1 < sorted.size - 1){
            val item_2 = sorted[i+1]
            // compare the two items for conflicts
            if(item.get(1) >= item_2.get(0))
            	return true // there is a conflict
        } 
    }
    
    return false
}

/* Problem 1: Minimum Meeting Rooms (hard)
 * Given a list of intervals representing the start and end time of ‘N’ meetings, 
 * find the minimum number of rooms required to hold all the meetings.
 * 
 * Meetings: [[1,4], [2,5], [7,9]] Output: 2
 * 	Explanation: Since [1,4] and [2,5] overlap, we need two rooms to hold these two meetings. [7,9] 
 * 	can occur in any of the two rooms later.
 * Meetings: [[6,7], [2,4], [8,12]] Output: 1
 * 	Explanation: None of the meetings overlap, therefore we only need one room to hold all meetings.
 * Meetings: [[1,4], [2,3], [3,6]] Output:2
 * 	Explanation: Since [1,4] overlaps with the other two meetings [2,3] and [3,6], we need two rooms to 
 * 	hold all the meetings.
 * Meetings: [[4,5], [2,3], [2,4], [3,5]] Output: 2
 * 	Explanation: We will need one room for [2,3] and [3,5], and another room for [2,4] and [4,5].
 */ 
fun findMinRequiredRooms(list: List<List<Int>>): Int {
    if(list.size == 0) return 0; if(list.size == 1) return 1;
    var rooms = 1
    val sorted = list.sortedWith(compareBy({it[1]}, {it[0]}))
    println(sorted)
    
    return -1
}
 
fun main() {
   val result_1 = mergeAllOverlappingIntervals(listOf(listOf(1,4), listOf(2,6), listOf(3,5)))
   val result_2 = insertInterval(listOf(listOf(1,3), listOf(5,7), listOf(8,12)), 5)
   val result_3 = findIntersection(listOf(listOf(1,3), listOf(5,6), listOf(7,9)),
                                   listOf(listOf(2,3), listOf(5,7)))
   val result_4 = isAppointmentConflict(listOf(listOf(4,5), listOf(2,3), listOf(3,6)))
   
   val prob_1 = findMinRequiredRooms(listOf(listOf(4,5), listOf(2,3), listOf(2,4), listOf(3,5)))
   
   println("Result for mergeAllOverlappingIntervals: $result_1")
   println("Result for insertInterval: $result_2")
   println("Result for findIntersection: $result_3")
   println("Result for isAppointmentConflict: $result_4")
   
   println("Result for findMinRequiredRooms: $prob_1")
}
