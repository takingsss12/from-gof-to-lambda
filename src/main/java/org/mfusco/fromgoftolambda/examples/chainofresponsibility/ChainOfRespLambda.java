package org.mfusco.fromgoftolambda.examples.chainofresponsibility;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.mfusco.fromgoftolambda.examples.chainofresponsibility.File.Type;

public class ChainOfRespLambda
{

  public static Optional<String> parseFile(File file, Type type)
  {
    return Optional.ofNullable(file)
        .filter(fileToFilter -> type.equals(fileToFilter.getType()))
        .map(Object::toString);
  }

  public static Optional<String> parseText(File file)
  {
    return parseFile(file, Type.TEXT);
  }

  public static Optional<String> parsePresentation(File file)
  {
    return parseFile(file, Type.PRESENTATION);
  }

  public static Optional<String> parseAudio(File file)
  {
    return parseFile(file, Type.AUDIO);
  }

  public static Optional<String> parseVideo(File file)
  {
    return parseFile(file, Type.VIDEO);
  }

  public static void main(String[] args)
  {
    File file = new File(File.Type.AUDIO, "Dream Theater  - The Astonishing");

    System.out.println(Stream
        .<Function<File, Optional<String>>>of(ChainOfRespLambda::parseText, ChainOfRespLambda::parsePresentation,
            ChainOfRespLambda::parseAudio, ChainOfRespLambda::parseVideo)
        .map(f -> f.apply(file))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Unknown file: " + file)));
  }
}
