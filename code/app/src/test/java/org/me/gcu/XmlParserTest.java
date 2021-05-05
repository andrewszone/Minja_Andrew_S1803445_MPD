package org.me.gcu;

import org.me.gcu.models.Channel;
import org.me.gcu.utils.XmlParser;

import org.junit.Test;

import java.io.InputStream;

public class XmlParserTest {

    @Test
    public void shouldParseXmlCorrectly(){
        // Given the XmlParser class
        XmlParser parser = new XmlParser();

        // When the data is available in xml format
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("data.xml");

        // Then the parser should output the required channel
        Channel data = parser.parse(in);
        assert(!data.getItems().isEmpty());
    }
}
