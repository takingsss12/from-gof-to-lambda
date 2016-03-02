/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mfusco.fromgoftolambda.examples.chainofresponsibility;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChainOfRespLambda {

    public static Optional<String> parseText(File file) {
        return file.getType() == File.Type.TEXT ?
               Optional.of("Text file: " + file.getContent()) :
               Optional.empty();
    }

    public static Optional<String> parsePresentation(File file) {
        return file.getType() == File.Type.PRESENTATION ?
               Optional.of("Presentation file: " + file.getContent()) :
               Optional.empty();
    }

    public static Optional<String> parseAudio(File file) {
        return file.getType() == File.Type.AUDIO ?
               Optional.of("Audio file: " + file.getContent()) :
               Optional.empty();
    }

    public static Optional<String> parseVideo(File file) {
        return file.getType() == File.Type.VIDEO ?
               Optional.of("Video file: " + file.getContent()) :
               Optional.empty();
    }

    public static void main( String[] args ) {
        File file = new File( File.Type.AUDIO, "Dream Theater  - The Astonishing" );

        System.out.println(
        Stream.<Function<File, Optional<String>>>of(
                ChainOfRespLambda::parseText,
                ChainOfRespLambda::parsePresentation,
                ChainOfRespLambda::parseAudio,
                ChainOfRespLambda::parseVideo )
                .map(f -> f.apply( file ))
                .filter( Optional::isPresent )
                .findFirst()
                .flatMap( Function.identity() )
                .orElseThrow( () -> new RuntimeException( "Unknown file: " + file ) )
                          );
    }
}
