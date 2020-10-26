package cz.sparko.course.android.flickrbrowser

import java.io.Serializable

data class Photo(
  val title: String,
  val author: String,
  val authorId: String,
  val link: String,
  val tags: String,
  val image: String
) : Serializable {
  companion object {
    private const val serialVersionUID = 1L
  }

  /**
   * Classes that require special handling during the serialization and
   * deserialization process must implement special methods with these exact
   * signatures:
   *
   * <PRE>
   * private void writeObject(java.io.ObjectOutputStream out)
   *     throws IOException
   * private void readObject(java.io.ObjectInputStream in)
   *     throws IOException, ClassNotFoundException;
   * private void readObjectNoData()
   *     throws ObjectStreamException;
   * </PRE>
   */
}
