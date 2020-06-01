package practice

trait Item {
  val name: String
}

case class Directory(
    name: String,
    contents: Seq[Item]
) extends Item

case class File(name: String) extends Item

object DirectoryRecurse {
  def recurse(directory: Directory, prefix: String = "/"): Seq[String] =
    directory.contents.flatMap {
      case file: File              => Seq(prefix + directory.name + "/" + file.name)
      case subdirectory: Directory => recurse(subdirectory, prefix + directory.name + "/")
    }
}
