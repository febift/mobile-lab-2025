package com.example.instagram;

import android.os.Build;

import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedRepository {
    private static List<FeedModel> feedList;

    private static int postCount = 1;


    public static void addFeed(FeedModel feed) {
        if (feedList == null) {
            feedList = new ArrayList<>();
        }
        feedList.add(0, feed); // Tambahkan di posisi paling atas
    }

    public static void setFeedList(List<FeedModel> list) {
        if (list == null) {
            feedList = new ArrayList<>();
        } else {
            feedList = list;
        }
    }

    public static int getPostCount(){
        return postCount++;
    }

    public static List<FeedModel> getFeedList() {
        if (feedList == null) {
            feedList = new ArrayList<>();

            List<PhotoModel> ningning1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/56/c6/6c/56c66cd97aa6b321f9406a57ccaac9bd.jpg")
            );

            List<PhotoModel> ningning2 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/e2/b1/bc/e2b1bca70b1a9da4b656fe8a43539195.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/d3/fd/d6/d3fdd69bd355860a3dafeefec9a950c9.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/f9/a9/83/f9a9832a59e4a393fbca661c086a011d.jpg")
            );

            List<PhotoModel> chiquita1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/4b/2b/e1/4b2be1cf3be9251cf9348de7beeb3253.jpg")
            );

            List<PhotoModel> IU1 = List.of(
                    new PhotoModel("https://i.pinimg.com/736x/b9/ec/18/b9ec18610e8cbbb27226c0745a01f5ce.jpg")
            );

            List<PhotoModel> dk1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/75/ae/d3/75aed3f8a0163a3d86256c0ec38634f1.jpg")
            );

            List<PhotoModel> dk2 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/00/f2/a2/00f2a27d9a1ad8dab16c064d94c4f158.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/34/2e/bd/342ebd1b3ba36acbcde1b8ffce350429.jpg")
            );

            List<PhotoModel> dk3 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/92/de/20/92de208bf89ea588928f052a7656d7ae.jpg")
            );

            List<PhotoModel> winter1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/a1/fd/99/a1fd990a7ee456a0a011beb62b6fcbe0.jpg")
            );

            List<PhotoModel> hoshi1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/5f/c5/64/5fc5642fd16c3503001dd07903412260.jpg")
            );

            List<PhotoModel> highlightIU = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/25/fd/e8/25fde8a3cc1e81c6aea39fb84077ce7e.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/c2/d6/80/c2d680d5bab0d726c0978206958c4e59.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/74/4e/00/744e005f3dee6ce872dfc0ffb022d9f9.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/3f/f9/d6/3ff9d62f138e950ec912e89ec773027a.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/22/46/ae/2246aefbc21c2d63927f13f7656c02e2.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/a1/04/2f/a1042f60c48f831b6c6a72e2fe497350.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/b5/e3/b5/b5e3b5bd2e8037872bc1119621e728f0.jpg")
            );

            List<PhotoModel> highlightNingning = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/78/8f/83/788f83700fcc1eb9d9d5e0dd152bf702.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/89/44/65/8944654070b767f132d9c0174aa71648.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/0b/1b/cb/0b1bcb35338deeb1531c137a359022de.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/56/c6/6c/56c66cd97aa6b321f9406a57ccaac9bd.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/0f/e3/b5/0fe3b5b3c3f69700570f681fcd33f163.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/a2/1a/11/a21a11ae9ecacf26568803b8daafd639.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/55/45/b8/5545b843015e8e2f1e1c8bb5e585f45e.jpg")
            );

            List<PhotoModel> highlightChiquita = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/8c/c0/9f/8cc09fef03ef4a67618ce15034ddab54.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/1b/62/18/1b6218eb5bc9e348a86c4c8f05fe008c.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/85/98/62/85986247988d76268e004f97c5b8cfd2.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/d9/66/b2/d966b27d33ff49992f0fdab84a3099c4.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/38/75/0b/38750b1c1e66cb1d67ac3bcb79d06810.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/ad/a5/9a/ada59a76a5f22eff49775c73b428c3bb.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/41/19/a5/4119a567a13ce93682303852fbd388be.jpg")
            );

            List<PhotoModel> highlightDK = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/b3/05/b7/b305b77311be8d46cc1278de1155512d.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/6c/87/e1/6c87e1c0f386dc571f1048f22029fbfe.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/f8/83/af/f883aff7b8bbbafa7133dbb01842c733.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/cd/bc/cd/cdbccd297db3342334d7ef1440c9b6e3.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/d4/6a/d3/d46ad365135e5c2bb0e729d9ae79feab.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/5e/c7/8d/5ec78dba29fd0d561a2a4779feefc1bc.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/cc/3b/e9/cc3be9ae4297c2cdd781709d4c04019f.jpg")
            );

            List<PhotoModel> highlightWinter = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/d1/64/43/d164438ee3ba9eb3e3ae17a79b439603.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/09/dc/e2/09dce20d4c27dc4563b1c3e4c471e6c6.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/b1/f5/d6/b1f5d6dc40be76feb9a86d608ec5e215.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/6c/38/2d/6c382de13923507a4cc08ff9797f10c0.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/10/37/be/1037be2ccf8c013883f2599253e458bd.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/ef/05/8d/ef058d50a21f7141a00eba7d33ea50bc.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/b2/45/54/b245540868aeedc41fae0c6796cbe79a.jpg")
            );

            List<PhotoModel> highlightHoshi= Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/df/db/18/dfdb18120e5294440e67a518d4eb4eff.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/a3/9e/e2/a39ee2542dde58518eee380a47cd7af2.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/f5/d5/25/f5d525b6699a90dfda3b73246667b853.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/27/c8/72/27c872cbdfe0dfcaf7cf9ca204097ca3.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/76/47/55/764755b607625a1a194f321474077b3f.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/dc/1b/13/dc1b13b975e1ae3b1df1dba7f0585593.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/54/88/04/548804826943c57af00fbc60d2ae11d8.jpg")
            );

            feedList.add(new FeedModel("https://i.pinimg.com/736x/b6/69/92/b66992f2a9e0e4fca358d221428bd0f7.jpg", "imnotningning", ningning1, "", 2039812, 8346, "NINGNING\n" +
                    "aespa",
                    "Aespa\uD83D\uDE38",
                    "https://i.pinimg.com/736x/b6/69/92/b66992f2a9e0e4fca358d221428bd0f7.jpg",
                    highlightNingning,
                    2,
                    12613714,
                    4
            ));
            feedList.add(new FeedModel(R.drawable.chiqiicon, "chiqipham", chiquita1, "baby monster", 24096, 117, "Chiquita\n",
                        "foto",
                        R.drawable.chiqiicon,
                        highlightChiquita,
                        1,
                        108827,
                        367
                ));

                feedList.add(new FeedModel(R.drawable.ningicon, "imnotningning", ningning2, "ninggg", 2039812, 8346, "NINGNING\n" +
                        "aespa",
                        "Aespa\uD83D\uDE38",
                        R.drawable.ningninghg,
                        highlightNingning,
                        1,
                        12613714,
                        4));

                feedList.add(new FeedModel(R.drawable.iuicon, "drwlxx", IU1, "Love Wins ALl", 31515, 3760, "Listen to Love Wins ALl \n",
                        "HD Pap",
                        R.drawable.iuhighlight,
                        highlightIU,
                        1,
                        107488,
                        982
                ));

                feedList.add(new FeedModel(R.drawable.dkchill, "dokyeom_", dk1, "THE DK" +
                        "SEVENTEEN\n",
                        3000,
                        1200,
                        "wow",
                        "Q/A HIGHLIGHT",
                        R.drawable.dkhg,
                        highlightDK,
                        3,
                        313147,
                        179));

                feedList.add(new FeedModel(R.drawable.dkchill, "dokyeom_", dk2, "THE DK\n" +
                        "SEVENTEEN\n",
                        3000,
                        1200,
                        "wow",
                        "Q/A HIGHLIGHT",
                        R.drawable.dkhg,
                        highlightDK,
                        3,
                        313147,
                        179));

                feedList.add(new FeedModel(R.drawable.dkchill, "dokyeom", dk3, "dk",
                        11101,
                        24,
                        "wow\n" +
                        "SEVENTEEN\n",
                        "Q/A HIGHLIGHT",
                        R.drawable.dkhg,
                        highlightDK,
                        3,
                        313147,
                        179));


                feedList.add(new FeedModel(R.drawable.wintericon, "imwinter", winter1, "minjeong\n",
                        710339,
                        10800,
                        "wow",
                        "Q/A HIGHLIGHT",
                        R.drawable.hgwinter,
                        highlightWinter,
                        1,
                        6470781,
                        420
                ));

                feedList.add(new FeedModel(R.drawable.hoshiselfi, "hoshiirwar", hoshi1, "harimau \uD83C\uDDEE\uD83C\uDDE9\uD83C\uDDF2\uD83C\uDDFE\uD83C\uDDF5\uD83C\uDDED\uD83C\uDDF0\uD83C\uDDED\uD83C\uDDF8\uD83C\uDDEC\uD83C\uDDF9\uD83C\uDDED\uD83C\uDDFB\uD83C\uDDF3\uD83C\uDDF1\uD83C\uDDE6\uD83C\uDDE7\uD83C\uDDF3\uD83C\uDDF9\uD83C\uDDF1\uD83C\uDDF2\uD83C\uDDF2",
                        490688,
                        99200,
                        "wowww\n",
                        "CHINA!",
                        R.drawable.hoshihg,
                        highlightHoshi,
                        1,
                        33909686,
                        398
                ));
        }
        return feedList;
    }
}
