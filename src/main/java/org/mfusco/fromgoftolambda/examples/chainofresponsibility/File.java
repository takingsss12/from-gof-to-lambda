package org.mfusco.fromgoftolambda.examples.chainofresponsibility;

public class File
{

  enum Type
  {
    TEXT, PRESENTATION, AUDIO, VIDEO;

    @Override
    public String toString()
    {
      return name().charAt(0) + name().substring(1)
          .toLowerCase();
    }
  }

  private final Type type;
  private final String content;

  public File(Type type, String content)
  {
    this.type = type;
    this.content = content;
  }

  public Type getType()
  {
    return type;
  }

  public String getContent()
  {
    return content;
  }

  @Override
  public String toString()
  {
    return type + " file: " + content;
  }
}
