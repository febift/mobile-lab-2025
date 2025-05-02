package com.example.satugram;

import java.util.ArrayList;
import java.util.List;

public class StoryDataProvider {

    /**
     * Creates a list of dummy story data for testing/demo purposes
     * @return List of Story objects with dummy data
     */
    public static List<Story> getDummyStories() {
        List<Story> stories = new ArrayList<>();

        // Add dummy stories (10 items)
        stories.add(new Story("1", "Your Story", "https://i.pinimg.com/736x/43/c8/d1/43c8d1f9482551a5fb3f2f334184d085.jpg", true));
        stories.add(new Story("2", "John_Smith", "https://randomuser.me/api/portraits/men/1.jpg", true));
        stories.add(new Story("3", "Emma_Watson", "https://randomuser.me/api/portraits/women/4.jpg", false));
        stories.add(new Story("4", "Michael_B", "https://randomuser.me/api/portraits/men/3.jpg", true));
        stories.add(new Story("5", "Sophia_J", "https://randomuser.me/api/portraits/women/6.jpg", false));
        stories.add(new Story("6", "James_K", "https://randomuser.me/api/portraits/men/4.jpg", true));
        stories.add(new Story("7", "Olivia_P", "https://randomuser.me/api/portraits/women/5.jpg", false));
        stories.add(new Story("8", "William_H", "https://randomuser.me/api/portraits/men/6.jpg", true));
        stories.add(new Story("9", "Ava_Wilson", "https://randomuser.me/api/portraits/women/8.jpg", false));
        stories.add(new Story("10", "Daniel_T", "https://randomuser.me/api/portraits/men/11.jpg", true));

        return stories;
    }

    /**
     * Get a specific story by ID
     * @param id The ID of the story to retrieve
     * @return The Story object if found, null otherwise
     */
    public static Story getStoryById(String id) {
        for (Story story : getDummyStories()) {
            if (story.getId().equals(id)) {
                return story;
            }
        }
        return null;
    }
}