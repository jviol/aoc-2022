fun main() {
    val input = java.io.File("src/main/resources", "07.txt").readLines().iterator()
    input.next()
    val root = Dir("/", null)
    var cwd = root
    var line = input.next().trim()
    val dirs = mutableListOf<Dir>()
    fun addDir(name: String): Dir {
        val dir = Dir(name, cwd)
        dirs.add(dir)
        return dir
    }
    while (input.hasNext()) {
        if (line.startsWith("$ cd ")) {
            val dir = line.substring(5)
            cwd = if (dir == "..") {
                cwd.parent!!
            } else {
                cwd.subdirs.computeIfAbsent(dir, ::addDir)
            }
            line = input.next().trim()
        } else if (line.startsWith("$ ls")) {
            while (input.hasNext()) {
                line = input.next().trim()
                if (line.startsWith("dir ")) {
                    val dir = line.substring(4)
                    cwd.subdirs.computeIfAbsent(dir, ::addDir)
                } else if (line.matches(Regex("\\d+ .*"))) {
                    val split = line.split(" ")
                    assert(split.size == 2)
                    val name = split[1]
                    val size = split[0].toInt()
                    cwd.files.putIfAbsent(name, size)
                } else {
                    break
                }
            }
        }
    }
    val part1 = dirs.map(Dir::size)
        .filter { it <= 100000 }
        .sum()
    println(part1)
    // part 2
    val usedSpace = root.size
    val unusedSpace = 70000000 - usedSpace
    val neededSpace = 30000000 - unusedSpace
    val toBeDeleted = dirs.map { it.size }
        .filter { it >= neededSpace }
        .min()
    println(toBeDeleted)
}

class Dir(private val name: String, val parent: Dir?) {
    val subdirs: MutableMap<String, Dir> = mutableMapOf()
    val files: MutableMap<String, Int> = mutableMapOf()
    val size: Int by lazy { files.values.sum() + subdirs.values.sumOf { it.size } }
    override fun toString(): String {
        return name
    }
}
