/* Depth First Search (DFS) - Technique to traverse a tree.
 * To recursively traverse a binary tree in a DFS fashion, we can start from the root 
 * and at every step, make two recursive calls one for the left and one for the right child.
 * Here are the steps for our Binary Tree Path Sum problem:
 * 	1. Start DFS with the root of the tree.
 * 	2. If the current node is not a leaf node, do two things:
 * 		- Subtract the value of the current node from the given number to get a new 
 * 		  sum => S = S - node.value
 * 		- Make two recursive calls for both the children of the current node with the new number 
 * 		  calculated in the previous step.
 * 	3. At every step, see if the current node being visited is a leaf node and if its value 
 * 	   is equal to the given number ‘S’. If both these conditions are true, we have found the 
 * 	   required root-to-leaf path, therefore return true.
 * 	4. If the current node is a leaf but its value is not equal to the given number ‘S’, return false.
 */ 

/* DFS Utils */
data class TreeNode(val item: Int, var left: TreeNode? = null, var right: TreeNode? = null)
fun createBinaryTree(): TreeNode {
    val root = TreeNode(12)
    root.left = TreeNode(7)
    root.right = TreeNode(1)
    root.left?.left = TreeNode(4)
    root.right?.left = TreeNode(10)
    root.right?.right = TreeNode(5)
    
    return root
}

fun createBinaryTreeSorted(): TreeNode {
    val root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left?.left = TreeNode(4)
    //root.left?.right = TreeNode(5)
    root.right?.left = TreeNode(5)
    root.right?.right = TreeNode(6)
    
    return root
}

fun dfsRecursive(root: TreeNode){
    print("${root.item}, ") 
    
    // if current node is not a leaf node
    if(root.left != null && root.right != null){
        dfsRecursive(root.left as TreeNode)
        dfsRecursive(root.right as TreeNode)
    } else if(root.left != null){
        dfsRecursive(root.left as TreeNode)
    } else if(root.right != null){
    	dfsRecursive(root.right as TreeNode)
    } else {
        // It is a leaf node 
    }
      
}

/* Binary Tree Path Sum (easy)
 * Given a binary tree and a number ‘S’, find if the tree has a path from root-to-leaf 
 * such that the sum of all the node values of that path equals ‘S’.
 */
fun binaryTreePathSum(root: TreeNode?, sum: Int = 0, s: Int): Boolean { 
    if(root == null) return false
    
    if(sum + root.item == s) return true // we found it
    
    return binaryTreePathSum(root.left, sum + root.item, s) 
    		|| binaryTreePathSum(root.right, sum + root.item, s)
}

/* All Paths for a Sum (medium)
 * Given a binary tree and a number ‘S’, find all paths from root-to-leaf such that the 
 * sum of all the node values of each path equals ‘S’.
 */ 
fun allPathsForSum(node: TreeNode?, s: Int, path: ArrayDeque<Int>, paths: MutableList<List<Int>>) {
    
    // base case
    if(node == null) return
    
    path.add(node.item)
//     print("node=${node.item} ")
//     print("path=[")
//     path.forEach{ print("$it, ") }
//     println("]")
    
    if(node.left == null && node.right == null){
        if(path.sum() == s) paths.add(path.toList()) // O(N)
        //println("paths=$paths")
        path.removeLast()
        
        return
    }
    
    allPathsForSum(node.left, s, path, paths)
    allPathsForSum(node.right, s, path, paths)
    
    path.removeLast()
} 

/* Sum of Path Numbers (medium)
 * Given a binary tree where each node can only have a digit (0-9) value, each root-to-leaf path 
 * will represent a number. Find the total sum of all the numbers represented by all paths.
 */
fun findTotalSumAllPaths(node: TreeNode?, path: MutableList<Int>): Int {
    if(node == null) return 0
    
    path.add(node.item)
    if(node.left == null && node.right == null){
        var total = 0
        for((i, it) in path.withIndex()){
            if(i == 0) total = it * 10
            else if(i == 1) total += it
            else if(i % 2 == 0) total = total * 10 + it
            else total += it
        }
        path.removeLast()
        return total
    }
    
    val leftTotal = findTotalSumAllPaths(node.left, path)
    val rightTotal = findTotalSumAllPaths(node.right, path)
    
    path.removeLast() // backtrack
    return leftTotal + rightTotal
} 

/* Path With Given Sequence (medium)
 * Given a binary tree and a number sequence, find if the sequence is present as a 
 * root-to-leaf path in the given tree.
 */
fun findSequencePresent(node: TreeNode?, seq: List<Int>, path: MutableList<Int>): Boolean {
    if(node == null) return false
    
    path.add(node.item)
    if(node.left == null && node.right == null){
        if(seq == path) return true
        
        path.removeLast() // back track
        return false
    }
    
    val leftOutput = findSequencePresent(node.left, seq, path)
    val rightOutput = findSequencePresent(node.right, seq, path)
    
    path.removeLast()
    return leftOutput || rightOutput
}

