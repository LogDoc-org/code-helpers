package org.logdoc.helpers.std;

import static org.logdoc.helpers.std.MimeType.of;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 22.08.2023 16:44
 * code-helpers ☭ sweat and blood
 */
public interface MimeTypes {
    MimeType FORM = of(Signs.HttpForm),
            JSON = of(Signs.Json),
            TEXTPLAIN = of(Signs.Txt),
            TEXTALL = of("text/*"),
            TEXTHTML = of(Signs.Html),
            TEXTXML = of(Signs.Xml),
            BINARY = of(Signs.Bin),
            XML = of(Signs.Xml),
            MULTIPART = of(Signs.Multiform);

    interface Signs {
        String Svg = "image/svg+xml",
                Gif = "image/gif",
                Bmp = "image/x-bitmap",
                Wav = "audio/x-wav",
                Html = "text/html",
                Xml = "application/xml",
                Pic = "image/x-pixmap",
                Png = "image/png",
                Jpg = "image/jpeg",
                Json = "application/json",
                HttpForm = "application/x-www-form-urlencoded",
                Txt = "text/plain",
                Bin = "application/octet-stream",
                Multiform = "multipart/form-data";
    }
}
