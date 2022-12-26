package me.underlow.advent2022.day17

class CircularProvider<T>(private val shapes: List<T>) {
    private var currentShapeIndex = 0

    fun nextShape(): T {
        val shape = shapes[currentShapeIndex]
        currentShapeIndex = (currentShapeIndex + 1) % shapes.size
        return shape
    }
}
