package vaadin.scala
import scala.collection.JavaConverters._
import vaadin.scala.mixins.ContainerMixin

trait FilterableContainer extends Container {

  /**
   * Filter based on item id
   */
  def \(itemId: Any): Option[Item] = getItem(itemId)

  /**
   * Filter based on Item
   */
  def filterItems(itemFilter: Item => Boolean): List[Item] = itemIds.map(getItem).flatten.filter(itemFilter).toList

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property[_] => Boolean): List[Property[_]] = itemIds.map(getItem).flatten.flatMap(Item.getProperties).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \\(propertyId: Any): List[Property[_]] = itemIds.map(getItem).flatten.map(_.property(propertyId)).flatten.toList
}

trait FilterableItem extends Item {
  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property[_] => Boolean): List[Property[_]] = Item.getProperties(this).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): Option[Property[_]] = property(propertyId)

  def values: List[Any] = Item.getProperties(this).map(_.value).toList
}

class FilterableContainerWrap(wrapped: com.vaadin.data.Container with ContainerMixin) extends Container with FilterableContainer {
  def p: com.vaadin.data.Container with ContainerMixin = wrapped

  def wrapItem(unwrapped: com.vaadin.data.Item) = new FilterableItemWrap(unwrapped)
}

class FilterableItemWrap(wrapped: com.vaadin.data.Item) extends FilterableItem {
  def p: com.vaadin.data.Item = wrapped
}

object EmptyFilterableItem extends FilterableItem {
  val p = null

  override def filterProperties(propertyFilter: Property[_] => Boolean): List[Property[_]] = List()

  override def \(propertyId: Any): Option[Property[_]] = None

  override def values: List[Any] = List()

  override def wrapProperty(unwrapped: com.vaadin.data.Property[_]) = null
}

class PropertyListWrap(wrapped: List[Property[_]]) {
  def values = wrapped.map(_.value)
}