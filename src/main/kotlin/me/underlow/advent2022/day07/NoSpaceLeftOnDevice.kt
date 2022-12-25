package me.underlow.advent2022.day07

import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/7

fun main() {
    val commands = getResourceAsLines("NoSpaceLeftOnDeviceInput.txt") // 1783610
    // split to command + result for list
    val tokens = splitToCommandTokens(commands)

    val fs = processCommands(tokens)
    fs.calculateSizes()

    val searchRequired = fs.searchRequired(mutableSetOf())
    val total = searchRequired.filter { it.size <= 100000 }

    val totalSum = total.sumOf { it.size }

    val spaceNeded =  30000000 - (70000000 - fs.size)
    val sortedBy = searchRequired.filter { it.size >= spaceNeded }.toList().sortedBy { it.size }

    println(totalSum)
    println(sortedBy[0].size) // 8465165 - too high
}

fun FilesystemItem.calculateSizes(): Int {
    if (this is File)
        return size
    if (size != 0)
        return size

    children().filterIsInstance<Directory>().forEach {
        if (it.size == 0)
            it.size = it.calculateSizes()
    }
    size = children().sumOf { it.size }
    return size
}

fun FilesystemItem.searchRequired(mutableSetOf: MutableSet<Directory>): MutableSet<Directory> {
    if (this is File)
        return mutableSetOf

    children().filterIsInstance<Directory>().forEach {
        mutableSetOf.add(it)
        it.children.forEach { it.searchRequired(mutableSetOf) }
    }

    if (this is Directory)
        mutableSetOf.add(this)

    return mutableSetOf
}

fun processCommands(tokens: List<Command>): FilesystemItem {
    val fs = Directory("/", 0, null, mutableListOf())
//    fs.parent = fs
    var currentDir = fs
    for (token in tokens) {
        println("Parsing $token")
        when (token) {
            is Cd -> {
                when (token.direction) {
                    "/" -> currentDir = fs
                    ".." -> {
                        currentDir = currentDir.parent!!
                    }

                    else -> {
                        val newDir = currentDir.children.filterIsInstance<Directory>().find { it.name == token.direction }
                        currentDir = newDir!!
                    }
                }
            }

            is Ls -> {
                if (currentDir.children.isEmpty()) {// fill with children
                    currentDir.children.clear()
                    currentDir.children.addAll(token.output)
                    currentDir.children.filterIsInstance<Directory>().forEach { it.parent = currentDir }
                }
            }
        }
    }
    return fs
}

interface FilesystemItem {
    var size: Int
    fun children(): List<FilesystemItem> {
        return emptyList()
    }

}

data class Directory(val name: String, override var size: Int, var parent: Directory?, val children: MutableList<FilesystemItem>) :
    FilesystemItem {
    override fun children(): List<FilesystemItem> {
        return children
    }

    override fun toString(): String {
        return "Directory(name='$name', size=$size, parent=${parent?.name}, children=$children)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Directory

        if (name != other.name) return false
        if (size != other.size) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + size
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }
}

data class File(val name: String, override var size: Int) : FilesystemItem


interface Command

data class Ls(val output: List<FilesystemItem>) : Command
data class Cd(val direction: String) : Command

fun splitToCommandTokens(commands: List<String>): List<Command> {
    val cmds = mutableListOf<Command>()
    var i = 0
    while (i < commands.size) {
        val currentLine = commands[i]
        when {
            currentLine.startsWith("\$ cd") -> {
                val direction = currentLine.substring(5, currentLine.length).trim()
                cmds += Cd(direction)
                i++
            }

            currentLine.startsWith("\$ ls") -> {
                var j = i + 1
                val output = mutableListOf<FilesystemItem>()
                while (j < commands.size && !commands[j].startsWith("\$")) {
                    val currentLine = commands[j]
                    output += parseLsLine(commands[j])
                    j++
                    i++
                }
                cmds += Ls(output)
                i++

            }

        }
    }
    return cmds
}

fun parseLsLine(s: String): FilesystemItem {
    when {
        s.startsWith("dir") -> return Directory(s.substring(4, s.length), 0, null, mutableListOf())
        else -> {
            val split = s.split(" ")
            return File(split[1], split[0].toInt())
        }
    }
}
