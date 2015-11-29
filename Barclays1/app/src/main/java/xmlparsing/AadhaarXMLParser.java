package xmlparsing;

import android.util.Xml;

import com.models.AadhaarCard;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hp laptop on 28-11-2015.
 */

public class AadhaarXMLParser {
    // We don't use namespaces
    private static final String ns = null;

    private AadhaarCard aadhaarCard;

    public AadhaarCard parse(String xmlContent) throws XmlPullParserException, IOException {
        InputStream in = new ByteArrayInputStream(xmlContent.getBytes());
        aadhaarCard = new AadhaarCard();
        aadhaarCard.originalXML = xmlContent;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readFeed(parser);
        } finally {
            in.close();
        }
        return aadhaarCard;
    }

    private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "PrintLetterBarcodeData");
        aadhaarCard.uid = parser.getAttributeValue(null, "uid");
        aadhaarCard.name = parser.getAttributeValue(null, "name");
        aadhaarCard.gender = parser.getAttributeValue(null, "gender");
        aadhaarCard.yob = parser.getAttributeValue(null, "yob");
        aadhaarCard.co = parser.getAttributeValue(null, "co");
        aadhaarCard.house = parser.getAttributeValue(null, "house");
        aadhaarCard.lm = parser.getAttributeValue(null, "lm");
        aadhaarCard.loc = parser.getAttributeValue(null, "loc");
        aadhaarCard.vtc = parser.getAttributeValue(null, "vtc");
        aadhaarCard.po = parser.getAttributeValue(null, "po");
        aadhaarCard.dist = parser.getAttributeValue(null, "dist");
        aadhaarCard.subdist = parser.getAttributeValue(null, "subdist");
        aadhaarCard.state = parser.getAttributeValue(null, "state");
        aadhaarCard.pincode = parser.getAttributeValue(null, "pc");
        aadhaarCard.dob = parser.getAttributeValue(null, "dob");
        aadhaarCard.lat = "0.0";
        aadhaarCard.lng = "0.0" ;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
