/* Breadth First Search (BFS): Technique to traverse a tree using a Queue.
 * 
 * This pattern is based on the Breadth First Search (BFS) technique to traverse a tree.
 * Any problem involving the traversal of a tree in a level-by-level order can be efficiently 
 * solved using this approach. We will use a Queue to keep track of all the nodes of a level 
 * before we jump onto the next level. This also means that the space complexity of the algorithm 
 * will be O(W), where ‘W’ is the maximum number of nodes on any level.
 * 
 * Binary Tree Level Order Traversal - We can use a Queue to efficiently traverse in BFS fashion. 
 * Here are the steps of our algorithm:
 * 	1. Start by pushing the root node to the queue.
 * 	2. Keep iterating until the queue is empty.
 * 	3. In each iteration, first count the elements in the queue (let’s call it levelSize). 
 * 	   We will have these many nodes in the current level.
 * 	4. Next, remove levelSize nodes from the queue and push their value in an array to represent 
 * 	   the current level.
 * 	5. After removing each node from the queue, insert both of its children into the queue.
 *  6. If the queue is not empty, repeat from step 3 for the next level.
 */ 

/* Helper function: populate a binary tree using a list
 */ 
data class TreeNode(val item: Int, var left: TreeNode? = null, var right: TreeNode? = null, 
                    var sibling: TreeNode? = null)
fun createBinaryTree(): TreeNode {
    val root = TreeNode(12)
    root.left = TreeNode(7)
    root.right = TreeNode(1)
    root.left?.left = TreeNode(9)
    root.right?.left = TreeNode(10)
    root.right?.right = TreeNode(5)
    root.left?.left?.left = TreeNode(3)
    
    return root
}

/* Binary Tree Level Order Traversal (easy)
 * Given a binary tree, populate an array to represent its level-by-level traversal. 
 * You should populate the values of all nodes of each level from left to right in separate sub-arrays.
 */
fun binaryTreeLevelOrderTraversal(): MutableList<List<Int>> {
     val root = createBinaryTree()
     val result = mutableListOf<List<Int>>()
     
     val queue = ArrayDeque<TreeNode>()
     queue.add(root)
     var levelSize = 0
     while(queue.isNotEmpty()){
        //println("Level Size: ${queue.size}")
        val elements = mutableListOf<Int>() 
        for(i in 0 until queue.size) { // iterate through the level size (current queue elements)
            val node = queue.removeFirst()
            elements.add(node.item)
            // Expand it's neighbours
            if(node.left != null) queue.add(node.left ?: return result)
            if(node.right != null) queue.add(node.right ?: return result)
        }
        
        result.add(elements)
     }
     
     return result
 }

/** Reverse Level Order Traversal (easy)
 * Given a binary tree, populate an array to represent its level-by-level traversal in reverse order,
 * i.e., the lowest level comes first. You should populate the values of all nodes in each level 
 * from left to right in separate sub-arrays.
 */ 
fun reversalLevelOrderTraversal(): List<List<Int>> {
    val result = binaryTreeLevelOrderTraversal()
    // swap in-place using two pointers
    var left = 0; var right = result.size - 1
    while(left <= right){
        //println("left=$result[left] right=$result[right]")
        val temp = result[left]
        result[left] = result[right]
        result[right] = temp
        
        ++left
        --right
    }
    
    return result
}
 
/* Zigzag Traversal (medium)
 * Given a binary tree, populate an array to represent its zigzag level order traversal. 
 * You should populate the values of all nodes of the first level from left to right, 
 * then right to left for the next level and keep alternating in the same manner for the 
 * following levels.
 */  
private fun reverseSortedList(list: MutableList<Int>): List<Int> {
    // swap in-place using two pointers
    var left = 0; var right = list.size - 1
    while(left <= right){
        //println("left=$result[left] right=$result[right]")
        val temp = list[left]
        list[left] = list[right]
        list[right] = temp
        
        ++left
        --right
    }
    
    return list
}
fun zigZagTraversal(): List<List<Int>> {
    val start = createBinaryTree()
    var result = mutableListOf<List<Int>>()
    // queue
    val deque = ArrayDeque<TreeNode>()
    deque.add(start)
    
    // process until queue is empty
    while(deque.isNotEmpty()){
        val levelSize = deque.size
        
        val levelNodes = mutableListOf<Int>()
        for(i in 0 until levelSize){
            val node = deque.removeFirst()
            levelNodes.add(node.item)
            
            // expand its neighbours
            if(node.left != null) deque.add(node.left ?: return result)
            if(node.right != null) deque.add(node.right ?: return result)
        }
        
        // now the result in zizag format using level size as the flag
        if(levelSize % 2 == 0)
            result.add(reverseSortedList(levelNodes))
        else
            result.add(levelNodes)
    }
    
    return result
} 

/* Level Averages in a Binary Tree (easy)
 * Given a binary tree, populate an array to represent the averages of all of its levels.
 */ 
fun levelAverages(): List<Double> {
    val start = createBinaryTree()
    val result = mutableListOf<Double>()
    
    val deque = ArrayDeque<TreeNode>()
    deque.add(start)
    
    while(deque.isNotEmpty()){
        val levelSize = deque.size
        
        val levelItems = mutableListOf<Int>()
        for(i in 0 until levelSize){
            val node = deque.removeFirst()
            levelItems.add(node.item)
            
            // Expand neighbours
            if(node.left != null) deque.add(node.left ?: return result)
            if(node.right != null) deque.add(node.right ?: return result)
        }
        
        // calculate the average and add it into the result
        result.add(levelItems.average())
    }
    
    return result
} 

