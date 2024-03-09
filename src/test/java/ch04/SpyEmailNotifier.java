package ch04;

public class SpyEmailNotifier implements EmailNotifier{
    private boolean called;
    private String email;

    public SpyEmailNotifier() {
    }

    public boolean getCalled() {
        return called;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void sendRegisterEmail(String email) {
        this.called = true;
        this.email = email;
    }
}
