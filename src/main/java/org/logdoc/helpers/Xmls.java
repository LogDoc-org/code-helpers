package org.logdoc.helpers;

import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:46
 * code-helpers ☭ sweat and blood
 */
public class Xmls {
    private Xmls() {}

    public static byte[] xml2StringBytes(final Node doc) {
        try { return toStringUnsafe(doc).getBytes(StandardCharsets.UTF_8); } catch (final Exception ignore) { }

        return null;
    }

    public static String toString(final Node doc) {
        try {
            return toStringUnsafe(doc);
        } catch (final TransformerException e) {
            return doc + " " + e;
        }
    }

    public static String toStringUnsafe(final Node doc) throws TransformerException {
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        final StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString().replaceAll("[\\n\\r]", "");
    }

}
