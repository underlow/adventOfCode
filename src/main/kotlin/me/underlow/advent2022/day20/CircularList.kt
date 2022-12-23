package me.underlow.advent2022.day20

import kotlin.math.abs

class CircularList(initialData: List<Long>) {

    val size: Int
    private val head: Node

    init {
        require(initialData.isNotEmpty()) {
            "No sense in empty circular list"
        }
        size = initialData.size
        head = Node(initialData[0], 0, null, null)

        var prev = head

        for (i in 1 until initialData.size) {
            prev.next = Node(initialData[i], i, prev, null)
            prev = prev.next!!
        }
        prev.next = head
        head.prev = prev

    }

    /**
     * for positive value clockwise (right)
     * for negative counterclockwise (left)
     */
    fun moveNode(originalPosition: Int, steps: Long) {
        val node = findNode { it.originalPosition == originalPosition }
            ?: error("No $originalPosition value in this list")

        val op = if (steps < 0) { n: Node -> n.moveLeft() } else { n: Node -> n.moveRight() }
        val actualSteps = abs(steps % (size - 1)).toInt()

        repeat(actualSteps) { op(node) }
    }

    private fun findNode(condition: (Node) -> Boolean): Node? {
        if (condition(head))
            return head
        var current = head.next
        while (current != head) {
            if (condition(current!!))
                return current

            current = current.next
        }
        return null
    }

    override fun toString(): String {
        return "CircularList(size=$size, head=${head.toList().joinToString { it.data.toString() }})"
    }

    private fun Node.toList(): List<Node> {
        val ret = mutableListOf(this@toList)
        var current = this.next
        while (current != this) {
            ret.add(current!!)
            current = current.next
        }
        return ret
    }

    // returns plain list starting from node.data == nodeData
    fun toList(nodeData: Long): List<Long> {
        val node = findNode { it.data == nodeData } ?: error("No $nodeData value in this list")
        return node.toList().map { it.data }
    }
}

// CAREFUL: broken equals and hashCode
private data class Node(val data: Long, val originalPosition: Int, var prev: Node?, var next: Node?) {
    override fun toString(): String {
        return "Node(data=$data, prev=${prev?.data}, next=${next?.data})"
    }

    fun moveLeft() {
        val oldPrev = prev!!
        val oldNext = next!!

        val newPrev = prev!!.prev!!
        val newNext = prev!!

        prev = newPrev
        next = newNext

        oldPrev.prev!!.next = this
        oldPrev.prev = this
        oldPrev.next = oldNext

        oldNext.prev = oldPrev
    }

    fun moveRight() {
        val oldPrev = prev!!
        val oldNext = next!!

        val newPrev = next!!
        val newNext = next!!.next!!

        prev = newPrev
        next = newNext

        oldNext.next!!.prev = this
        oldNext.prev = oldPrev
        oldNext.next = this

        oldPrev.next = oldNext
    }

}
