package com.revature.pn.common.util;

import com.revature.pn.users.UserResponse;

public class SecurityUtils {

    public static boolean isAdmin(UserResponse subject) {
        return subject.getRole().equals("ADMIN");
    }

    public static boolean isFinanceManager(UserResponse subject) {
        return subject.getRole().equals("FINANCE MANAGER");
    }

    // Only to be used with GET user requests
    public static boolean requesterOwned(UserResponse subject, String resourceId) {
        if(resourceId != null) {
            return subject.getUserId().equals(resourceId);
        }
        else {
            return false;
        }
    }

   /* public static boolean isLead(UserResponse subject) {
        return subject.getRole().equals("LEAD");
    }
    public static boolean isEmployee(UserResponse subject) {
        return subject.getRole().equals("EMPLOYEE");
   }

    */


}
