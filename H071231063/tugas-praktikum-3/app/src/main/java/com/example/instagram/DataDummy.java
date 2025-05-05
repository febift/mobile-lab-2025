package com.example.instagram;


import java.util.ArrayList;

public class DataDummy {
    public static ArrayList<Feed> feeds = generateDummyFeeds();
    public static ArrayList<FeedProfile> feedProfiles = generateDummyFeedProfiles();
    public static ArrayList<Story> stories = generateDummyStory();

    private static ArrayList<Feed> generateDummyFeeds() {
        ArrayList<Feed> feeds = new ArrayList<>();

        feeds.add(new Feed("cartoonnetworkofficial", "Cartoon Network","4M","929","TV network\n" +
                "\uD83C\uDFC1 Official #CartoonNetwork IG\n" +
                "\uD83D\uDCAC Be respectful and kind in our comments pls\n" +
                "\uD83C\uDFDD Animal Crossing\n" +
                "Creator: MA-1337-4885-1468\n" +
                "Dream: DA-1304-8404-3554", "470k","5.647","105k","We're honoring #BlackHistoryMonth and celebrating the Black lives and voices that changed the world \uD83D\uDDA4 Let’s uplift Black excellence, reflect on the past, and continue to create a future where every story is heard.", R.drawable.cn1, R.drawable.cn2,"February 1"));
        feeds.add(new Feed("naver_webtoon", "네이버웹툰","216k","6","@naver_webtoon","617","7","58", "완결웹툰 완전무료, #완완무\n" +
                "\n" +
                "1차 이벤트 기간이 얼마 남지 않았다는 소식 \uD83E\uDD79\n" +
                "\n" +
                "지금 네이버웹툰에서 ‘완완무’ 검색하고 전 회차 무료로 감상하러 가기 \uD83D\uDC9A", R.drawable.naver1, R.drawable.naver2,"December 18, 2024"));
        feeds.add(new Feed("ishowspeed", "IShowSpeed","34.2M","399","Video creator\n" +
                "@cristiano @monkeydluffy\n" +
                "youtube.com/@IShowSpeed","5.5M","25.1k","83.3k",  "STREAMER OF THE YEAR.", R.drawable.speed1, R.drawable.speed2,"December 8, 2024"));
        feeds.add(new Feed("mrbeast", "MrBeast","67.8M","679","MrBeast\n" +
                "I make videos sometimes\n" +
                "beastgames.com","985k","5.198","17.6k", "English vs Spanish \uD83C\uDFC0\n" +
                "Pregame stream is live on Fede’s channel! GET IN HERE AND REP AMERICA", R.drawable.mrbeast1, R.drawable.mrbeast2, "April 25"));
        feeds.add(new Feed("guinnessworldrecords", "Guinness World Records","10.9M","1.667","Brand\n" +
                "The global authority on record breaking achievements since 1955 \uD83C\uDF0D","1.669","36","34", "Can’t believe KIDZ BOP achieved the Guinness World Records title for the youngest music performance ensemble to headline an arena yesterday! \uD83E\uDD29\n" +
                "\n" +
                "The US #KIDZBOPLIVE Certified BOP Tour kicks off June 14! Maybe we’ll break another record this summer?! \uD83C\uDF89\n" +
                "#GuinnessWorldRecords #RecordBreaking #KIDZBOP", R.drawable.recor1, R.drawable.recor2,"March 9"));
        feeds.add(new Feed("harvard", "Harvard University","2.6M","163","Education\n" +
                "Sharing photos of #Harvard on campus and around the world.\n" +
                "02138\n", "180k","4.936","29.8k"," I just wanted to express my feelings", R.drawable.havard1, R.drawable.havard2,"April 15"));
        feeds.add(new Feed("spotify", "Spotify","12.9M","2.142","my bank account hates to see \"new concert announced\" coming","37.7k","1.539","3.606","What do you have on repeat right now? \uD83D\uDD01 Here’s the most-streamed music this week! \uD83C\uDFB6", R.drawable.spotify1, R.drawable.spotify2,"April 29"));
        feeds.add(new Feed("disney", "Disney","40.4","97","When you wish upon a star your dreams come true ✨\n" +
                "Follow #DisneyWeekOfWishes & learn how you can become a WishMaker for Make-A-Wish at wish.org/Disney","74.4k","170","2.536","The earth is a magical place! Discover how the beauty of your favorite Disney movies were inspired by these real-life locations.\n" +
                "\n" +
                "Stream the #ourHOME collection to celebrate Earth Month on @DisneyPlus.", R.drawable.disney1, R.drawable.disney2,"May 1"));
        feeds.add(new Feed("health", "human fact","886k","945","Health & wellness website\n" +
                "Your place for evidence-based, actionable health and wellness info.\n" +
                "Click on link in bio for more ⬇\uFE0F","2.789","1.350","30", "surprising human body facts you probably didn t know", R.drawable.health1, R.drawable.health2,"July 21, 2024"));
        feeds.add(new Feed("netflix ", "netflix.US","36.6","1.333","✨the taco of success always drips with the salsa of failure ✨- @danibowman1\n","286k","719","27k","whos dying to win? the trailer for FEAR STREET: PROM QUEEN premieres tomorrow!", R.drawable.netflix1, R.drawable.netflix2,"April 28"));

        return feeds;
    }

    private static ArrayList<FeedProfile> generateDummyFeedProfiles() {
        ArrayList<FeedProfile> feedProfiles = new ArrayList<>();
        String uriBase = "android.resource://com.example.instagram/";
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.story1, "Aurora","200","150", "100"));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.story2, "Jelyfish","320","240", "200"));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.story3, "Shooting star","500","330", "350"));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.story4, "Galaxy","450","370", "320"));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.story5, "Fantasy World","270","190", "230"));
        return feedProfiles;
    }


    private static ArrayList<Story> generateDummyStory() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story("Aurora", R.drawable.story1));
        stories.add(new Story("Jellyfish", R.drawable.story2));
        stories.add(new Story("Shooting star", R.drawable.story3));
        stories.add(new Story("Galaxy", R.drawable.story4));
        stories.add(new Story("Fantasy world", R.drawable.story5));
        stories.add(new Story("Butterfly", R.drawable.story6));
        stories.add(new Story("Texture", R.drawable.story7));

        return stories;
    }
}