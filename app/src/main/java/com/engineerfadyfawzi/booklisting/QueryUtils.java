package com.engineerfadyfawzi.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving book data from Google Books.
 */
public class QueryUtils
{
    /**
     * Sample JSON response for a Google Books APIs query.
     */
    public static final String SAMPLE_JSON_RESPONSE = "{\n" +
            " \"kind\": \"books#volumes\",\n" +
            " \"totalItems\": 431,\n" +
            " \"items\": [\n" +
            "  {\n" +
            "   \"kind\": \"books#volume\",\n" +
            "   \"id\": \"qKFDDAAAQBAJ\",\n" +
            "   \"etag\": \"HlWvBTAH69g\",\n" +
            "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/qKFDDAAAQBAJ\",\n" +
            "   \"volumeInfo\": {\n" +
            "    \"title\": \"Android\",\n" +
            "    \"authors\": [\n" +
            "     \"P.K. Dixit\"\n" +
            "    ],\n" +
            "    \"publisher\": \"Vikas Publishing House\",\n" +
            "    \"publishedDate\": \"2014\",\n" +
            "    \"description\": \"Android is a movement that has transferred data from laptop to hand-held devices like mobiles. Though there are alternate technologies that compete with Android, but it is the front runner in mobile technology by a long distance. Good knowledge in basic Java will help you to understand and develop Android technology and apps. Many universities in India and across the world are now teaching Android in their syllabus, which shows the importance of this subject. This book can be read by anyone who knows Java and XML concepts. It includes a lot of diagrams along with explanations to facilitate better understanding by students. This book aptly concludes with a project that uses Android, which will greatly benefit students in learning the practical aspects of Android. Key Features • Instructions in designing different Android user interfaces • Thorough explanations of all activities • JSON • Android-based project to aid practical understanding\",\n" +
            "    \"industryIdentifiers\": [\n" +
            "     {\n" +
            "      \"type\": \"ISBN_13\",\n" +
            "      \"identifier\": \"9789325977884\"\n" +
            "     },\n" +
            "     {\n" +
            "      \"type\": \"ISBN_10\",\n" +
            "      \"identifier\": \"9325977885\"\n" +
            "     }\n" +
            "    ],\n" +
            "    \"readingModes\": {\n" +
            "     \"text\": false,\n" +
            "     \"image\": true\n" +
            "    },\n" +
            "    \"pageCount\": 372,\n" +
            "    \"printType\": \"BOOK\",\n" +
            "    \"categories\": [\n" +
            "     \"Computers\"\n" +
            "    ],\n" +
            "    \"averageRating\": 3.0,\n" +
            "    \"ratingsCount\": 1,\n" +
            "    \"maturityRating\": \"NOT_MATURE\",\n" +
            "    \"allowAnonLogging\": false,\n" +
            "    \"contentVersion\": \"preview-1.0.0\",\n" +
            "    \"panelizationSummary\": {\n" +
            "     \"containsEpubBubbles\": false,\n" +
            "     \"containsImageBubbles\": false\n" +
            "    },\n" +
            "    \"imageLinks\": {\n" +
            "     \"smallThumbnail\": \"http://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "     \"thumbnail\": \"http://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "    },\n" +
            "    \"language\": \"en\",\n" +
            "    \"previewLink\": \"http://books.google.com.eg/books?id=qKFDDAAAQBAJ&printsec=frontcover&dq=android&hl=&cd=1&source=gbs_api\",\n" +
            "    \"infoLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ&source=gbs_api\",\n" +
            "    \"canonicalVolumeLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ\"\n" +
            "   },\n" +
            "   \"saleInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"saleability\": \"FOR_SALE\",\n" +
            "    \"isEbook\": true,\n" +
            "    \"listPrice\": {\n" +
            "     \"amount\": 274.15,\n" +
            "     \"currencyCode\": \"EGP\"\n" +
            "    },\n" +
            "    \"retailPrice\": {\n" +
            "     \"amount\": 274.15,\n" +
            "     \"currencyCode\": \"EGP\"\n" +
            "    },\n" +
            "    \"buyLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ&rdid=book-qKFDDAAAQBAJ&rdot=1&source=gbs_api\",\n" +
            "    \"offers\": [\n" +
            "     {\n" +
            "      \"finskyOfferType\": 1,\n" +
            "      \"listPrice\": {\n" +
            "       \"amountInMicros\": 2.7415E8,\n" +
            "       \"currencyCode\": \"EGP\"\n" +
            "      },\n" +
            "      \"retailPrice\": {\n" +
            "       \"amountInMicros\": 2.7415E8,\n" +
            "       \"currencyCode\": \"EGP\"\n" +
            "      }\n" +
            "     }\n" +
            "    ]\n" +
            "   },\n" +
            "   \"accessInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"viewability\": \"PARTIAL\",\n" +
            "    \"embeddable\": true,\n" +
            "    \"publicDomain\": false,\n" +
            "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
            "    \"epub\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"pdf\": {\n" +
            "     \"isAvailable\": true,\n" +
            "     \"acsTokenLink\": \"http://books.google.com.eg/books/download/Android-sample-pdf.acsm?id=qKFDDAAAQBAJ&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
            "    },\n" +
            "    \"webReaderLink\": \"http://play.google.com/books/reader?id=qKFDDAAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "    \"accessViewStatus\": \"SAMPLE\",\n" +
            "    \"quoteSharingAllowed\": false\n" +
            "   },\n" +
            "   \"searchInfo\": {\n" +
            "    \"textSnippet\": \"Many universities in India and across the world are now teaching Android in their syllabus, which shows the importance of this subject. This book can be read by anyone who knows Java and XML concepts.\"\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"books#volume\",\n" +
            "   \"id\": \"5BGBswAQSiEC\",\n" +
            "   \"etag\": \"PclWhD9IM8g\",\n" +
            "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/5BGBswAQSiEC\",\n" +
            "   \"volumeInfo\": {\n" +
            "    \"title\": \"Programming Android\",\n" +
            "    \"authors\": [\n" +
            "     \"Zigurd Mednieks\",\n" +
            "     \"Laird Dornin\",\n" +
            "     \"Blake Meike\",\n" +
            "     \"Masumi Nakamura\"\n" +
            "    ],\n" +
            "    \"publisher\": \"\\\"O'Reilly Media, Inc.\\\"\",\n" +
            "    \"publishedDate\": \"2011-07-22\",\n" +
            "    \"description\": \"Presents instructions for creating Android applications for mobile devices using Java.\",\n" +
            "    \"industryIdentifiers\": [\n" +
            "     {\n" +
            "      \"type\": \"ISBN_13\",\n" +
            "      \"identifier\": \"9781449389697\"\n" +
            "     },\n" +
            "     {\n" +
            "      \"type\": \"ISBN_10\",\n" +
            "      \"identifier\": \"1449389694\"\n" +
            "     }\n" +
            "    ],\n" +
            "    \"readingModes\": {\n" +
            "     \"text\": false,\n" +
            "     \"image\": true\n" +
            "    },\n" +
            "    \"pageCount\": 482,\n" +
            "    \"printType\": \"BOOK\",\n" +
            "    \"categories\": [\n" +
            "     \"Computers\"\n" +
            "    ],\n" +
            "    \"averageRating\": 3.5,\n" +
            "    \"ratingsCount\": 2,\n" +
            "    \"maturityRating\": \"NOT_MATURE\",\n" +
            "    \"allowAnonLogging\": true,\n" +
            "    \"contentVersion\": \"0.3.0.0.preview.1\",\n" +
            "    \"imageLinks\": {\n" +
            "     \"smallThumbnail\": \"http://books.google.com/books/content?id=5BGBswAQSiEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "     \"thumbnail\": \"http://books.google.com/books/content?id=5BGBswAQSiEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "    },\n" +
            "    \"language\": \"en\",\n" +
            "    \"previewLink\": \"http://books.google.com.eg/books?id=5BGBswAQSiEC&printsec=frontcover&dq=android&hl=&cd=2&source=gbs_api\",\n" +
            "    \"infoLink\": \"http://books.google.com.eg/books?id=5BGBswAQSiEC&dq=android&hl=&source=gbs_api\",\n" +
            "    \"canonicalVolumeLink\": \"https://books.google.com/books/about/Programming_Android.html?hl=&id=5BGBswAQSiEC\"\n" +
            "   },\n" +
            "   \"saleInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"saleability\": \"NOT_FOR_SALE\",\n" +
            "    \"isEbook\": false\n" +
            "   },\n" +
            "   \"accessInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"viewability\": \"PARTIAL\",\n" +
            "    \"embeddable\": true,\n" +
            "    \"publicDomain\": false,\n" +
            "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
            "    \"epub\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"pdf\": {\n" +
            "     \"isAvailable\": true\n" +
            "    },\n" +
            "    \"webReaderLink\": \"http://play.google.com/books/reader?id=5BGBswAQSiEC&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "    \"accessViewStatus\": \"SAMPLE\",\n" +
            "    \"quoteSharingAllowed\": false\n" +
            "   },\n" +
            "   \"searchInfo\": {\n" +
            "    \"textSnippet\": \"Presents instructions for creating Android applications for mobile devices using Java.\"\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"books#volume\",\n" +
            "   \"id\": \"V-gtAgAAQBAJ\",\n" +
            "   \"etag\": \"xW159PuLvhU\",\n" +
            "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/V-gtAgAAQBAJ\",\n" +
            "   \"volumeInfo\": {\n" +
            "    \"title\": \"Voice Application Development for Android\",\n" +
            "    \"authors\": [\n" +
            "     \"Michael F. McTear\",\n" +
            "     \"Zoraida Callejas\"\n" +
            "    ],\n" +
            "    \"publisher\": \"Packt Publishing Ltd\",\n" +
            "    \"publishedDate\": \"2013-12-11\",\n" +
            "    \"description\": \"This book will give beginners an introduction to building voice-based applications on Android. It will begin by covering the basic concepts and will build up to creating a voice-based personal assistant. By the end of this book, you should be in a position to create your own voice-based applications on Android from scratch in next to no time.Voice Application Development for Android is for all those who are interested in speech technology and for those who, as owners of Android devices, are keen to experiment with developing voice apps for their devices. It will also be useful as a starting point for professionals who are experienced in Android application development but who are not familiar with speech technologies and the development of voice user interfaces. Some background in programming in general, particularly in Java, is assumed.\",\n" +
            "    \"industryIdentifiers\": [\n" +
            "     {\n" +
            "      \"type\": \"ISBN_13\",\n" +
            "      \"identifier\": \"9781783285303\"\n" +
            "     },\n" +
            "     {\n" +
            "      \"type\": \"ISBN_10\",\n" +
            "      \"identifier\": \"1783285303\"\n" +
            "     }\n" +
            "    ],\n" +
            "    \"readingModes\": {\n" +
            "     \"text\": true,\n" +
            "     \"image\": true\n" +
            "    },\n" +
            "    \"pageCount\": 134,\n" +
            "    \"printType\": \"BOOK\",\n" +
            "    \"categories\": [\n" +
            "     \"Computers\"\n" +
            "    ],\n" +
            "    \"maturityRating\": \"NOT_MATURE\",\n" +
            "    \"allowAnonLogging\": true,\n" +
            "    \"contentVersion\": \"1.3.4.0.preview.3\",\n" +
            "    \"panelizationSummary\": {\n" +
            "     \"containsEpubBubbles\": false,\n" +
            "     \"containsImageBubbles\": false\n" +
            "    },\n" +
            "    \"imageLinks\": {\n" +
            "     \"smallThumbnail\": \"http://books.google.com/books/content?id=V-gtAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "     \"thumbnail\": \"http://books.google.com/books/content?id=V-gtAgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "    },\n" +
            "    \"language\": \"en\",\n" +
            "    \"previewLink\": \"http://books.google.com.eg/books?id=V-gtAgAAQBAJ&printsec=frontcover&dq=android&hl=&cd=3&source=gbs_api\",\n" +
            "    \"infoLink\": \"https://play.google.com/store/books/details?id=V-gtAgAAQBAJ&source=gbs_api\",\n" +
            "    \"canonicalVolumeLink\": \"https://play.google.com/store/books/details?id=V-gtAgAAQBAJ\"\n" +
            "   },\n" +
            "   \"saleInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"saleability\": \"FOR_SALE\",\n" +
            "    \"isEbook\": true,\n" +
            "    \"listPrice\": {\n" +
            "     \"amount\": 346.33,\n" +
            "     \"currencyCode\": \"EGP\"\n" +
            "    },\n" +
            "    \"retailPrice\": {\n" +
            "     \"amount\": 346.33,\n" +
            "     \"currencyCode\": \"EGP\"\n" +
            "    },\n" +
            "    \"buyLink\": \"https://play.google.com/store/books/details?id=V-gtAgAAQBAJ&rdid=book-V-gtAgAAQBAJ&rdot=1&source=gbs_api\",\n" +
            "    \"offers\": [\n" +
            "     {\n" +
            "      \"finskyOfferType\": 1,\n" +
            "      \"listPrice\": {\n" +
            "       \"amountInMicros\": 3.4633E8,\n" +
            "       \"currencyCode\": \"EGP\"\n" +
            "      },\n" +
            "      \"retailPrice\": {\n" +
            "       \"amountInMicros\": 3.4633E8,\n" +
            "       \"currencyCode\": \"EGP\"\n" +
            "      }\n" +
            "     }\n" +
            "    ]\n" +
            "   },\n" +
            "   \"accessInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"viewability\": \"PARTIAL\",\n" +
            "    \"embeddable\": true,\n" +
            "    \"publicDomain\": false,\n" +
            "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
            "    \"epub\": {\n" +
            "     \"isAvailable\": true\n" +
            "    },\n" +
            "    \"pdf\": {\n" +
            "     \"isAvailable\": true\n" +
            "    },\n" +
            "    \"webReaderLink\": \"http://play.google.com/books/reader?id=V-gtAgAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "    \"accessViewStatus\": \"SAMPLE\",\n" +
            "    \"quoteSharingAllowed\": false\n" +
            "   },\n" +
            "   \"searchInfo\": {\n" +
            "    \"textSnippet\": \"This book will give beginners an introduction to building voice-based applications on Android.\"\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"books#volume\",\n" +
            "   \"id\": \"3aRNBgAAQBAJ\",\n" +
            "   \"etag\": \"REOuL+4SleU\",\n" +
            "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/3aRNBgAAQBAJ\",\n" +
            "   \"volumeInfo\": {\n" +
            "    \"title\": \"Android Stealth Hacking السرقة المتخفيه للأندرويد\",\n" +
            "    \"subtitle\": \"Arabic\",\n" +
            "    \"authors\": [\n" +
            "     \"حسن بدران\"\n" +
            "    ],\n" +
            "    \"publisher\": \"Hasan Badran حسن بدران\",\n" +
            "    \"publishedDate\": \"2015-01-21\",\n" +
            "    \"description\": \".السرقه المخفيه لأجهزة الأندرويد هذا الكتاب يشرح كيف يقوم المبرمجين بسرقة جميع معلومات جهازك و أيضا فيه شرح أخطر الهجمات على نظام الأندرويد و كيفية تطبيقها و يحتوي أيضا على نصائح كثيره و تنفع القارئ كثيرا.\",\n" +
            "    \"readingModes\": {\n" +
            "     \"text\": false,\n" +
            "     \"image\": true\n" +
            "    },\n" +
            "    \"pageCount\": 51,\n" +
            "    \"printType\": \"BOOK\",\n" +
            "    \"maturityRating\": \"NOT_MATURE\",\n" +
            "    \"allowAnonLogging\": false,\n" +
            "    \"contentVersion\": \"preview-1.0.0\",\n" +
            "    \"imageLinks\": {\n" +
            "     \"smallThumbnail\": \"http://books.google.com/books/content?id=3aRNBgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "     \"thumbnail\": \"http://books.google.com/books/content?id=3aRNBgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "    },\n" +
            "    \"language\": \"ar\",\n" +
            "    \"previewLink\": \"http://books.google.com.eg/books?id=3aRNBgAAQBAJ&pg=PA4&dq=android&hl=&cd=4&source=gbs_api\",\n" +
            "    \"infoLink\": \"http://books.google.com.eg/books?id=3aRNBgAAQBAJ&dq=android&hl=&source=gbs_api\",\n" +
            "    \"canonicalVolumeLink\": \"https://books.google.com/books/about/Android_Stealth_Hacking_%D8%A7%D9%84%D8%B3%D8%B1%D9%82%D8%A9_%D8%A7.html?hl=&id=3aRNBgAAQBAJ\"\n" +
            "   },\n" +
            "   \"saleInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"saleability\": \"NOT_FOR_SALE\",\n" +
            "    \"isEbook\": false\n" +
            "   },\n" +
            "   \"accessInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"viewability\": \"PARTIAL\",\n" +
            "    \"embeddable\": true,\n" +
            "    \"publicDomain\": false,\n" +
            "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
            "    \"epub\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"pdf\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"webReaderLink\": \"http://play.google.com/books/reader?id=3aRNBgAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "    \"accessViewStatus\": \"SAMPLE\",\n" +
            "    \"quoteSharingAllowed\": false\n" +
            "   },\n" +
            "   \"searchInfo\": {\n" +
            "    \"textSnippet\": \"ForEducationalPurposes Only. إصدارات \\u003cb\\u003eأندرويد\\u003c/b\\u003e :: - 3 الإصدار 1.5 من \\u003cb\\u003eأندرويد\\u003c/b\\u003e : \\u003cbr\\u003e\\nCupcake الإصدار 1.6 من \\u003cb\\u003eأندرويد\\u003c/b\\u003e :Donut الإصداران 2.0 و2.1 من \\u003cb\\u003eأندرويد\\u003c/b\\u003e: Eclair الإصدار \\u003cbr\\u003e\\n2.2 من \\u003cb\\u003eأندرويد\\u003c/b\\u003e: (Frozen Yogurt (Froyo الإصدار 2.3 من \\u003cb\\u003eأندرويد\\u003c/b\\u003e: Gingerbread الإصدارات \\u003cbr\\u003e\\n3.0 و3.1&nbsp;...\"\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"books#volume\",\n" +
            "   \"id\": \"bmJIl_wPgQsC\",\n" +
            "   \"etag\": \"UXF3q8UGlk0\",\n" +
            "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/bmJIl_wPgQsC\",\n" +
            "   \"volumeInfo\": {\n" +
            "    \"title\": \"Professional Android 4 Application Development\",\n" +
            "    \"authors\": [\n" +
            "     \"Reto Meier\"\n" +
            "    ],\n" +
            "    \"publisher\": \"John Wiley & Sons\",\n" +
            "    \"publishedDate\": \"2012-05-01\",\n" +
            "    \"description\": \"Provides information on using Android 3 to build and enhance mobile applications, covering such topics as creating user interfaces, using intents, databases, creating and controlling services, creating app widgets, playing audio and video, telphony, and using sensors. Original.\",\n" +
            "    \"industryIdentifiers\": [\n" +
            "     {\n" +
            "      \"type\": \"ISBN_13\",\n" +
            "      \"identifier\": \"9781118102275\"\n" +
            "     },\n" +
            "     {\n" +
            "      \"type\": \"ISBN_10\",\n" +
            "      \"identifier\": \"1118102274\"\n" +
            "     }\n" +
            "    ],\n" +
            "    \"readingModes\": {\n" +
            "     \"text\": false,\n" +
            "     \"image\": true\n" +
            "    },\n" +
            "    \"pageCount\": 817,\n" +
            "    \"printType\": \"BOOK\",\n" +
            "    \"categories\": [\n" +
            "     \"Computers\"\n" +
            "    ],\n" +
            "    \"averageRating\": 3.5,\n" +
            "    \"ratingsCount\": 3,\n" +
            "    \"maturityRating\": \"NOT_MATURE\",\n" +
            "    \"allowAnonLogging\": false,\n" +
            "    \"contentVersion\": \"0.9.0.0.preview.1\",\n" +
            "    \"imageLinks\": {\n" +
            "     \"smallThumbnail\": \"http://books.google.com/books/content?id=bmJIl_wPgQsC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "     \"thumbnail\": \"http://books.google.com/books/content?id=bmJIl_wPgQsC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "    },\n" +
            "    \"language\": \"en\",\n" +
            "    \"previewLink\": \"http://books.google.com.eg/books?id=bmJIl_wPgQsC&printsec=frontcover&dq=android&hl=&cd=5&source=gbs_api\",\n" +
            "    \"infoLink\": \"http://books.google.com.eg/books?id=bmJIl_wPgQsC&dq=android&hl=&source=gbs_api\",\n" +
            "    \"canonicalVolumeLink\": \"https://books.google.com/books/about/Professional_Android_4_Application_Devel.html?hl=&id=bmJIl_wPgQsC\"\n" +
            "   },\n" +
            "   \"saleInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"saleability\": \"NOT_FOR_SALE\",\n" +
            "    \"isEbook\": false\n" +
            "   },\n" +
            "   \"accessInfo\": {\n" +
            "    \"country\": \"EG\",\n" +
            "    \"viewability\": \"PARTIAL\",\n" +
            "    \"embeddable\": true,\n" +
            "    \"publicDomain\": false,\n" +
            "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
            "    \"epub\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"pdf\": {\n" +
            "     \"isAvailable\": false\n" +
            "    },\n" +
            "    \"webReaderLink\": \"http://play.google.com/books/reader?id=bmJIl_wPgQsC&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "    \"accessViewStatus\": \"SAMPLE\",\n" +
            "    \"quoteSharingAllowed\": false\n" +
            "   },\n" +
            "   \"searchInfo\": {\n" +
            "    \"textSnippet\": \"Professional Android 4 Application Development: Provides an in-depth look at the Android application components and their lifecycles Explores Android UI metaphors, design philosophies, and UI APIs to create compelling user interfaces for ...\"\n" +
            "   }\n" +
            "  }\n" +
            " ]\n" +
            "}";
    
    /**
     * Create a private constructor because no on should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils()
    {
    
    }
    
    /**
     * Return a list of {@link Book} objects that has been built up from parsing a JSON response.
     *
     * @return
     */
    public static ArrayList< Book > extractBooks()
    {
        // Create an empty ArrayList that we can start adding books to
        ArrayList< Book > books = new ArrayList<>();
        
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {
            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject( SAMPLE_JSON_RESPONSE );
            
            // Extract the JSONArray associated with the key called "items",
            // with represents a list of items (or books).
            JSONArray bookArray = baseJsonResponse.getJSONArray( "items" );
            
            // For each book in the bookArray, create a {@link Book} object.
            for ( int i = 0; i < bookArray.length(); i++ )
            {
                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject( i );
                
                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a title, author and
                // average rate for that book (current book)
                JSONObject volumeInfo = currentBook.getJSONObject( "volumeInfo" );
                
                // Extract the value for the key called "title" (the book title).
                String title = volumeInfo.getString( "title" );
                
                // Extract the value for the key called "authors" (the book authors).
                String authors = getAuthors( volumeInfo );
                
                // Extract the value for the key called "averageRating" (the book authors).
                double averageRating = volumeInfo.getDouble( "averageRating" );
                
                // For a given book, extract the JSONObject associated with the
                // key called "saleInfo" and "retailPrice",
                // which represents price for that book (current book).
                JSONObject saleInfo = currentBook.getJSONObject( "saleInfo" );
                JSONObject retailPrice = saleInfo.getJSONObject( "retailPrice" );
                
                // Extract the value for the key called "amount" (the book price).
                double amount = retailPrice.getDouble( "amount" );
                
                // Extract the value for the key called "currencyCode" (the book local currency).
                String currencyCode = retailPrice.getString( "currencyCode" );
                
                // combine the price value (amount) with the local currency acronym (currencyCode)
                String localPrice = currencyCode + "  " + amount;
                
                // Create a new {@link Book} object with the title, author, average rate and price
                // from the JSON response.
                Book book = new Book( title, authors, averageRating, localPrice );
                
                // Add the new {@link Book} to the list of books.
                books.add( book );
            }
        }
        catch ( JSONException jsonException )
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. print a log message
            // with the message from the exception.
            Log.e( "QueryUtils", "Problem parsing the book JSON results", jsonException );
            jsonException.printStackTrace();
        }
        
        // Return the list of books
        return books;
    }
    
    // Return comma separated author list when there is more than one author
    private static String getAuthors( JSONObject jsonObject )
    {
        try
        {
            JSONArray authors = jsonObject.getJSONArray( "authors" );
            int authorsNumber = authors.length();
            String[] authorStrings = new String[ authorsNumber ];
            for ( int i = 0; i < authorsNumber; i++ )
                authorStrings[ i ] = authors.getString( i );
            
            return TextUtils.join( ", ", authorStrings );
        }
        catch ( JSONException jsonException )
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. print a log message
            // with the message from the exception.
            Log.e( "QueryUtils.getAuthors()", "Problem parsing the book JSON results", jsonException );
            jsonException.printStackTrace();
            return "";
        }
    }
}