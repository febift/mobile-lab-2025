package com.example.satugram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighlightDataProvider {
    public static List<Highlight> getUserHighlights(String userId) {
        // In a real app, you would fetch this data from a database or API
        List<Highlight> highlights = new ArrayList<>();

        highlights.add(new Highlight(
                "h1",
                "Travel",
                "https://picsum.photos/seed/travel/200",
                Arrays.asList(
                        "https://picsum.photos/seed/travel1/500",
                        "https://picsum.photos/seed/travel2/500",
                        "https://picsum.photos/seed/travel3/500"
                )
        ));

        highlights.add(new Highlight(
                "h2",
                "Food",
                "https://picsum.photos/seed/food/200",
                Arrays.asList(
                        "https://picsum.photos/seed/food1/500",
                        "https://picsum.photos/seed/food2/500"
                )
        ));

        highlights.add(new Highlight(
                "h3",
                "Music",
                "https://picsum.photos/seed/music/200",
                Arrays.asList(
                        "https://picsum.photos/seed/music1/500",
                        "https://picsum.photos/seed/music2/500",
                        "https://picsum.photos/seed/music3/500"
                )
        ));

        highlights.add(new Highlight(
                "h4",
                "Friends",
                "https://picsum.photos/seed/friends/200",
                Arrays.asList(
                        "https://picsum.photos/seed/friends1/500",
                        "https://picsum.photos/seed/friends2/500"
                )
        ));

        highlights.add(new Highlight(
                "h5",
                "Pets",
                "https://picsum.photos/seed/pets/200",
                Arrays.asList(
                        "https://picsum.photos/seed/pets1/500",
                        "https://picsum.photos/seed/pets2/500"
                )
        ));

        highlights.add(new Highlight(
                "h6",
                "Nature",
                "https://picsum.photos/seed/nature/200",
                Arrays.asList(
                        "https://picsum.photos/seed/nature1/500",
                        "https://picsum.photos/seed/nature2/500"
                )
        ));

        highlights.add(new Highlight(
                "h7",
                "Sports",
                "https://picsum.photos/seed/sports/200",
                Arrays.asList(
                        "https://picsum.photos/seed/sports1/500",
                        "https://picsum.photos/seed/sports2/500"
                )
        ));

        return highlights;
    }
}
