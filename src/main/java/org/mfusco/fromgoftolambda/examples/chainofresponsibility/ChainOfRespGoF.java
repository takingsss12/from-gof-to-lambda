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

public class ChainOfRespGoF {

    public interface FileParser {
        String parse(File file);
        void setNextParser(FileParser next);
    }

    public abstract static class AbstractFileParser implements FileParser {
        protected FileParser next;

        @Override
        public void setNextParser( FileParser next ) {
            this.next = next;
        }
    }

    public static class TextFileParser extends AbstractFileParser {
        @Override
        public String parse( File file ) {
            if ( file.getType() == File.Type.TEXT ) {
                return "Text file: " + file.getContent();
            } else if (next != null) {
                return next.parse( file );
            } else {
                throw new RuntimeException( "Unknown file: " + file );
            }
        }
    }

    public static class PresentationFileParser extends AbstractFileParser {
        @Override
        public String parse( File file ) {
            if ( file.getType() == File.Type.PRESENTATION ) {
                return "Presentation file: " + file.getContent();
            } else if (next != null) {
                return next.parse( file );
            } else {
                throw new RuntimeException( "Unknown file: " + file );
            }
        }
    }

    public static class AudioFileParser extends AbstractFileParser {
        @Override
        public String parse( File file ) {
            if ( file.getType() == File.Type.AUDIO ) {
                return "Audio file: " + file.getContent();
            } else if (next != null) {
                return next.parse( file );
            } else {
                throw new RuntimeException( "Unknown file: " + file );
            }
        }
    }

    public static class VideoFileParser extends AbstractFileParser {
        @Override
        public String parse( File file ) {
            if ( file.getType() == File.Type.VIDEO ) {
                return "Video file: " + file.getContent();
            } else if (next != null) {
                return next.parse( file );
            } else {
                throw new RuntimeException( "Unknown file: " + file );
            }
        }
    }

    public static void main( String[] args ) {
        FileParser textParser = new TextFileParser();
        FileParser presentationParser = new PresentationFileParser();
        FileParser audioParser = new AudioFileParser();
        FileParser videoParser = new VideoFileParser();

        textParser.setNextParser( presentationParser );
        presentationParser.setNextParser( audioParser );
        audioParser.setNextParser( videoParser );

        File file = new File( File.Type.AUDIO, "Dream Theater  - The Astonishing" );

        System.out.println( textParser.parse( file ) );
    }
}
