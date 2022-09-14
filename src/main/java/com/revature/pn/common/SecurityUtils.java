package com.revature.pn.common;

import com.revature.pn.users.UserResponse;

public class SecurityUtils {

    public static boolean isDirector(UserResponse subject) {
        return subject.getRole().equals("DIRECTOR");
    }

    // Only to be used with GET user requests
    public static boolean requesterOwned(UserResponse subject, String resourceId) {
        return subject.getId().equals(resourceId);
    }
}