/* Count Paths for a Sum (medium)
 * Given a binary tree and a number ‘S’, find all paths in the tree such that the sum of all 
 * the node values of each path equals ‘S’. Please note that the paths can start or end at any 
 * node but all paths must follow direction from parent to child (top to bottom).
 */ 
fun findAllPathSum(node: TreeNode?, s: Int, 
                   path: MutableList<Int>, result: MutableList<List<Int>>){
    
    if(node == null) return
    
    path.add(node.item)
    if(node.left == null && node.right == null){
        // check if a path exists where sum == s
        var total = 0
        var i = path.size - 1
        while(i >= 0){
            total += path[i]
            if(total == s){
                val list = mutableListOf<Int>()
                for(j in i until path.size)
                    list.add(path[j])
                result.add(list)
                break
            }
            
            --i
        }

        path.removeLast()
    }
    
    
    findAllPathSum(node.left, s, path, result)
    findAllPathSum(node.right, s, path, result)
    
    // check if a path exists where sum == s
    var total = 0
    var i = path.size - 1
    while(i >= 0){
            total += path[i]
            if(total == s){
                val list = mutableListOf<Int>()
                for(j in i until path.size)
                    list.add(path[j])
                result.add(list)
                break
            }
            
            --i
        }

        if(path.isNotEmpty()) path.removeLast()    
}

/* Problem 1: Tree Diameter (medium)
 * Given a binary tree, find the length of its diameter. The diameter of a tree is the number of 
 * nodes on the longest path between any two leaf nodes. The diameter of a tree may or may not 
 * pass through the root.
 * 
 * Note: You can always assume that there are at least two leaf nodes in the given tree.
 */ 
var gTreeDiameter = 0 
fun calculateHeight(node: TreeNode?, treeDiameter: Int): Int{
    if(node == null) return 0
    
    val leftTreeHeight = calculateHeight(node.left, gTreeDiameter)
    val rightTreeHeight = calculateHeight(node.right, gTreeDiameter)
    
    // if the current node doesn't have a left or right subtree, we can't have
    // a path passing through it, since we need a leaf node on each side
    if(leftTreeHeight != 0 && rightTreeHeight != 0){
        // diameter at the current node will be equal to the height of left subtree +
      	// the height of right sub-trees + '1' for the current node
      	val diameter = leftTreeHeight + rightTreeHeight + 1
        
        // update the global tree diameter
    	gTreeDiameter = Math.max(gTreeDiameter, diameter)
    }
    
    // height of the current node will be equal to the maximum of the heights of
    // left or right subtrees plus '1' for the current node
    return Math.max(leftTreeHeight, rightTreeHeight) + 1
} 
fun findDiameterTree(node: TreeNode?): Int {
    
    calculateHeight(node, gTreeDiameter)
    return gTreeDiameter
} 

/* Prob 2: Path with Maximum Sum (hard)
 * Find the path with the maximum sum in a given binary tree. Write a function that returns 
 * the maximum sum.
 * A path can be defined as a sequence of nodes between any two nodes and doesn’t 
 * necessarily pass through the root. The path must contain at least one node.
 */ 
var globalMaxSum = 0 
fun calculateHeightSum(node: TreeNode?): Int {
    if(node == null) return 0
    
    val left = calculateHeightSum(node.left)
    val right = calculateHeightSum(node.right)
    
    if(left != 0 && right != 0){
        // A path exists between two leaf nodes
        val total = left + right + 1
        
        // keep track of the maximum sum (diameter)
        globalMaxSum = Math.max(total, globalMaxSum)
    }
    
    return Math.max(left, right) + node.item
}
fun findMaxPathSum(node: TreeNode?): Int {
    calculateHeightSum(node)
    return globalMaxSum
}



fun main() {
   val result_1 = binaryTreePathSum(createBinaryTree(), 0, 23)
   val result_2 = mutableListOf<List<Int>>() 
   allPathsForSum(createBinaryTree(), 23, ArrayDeque<Int>(), result_2)
   val result_3 = findTotalSumAllPaths(createBinaryTreeSorted(), mutableListOf<Int>())
   val result_4 = findSequencePresent(createBinaryTreeSorted(), 
                                      listOf(1, 2, 4), mutableListOf<Int>())
   val result_5 = mutableListOf<List<Int>>()
    findAllPathSum(createBinaryTree(), 11, mutableListOf<Int>(), result_5)
   
   val prob_1 = findDiameterTree(createBinaryTreeSorted())
   val prob_2 = findMaxPathSum(createBinaryTreeSorted())
   
   println("Result for binaryTreePathSum: $result_1")
   println("Result for allPathsForSum: $result_2")
   println("Result for findtotalSumAllPaths: $result_3")
   println("Result for findSequencePresent: $result_4")
   println("Result for findAllPathSum: $result_5")
   
   println("Result for findDiameterTree: $prob_1")
   println("Result for findMaxPathSum: $prob_2")
}
