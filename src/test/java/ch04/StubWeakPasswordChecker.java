package ch04;

public class StubWeakPasswordChecker implements WeakPasswordChecker{
    private boolean weak;
    public StubWeakPasswordChecker() {
    }

    public void setWeak(boolean weak) {
        this.weak = weak;
    }

    @Override
    public boolean checkPasswordWeak(String pwd) {
        return weak;
    }
}
