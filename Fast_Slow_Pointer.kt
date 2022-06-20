/**
 * The Fast & Slow Pointer Pattern. Also known as the Hare & Tortoise algorithm.
 * This pattern is a pointer algorithm that uses two pointers which move through the array (or 
 * sequence/LinkedList) at different speeds. This approach is quite useful when dealing with 
 * cyclic LinkedLists or Arrays. 
 * 
 * By moving at different speeds (say, in a cyclic LinkedList), the algorithm proves that the two
 * pointers are bound to meet. The fast pointer should catch the slow pointer once both the pointers
 * are in a cyclic loop.
 * 
 * One of the famous problems solved using this technique was Finding a cycle in a LinkedList.
 */ 

/**
 * LinkedList Cycle (easy)
 * Given the head of a Singly LinkedList, write a function to determine if the LinkedList has a cycle?
 */ 
data class Node(val value: Int, var next: Node? = null)
private fun createCyclicList(size: Int = 6, isCyclic: Boolean = true): Node? {
    var root: Node? = null
    var curr: Node? = null
    var cyclicNode: Node? = null
    for(value in 1..size) {
        if(root == null) {
            root = Node(value)
            curr = root
            continue
        }
        
        val node = Node(value)	
        curr?.next = node
        curr = node
        
        if(value == 3)
        	cyclicNode = curr 
    }
    
    // Make it a cyclic
    if(isCyclic) curr?.next = cyclicNode
    
    return root
}
fun hasCycle(): Boolean{
    var root = createCyclicList()
    
    var pt1 = root; var pt2 = root
    while(pt2 != null) {
        //println("pt1=${pt1?.value} pt2=${pt2?.value}")
        pt1 = pt1?.next // move one step
        pt2 = pt2?.next?.next // moves two steps
        
        if(pt1 == pt2) break
    }
    return if(pt1 == pt2) true else false
}


/**
 * Given the head of a LinkedList with a cycle, find the length of the cycle.
 */ 
fun findLengthOfCycle(): Int {
    var root = createCyclicList()
    
    var pt1 = root; var pt2 = root
    while(pt2 != null) {
        pt1 = pt1?.next // move one step
        pt2 = pt2.next?.next // move two steps
        
        if(pt1 == pt2) break // check for cycle
    }
    
    var counter = 0
    if(pt1 == pt2) { // There is a cycle
        var curr = pt1?.next
        ++counter
    	while(curr != pt1){
            curr = curr?.next
            ++counter
        }
    }
    
    return counter
} 

/**
 * Start of LinkedList Cycle (medium)
 * Given the head of a Singly LinkedList that contains a cycle, 
 * write a function to find the starting node of the cycle.
 */ 
private fun getLengthCycle(start: Node?): Int {
    var counter = 0
    var curr = start?.next; ++counter
    while(curr != start){
        ++counter
        curr = curr?.next
    }
    
    return counter
}
fun findStartingNodeCycle(): Int {
    val head = createCyclicList()
    
    var pt1 = head; var pt2 = head
    while(pt2 != null) {
        pt1 = pt1?.next
        pt2 = pt2?.next?.next
        
        if(pt1 == pt2) break // Check if cycle exists
    }
    
    // find the starting cycle node
    var slow = head; var fast = head;
    if(pt1 == pt2) {
    	var cycleLength = getLengthCycle(pt1)
        //println("cycleLength=$cycleLength")
        while(cycleLength > 0){ // Move K nodes == cycleLength
            fast = fast?.next
            --cycleLength
        }
        
        while(slow != fast){ // move one step at a time until they clide
            slow = slow?.next 
            fast = fast?.next
        }
    }
    
    return if(pt2 == null) -1 else slow?.value ?: -1
} 

/**
 * Happy Number (medium)
 * Any number will be called a happy number if, after repeatedly replacing it with a 
 * number equal to the sum of the square of all of its digits, leads us to number ‘1’. 
 * All other (not-happy) numbers will never reach ‘1’. Instead, they will be stuck 
 * in a cycle of numbers which does not include ‘1’.
 * 
 * Input: 23   Output: true (23 is a happy number)  
 * 	Explanations: Here are the steps to find out that 23 is a happy number:
 * 
 * Input: 12   Output: false (12 is not a happy number)  
 * 	Explanations: Here are the steps to find out that 12 is not a happy number:
 */  
