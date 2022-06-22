/* In-Place Reversal of a LinkedList Pattern: 
 * 
 * In a lot of problems, we are asked to reverse the links between a set of nodes of a 
 * LinkedList. Often, the constraint is that we need to do this in-place, i.e., 
 * using the existing node objects and without using extra memory. In-place Reversal 
 * of a LinkedList pattern describes an efficient way to solve the above problem.
 */ 

/* Reverse a LinkedList (easy)
 * Given the head of a Singly LinkedList, reverse the LinkedList. 
 * Write a function to return the new head of the reversed LinkedList.
 * 
 * List: 2 -> 4 -> 6 -> 8 -> 10 -> null 
 */ 
data class Node(val item: Int, var next: Node? = null)
private fun createLinkedList(list: List<Int>): Node? {
    var head: Node? = null
    var curr: Node? = null
    for(item in list){
        if(head == null){
        	head = Node(item)
            curr = head
            continue
        }
        
        val node = Node(item)
        curr?.next = node
        curr = node
    }
    
    return head
}
private fun printLinkedList(head: Node?, msg: String){
    var curr = head
    print("Result for $msg: ")
    while(curr != null){
        print("${curr.item} -> ")
        curr = curr.next
    }
    println("null")
}
fun reverseLinkedList(list: List<Int>){
    if(list.size < 2) return
    
    var head = createLinkedList(list)
    var curr = head; var prev: Node? = null
    while(curr != null){
        val currNext = curr.next
        curr.next = prev
        
        prev = curr
        curr = currNext
    }
    
    printLinkedList(prev, "reverseLinkedList")
}

/* Reverse a Sub-list(medium)
 * Given the head of a LinkedList and two positions ‘p’ and ‘q’, 
 * reverse the LinkedList from position ‘p’ to ‘q’.
 */  
fun reverseSubList(list: List<Int>, p: Int, q: Int){
    val head = createLinkedList(list)
    var curr = head; var prev: Node? = null
    var counter = 0 // To keep track when we reach p and q
    
    while(curr != null && counter < q){
        if(counter == p)
        	prev?.next = null
        
        if(counter >= p){
            // reverse the sublist
            val nextCurr = curr?.next
            curr.next = prev
            
            prev = curr
            curr = nextCurr
            ++counter
            continue
        }
        
        ++counter
        prev = curr
        curr = curr?.next
    }
    
    printLinkedList(curr, "reverseSubList")
} 

/* Reverse every K-element Sub-list (medium)
 * Given the head of a LinkedList and a number ‘k’, reverse every ‘k’ sized sub-list 
 * starting from the head. If, in the end, you are left with a sub-list with 
 * less than ‘k’ elements, reverse it too.
 * 
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
 * Output: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 8 -> 7 -> null
 */  
fun reverseSubList(head: Node?, k: Int): Pair<Node?, Node?> {
    if(head == null || head?.next == null) return Pair(null, null)
    
    var curr: Node? = head; var prev: Node? = null
    var counter = 0
    while(curr != null && counter < k){
        // reverse it
        val nextCurr = curr.next
        curr.next = prev
        prev = curr
        curr = nextCurr
        ++counter
    }
    
    return Pair(prev, head)
} 
fun reverseEveryKSubList(list: List<Int>, k: Int){
    val original_head = createLinkedList(list)
    var newHead: Node? = null; var curr = original_head
    var currNext: Node? = null; var prev_lastNode: Node? = null
    while(curr != null){
        val nextCurr = curr?.next?.next?.next
        val (headNode, lastNode) = reverseSubList(curr, k)
        
        if(newHead == null) newHead = headNode
        
        // keep track of previous sublist last node
        if(prev_lastNode == null) 
        	prev_lastNode = lastNode
        else{
            prev_lastNode.next = headNode
            prev_lastNode = lastNode
        }
        
        curr = nextCurr 
    }
    
    printLinkedList(newHead, "reverseEveryKSubList")
} 

/* Problem 1: Reverse alternating K-element Sub-list (medium)
 * Given the head of a LinkedList and a number ‘k’, reverse every alternating ‘k’ 
 * sized sub-list starting from the head.
 * If, in the end, you are left with a sub-list with less than ‘k’ elements, reverse it too.
 * 
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
 * Output: 3 -> 2 -> 3 -> 4 -> 6 -> 5 -> 7 -> 8 -> null
 */ 
fun reverseAlternatingKSubList(list: List<Int>, k: Int){
    val original_head = createLinkedList(list)
    var newHead: Node? = null; var curr = original_head
    var prev_lastNode: Node? = null
    var doAlternate = true
    while(curr != null){
        var nextCurr: Node? = curr
        for(i in 1..k)
        	nextCurr = nextCurr?.next
        
        if(doAlternate){
            val (headNode, lastNode) = reverseSubList(curr, k)
        
            if(newHead == null) newHead = headNode

            // keep track of previous sublist last node
            if(prev_lastNode == null) 
                prev_lastNode = lastNode
            else{
                prev_lastNode.next = headNode
                prev_lastNode = lastNode
            }
        } else {
            prev_lastNode?.next = curr
            for(i in 1..k)
            	prev_lastNode = prev_lastNode?.next
        }
        
        if(doAlternate) doAlternate = false else doAlternate = true // switch
        curr = nextCurr 
    }
    
    printLinkedList(newHead, "reverseAlternatingKSubList")
}

/* Problem 2: Rotate a LinkedList (medium)
 * Given the head of a Singly LinkedList and a number ‘k’, 
 * rotate the LinkedList to the right by ‘k’ nodes.
 * 
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6
 * Output: 4 -> 5 -> 6 -> 1 -> 2 -> 3 -> null
 */ 
private fun rotateRightByK(node: Node?, k: Int): Triple<Node?, Node?, Node?> {
    if(node == null) return Triple(null, null, null)
    var start = node; var middle: Node? = null; var last: Node? = start
    for(i in 0 until k*2){
        if(i == k - 1)
        	middle = last
        last = last?.next
    }
    
    if(last == null){
        last = middle
        while(last?.next != null)
        	last = last?.next
    }
    
    if(middle == last)
    	return Triple(start, last, null) // nothing to rotate
    
    var middleNext: Node? = middle?.next
    middle?.next = null
    last?.next = start
    
    return Triple(middleNext, middle, last?.next)
}
fun rotateLinkedList(list: List<Int>, k: Int){
    val head = createLinkedList(list)
    var curr = head
    var newHead: Node? = null
    while(curr != null){
        val (start, last, nextCurr) = rotateRightByK(curr, k)
        println("start=${start?.item} last=${last?.item} nextCurr=${nextCurr?.item}")
        
        if(newHead == null) newHead = start
        
        curr = nextCurr
    }
    
    printLinkedList(newHead, "rotateLinkedList")
}
 
fun main() {
   reverseLinkedList(listOf(2, 4, 6, 8, 10))
   reverseSubList(listOf(1, 2, 3, 4, 5), 2, 4)
   reverseEveryKSubList(listOf(1, 2, 3, 4, 5, 6, 7, 8), 3)
   reverseAlternatingKSubList(listOf(1, 2, 3, 4, 5, 6, 7, 8), 2)
   rotateLinkedList(listOf(1, 2, 3, 4, 5), 2)
   
}
