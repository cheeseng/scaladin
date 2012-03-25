package vaadin.scala

abstract class AbstractTextField extends AbstractField {

  override def p: com.vaadin.ui.AbstractTextField

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) = p.setInputPrompt(prompt.getOrElse(null))
  def prompt_=(prompt: String) = p.setInputPrompt(prompt)

  def nullRepresentation = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) = { require(nullRepresentation != null); p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) = p.setNullSettingAllowed(nullSettingAllowed)

  def maxLength = p.getMaxLength
  def maxLength_=(maxLength: Int) = p.setMaxLength(maxLength)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}