fun isHappyNumber(input: Int): Boolean {
    var visited = mutableSetOf<Int>()
    var quotient = input / 10 // quotient
    var reminder = input % 10 // reminder  
    var number = (quotient * quotient) + (reminder * reminder)
    
    while(number != 1 && !visited.contains(number)){
        //println("(quotient=$quotient + reminder=$reminder)^2 = $number")
        visited.add(number)
        
        quotient = number / 10 // quotient
    	reminder = number % 10 // reminder
        if(quotient > 9){
            val quotient_2 = quotient / 10
            val reminder_2 = quotient % 10
            number = (reminder * reminder) + (quotient_2 * quotient_2) + (reminder_2 * reminder_2)
        } else {
            number = (quotient * quotient) + (reminder * reminder)
        }
        	
    }
    
    return if(number == 1) true else false
} 
 
/**
 * Middle of the LinkedList (easy)
 * Given the head of a Singly LinkedList, write a method to return the middle node of the LinkedList.
 * If the total number of nodes in the LinkedList is even, return the second middle node.
 * 
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> null Output: 3
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null Output: 4
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null Output: 4
 */ 
fun findMiddleLinkedList(size: Int = 6): Int {
    val head = createCyclicList(size, false)
    var pt1 = head; var pt2 = head
    
    while(pt2 != null){
        pt1 = pt1?.next
        pt2 = pt2?.next?.next
        
        if(pt2?.next == null)
        	return pt1?.value ?: -1
    }
    
    return pt1?.value ?: -1
} 
 
/*
 * Problem 1: Palindrome LinkedList (medium)
 * Given the head of a Singly LinkedList, write a method to check if the LinkedList is a palindrome 
 * or not.
 * 
 * Your algorithm should use constant space and the input LinkedList should be in the original 
 * form once the algorithm is finished. The algorithm should have O(N) time complexity 
 * where ‘N’ is the number of nodes in the LinkedList.
 * 
 * Input: 2 -> 4 -> 6 -> 4 -> 2 -> null Output: true
 * Input: 2 -> 4 -> 6 -> 4 -> 2 -> 2 -> null Output: false
 */ 
private fun reverseSinglyLL(head: Node?) {
    var curr = head?.next; var prev = head
    head?.next = null
    while(curr != null) {
        //println("prev=${prev?.value} curr=${curr?.value}")
        // reverse it
        val temp = curr?.next
        curr?.next = prev // 6 <- 4 -> 2
        //prev?.next = null
        prev = curr
        //printSinglyLL(prev)
        curr = temp
    }
}
private fun printSinglyLL(head: Node?) {
    var curr = head
    while(curr != null){
        print("${curr.value} -> ")
        curr = curr?.next
    }
    println("null")
}
private fun createSinglyLinkedList(list: List<Int>): Node? {
    if(list.isEmpty() || list == null) return null
    
    var head: Node? = null
    var currNode: Node? = null
    for(item in list){
        if(head == null) { head = Node(item); currNode = head; continue }
        val node = Node(item)
        currNode?.next = node
        currNode = node
    }
    
    return head
}
fun isLLPalindrome(list: List<Int>): Boolean {
    val head = createSinglyLinkedList(list)
    //printSinglyLL(head)
    
    // Now check if palindrome
    var pt1 = head; var pt2 = head; 
    var middle: Node? = null; var tail: Node? = null
    var count = 1
    while(pt2?.next != null){
        //println("pt1=${pt1?.value} pt2=${pt2?.value}")
        pt1 = pt1?.next
        val prev_pt2 = pt2
        pt2 = pt2?.next?.next
        
        if(pt2 == null)
        	tail = prev_pt2?.next
        else if(pt2?.next == null) 
        	tail = pt2 // we found the tail
        
        count += 2
    }
    middle = pt1 // assign the middle
    //println("middle=${middle?.value} tail=${tail?.value}")
    reverseSinglyLL(middle)
    
    //printSinglyLL(tail)
    
    // compare the head and tail until they both reach the middle
    pt1 = head; pt2 = tail
    while(pt1 != middle && pt2 != middle) {
        if(pt1?.value != pt2?.value)
        	return false
        pt1 = pt1?.next
        pt2 = pt2?.next
    }
    
    return true
} 
 
