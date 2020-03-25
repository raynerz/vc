package ch.dappen.vc.support;

public class MoodChangingHelper {
    private final IMoodController moodController;

    public MoodChangingHelper(IMoodController moodController) {
        this.moodController = moodController;
    }

    public IMoodController getMoodController() {
        return moodController;
    }
}