/* Minimum Depth of a Binary Tree (easy)
 * Find the minimum depth of a binary tree. The minimum depth is the number of nodes 
 * along the shortest path from the root node to the nearest leaf node.
 */
fun findMinDepthBinaryTree(): Int {
    val start = createBinaryTree()
    val deque = ArrayDeque<TreeNode>()
    deque.add(start)
    
    var minDepth = 0
    while(deque.isNotEmpty()){ 
        ++minDepth
        for(i in 0 until deque.size){
            val node = deque.removeFirst()
            if(node.left == null && node.right == null)
            	return minDepth
            
            // expand its neighbours
            if(node.left != null) deque.add(node.left ?: return -1)
            if(node.right != null) deque.add(node.right ?: return -1)
        }
    }
    
    // There is no minimum depth as the tree is well balanced
    return minDepth
} 

/* Level Order Successor (easy)
 * Given a binary tree and a node, find the level order successor of the given node in the tree. 
 * The level order successor is the node that appears right after the given node in the 
 * level order traversal.
 */
fun findLevelOrderSuccessor(given: TreeNode): TreeNode? {
    val start = createBinaryTree()
    val deque = ArrayDeque<TreeNode>()
    deque.add(start)
    
    while(deque.isNotEmpty()){
        val string = deque.joinToString(", ")
        println("$string")
        for(i in 0 until deque.size){
            val node = deque.removeFirst()
            
            if(node.item == given.item){
                if(deque.isEmpty())
                	return node.left
                return deque.removeFirst()
            }
                
            if(node.left != null) deque.add(node.left as TreeNode)
            if(node.right != null) deque.add(node.right as TreeNode)
        }
    }
    
    return null
} 

/* Connect Level Order Siblings (medium)
 * Given a binary tree, connect each node with its level order successor. 
 * The last node of each level should point to a null node.
 */ 
fun connectLevelOrderSiblings(){
    val start = createBinaryTree()
    val queue = ArrayDeque<TreeNode>()
    queue.add(start)
    
    println("Result for connectLevelOrderSibling: ")
    
    while(queue.isNotEmpty()){
        var sizeLevel = queue.size
        for(i in 0 until queue.size){
            val node = queue.removeFirst()
            
            if(sizeLevel > 1){
                node.sibling = queue.first() // point the sibling to the successor
            }
            
            if(node.left != null) queue.add(node.left as TreeNode)
            if(node.right != null) queue.add(node.right as TreeNode)
            
            --sizeLevel
            
            println("[item=${node.item}, left=${node.left?.item}, right=${node.right?.item}], "
                  + "sibling=${node.sibling?.item}]")
        }
    }   
}

/* Connect All Level Order Siblings (medium)
 * Given a binary tree, connect each node with its level order successor. 
 * The last node of each level should point to the first node of the next level.
 */
fun connectAllLevelOrderSiblings(){
    val start = createBinaryTree()
    val queue = ArrayDeque<TreeNode>()
    queue.add(start)
    
    println("Result for connectAllLevelOrderSiblings: ")
    
    while(queue.isNotEmpty()){
        
        for(i in 0 until queue.size){
            val node = queue.removeFirst()
            
            if(node.left != null) queue.add(node.left as TreeNode)
            if(node.right != null) queue.add(node.right as TreeNode)
            
            if(queue.isNotEmpty())
            	node.sibling = queue.first()
            
            println("[item=${node.item}, left=${node.left?.item}, right=${node.right?.item}], "
                  + "sibling=${node.sibling?.item}]")
        }
    }   
} 

/* Problem 2: Right View of a Binary Tree (easy)
 * Given a binary tree, return an array containing nodes in its right view. 
 * The right view of a binary tree is the set of nodes visible when the tree 
 * is seen from the right side.
 */ 
fun rightViewBinaryTree(): List<Int> {
    val start = createBinaryTree()
    val result = mutableListOf<Int>()
    
    val queue = ArrayDeque<TreeNode>()
    queue.add(start)
    while(queue.isNotEmpty()){
        var node: TreeNode? = null
        for(i in 0 until queue.size){
            node = queue.removeFirst()
            
            if(node.left != null) queue.add(node.left as TreeNode)
            if(node.right != null) queue.add(node.right as TreeNode)
        }
        
        result.add(node?.item ?: -1)
    }
    
    return result
} 
 
fun main() {
   val result_1 = binaryTreeLevelOrderTraversal()
   val result_2 = reversalLevelOrderTraversal()
   val result_3 = zigZagTraversal()
   val result_4 = levelAverages()
   val result_5 = findMinDepthBinaryTree()
   val result_6 = findLevelOrderSuccessor(TreeNode(12))
   
   println("Result for binaryTreeLevelOrderTraversal: $result_1")
   println("Result for reversalLevelOrderTraversal: $result_2")
   println("Result for zigZagTraversal: $result_3")
   println("Result for levelAverages: $result_4")
   println("Result for findMinDepthBinaryTree: $result_5")
   println("Result for findLevelOrderSuccessor: ${result_6?.item}")
   
   connectLevelOrderSiblings()
   connectAllLevelOrderSiblings()
   val prob_2 = rightViewBinaryTree()
   println("Result for rightViewBinaryTree: $prob_2")
}