/**
 * Problem 2: Rearrange a LinkedList (medium)
 * Given the head of a Singly LinkedList, write a method to modify the LinkedList such 
 * that the nodes from the second half of the LinkedList are inserted alternately to the nodes 
 * from the first half in reverse order. So if the LinkedList has nodes 
 * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null, your method should return 1 -> 6 -> 2 -> 5 -> 3 -> 4 -> null.
 * 
 * Your algorithm should not use any extra space and the input LinkedList should be modified in-place.
 * 
 * Input: 2 -> 4 -> 6 -> 8 -> 10 -> 12 -> null Output: 2 -> 12 -> 4 -> 10 -> 6 -> 8 -> null 
 * Input: 2 -> 4 -> 6 -> 8 -> 10 -> null Output: 2 -> 10 -> 4 -> 8 -> 6 -> null
 */  
fun findMiddleLinkedList(head: Node?): Node? {
    if(head == null) return null
    
    var pt1 = head; var pt2 = head;
    while(pt2?.next != null){
        pt1 = pt1?.next
        pt2 = pt2?.next?.next
    }
    
    return pt1
}
fun reverseLinkedList(head: Node?): Node? {
    if(head == null) return null
    
    var prev = head
    var curr = head?.next
    head?.next = null // set the head nex tot null
    while(curr != null){
        var currNext = curr?.next
        curr?.next = prev
        prev = curr
        curr = currNext
    }
    
    return prev
}
fun rearrangeLinkedList(list: List<Int>): Node? {
    val head = createSinglyLinkedList(list)
    val middle = findMiddleLinkedList(head)
    val tail = reverseLinkedList(middle)
    //printSinglyLL(tail)
    
    var currLeft = head; var currRight = tail
    while(currLeft != middle && currRight != middle) {
        var nextLeft = currLeft?.next
        var nextRight = currRight?.next
        
        //println("currLeft=${currLeft?.value} nextLeft=${nextLeft?.value} currRight=${currRight?.value} nextRight=${nextRight?.value}")
        
        currLeft?.next = currRight
        currRight?.next = nextLeft
        
        currLeft = nextLeft
        currRight = nextRight
    }
    
    return head
}

/**
 * Problem 3: Cycle in a Circular Array (hard)
 * We are given an array containing positive and negative numbers. Suppose the array contains a 
 * number ‘M’ at a particular index. Now, if ‘M’ is positive we will move forward ‘M’ indices and 
 * if ‘M’ is negative move backwards ‘M’ indices. You should assume that the array is circular which
 *  means two things:
 * 		1. If, while moving forward, we reach the end of the array, we will jump to the first element 
 * 			to continue the movement.
 * 		2. If, while moving backward, we reach the beginning of the array, we will jump to the 
 * 			last element to continue the movement.
 * 
 * Write a method to determine if the array has a cycle. The cycle should have more than one element 
 * and should follow one direction which means the cycle should not contain both forward and backward 
 * movements.
 * 
 * Input: [1, 2, -1, 2, 2] Output: true
 * 	Explanation: The array has a cycle among indices: 0 -> 1 -> 3 -> 0
 * Input: [2, 2, -1, 2] Output: true
 * 	Explanation: The array has a cycle among indices: 1 -> 3 -> 1
 * Input: [2, 1, -1, -2] Output: false
 * 	Explanation: The array does not have any cycle.
 */ 
fun detectCycleInCircularArray(list: List<Int>): Boolean {
    /* TODO */
    return false
}
 
fun main() {
    val result_1 = hasCycle()
    val result_2 = findLengthOfCycle()
    val result_3 = findStartingNodeCycle()
    val result_4 = isHappyNumber(12)
    val result_5 = findMiddleLinkedList(7)
    
    val prob_1 = isLLPalindrome(listOf(2, 4, 6, 4, 2, 2))
    val prob_2: Node? = rearrangeLinkedList(listOf(2, 4, 6, 8, 10, 12))
    val prob_3 = detectCycleInCircularArray(listOf(1, 2, -1, 2, 2))
    
    println("Result for hasCycle: $result_1")
    println("Result for findLengthOfCycle: $result_2")
    println("Result for findStartingNodeCycle: $result_3")
    println("Result for isHappyNumber: $result_4")
    println("Result for findMiddleLinkedList: $result_5")
    println("Result for isLLPalindrome: $prob_1")
    print("Result for rearrangeLinkedList: ")
    printSinglyLL(prob_2)
    println("Result for detectCycleInCircularArray: /* TODO */")
    
}
