package cn.rabbbbbit.week08.cache;

public final class RedisKeyGroups {

    private RedisKeyGroups() {
    }

    public static String userProfile(String prefix, long userId) {
        return prefix + ":user:" + userId + ":profile";
    }

    public static String userVisitCount(String prefix, long userId) {
        return prefix + ":user:" + userId + ":visit-count";
    }

    public static String sessionToken(String prefix, String tokenId) {
        return prefix + ":session:" + tokenId;
    }
}
