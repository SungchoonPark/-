package ch02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String password) {
        if (password == null || password.isEmpty()) return PasswordStrength.INVALID;

        int metCounts = getMetCriteriaCounts(password);

        if (metCounts <= 1) return PasswordStrength.WEAK;
        if (metCounts == 2) return PasswordStrength.NORMAL;
        return PasswordStrength.STRONG;
    }

    private static int getMetCriteriaCounts(String password) {
        int metCounts = 0;
        if (password.length() >= 8) metCounts++;
        if (meetsContainingNumberCriteria(password)) metCounts++;
        if (meetsContainingUppercaseCriteria(password)) metCounts++;
        return metCounts;
    }

    private static boolean meetsContainingNumberCriteria(String password) {
        boolean isContainNums = false;
        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9') {
                isContainNums = true;
                break;
            }
        }
        return isContainNums;
    }

    private static boolean meetsContainingUppercaseCriteria(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
}
